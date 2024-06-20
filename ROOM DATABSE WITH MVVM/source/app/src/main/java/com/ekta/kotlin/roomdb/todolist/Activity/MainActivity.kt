package com.ekta.kotlin.roomdb.todolist.Activity

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekta.kotlin.roomdb.todolist.Adapter.PersonAdapter
import com.ekta.kotlin.roomdb.todolist.Adapter.SwipeToDeleteCallback
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo
import com.ekta.kotlin.roomdb.todolist.R
import com.ekta.kotlin.roomdb.todolist.Util.AddDataDailog
import com.ekta.kotlin.roomdb.todolist.Util.DialogCallback
import com.ekta.kotlin.roomdb.todolist.Util.Utils
import com.ekta.kotlin.roomdb.todolist.ViewModel.PeronViewModel
import com.ekta.kotlin.roomdb.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var videoModel: PeronViewModel
    lateinit var adapter: PersonAdapter
    private var personList = mutableListOf<PersonInfo>()
    lateinit var dialog: AddDataDailog
//    lateinit var personinfo: PersonInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoModel = PeronViewModel(application)

        binding.fabAddTodo.setOnClickListener {


            Utils.getUtils(this).startActivity(AddTodoActivity::class.java)

        }

        getAllPersonList()
    }

    fun getAllPersonList() {

        videoModel.allPerson.observe(this, Observer { list ->
            list?.let {
                personList = list.toMutableList()
                adapter.updateList(list)
            }
        })

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        adapter = PersonAdapter(this, object : PersonAdapter.OnclickUpdate {
            override fun updateitem(personInfo: PersonInfo) {
                dialog = AddDataDailog(applicationContext, object : DialogCallback {
                    override fun Adddata() {

                    }


                    override fun updateData(personInfo1: PersonInfo) {
                        videoModel.upadtePerson(personInfo1)

                    }

                }, true, personInfo)
                dialog.show(supportFragmentManager, "")
            }

        })
        binding.recyclerView.adapter = adapter
        enableSwipeToDelete()
    }


    private fun enableSwipeToDelete() {


       /* val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeAt(position)
                videoModel.deletePerson(personList[position])
                personinfo = personList[position]
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.recyclerView)*/
    }


}