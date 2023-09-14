package com.projects.chat_app.ui.login

import com.projects.domain.repositoriesContract.user.UserRepository
import com.projects.domain.useCases.GetUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetUserModule
{
    @Provides
    fun getUser(userRepository: UserRepository):GetUser
    {
        return GetUser(userRepository)
    }
}