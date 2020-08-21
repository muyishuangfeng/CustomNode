package com.yk.silence.customnode.viewmodel.friend

import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.db.node.RoomHelper

class FriendRepository {

    /**
     * 查询好友
     */
    suspend fun queryFriend() = RoomHelper.queryFriends()

    /**
     * 删除好友
     */
    suspend fun deleteFriend(model: FriendModel) = RoomHelper.deleteFriend(model)

    /**
     * 添加好友
     */
    suspend fun addFriend(model: FriendModel) = RoomHelper.addFriend(model)
}