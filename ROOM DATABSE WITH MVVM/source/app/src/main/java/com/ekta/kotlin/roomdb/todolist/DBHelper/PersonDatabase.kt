package com.ekta.kotlin.roomdb.todolist.DBHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = (arrayOf(PersonInfo::class)), version = 1)
abstract class PersonDatabase : RoomDatabase() {


    abstract fun getPersonInfo(): PersonDao


    companion object {

        @Volatile
        private var Instance: PersonDatabase? = null

        fun getDatabase(context: Context): PersonDatabase {

            return Instance ?: synchronized(this) {
                val DATABASE_NAME = "PERSON_INFO_DB"
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    DATABASE_NAME
                ).build()

                Instance = instance
                instance
            }


        }
    }

}