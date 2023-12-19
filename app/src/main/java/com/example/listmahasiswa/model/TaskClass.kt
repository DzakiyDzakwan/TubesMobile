package com.example.listmahasiswa.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class TaskClass(val id: Int,
                     val name: String,
                     val description: String,
                     val mark_as_finished: UByte,
                     val started_at: Date,
                     val deadline_at: Date,
                     val created_at: Date,
                     val updated_at: Date) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte().toUByte(),
        Date(parcel.readLong()),
        Date(parcel.readLong()),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(mark_as_finished.toByte())
        parcel.writeLong(started_at.time)
        parcel.writeLong(deadline_at.time)
        parcel.writeLong(created_at.time)
        parcel.writeLong(updated_at.time)
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskClass>
    {
        override fun createFromParcel(parcel: Parcel): TaskClass
        {
            return TaskClass(parcel)
        }

        override fun newArray(size: Int): Array<TaskClass?>
        {
            return arrayOfNulls(size)
        }
    }
}
