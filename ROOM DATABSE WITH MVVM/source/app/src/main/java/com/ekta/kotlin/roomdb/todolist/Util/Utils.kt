package com.ekta.kotlin.roomdb.todolist.Util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.ekta.kotlin.roomdb.todolist.R
import com.ekta.kotlin.roomdb.todolist.databinding.DialogAddDataBinding


class Utils(var context: Context) {


    companion object {

        @Volatile
        private var INSTANCE: Utils? = null

        fun getUtils(context: Context): Utils {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Utils(context).also { INSTANCE = it }
            }

        }
    }

    fun startActivity(clazz: Class<out Activity>) {
        context.startActivity(Intent(context, clazz))
    }

    fun showtoast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


  /*  fun showAddDataDialog(context: Context) {


        val dialog = Dialog(context)
        val binding: DialogAddDataBinding = DialogAddDataBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.getRoot())
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        binding.tvYes.setOnClickListener {

            dialog.dismiss()
        }
        dialog.findViewById<View>(R.id.tvNo).setOnClickListener { dialog.dismiss() }
        dialog.window!!.setLayout(-1, -2)
        dialog.show()
    }
*/

}