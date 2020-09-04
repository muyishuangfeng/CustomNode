package com.yk.silence.customnode.widget.activity

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.ac.BaseVMActivity
import com.yk.silence.customnode.common.*
import com.yk.silence.customnode.databinding.ActivityAddNodeBinding
import com.yk.silence.customnode.db.node.HomeModel
import com.yk.silence.customnode.db.node.HomeNode
import com.yk.silence.customnode.db.node.HomePictureModel
import com.yk.silence.customnode.im.CThreadPoolExecutor
import com.yk.silence.customnode.impl.OnCameraClickListener
import com.yk.silence.customnode.impl.OnCameraResultListener
import com.yk.silence.customnode.impl.OnOssResultListener
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.ui.dialog.ChooseDialogHelper
import com.yk.silence.customnode.util.CameraUtil
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.util.oss.OSSHelper
import com.yk.silence.customnode.viewmodel.home.HomeViewModel
import com.yk.silence.customnode.widget.adapter.AddNodeAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_add_node.*

class AddNodeActivity : BaseVMActivity<HomeViewModel, ActivityAddNodeBinding>() {

    private lateinit var mAdapter: AddNodeAdapter
    private var mPhotoList: MutableList<String> = arrayListOf()
    private var mIDNumber = 0

    override fun getLayoutID() = R.layout.activity_add_node

    override fun viewModelClass() = HomeViewModel::class.java


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
                                            this@AddNodeActivity
                                        )
                                    } else {
                                        //跳转到相机
                                        CameraUtil.openCamera(
                                            this@AddNodeActivity
                                        )
                                    }
                                }
                            })
                    }
                })
        }
        binding.rlvAddNode.adapter = mAdapter

    }

    override fun initData() {
        super.initData()
        mViewModel.queryMAXID()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtil.onActivityResult(requestCode, resultCode, data, object : OnCameraResultListener {
            override fun onCameraResult(path: String?) {
                if (path != null) {
                    uploadFile(path)
                }

            }

            override fun onAlbumResult(path: String?) {
                if (path != null) {
                    uploadFile(path)
                }
            }

        })

    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            mNumber.observe(this@AddNodeActivity, Observer {
                mIDNumber = it
            })
            mAddStatus.observe(this@AddNodeActivity, Observer {
                EventUtil.send(EventModel(MSG_CODE_ADD_NODE))
                ActivityManager.finish(AddNodeActivity::class.java)
            })
            mUserNodeMode.observe(this@AddNodeActivity, Observer {
                mViewModel.addNode(it, mPhotoList)
            })


        }
    }

    /**
     * 提交
     */
    private fun submit() {
        if (mPhotoList.size ?: 0 > 0) {
            mViewModel.addNetNode(mPhotoList, mBinding.edtAddNode.text.toString())
        }
    }

    /**
     * 上传文件
     */
    private fun uploadFile(path: String) {
        val mName = System.currentTimeMillis().toString() + ".jpg"
        CThreadPoolExecutor.runInBackground(Runnable {
            OSSHelper.updateFile(
                APP.sInstance.applicationContext,
                mName,
                path,
                object : OnOssResultListener {
                    override fun onResultSuccess() {
                        val mPhotoUrl = BASE_OSS_URL + mName
                        runOnUiThread {
                            mPhotoList.add(mPhotoUrl)
                            mAdapter.setNewData(mPhotoList)
                        }

                    }

                    override fun onResultFailed() {

                    }

                })
        })

    }

}



