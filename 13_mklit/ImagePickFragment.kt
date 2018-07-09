package net.yanzm.mlkitsample

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ImagePickFragment : Fragment() {

    interface ImagePickListener {
        fun onImagePicked(imageUri: Uri)
    }

    private var listener: ImagePickListener? = null
    private var imageUri: Uri? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as? ImagePickListener
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_pick, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view!!.setOnClickListener {
            AlertDialog.Builder(context!!)
                    .setItems(arrayOf("Camera", "Gallery")) { _, which ->
                        when (which) {
                            0 -> startImageCaptureIntent()
                            1 -> startGetContentIntent()
                        }
                    }
                    .show()
        }
    }

    private fun startImageCaptureIntent() {
        imageUri = null

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(context!!.packageManager) != null) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, "New Picture")
                put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
            }

            imageUri = context!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE)
        }
    }

    private fun startGetContentIntent() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = imageUri ?: return
                    imageUri = null
                    listener?.onImagePicked(uri)
                }
            }
            REQUEST_CODE_CHOOSE_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let {
                        listener?.onImagePicked(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_CAPTURE = 1
        private const val REQUEST_CODE_CHOOSE_IMAGE = 2
    }
}
