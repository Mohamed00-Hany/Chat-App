package com.projects.chat_app.ui.register

import com.projects.domain.repositoriesContract.user.UserRepository
import com.projects.domain.useCases.InsertUser
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InsertUserModule
{
    fun insertUser(userRepository: UserRepository): InsertUser
    {
        return InsertUser(userRepository)
    }
}