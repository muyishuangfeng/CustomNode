package com.yk.silence.customnode.db.helper

import com.yk.silence.customnode.db.mine.MyselfModel

object MyselfInfoHelper {
    /**
     * 插入我的信息
     */
   suspend fun insertMyInfo(): MutableList<MyselfModel> {
        val mList = mutableListOf<MyselfModel>()
        val model = MyselfModel()
        model.avatar = "https://muyishuangfeng.oss-cn-beijing.aliyuncs.com/6e8705149dac.webp"
        model.link = "https://www.jianshu.com/u/1e2eec6f972c"
        model.motto =
            "如果天空是黑暗的，那就摸黑生存；如果发出声音是危险的，那就保持沉默；如果自觉无力发光的，那就蜷伏于墙角。但不要习惯了黑暗就为黑暗辩护；不要为自己的苟且而得意；不要嘲讽那些比自己更勇敢热情的人们。我们可以卑微如尘土，不可扭曲如蛆虫"
        model.name = "Silence潇湘夜雨"
        model.mine_type = "简书"
        mList.add(model)
        val model2 = MyselfModel()
        model2.avatar = "https://muyishuangfeng.oss-cn-beijing.aliyuncs.com/6e8705149dac.webp"
        model2.link = "https://juejin.im/user/131597124251799"
        model2.motto =
            "如果天空是黑暗的，那就摸黑生存；如果发出声音是危险的，那就保持沉默；如果自觉无力发光的，那就蜷伏于墙角。但不要习惯了黑暗就为黑暗辩护；不要为自己的苟且而得意；不要嘲讽那些比自己更勇敢热情的人们。我们可以卑微如尘土，不可扭曲如蛆虫"
        model2.name = "Silence潇湘夜雨"
        model2.mine_type = "掘金"
        mList.add(model2)
        val model3 = MyselfModel()
        model3.avatar = "https://muyishuangfeng.oss-cn-beijing.aliyuncs.com/6e8705149dac.webp"
        model3.link = "https://github.com/muyishuangfeng"
        model3.motto =
            "如果天空是黑暗的，那就摸黑生存；如果发出声音是危险的，那就保持沉默；如果自觉无力发光的，那就蜷伏于墙角。但不要习惯了黑暗就为黑暗辩护；不要为自己的苟且而得意；不要嘲讽那些比自己更勇敢热情的人们。我们可以卑微如尘土，不可扭曲如蛆虫"
        model3.name = "Silence潇湘夜雨"
        model3.mine_type = "GitHub"
        mList.add(model3)
        val model4 = MyselfModel()
        model4.avatar = "https://muyishuangfeng.oss-cn-beijing.aliyuncs.com/16userAvatar.jpg"
        model4.link = "https://muyishuangfeng.github.io/"
        model4.motto =
            "如果天空是黑暗的，那就摸黑生存；如果发出声音是危险的，那就保持沉默；如果自觉无力发光的，那就蜷伏于墙角。但不要习惯了黑暗就为黑暗辩护；不要为自己的苟且而得意；不要嘲讽那些比自己更勇敢热情的人们。我们可以卑微如尘土，不可扭曲如蛆虫"
        model4.name = "Silence潇湘夜雨"
        model4.mine_type = "个人博客"
        mList.add(model4)
        val model5 = MyselfModel()
        model5.avatar = "https://muyishuangfeng.oss-cn-beijing.aliyuncs.com/16userAvatar.jpg"
        model5.link = "https://mp.csdn.net/console/article"
        model5.motto =
            "如果天空是黑暗的，那就摸黑生存；如果发出声音是危险的，那就保持沉默；如果自觉无力发光的，那就蜷伏于墙角。但不要习惯了黑暗就为黑暗辩护；不要为自己的苟且而得意；不要嘲讽那些比自己更勇敢热情的人们。我们可以卑微如尘土，不可扭曲如蛆虫"
        model5.name = "Silence潇湘夜雨"
        model5.mine_type = "CSDN"
        mList.add(model5)
        return mList

    }
}