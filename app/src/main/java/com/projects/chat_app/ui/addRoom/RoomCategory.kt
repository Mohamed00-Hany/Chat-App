package com.projects.chat_app.ui.addRoom

import com.projects.chat_app.R

data class RoomCategory(
    val id: String? = null,
    val name: Int? = null,
    val imageId: Int? = null
) {
    companion object {
        fun getListRoomCategories() = listOf(
            RoomCategory("sports", R.string.sports, R.drawable.sports),
            RoomCategory("music", R.string.music, R.drawable.music),
            RoomCategory("movies", R.string.movies, R.drawable.movies)
        )
    }
}
