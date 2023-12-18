package com.example.listmahasiswa

import android.os.Parcel
import android.os.Parcelable

data class TaskClass(var id:String, var name:String, var desc:String, var date:String):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(date)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskClass> {
        override fun createFromParcel(parcel: Parcel): TaskClass {
            return TaskClass(parcel)
        }

        override fun newArray(size: Int): Array<TaskClass?> {
            return arrayOfNulls(size)
        }
    }
}
