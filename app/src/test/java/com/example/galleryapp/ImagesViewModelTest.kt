package com.example.galleryapp

import android.net.Uri
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImagesViewModelTest {
    private val imagesMock = listOf(
        Uri.parse("file1"),
        Uri.parse("file2"),
        Uri.parse("file3"),
        Uri.parse("file4")
    )

    private lateinit var viewModel: ImagesViewModel

    @Before
    fun before() {
        viewModel = ImagesViewModel(imagesMock)
    }

    @Test
    fun getImage_with_first_position_should_return_correct_image() {
        val inPosition = 0

        val result = viewModel.getImage(inPosition)

        val expectedResult = Uri.parse("file1")
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun getImage_with_last_position_should_return_correct_image() {
        val inPosition = 3

        val result = viewModel.getImage(inPosition)

        val expectedResult = Uri.parse("file4")
        Truth.assertThat(result).isEqualTo(expectedResult)
    }

    @Test(expected = ImagesViewModel.IncorrectPosition::class)
    fun getImage_with_too_small_position_should_throw_incorrect_position() {
        val inPosition = -1

        viewModel.getImage(inPosition)
    }

    @Test(expected = ImagesViewModel.IncorrectPosition::class)
    fun getImage_with_too_big_position_should_throw_incorrect_position() {
        val inPosition = 4

        viewModel.getImage(inPosition)
    }

    @Test
    fun getImagesCount_should_return_correct_images_count() {
        val result = viewModel.getImagesCount()

        Truth.assertThat(result).isEqualTo(4)
    }

    @Test
    fun imagePosition_is_changing_correctly_to_last() {
        val inCurrPosition = 2
        val inNewPosition = 3

        viewModel.setCurrImagePosition(inCurrPosition)
        val resultBeforeChange = viewModel.getCurrImagePosition()
        viewModel.setCurrImagePosition(inNewPosition)
        val resultAfterChange = viewModel.getCurrImagePosition()

        Truth.assertThat(resultBeforeChange).isEqualTo(2)
        Truth.assertThat(resultAfterChange).isEqualTo(3)
    }

    @Test
    fun imagePosition_is_changing_correctly_to_first() {
        val inCurrPosition = 2
        val inNewPosition = 0

        viewModel.setCurrImagePosition(inCurrPosition)
        val resultBeforeChange = viewModel.getCurrImagePosition()
        viewModel.setCurrImagePosition(inNewPosition)
        val resultAfterChange = viewModel.getCurrImagePosition()

        Truth.assertThat(resultBeforeChange).isEqualTo(2)
        Truth.assertThat(resultAfterChange).isEqualTo(0)
    }

    @Test(expected = ImagesViewModel.IncorrectPosition::class)
    fun imagePosition_change_to_too_big_should_throw_incorrect_position() {
        val inNewPosition = 4

        viewModel.setCurrImagePosition(inNewPosition)
    }

    @Test(expected = ImagesViewModel.IncorrectPosition::class)
    fun imagePosition_change_to_too_small_should_throw_incorrect_position() {
        val inNewPosition = -1

        viewModel.setCurrImagePosition(inNewPosition)
    }
}