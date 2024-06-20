package com.ekta.kotlin.roomdb.todolist.Util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo
import com.ekta.kotlin.roomdb.todolist.databinding.DialogAddDataBinding
import java.io.ByteArrayOutputStream

class AddDataDailog(
    context: Context,
    dialogCallback: DialogCallback,
    from: Boolean,
    personInfo: PersonInfo
) :
    DialogFragment() {

    lateinit var binding: DialogAddDataBinding


    @get:JvmName("context")
    var context: Context? = null
    var dialogCallback: DialogCallback
    var isupdate: Boolean
    var personInfo: PersonInfo
    lateinit var imageuri: Uri
    lateinit var vProfileImage: String


    init {
        this.isupdate = from
        this.context = context
        this.dialogCallback = dialogCallback
        this.personInfo = personInfo
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddDataBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isupdate) {
            binding.cardAddData.visibility = View.GONE
            binding.cardUpdate.visibility = View.VISIBLE


            binding.etPname.setText(personInfo.name)
            binding.etPemail.setText(personInfo.email_id)
            binding.etPnumber.setText(personInfo.ph_no.toString())
            binding.etPaddress.setText(personInfo.address)
            Glide.with(requireContext()).load((personInfo.profile_img)).into(binding.ivProfile)

            binding.ivProfile.setOnClickListener {
                showImageSelectionDialog()
            }

            vProfileImage = personInfo.profile_img.toString()


            binding.tvupadte.setOnClickListener {
                dialogCallback.updateData(
                    personInfo = PersonInfo(
                        personInfo.id,
                        binding.etPname.text.toString(),
                        binding.etPemail.text.toString(),
                        binding.etPaddress.text.toString(),
                        binding.etPnumber.text.toString().toInt(),
                        vProfileImage
                    )
                )
                dialog?.dismiss()

            }


        } else {
            binding.cardAddData.visibility = View.VISIBLE
            binding.cardUpdate.visibility = View.GONE
        }

        binding.tvYes.setOnClickListener {
            dialogCallback.Adddata()
        }





        dialog?.window?.attributes = getLayoutParams(dialog!!)
    }


    private fun getLayoutParams(dialog: Dialog): WindowManager.LayoutParams? {
        val layoutParams = WindowManager.LayoutParams()
        dialog.window?.attributes?.let {
            layoutParams.copyFrom(it)
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        return layoutParams
    }

    private fun showImageSelectionDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder = android.app.AlertDialog.Builder(requireActivity())
        builder.setTitle("Select Image Source")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> openCamera()
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent1)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.ivProfile.setImageBitmap(imageBitmap)
                imageuri = getImageUri(imageBitmap)!!
                vProfileImage = getRealPathFromURI(imageuri).toString()

            }
        }

    fun getRealPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            requireContext().contentResolver.query(contentUri, projection, null, null, null)
        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            filePath
        } else {
            contentUri.path
        }
    }

    fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                requireContext().contentResolver,
                inImage,
                "Title",
                null
            )
        return Uri.parse(path)
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* video/*"
        galleryluancher.launch(intent)
    }

    var galleryluancher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val selectedUri: Uri? = data?.data
                selectedUri?.let { uri ->
                    // Check if it's an image or video
                    if (uri.toString().contains("image")) {
                        // Handle image
                        binding?.ivProfile?.setImageURI(uri)

                        vProfileImage = getRealPathFromURI(uri).toString()


                    }
                }
            }
        }

}






