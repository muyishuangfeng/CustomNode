package com.yk.silence.customnode.db.node

import androidx.room.*

@Entity(tableName = "home_node")
data class HomeModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    var name: String = "",
    var avatar: String = "",
    var content: String = "",
    var time: String = ""
)

@Entity(
    tableName = "node_picture",
    foreignKeys = [
        ForeignKey(entity = HomeModel::class, parentColumns = ["id"], childColumns = ["img_id"])
    ],
    indices = [Index("img_id")]
)
data class HomePictureModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "img_id")
    var imgID: Long = 0,
    var imgUrl: String = ""
)

/**
 * 主页记录
 */
data class HomeNode(
    @Embedded
    var homeModel: HomeModel? = null,
    @Relation(parentColumn = "id", entityColumn = "img_id")
    var pictures: MutableList<HomePictureModel> = arrayListOf()
)