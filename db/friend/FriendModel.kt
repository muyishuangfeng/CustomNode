package com.yk.silence.customnode.db.friend

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 好友表
 */
@Entity(tableName = "user_friend")
class FriendModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var user_id: String = "",
    var user_name: String = "",
    var user_avatar: String = "",
    var friend_time: String = ""
)

/**
 * 聊天表
 */
@Entity(tableName = "user_chat")
class ChatModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var chat_id: String,
    var chat_content: String,
    var chat_type: String
)