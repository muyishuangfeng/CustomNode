package com.yk.silence.customnode.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yk.silence.customnode.common.DATA_BASE

@Database(entities = [HomeModel::class, HomePictureModel::class], version = 1, exportSchema = true)
abstract class NodeDataBase : RoomDatabase() {

    abstract fun homeNodeDao(): HomeNodeDao

    companion object {
        @Volatile
        private var instance: NodeDataBase? = null

        fun getInstance(context: Context): NodeDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NodeDataBase {
            return Room.databaseBuilder(context, NodeDataBase::class.java, DATA_BASE)
                .build()
        }
    }
}