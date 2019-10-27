package com.example.galleryapp

import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_photo_zoom.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoZoomFragment : Fragment() {

    private lateinit var viewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(PhotoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = PhotoPagerAdapter()
        view_pager.currentItem = viewModel.getCurrPhotoPosition()
    }

    inner class PhotoPagerAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return viewModel.getPhotosCount()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_photo, container, false)

            val photoId = viewModel.getPhotoId(position)

            Glide.with(requireActivity())
                .load(photoId)
                .into(itemView.image_view)

            itemView.image_view.transitionName = "zoom_photo"

            container.addView(itemView, 0)

            return itemView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}