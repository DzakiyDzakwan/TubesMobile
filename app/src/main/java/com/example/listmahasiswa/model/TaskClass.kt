package com.example.listmahasiswa.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class TaskClass(val id: Int?,
                     val name: String,
                     val markAsFinished: Boolean,
                     val startedAt: Date?,
                     val deadlineAt: Date?,
                     val createdAt: Date?,
                     val updatedAt: Date?) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Date::class.java.classLoader) as? Date
            ?: Date(), // Use Date() as a default value if null
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date(),
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date(),
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: 0) // Use 0 as a default value if id is null
        parcel.writeString(name)
//        parcel.writeString(description)
        parcel.writeByte(if (markAsFinished) 1 else 0)
        // Write nullable dates with null checks
        parcel.writeLong(startedAt?.time ?: 0L)
        parcel.writeLong(deadlineAt?.time ?: 0L)
        parcel.writeLong(createdAt?.time ?: 0L)
        parcel.writeLong(updatedAt?.time ?: 0L)
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
