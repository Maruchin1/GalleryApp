package com.example.galleryapp

import android.content.res.Configuration
import android.os.Bundle
import android.transition.Fade
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.android.synthetic.main.item_photo.view.*

class ImagesListFragment : Fragment() {

    private val viewModel: ImagesViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            ImagesViewModel.Factory()
        ).get(ImagesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orientation = resources.configuration.orientation
        val columnsCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

        recycler_view.adapter = PhotoListAdapter()
        recycler_view.layoutManager = GridLayoutManager(requireContext(), columnsCount)

        val snapHelper = GravitySnapHelper(Gravity.TOP)
        snapHelper.attachToRecyclerView(recycler_view)
    }

    override fun onStart() {
        super.onStart()
        recycler_view.scrollToPosition(viewModel.getCurrImagePosition())
    }

    inner class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoViewHolder {

            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_photo, parent, false)

            return PhotoViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return viewModel.getImagesCount()
        }

        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            val image = viewModel.getImage(position)

            Glide.with(requireContext())
                .load(image)
                .into(holder.itemView.image_view)

            holder.itemView.image_view.setOnClickListener { view ->
                openImage(position, view as ImageView)
            }
        }

        private fun openImage(position: Int, imageView: ImageView) {
            viewModel.setCurrImagePosition(position)

            imageView.transitionName = "list_photo$position"

            val photoZoomFragmentWithTransition = ImagePreviewFragment().apply {
                sharedElementEnterTransition = PhotoTransition()
                enterTransition = Fade()
            }

            exitTransition = Fade()

            requireFragmentManager().beginTransaction()
                .addSharedElement(imageView, "zoom_photo")
                .addToBackStack(null)
                .replace(R.id.fragments_frame, photoZoomFragmentWithTransition)
                .commit()
        }

        inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}