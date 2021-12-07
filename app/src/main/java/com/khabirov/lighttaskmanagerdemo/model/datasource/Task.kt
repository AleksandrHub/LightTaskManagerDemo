package com.niww.lighttaskmanager.model.datasource

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var state: Int = 0,
    var name: String = "",
    var address: String = "",
    var employer: String = "",
    var performer: String = "",
    var description: String = "",
    var timing: String = "",
    var isArchived: Boolean = false
) : Parcelable