package com.yk.silence.customnode.db.node

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yk.silence.customnode.common.DATA_BASE
import com.yk.silence.customnode.db.friend.ChatDao
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.db.friend.FriendModel

@Database(
    entities = [HomeModel::class, HomePictureModel::class, FriendModel::class, ChatModel::class],
    version = 1,
    exportSchema = true
)
abstract class NodeDataBase : RoomDatabase() {
    /**
     * 记事本
     */
    abstract fun homeNodeDao(): HomeNodeDao

    /**
     * 聊天
     */
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var instance: NodeDataBase? = null

        fun getInstance(context: Context): NodeDataBase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        )
                            .also { instance = it }
                }
        }

        private fun buildDatabase(context: Context): NodeDataBase {
            return Room.databaseBuilder(context, NodeDataBase::class.java, DATA_BASE)
                .build()
        }
    }
}