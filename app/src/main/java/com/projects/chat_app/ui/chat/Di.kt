package com.projects.chat_app.ui.chat

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class MessagesAdapterModule
{
    @Provides
    fun provideMessagesAdapter():MessagesAdapter
    {
        return MessagesAdapter(mutableListOf())
    }
}