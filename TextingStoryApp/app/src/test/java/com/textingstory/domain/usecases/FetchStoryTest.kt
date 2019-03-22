package com.textingstory.domain.usecases

import com.google.common.truth.Truth
import com.textingstory.domain.model.Story
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchStoryTest {
    private lateinit var classUnderTest: FetchStory

    @Test
    fun `fetching story succeeds`() {
        //Given
        classUnderTest = FetchStory()

        //When
        var result: Story? = null
        runBlocking {
            result = classUnderTest.launch("scavengerhunt")
        }

        //Then
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.title).isEqualTo("Scavenger Hunt")
        Truth.assertThat(result?.messages?.size).isEqualTo(129)
    }
}