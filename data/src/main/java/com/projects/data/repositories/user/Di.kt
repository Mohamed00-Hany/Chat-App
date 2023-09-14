package com.projects.data.repositories.user

import com.projects.domain.repositoriesContract.user.UserDataSource
import com.projects.domain.repositoriesContract.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserRepositoryModule
{
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl):UserRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataSourceModule
{
    @Binds
    abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl):UserDataSource
}