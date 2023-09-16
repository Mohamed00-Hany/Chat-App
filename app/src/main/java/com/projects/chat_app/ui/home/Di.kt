package com.projects.chat_app.ui.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class RoomsAdapterModule
{
    @Provides
    fun provideRoomsAdapter():RoomsAdapter
    {
        return RoomsAdapter()
    }
}