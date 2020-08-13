package com.yk.silence.customnode.db

import com.yk.silence.customnode.common.APP

/**
 * 数据库帮助类
 */
object RoomHelper {


    /**
     * 获取记事本
     */
    private val mHomeNodeDao = NodeDataBase.getInstance(APP.sInstance).homeNodeDao()

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
}