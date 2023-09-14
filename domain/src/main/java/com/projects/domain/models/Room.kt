package com.projects.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id:String?=null,
    val name:String?=null,
    val description:String?=null,
    val roomCategoryId: String?=null,
    val createdBy:String?=null
) : Parcelable
