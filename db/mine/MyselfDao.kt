package com.yk.silence.customnode.db.mine

import androidx.room.*

/**
 * 我的信息
 */
@Dao
interface MyselfDao {

    /**
     * 插入
     */
    @Transaction
    @Insert(entity = MyselfModel::class)
    suspend fun insertInfo(model: MyselfModel)

    /**
     * 查询所有信息
     */
    @Transaction
    @Query("SELECT * FROM tab_mine")
    suspend fun queryAllInfo(): MutableList<MyselfModel>

    /**
     * 查询所有信息
     */
    @Transaction
    @Delete(entity = MyselfModel::class)
    suspend fun delete(model: MyselfModel)

    /**
     * 我的名称是否存在
     */
    @Transaction
    @Query("SELECT EXISTS(SELECT 1 FROM tab_mine WHERE link= :link LIMIT 1)")
    suspend fun isNameExit(link: String?): Boolean
}