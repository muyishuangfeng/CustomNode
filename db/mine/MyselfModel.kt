package com.yk.silence.customnode.db.mine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_mine")
class MyselfModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var avatar: String = "",
    var link: String = "",
    var motto: String = ""
)