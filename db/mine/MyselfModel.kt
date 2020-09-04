package com.yk.silence.customnode.db.mine

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_mine")
class MyselfModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var name: String? = "",
    var avatar: String? = "",
    var link: String? = "",
    var motto: String? = "",
    var mine_type: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeString(link)
        parcel.writeString(motto)
        parcel.writeString(mine_type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyselfModel> {
        override fun createFromParcel(parcel: Parcel): MyselfModel {
            return MyselfModel(parcel)
        }

        override fun newArray(size: Int): Array<MyselfModel?> {
            return arrayOfNulls(size)
        }
    }
}