package com.example.leadsdoittest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "user_name")
    var userName: String = " ",

    @ColumnInfo(name = "user_phone")
    var userPhone: String = " ",

    @ColumnInfo(name = "user_email")
    var userEmail: String = " "
)