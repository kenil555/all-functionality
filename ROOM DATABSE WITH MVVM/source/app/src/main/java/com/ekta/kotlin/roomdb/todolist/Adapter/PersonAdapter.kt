package com.ekta.kotlin.roomdb.todolist.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo
import com.ekta.kotlin.roomdb.todolist.R
import com.ekta.kotlin.roomdb.todolist.Util.AddDataDailog
import com.ekta.kotlin.roomdb.todolist.Util.DialogCallback
import com.ekta.kotlin.roomdb.todolist.databinding.ListItemBinding


class PersonAdapter(context: Context,onclickUpdate: OnclickUpdate) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context: Context
    var onclickUpdate: OnclickUpdate

    init {
        this.context = context
        this.onclickUpdate = onclickUpdate
    }

    interface OnclickUpdate {

        fun updateitem(personInfo: PersonInfo)

    }


    private val personList = ArrayList<PersonInfo>()


    fun updateList(newList: List<PersonInfo>) {
        personList.clear()
        personList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val inquiryModel = personList.get(position)
        viewHolder.binding.tvName.text = inquiryModel.name
        viewHolder.binding.tvEmail.text = inquiryModel.email_id
        viewHolder.binding.tvAddress.text = inquiryModel.address
        viewHolder.binding.tvNumber.text = inquiryModel.ph_no.toString()

        Glide.with(context).load(inquiryModel.profile_img)
            .into(viewHolder.binding.image)


        viewHolder.binding.ivUpdate.setOnClickListener {
            onclickUpdate.updateitem(inquiryModel)
        }



    }
    fun removeAt(position: Int) {
        personList.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int {
        return personList.size
    }


}