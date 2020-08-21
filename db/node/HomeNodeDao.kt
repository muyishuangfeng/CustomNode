package com.yk.silence.customnode.db.node

import androidx.room.*
import com.yk.silence.customnode.db.node.HomeModel
import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.db.node.HomePictureModel

/**
 * 获取记事本
 */
@Dao
interface HomeNodeDao {

    /**
     * 插入
     */
    @Transaction
    @Insert(entity = HomeModel::class)
    suspend fun insertNode(homeModel: HomeModel): Long

    /**
     * 插入图片
     */
    @Transaction
    @Insert(entity = HomePictureModel::class)
    suspend fun insertPicture(homePictureModel: HomePictureModel): Long


    /**
     * 删除
     */
    @Transaction
    @Delete
    suspend fun deleteNode(homeModel: HomeModel)

    /**
     * 删除图片
     */
    @Transaction
    @Delete
    suspend fun deletePicture(homePictureModel: HomePictureModel)

    /**
     * 查询所有
     */
    @Transaction
    @Query("SELECT * FROM home_node WHERE id IN (SELECT DISTINCT(img_id) FROM node_picture)")
    suspend fun queryAll(): List<HomeNode>


    /**
     * 查询最大ID
     */
    @Query("SELECT MAX(id) FROM home_node")
    suspend fun searchMAXID(): Int


    /**
     * ID是否存在
     */
    @Query("SELECT EXISTS(SELECT 1 FROM home_node WHERE id = :id LIMIT 1)")
    suspend fun isExit(id: Int): Boolean

    /**
     * 根据ID查询记录
     */
    @Transaction
    @Query("SELECT * FROM home_node where id=(:id)")
    suspend fun queryNode(id: Int): HomeModel

    /**
     * 根据ID查询图片
     */
    @Transaction
    @Query("SELECT * FROM node_picture where img_id=(:id)")
    suspend fun queryPicture(id: Int): HomePictureModel
}