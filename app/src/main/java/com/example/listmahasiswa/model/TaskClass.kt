package com.example.listmahasiswa.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class TaskClass(val id: Int?,
                     val name: String,
                     val markAsFinished: Boolean,
                     val started_at: Date?,
                     val deadline_at: Date?,
                     val created_at: Date?,
                     val updated_at: Date?) : Parcelable
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

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeInt(id ?: 0) // Use 0 as a default value if id is null
        parcel.writeString(name)
//        parcel.writeString(description)
        parcel.writeByte(if (markAsFinished) 1 else 0)
        // Write nullable dates with null checks
        parcel.writeLong(started_at?.time ?: 0L)
        parcel.writeLong(deadline_at?.time ?: 0L)
        parcel.writeLong(created_at?.time ?: 0L)
        parcel.writeLong(updated_at?.time ?: 0L)
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
