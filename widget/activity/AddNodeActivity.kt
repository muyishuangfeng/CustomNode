package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.REQUEST_ADD_NODE_CODE
import com.yk.silence.customnode.common.REQUEST_CODE_OPEN_PHOTO_ALBUM
import com.yk.silence.customnode.common.REQUEST_CODE_TAKE_PHOTO
import com.yk.silence.customnode.databinding.ActivityAddNodeBinding
import com.yk.silence.customnode.db.HomeModel
import com.yk.silence.customnode.db.HomeNode
import com.yk.silence.customnode.db.HomePictureModel
import com.yk.silence.customnode.impl.OnCameraClickListener
import com.yk.silence.customnode.ui.ChooseDialogHelper
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.EventBus
import com.yk.silence.customnode.viewmodel.home.HomeViewModel
import com.yk.silence.customnode.viewmodel.node.AddNodeViewModel
import com.yk.silence.customnode.widget.adapter.AddNodeAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_add_node.*

class AddNodeActivity : BaseVMActivity<AddNodeViewModel, ActivityAddNodeBinding>() {

    private lateinit var mAdapter: AddNodeAdapter
    private var mPhotoList: MutableList<String> = arrayListOf()
    private var mIDNumber = 0

    override fun getLayoutID() = R.layout.activity_add_node

    override fun viewModelClass() = AddNodeViewModel::class.java

    override fun initBinding(binding: ActivityAddNodeBinding) {
        super.initBinding(binding)
        binding.titleAddNode.setTitleClickListener(object : CustomTitleBar.TitleClickListener {
            override fun onLeftClick() {
                ActivityManager.finish(AddNodeActivity::class.java)
            }

            override fun onRightClick() {
                if (edt_add_node.text.toString().isNotEmpty() || mPhotoList.isNotEmpty()) {
                    submit()
                }
            }
        })
        binding.rlvAddNode.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mAdapter = AddNodeAdapter().apply {
            bindToRecyclerView(binding.rlvAddNode)
            mOnItemDeleteListener = {
                val path = mAdapter.data[it]
                if (mPhotoList.isNotEmpty()) {
                    mPhotoList.remove(path)
                    mAdapter.setNewData(mPhotoList)
                }
            }

            addFooterView(LayoutInflater.from(this@AddNodeActivity)
                .inflate(R.layout.item_add_node_layout, binding.rlvAddNode, false).apply {
                    setOnClickListener {
                        ChooseDialogHelper.showCamera(
                            this@AddNodeActivity,
                            object : OnCameraClickListener {
                                override fun onCameraClick(position: Int) {
                                    if (position == 1) {
                                        //跳转到相册
                                        CameraUtil.openAlbum(
                                            this@AddNodeActivity,
                                            REQUEST_CODE_OPEN_PHOTO_ALBUM
                                        )
                                    } else {
                                        //跳转到相机
                                        CameraUtil.openAlbum(
                                            this@AddNodeActivity,
                                            REQUEST_CODE_TAKE_PHOTO
                                        )
                                    }
                                }
                            })
                    }
                })
        }

    }

    override fun initData() {
        super.initData()
        mViewModel.queryMAXID()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtil.onActivityResult(
            this,
            requestCode,
            resultCode,
            data,
            object : CameraUtil.OnCameraResultListener {
                override fun onCameraResult(uri: Uri) {
                    mPhotoList.add(uri.toString())
                    mAdapter.setNewData(mPhotoList)
                }

                override fun onAlbumResult(path: String) {
                    mPhotoList.add(path)
                    mAdapter.setNewData(mPhotoList)
                }
            })
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mNumber.observe(this@AddNodeActivity, Observer {
                mIDNumber = it
            })
        }
    }

    /**
     * 提交
     */
    private fun submit() {
        val homeNode = HomeNode()
        val model = HomeModel()
        model.id = mIDNumber + 1
        model.name = "木易"
        model.content = edt_add_node.text.toString()
        model.time = System.currentTimeMillis().toString()
        val mPictures = arrayListOf<HomePictureModel>()
        if (mPhotoList.size ?: 0 > 0) {
            for (index in mPhotoList.indices) {
                val mPictureModel = HomePictureModel()
                mPictureModel.imgUrl = mPhotoList[index]
                mPictures.add(mPictureModel)
            }
        }
        homeNode.homeModel = model
        homeNode.pictures = mPictures
        mViewModel.addNode(homeNode)
        ActivityManager.finish(AddNodeActivity::class.java)
    }


}



