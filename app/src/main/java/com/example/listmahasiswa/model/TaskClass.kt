package com.example.listmahasiswa.model

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class TaskClass(val id: Int,
                     val name: String,
                     val mark_as_finished: UByte,
                     val started_at: Date,
                     val deadline_at: Date,
                     val created_at: Date,
                     val updated_at: Date) : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte().toUByte(),
        parcel.readValue(Date::class.java.classLoader) as? Date
            ?: Date(), // Use Date() as a default value if null
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date(),
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date(),
        parcel.readValue(Date::class.java.classLoader) as? Date ?: Date()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeByte(mark_as_finished.toByte())
        parcel.writeValue(started_at)
        parcel.writeValue(deadline_at)
        parcel.writeValue(created_at)
        parcel.writeValue(updated_at)
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
