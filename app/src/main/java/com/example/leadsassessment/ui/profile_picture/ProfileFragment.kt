package com.example.leadsassessment.ui.profile_picture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.leadsassessment.R
import com.example.leadsassessment.databinding.FragmentProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.InputStream


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val GALLERY_REQ_CODE = 101


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentProfileBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.profileIcon?.setOnClickListener {
            getImageFromDevice()
        }

        binding?.editProfilePic?.setOnClickListener {
            binding?.profileIcon?.performClick()
        }

        setProfileImgUrl("https://com.example.leadsassessment/leads_corporation/profile/.jpg")
    }

    private fun setProfileImgUrl(imageUri: String?) {
        val options = RequestOptions()
            .placeholder(R.drawable.avatar)
            .circleCrop()
        binding?.profileIcon?.let { view ->
            Glide.with(view)
                .load(imageUri)
                .apply(options)
                .into(view)
        }
    }

    private fun getImageFromDevice() {
        ImagePicker.with(this)
            .cropSquare()
            .maxResultSize(200, 200)
            .compress(300)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        if (resultCode == Activity.RESULT_OK) {
            val uri = data?.data!!
            val imagePath = uri.path ?: ""
            binding?.profileIcon?.let { view ->
                Glide.with(requireContext())
                    .load(imagePath)
                    .apply(RequestOptions().placeholder(R.drawable.avatar).circleCrop())
                    .into(view)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            //context?.toast(ImagePicker.getError(data))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQ_CODE) {
            val imageStream: InputStream? = activity?.contentResolver?.openInputStream(Uri.parse(data?.data.toString()))
            imageStream?.let {
                val scImg = scaledBitmapImage(BitmapFactory.decodeStream(imageStream))
                binding?.profileIcon?.setImageDrawable(getCircularImage(context, scImg))

            }
        }
    }


    private fun scaledBitmapImage(bitmap: Bitmap): Bitmap {
        val nh = (bitmap.height.times((512.0 / bitmap.width)))
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 512, nh.toInt(), true)
        return scaledBitmap
    }

    private fun getCircularImage(context: Context?, bitmap: Bitmap): RoundedBitmapDrawable {
        val circularBitmapDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(requireContext().resources, bitmap)
        circularBitmapDrawable.isCircular = true
        return circularBitmapDrawable
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}