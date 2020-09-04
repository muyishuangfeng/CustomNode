package com.yk.silence.customnode.db.mine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

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
    suspend fun queryAllInfo(): MutableList<MyselfModel?>
}