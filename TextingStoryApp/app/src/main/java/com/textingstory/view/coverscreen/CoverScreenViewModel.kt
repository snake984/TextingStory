package com.textingstory.view.coverscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.textingstory.domain.usecases.FetchStory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoverScreenViewModel : ViewModel(), CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val _viewConfig = MutableLiveData<CoverScreen.ViewConfig>()
    val viewConfig: LiveData<CoverScreen.ViewConfig> = _viewConfig

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val fetchStory = FetchStory()

    init {
        fetchStory("scavengerhunt")
    }

    private fun fetchStory(uid: String) {
        launch {
            _isLoading.postValue(true)

            val story = fetchStory.launch(uid)
            _isLoading.postValue(false)

            when (story) {
                is FetchStory.Result.Success -> {
                    _viewConfig.postValue(
                        CoverScreen.ViewConfig(
                            title = story.data.title,
                            description = story.data.description,
                            coverImageUrl = story.data.coverImageUrl,
                            messages = story.data.messages
                        )
                    )
                }
                is FetchStory.Result.Error -> _error.postValue(story.exception)
            }
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}