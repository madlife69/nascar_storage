package tn.esprit.nascar.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tn.esprit.nascar.dao.EventDao
import tn.esprit.nascar.models.Events

//TODO 10 Change this class to an abstract class that inherit from ROOMDatabase
@Database(entities = [Events::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun EventDao(): EventDao


    companion object {
        //TODO 11 Apply the Design Pattern singleton
        var db: AppDatabase? = null


        fun getDatabase(context: Context?=null): AppDatabase {
            if (db == null) {
                synchronized(AppDatabase::class) {
                    db = Room.databaseBuilder(
                        context!!.applicationContext,
                        AppDatabase::class.java,
                        "Sample.db"
                    )

                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!
        }
    }
}