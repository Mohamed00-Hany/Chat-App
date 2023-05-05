package com.projects.chat_app.database.models

import android.os.Parcelable
import com.projects.chat_app.ui.addRoom.RoomCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id:String?=null,
    val name:String?=null,
    val description:String?=null,
    val roomCategoryId: String?=null,
    val createdBy:String?=null
) : Parcelable
{
        fun getImageId():Int?
        {
            return RoomCategory.getCategoryById(roomCategoryId).imageId
        }
}
