package com.yk.silence.customnode.db.node

import com.yk.silence.customnode.common.APP
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.widget.fragment.ChatFragment

/**
 * 数据库帮助类
 */
object RoomHelper {


    /**
     * 获取记事本
     */
    private val mHomeNodeDao = NodeDataBase.getInstance(APP.sInstance).homeNodeDao()

    /**
     * 获取好友
     */
    private val mChatDao = NodeDataBase.getInstance(APP.sInstance).chatDao()

    /**
     * 倒叙查询所有记录
     */
    suspend fun getAllNodes() = mHomeNodeDao.queryAll().reversed()


    /**
     * 添加记录
     */
    suspend fun addNode(homeNode: HomeNode) {
        if (!mHomeNodeDao.isExit(homeNode.homeModel!!.id)) {
            mHomeNodeDao.insertNode(homeNode.homeModel!!)
            homeNode.pictures.forEach {
                mHomeNodeDao.insertPicture(
                    HomePictureModel(
                        imgID = homeNode.homeModel!!.id.toLong(),
                        imgUrl = it.imgUrl
                    )
                )

            }
        }


    }

    /**
     * 删除记录
     */
    suspend fun deleteNode(homeNode: HomeNode) {
        homeNode.pictures.forEach {
            mHomeNodeDao.deletePicture(it)
        }
        mHomeNodeDao.queryNode(homeNode.homeModel!!.id).let {
            mHomeNodeDao.deleteNode(it)
        }


    }

    /**
     * 查询最大的ID
     */
    suspend fun searchMAXID() = mHomeNodeDao.searchMAXID()


    /**
     * 添加好友
     */
    suspend fun addFriend(model: FriendModel) {
        if (!mChatDao.isFriendExit(model.user_id!!)) {
            mChatDao.insertFriend(model).apply {
                mChatDao.queryFriend()
            }
        }
    }

    /**
     * 删除好友
     */
    suspend fun deleteFriend(model: FriendModel) {

        if (mChatDao.isFriendExit(model.user_id!!)) {
            mChatDao.deleteFriend(model).apply {
                mChatDao.queryFriend()
            }
        }

    }

    /**
     * 查询好友
     */
    suspend fun queryFriends(): MutableList<FriendModel> {
        return mChatDao.queryFriend()
    }

    /**
     * 添加聊天信息
     */
    suspend fun addChat(model: ChatModel) {
        if (mChatDao.isFriendExit(model.chat_id)) {
            mChatDao.insertChat(model)
        }
    }

    /**
     * 分页查询聊天
     */
    suspend fun queryChats(id: String, start: Int): MutableList<ChatModel> {
        return mChatDao.queryChat(id, start)
    }

    /**
     * 分页查询聊天
     */
    suspend fun queryChats(id: String): MutableList<ChatModel> {
        return mChatDao.queryChat(id)
    }

}