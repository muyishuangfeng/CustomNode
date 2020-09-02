package com.yk.silence.customnode.db.friend

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 好友表
 */
@Entity(tableName = "user_friend")
class FriendModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var user_id: String? = "",
    var user_name: String? = "",
    var user_avatar: String? = "",
    var friend_time: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user_id)
        parcel.writeString(user_name)
        parcel.writeString(user_avatar)
        parcel.writeString(friend_time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendModel> {
        override fun createFromParcel(parcel: Parcel): FriendModel {
            return FriendModel(parcel)
        }

        override fun newArray(size: Int): Array<FriendModel?> {
            return arrayOfNulls(size)
        }
    }
}

/**
 * 聊天表
 */
@Entity(tableName = "user_chat")
class ChatModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var chat_id: String = "",
    var user_id: String = "",
    var chat_avatar: String = "",
    var chat_content: String = "",
    var chat_type: Int = 0,
    var chat_content_type: Int = 0
)
