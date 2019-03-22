package com.textingstory.view.coverscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.textingstory.domain.usecases.FetchStory
import kotlinx.coroutines.*

class CoverScreenViewModel : ViewModel() {
    private val _viewConfig = MutableLiveData<CoverScreen.ViewConfig>()
    val viewConfig: LiveData<CoverScreen.ViewConfig> = _viewConfig

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    private val fetchStory = FetchStory()
    private var fetchStoryJob: Job? = null

    init {
        fetchStory("scavengerhunt")
    }

    private fun fetchStory(uid: String) {
        dispose()
        fetchStoryJob = CoroutineScope(Dispatchers.IO).launch {
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

    fun dispose() {
        fetchStoryJob?.cancel()
    }
}