package com.textingstory.view.storyscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.textingstory.R
import com.textingstory.domain.model.Message
import kotlinx.android.synthetic.main.activity_story.*

class StoryScreen : AppCompatActivity() {

    private lateinit var input: Input
    private lateinit var messagesAdapter: MessagesAdapter
    private var currentMessageOrdinal = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        savedInstanceState?.let {
            currentMessageOrdinal = it.getInt(CURRENT_MESSAGE_INDEX)
        }
        intent.extras?.getParcelableArray(MESSAGES)?.let {
            input = Input(it.toList() as List<Message>)
            setupMessageList()
        }

        nextButton.setOnClickListener {
            if (currentMessageOrdinal < input.messages.size) {
                showNextMessage()
            } else {
                Toast.makeText(this, "To be continued", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNextMessage() {
        currentMessageOrdinal++
        messagesAdapter.addMessage(input.messages.first { message -> message.ordinal == currentMessageOrdinal })
        messagesRecyclerView.smoothScrollToPosition(currentMessageOrdinal)
    }

    private fun setupMessageList() {
        messagesAdapter = MessagesAdapter(input.messages.subList(0, currentMessageOrdinal))
        messagesRecyclerView.adapter = messagesAdapter
        messagesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_MESSAGE_INDEX, currentMessageOrdinal)
    }

    data class Input(val messages: List<Message>)

    companion object {
        const val MESSAGES = "messages"
        const val CURRENT_MESSAGE_INDEX = "current_message_index"

        fun newIntent(context: Context, messages: List<Message>): Intent =
            Intent(context, StoryScreen::class.java)
                .putExtra(MESSAGES, messages.sortedBy { message -> message.ordinal }.toTypedArray())
    }
}