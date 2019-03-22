package com.textingstory.view.storyscreen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.textingstory.R
import com.textingstory.domain.model.Message
import kotlinx.android.synthetic.main.message_item_view.view.*
import kotlin.random.Random

class MessagesAdapter(messages: List<Message>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    private val messages: MutableList<Message> = ArrayList(messages)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item_view, parent, false))

    override fun getItemCount(): Int = messages.size

    override fun getItemId(position: Int): Long = messages[position].hashCode().toLong()

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) = holder.bind(messages[position])

    fun addMessage(message: Message) {
        val index = messages.size
        messages.add(index, message)
        notifyItemInserted(index)
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val senderColors = listOf(
            "#26C4B5",
            "#B175DC"
        )

        fun bind(message: Message) {
            itemView.senderNameText.text = message.sender.name
            val senderColor = Color.parseColor(senderColors.random(Random(message.sender.hashCode() + 1)))
            itemView.senderNameText.setTextColor(senderColor)
            itemView.messageText.text = message.text
        }
    }
}