package com.ekta.kotlin.roomdb.todolist.DBHelper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PersonINFO")
data class PersonInfo(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo("person_name") val name: String?,
    @ColumnInfo("person_email") val email_id: String?,
    @ColumnInfo("person_address") val address: String?,
    @ColumnInfo("person_phoneNo") val ph_no: Int?,
    @ColumnInfo("person_img") val profile_img: String?
)
