package com.yk.silence.customnode.db.friend

import androidx.room.*

/**
 * 聊天
 */
@Dao
interface ChatDao {
    /**
     * 添加好友
     */
    @Transaction
    @Insert(entity = FriendModel::class)
    suspend fun insertFriend(model: FriendModel)

    /**
     * 删除好友
     */
    @Transaction
    @Delete(entity = FriendModel::class)
    suspend fun deleteFriend(model: FriendModel)

    /**
     * 查询好友
     */
    @Transaction
    @Query("SELECT * FROM user_friend ORDER BY user_id DESC ")
    suspend fun queryFriend(): MutableList<FriendModel>

    /**
     * 添加聊天
     */
    @Transaction
    @Insert(entity = ChatModel::class)
    suspend fun insertChat(model: ChatModel)

    /**
     * 根据好友ID查询聊天信息
     */
    @Transaction
    @Query("SELECT * FROM user_chat WHERE chat_id=(:chatID) AND user_id=(:userID) ORDER BY id DESC LIMIT :start*10")
    suspend fun queryChat(chatID: String, userID: String, start: Int): MutableList<ChatModel>

    /**
     * 根据好友ID查询聊天信息
     */
    @Transaction
    @Query("SELECT * FROM user_chat WHERE chat_id=(:id) AND user_id=(:userID) ORDER BY id DESC")
    suspend fun queryChat(id: String, userID: String): MutableList<ChatModel>


    /**
     * 好友ID是否存在
     */
    @Query("SELECT EXISTS(SELECT 1 FROM user_friend WHERE user_id = :id LIMIT 1)")
    suspend fun isFriendExit(id: String): Boolean

    /**
     * 聊天ID是否存在
     */
    @Query("SELECT EXISTS(SELECT 1 FROM user_chat WHERE chat_id = :id LIMIT 1)")
    suspend fun isChatExit(id: String): Boolean
}