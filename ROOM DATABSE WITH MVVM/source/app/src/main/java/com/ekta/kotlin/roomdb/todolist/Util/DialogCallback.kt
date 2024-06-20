package com.ekta.kotlin.roomdb.todolist.Util

import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo

interface DialogCallback {

    fun Adddata()
    fun updateData(personInfo: PersonInfo)

}