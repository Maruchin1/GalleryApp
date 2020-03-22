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
import kotlinx.android.synthetic.main.fragment_images_list.*
import kotlinx.android.synthetic.main.item_photo.view.*

class ImagesListFragment : Fragment() {
    companion object {
        private const val PORTRAIT_COLUMNS = 2
        private const val LANDSCAPE_COLUMNS = 4
    }

    private val viewModel: ImagesViewModel by lazy {
        ViewModelProviders.of(
            requireActivity(),
            ImagesViewModel.Factory(requireContext())
        ).get(ImagesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_images_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        recycler_view.scrollToPosition(viewModel.getCurrImagePosition())
    }

    private fun setupRecyclerView() {
        val columns = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> PORTRAIT_COLUMNS
            else -> LANDSCAPE_COLUMNS
        }
        with(recycler_view) {
            adapter = ImagesListAdapter()
            layoutManager = GridLayoutManager(requireContext(), columns)
            val snapHelper = GravitySnapHelper(Gravity.TOP)
            snapHelper.attachToRecyclerView(this)
        }
    }

    inner class ImagesListAdapter : RecyclerView.Adapter<ImagesListAdapter.ImageViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ImageViewHolder {
            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_photo, parent, false)
            return ImageViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return viewModel.getImagesCount()
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
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
            exitTransition = Fade()
            requireFragmentManager().beginTransaction()
                .addSharedElement(imageView, "zoom_photo")
                .addToBackStack(null)
                .replace(R.id.fragments_frame, getImagePreviewFragment())
                .commit()
        }

        private fun getImagePreviewFragment(): Fragment {
            return ImagePreviewFragment().apply {
                sharedElementEnterTransition = PhotoTransition()
                enterTransition = Fade()
            }
        }

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}