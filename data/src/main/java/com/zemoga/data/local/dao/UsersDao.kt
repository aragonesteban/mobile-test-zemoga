package com.zemoga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zemoga.data.local.entities.UserEntity

@Dao
interface UsersDao {

    @Insert
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): UserEntity?

}