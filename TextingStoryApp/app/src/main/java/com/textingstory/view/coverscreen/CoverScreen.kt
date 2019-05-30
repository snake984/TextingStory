package com.textingstory.view.coverscreen

import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.textingstory.R
import com.textingstory.domain.model.Message
import com.textingstory.view.storyscreen.StoryScreen
import com.textingstory.view.util.PictureHelper
import com.textingstory.view.util.hide
import com.textingstory.view.util.show
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*


class CoverScreen : AppCompatActivity() {

    private lateinit var viewModel: CoverScreenViewModel
    private var state: ViewConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(CoverScreenViewModel::class.java)
        observeStoryFetching()

        savedInstanceState?.let {
            state = savedInstanceState?.getParcelable(STATE)
            state?.let {
                setupView(it)
            }
        }

        startButton.setOnClickListener {
            state?.messages?.let { messages ->
                startActivity(StoryScreen.newIntent(this, messages))
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE, state)
    }

    private fun observeStoryFetching() {
        viewModel.isLoading.observe(this, Observer<Boolean> {
            if (it) coverProgressBar.show()
            else coverProgressBar.hide()
        })

        viewModel.viewConfig.observe(this, Observer<ViewConfig> {
            state = it
            setupView(it)
        })

        viewModel.error.observe(this, Observer<Throwable> {
            Toast.makeText(this, "An error occured, please retry", Toast.LENGTH_LONG).show()
        })
    }

    private fun setupView(viewConfig: ViewConfig) {
        storyTitle.text = viewConfig.title
        storyDescription.text = viewConfig.description
        startButton.show()
        PictureHelper().setupImageView(this, viewConfig.coverImageUrl, coverPicture)
    }

    @Parcelize
    data class ViewConfig(
        val title: String,
        val description: String,
        val coverImageUrl: String,
        val messages: List<Message>
    ) : Parcelable

    companion object {
        const val STATE = "state"
    }
}
