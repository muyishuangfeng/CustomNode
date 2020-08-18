package com.yk.silence.customnode.model

data class CircleModel(
    var avatar: String = "",
    var userName: String = "",
    var content: String = "",
    var starNumber: Int = 0,
    var pictures: MutableList<CircleItemImage> = arrayListOf(),
    var AccessList: MutableList<CircleAccess> = arrayListOf()
)


data class CircleItemImage(var index: Int = 0, var imgUrl: String = "",  var width: Int = 0,
        val height:Int = 0)

data class CircleAccess(
    var accessName: String = "",
    var accessOtherName: String = "",
    var accessContent: String = ""
)