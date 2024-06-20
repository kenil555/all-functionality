package com.ekta.kotlin.roomdb.todolist.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonDatabase
import com.ekta.kotlin.roomdb.todolist.DBHelper.PersonInfo
import com.ekta.kotlin.roomdb.todolist.Repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeronViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PersonRepository

    val allPerson: LiveData<List<PersonInfo>>


    init {
        val dao = PersonDatabase.getDatabase(application).getPersonInfo()
        repository = PersonRepository(dao)
        allPerson = repository.allperosn
    }


    fun insertPerson(personInfo: PersonInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPerson(personInfo)
    }

    fun upadtePerson(personInfo: PersonInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePerson(personInfo)
    }
    fun deletePerson(personInfo: PersonInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePerson(personInfo)
    }

}