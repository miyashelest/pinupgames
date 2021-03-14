package com.example.leadsdoittest.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract val usersDatabaseDao: UsersDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "users_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    Log.d("DB", "created")
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}