package com.bkalysh.devicer2.mocked.backend.db.repository

import com.bkalysh.devicer2.mocked.backend.db.dao.UserDao
import com.bkalysh.devicer2.mocked.backend.db.model.User

class UserRepository(private val userDao: UserDao) {

    // Insert a user in the database
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    // Get a user by its ID
    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }

    // Get a user by email
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    // Delete a specific user
    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }
}