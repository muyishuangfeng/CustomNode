package com.yk.silence.customnode.db.helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yk.silence.customnode.common.DATA_BASE
import com.yk.silence.customnode.db.friend.ChatDao
import com.yk.silence.customnode.db.friend.ChatModel
import com.yk.silence.customnode.db.friend.FriendModel
import com.yk.silence.customnode.db.mine.MyselfDao
import com.yk.silence.customnode.db.mine.MyselfModel
import com.yk.silence.customnode.db.node.HomeModel
import com.yk.silence.customnode.db.node.HomeNodeDao
import com.yk.silence.customnode.db.node.HomePictureModel


@Database(
    entities = [HomeModel::class, HomePictureModel::class, FriendModel::class, ChatModel::class, MyselfModel::class],
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

    /**
     * 我的信息
     */
    abstract fun myselfDao(): MyselfDao

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
                //.addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }

//        /**
//         * 升级数据库
//         */
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE tab_mine(name TEXT,avatar TEXT,link TEXT,motto TEXT,id INTEGER PRIMARY KEY)")
//            }
//        }
//        /**
//         * 升级数据库
//         */
//        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE tab_mine ADD COLUMN mine_type TEXT")
//            }
//        }
    }


}