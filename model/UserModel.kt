package com.yk.silence.customnode.model

import android.os.Parcel
import android.os.Parcelable

/**
 * 用户信息
 */
data class UserModel(
    var id: Int,
    var user_name: String?,
    var user_avatar: String?,
    var user_pass: String?,
    var login_time: String?,
    var user_token: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user_name)
        parcel.writeString(user_avatar)
        parcel.writeString(user_pass)
        parcel.writeString(login_time)
        parcel.writeString(user_token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}

