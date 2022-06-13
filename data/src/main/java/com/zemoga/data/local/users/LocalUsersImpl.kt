package com.zemoga.data.local.users

import com.zemoga.data.local.dao.UsersDao
import com.zemoga.data.local.entities.UserEntity
import com.zemoga.domain.model.User

class LocalUsersImpl(
    private val usersDao: UsersDao
) : LocalUsers {

    override fun insertUser(user: User) {
        val userEntity = UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            phone = user.phone,
            website = user.website,
        )
        usersDao.insertUser(userEntity)
    }

    override fun getUserById(userId: Int): User? {
        val result = usersDao.getUserById(userId)
        return result?.run {
            User(
                id = id,
                name = name,
                email = email,
                phone = phone,
                website = website,
            )
        }
    }

}