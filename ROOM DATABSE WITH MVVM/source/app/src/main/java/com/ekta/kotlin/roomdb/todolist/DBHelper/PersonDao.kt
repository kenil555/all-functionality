package com.ekta.kotlin.roomdb.todolist.DBHelper

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(personInfo: PersonInfo)

    @Delete
    suspend fun deleteData(personInfo: PersonInfo)


    @Query("SELECT * FROM PERSONINFO order by id ASC")
    fun getallData(): LiveData<List<PersonInfo>>

    @Query("UPDATE personinfo set person_name= :name, person_email =:email_id, person_address = :address, person_phoneNo = :ph_no ,person_img= :profile_img where id =:id")
    suspend fun updateInfo(
        id: Int?,
        name: String?,
        email_id: String?,
        address: String?,
        ph_no: Int?,
        profile_img: String?
    )

}