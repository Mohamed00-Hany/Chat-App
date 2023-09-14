package com.projects.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.projects.data.database.FireStoreUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FireStoreUtilsModule
{
    @Provides
    fun bindFireStoreUtils(firebaseFireStore: FirebaseFirestore): FireStoreUtils
    {
        return FireStoreUtils(firebaseFireStore)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class FireStoreModule
{
    @Provides
    fun bindFireStore(): FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }
}