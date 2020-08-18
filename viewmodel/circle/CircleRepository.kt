package com.yk.silence.customnode.viewmodel.circle

import com.yk.silence.customnode.model.CircleAccess
import com.yk.silence.customnode.model.CircleItemImage
import com.yk.silence.customnode.model.CircleModel

class CircleRepository {


    suspend fun getData(): MutableList<CircleModel> {
        val mList: MutableList<CircleModel> = arrayListOf()
        val mPictureList: MutableList<CircleItemImage> = arrayListOf()
        for (index in 0 until 4) {
            val mPictureModel = CircleItemImage()
            mPictureModel.imgUrl =
                "https://nightlesson.oss-cn-hangzhou.aliyuncs.com/0053f885c4fd91f98f.jpg"
            mPictureList.add(mPictureModel)
        }
        val mAccessList: MutableList<CircleAccess> = arrayListOf()
        for (index in 0 until 2) {
            val access = CircleAccess()
            access.accessName = "木易"
            access.accessContent = "12345678"
            mAccessList.add(access)
        }

        for (index in 0 until 3) {
            val model = CircleModel()
            model.userName = "Silence潇湘夜雨"
            model.content = "天若有情天亦老"
            model.avatar =
                "https://nightlesson.oss-cn-hangzhou.aliyuncs.com/0053f885c4fd91f98f.jpg"
            model.pictures = mPictureList
            model.AccessList = mAccessList
            mList.add(model)
        }
        return mList
    }
}