package com.example.galleryapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.viewpager.widget.ViewPager
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class GalleryUiTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private val recyclerView: RecyclerView
        get() = activityRule.activity.findViewById(R.id.recycler_view)
    private val viewPager: ViewPager
        get() = activityRule.activity.findViewById(R.id.view_pager)

    private val recyclerViewInteraction: ViewInteraction
        get() = onView(withId(R.id.recycler_view))
    private val viewPagerInteraction: ViewInteraction
        get() = onView(withId(R.id.view_pager))

    private fun RecyclerView.itemsCount(): Int {
        return this.adapter?.itemCount ?: throw Exception("ViewPager has no adapter")
    }

    private fun ViewInteraction.clickRecyclerItem(position: Int) {
        this.perform(
            actionOnItemAtPosition<ImagesListFragment.ImagesListAdapter.ImageViewHolder>(
                position,
                ViewActions.click()
            )
        )
    }

    @Test
    fun clickFirstItem_itShouldOpenIPreview() {
        if (recyclerView.itemsCount() == 0) {
            return
        }
        val firstItemPosition = 0
        recyclerViewInteraction.clickRecyclerItem(firstItemPosition)

        viewPagerInteraction.check(matches(isDisplayed()))
    }

    @Test
    fun openFirstItem_swipeRight_shouldDisplayTheSameItem() {
        if (recyclerView.itemsCount() == 0) {
            return
        }
        val firstItemPosition = 0
        recyclerViewInteraction.clickRecyclerItem(firstItemPosition)
        viewPagerInteraction.perform(swipeRight())

        Truth.assertThat(viewPager.currentItem).isEqualTo(firstItemPosition)
    }

    @Test
    fun openFirstItem_swipeLeft_shouldDisplayNextItem() {
        if (recyclerView.itemsCount() <= 1) {
            return
        }
        val firstItemPosition = 0
        recyclerViewInteraction.clickRecyclerItem(firstItemPosition)
        viewPagerInteraction.perform(swipeLeft())

        val nextItemPosition = firstItemPosition + 1
        Truth.assertThat(viewPager.currentItem).isEqualTo(nextItemPosition)
    }

    @Test
    fun openLastItem_swipeLeft_shouldDisplayTheSameItem() {
        if (recyclerView.itemsCount() == 0) {
            return
        }
        val lastItemPosition = recyclerView.itemsCount() - 1
        recyclerViewInteraction.clickRecyclerItem(lastItemPosition)
        viewPagerInteraction.perform(swipeLeft())

        Truth.assertThat(viewPager.currentItem).isEqualTo(lastItemPosition)
    }

    @Test
    fun openLastItem_swipeRight_should_DisplayPreviousItem() {
        if (recyclerView.itemsCount() <= 1) {
            return
        }
        val lastItemPosition = recyclerView.itemsCount() - 1
        recyclerViewInteraction.clickRecyclerItem(lastItemPosition)
        viewPagerInteraction.perform(swipeRight())

        val previousItemPosition = lastItemPosition - 1
        Truth.assertThat(viewPager.currentItem).isEqualTo(previousItemPosition)
    }
}
