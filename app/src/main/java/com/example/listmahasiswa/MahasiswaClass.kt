package com.example.listmahasiswa

import android.os.Parcel
import android.os.Parcelable

data class MahasiswaClass(var id:String, var image:Int , var name:String, var email:String, var faculty:String, var major:String, var semester:Int ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(image)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(faculty)
        parcel.writeString(major)
        parcel.writeInt(semester)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MahasiswaClass> {
        override fun createFromParcel(parcel: Parcel): MahasiswaClass {
            return MahasiswaClass(parcel)
        }

        override fun newArray(size: Int): Array<MahasiswaClass?> {
            return arrayOfNulls(size)
        }
    }
}
