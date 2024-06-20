package com.ekta.kotlin.roomdb.todolist.Repository

import androidx.lifecycle.LiveData
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonDao
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo

class PersonRepository (protected val personDao: PersonDao) {


    var allperosn: LiveData<List<PersonInfo>> = personDao.getallData()


    suspend fun insertPerson(personInfo: PersonInfo){
        personDao.insertData(personInfo)
    }

    suspend fun deletePerson(personInfo: PersonInfo){
        personDao.deleteData(personInfo)
    }

    suspend fun updatePerson(personInfo: PersonInfo){
        personDao.updateInfo(personInfo.id,personInfo.name,personInfo.email_id,personInfo.address,personInfo.ph_no,personInfo.profile_img)
    }


}