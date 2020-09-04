package com.yk.silence.customnode.viewmodel.home

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.yk.silence.customnode.base.vm.BaseViewModel
import com.yk.silence.customnode.db.node.HomeModel
import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.db.node.HomePictureModel
import com.yk.silence.customnode.model.UserNodeMode

class HomeViewModel : BaseViewModel() {

    private val mHomeRepository: HomeRepository by lazy { HomeRepository() }

    //记录集合
    val mNodeList = MutableLiveData<MutableList<HomeNode>>()

    //重新加载
    val mReloadStatus = MutableLiveData<Boolean>()

    //刷新
    val mRefreshStatus = MutableLiveData<Boolean>()

    //空状态
    val mEmptyStatus = MutableLiveData<Boolean>()

    //添加状态
    val mAddStatus = MutableLiveData<Boolean>()

    //ID
    val mNumber = MutableLiveData<Int>()

    //上传结果
    val mUserNodeMode = MutableLiveData<UserNodeMode>()


    /**
     * 获取数据
     */
    fun getData() {
        mEmptyStatus.value = false
        mReloadStatus.value = false
        mRefreshStatus.value = true
        launch(
            block = {
                val mHomeNode = mHomeRepository.queryAllNode()
                mNodeList.value = mHomeNode.toMutableList()
                mRefreshStatus.value = false
                mReloadStatus.value = false
                mEmptyStatus.value = mHomeNode.isEmpty()
            },
            error = {
                mEmptyStatus.value = true
                mReloadStatus.value = false
                mRefreshStatus.value = false
            }
        )
    }


    /**
     * 删除
     */
    fun deleteData(homeNode: HomeNode) {
        launch(
            block = {
                mHomeRepository.deleteNode(homeNode)
            }
        )
    }

    /**
     * 删除
     */
    fun deleteData(id: Int, homeNode: HomeNode) {
        launch(
            block = {
                mHomeRepository.deleteNode(id).apply {
                    mHomeRepository.deleteNode(homeNode)
                }
            }
        )
    }

    /**
     * 查询最大ID
     */
    fun queryMAXID() {
        launch(
            block = {
                mNumber.value = mHomeRepository.queryMAXID()
            }
        )
    }

    /**
     * 添加记录
     */
    fun addNode(userMode: UserNodeMode, list: List<String>) {
        mAddStatus.value = false
        launch(
            block = {
                val homeNode = HomeNode()
                val model = HomeModel()
                model.id = userMode.id
                model.name = mUserRepository.getUserInfo()?.user_name.toString()
                model.avatar=userMode.node_avatar
                model.content = userMode.node_content
                model.time = userMode.node_time
                val mPictures = arrayListOf<HomePictureModel>()
                if (list.size ?: 0 > 0) {
                    for (index in list.indices) {
                        val mPictureModel = HomePictureModel()
                        mPictureModel.imgID = userMode.id.toLong()
                        mPictureModel.imgUrl = list[index]
                        mPictures.add(mPictureModel)
                    }
                }
                homeNode.homeModel = model
                homeNode.pictures = mPictures
                mHomeRepository.addNode(homeNode)
                mAddStatus.value = true
            }
        )
    }

    /**
     * 添加在线记录
     */
    fun addNetNode(list: List<String>, content: String) {
        launch(
            block = {
                val mList = arrayListOf<HomePictureModel>()
                for (index in list.indices) {
                    val mPictureModel = HomePictureModel()
                    mPictureModel.imgUrl = list[index]
                    mList.add(mPictureModel)
                }
                val model = HomeModel()
                model.avatar = mUserRepository.getUserInfo()?.user_avatar.toString()
                model.id = mUserRepository.getUserInfo()!!.id
                model.time = System.currentTimeMillis().toString()
                model.content = content
                mUserNodeMode.value =
                    mHomeRepository.addNode(Gson().toJson(model), Gson().toJson(mList))

            }
        )
    }
}