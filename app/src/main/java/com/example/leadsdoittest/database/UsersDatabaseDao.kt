package com.example.leadsdoittest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDatabaseDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query(value = "SELECT * from users WHERE userId = :key")
    fun get(key: Long): User

    @Query(value = "DELETE FROM users")
    fun clear()

    @Query(value = "SELECT * FROM users ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>
}