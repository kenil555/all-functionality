package com.ekta.kotlin.roomdb.todolist.Activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo
import com.ekta.kotlin.roomdb.todolist.R
import com.ekta.kotlin.roomdb.todolist.Util.AddDataDailog
import com.ekta.kotlin.roomdb.todolist.Util.DialogCallback
import com.ekta.kotlin.roomdb.todolist.Util.Utils
import com.ekta.kotlin.roomdb.todolist.ViewModel.PeronViewModel
import com.ekta.kotlin.roomdb.todolist.databinding.ActivityAddTodoBinding
import java.io.ByteArrayOutputStream
import java.io.File

class AddTodoActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTodoBinding
    lateinit var videoModel: PeronViewModel
    lateinit var imageuri: Uri
    lateinit var vProfileImage: String
    lateinit var dialog: AddDataDailog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkPermissions()

        videoModel = PeronViewModel(application)

        binding.ivProfile.setOnClickListener {
            showImageSelectionDialog()
        }

        binding.imgDone.setOnClickListener {

            dialog = AddDataDailog(this, object : DialogCallback {
                override fun Adddata() {
                    addData()
                    Utils.getUtils(applicationContext).startActivity(MainActivity::class.java)
                    dialog.dismiss()
                }

                override fun updateData(personInfo: PersonInfo) {
                    TODO("Not yet implemented")
                }

            }, false,PersonInfo(null,"","","",null,""))
            dialog.show(supportFragmentManager, "")


        }
        binding.imgBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }



    fun addData() {
        var name = binding.etPname.text.toString()
        var emailid = binding.etPemail.text.toString()
        var number = binding.etPnumber.text.toString().toInt()
        var address = binding.etPname.text.toString()
        var img_url = vProfileImage


        videoModel.insertPerson(
            personInfo = PersonInfo(
                null,
                name,
                emailid,
                address,
                number,
                img_url
            )
        )


    }

    private fun showImageSelectionDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder = android.app.AlertDialog.Builder(this)
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
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
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
                applicationContext.contentResolver,
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


    private fun checkPermissions() {
        val permissions = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_VIDEO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.READ_MEDIA_VIDEO)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 0)
        }
    }


}