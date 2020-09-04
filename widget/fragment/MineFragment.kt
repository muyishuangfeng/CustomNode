package com.yk.silence.customnode.widget.fragment

import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.common.MSG_CODE_UPDATE_INFO
import com.yk.silence.customnode.databinding.FragmentMineBinding
import com.yk.silence.customnode.model.EventModel
import com.yk.silence.customnode.util.EventUtil
import com.yk.silence.customnode.viewmodel.mine.MineViewModel
import com.yk.silence.customnode.widget.activity.AboutActivity
import com.yk.silence.customnode.widget.activity.MySelfInfoActivity
import org.greenrobot.eventbus.Subscribe

class MineFragment : BaseVMFragment<MineViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getFragmentID() = R.layout.fragment_mine

    override fun viewModelClass() = MineViewModel::class.java

    override fun initBinding(mBinding: FragmentMineBinding) {
        super.initBinding(mBinding)
        EventUtil.register(this)
        mBinding.imgMineAvatar.setOnClickListener {
            ActivityManager.start(MySelfInfoActivity::class.java)
        }
        mBinding.txtMineAbout.setOnClickListener {
            ActivityManager.start(AboutActivity::class.java)
        }
        mBinding.txtMineSetting.setOnClickListener {
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getData()
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            mUserModel.observe(viewLifecycleOwner, Observer {
                mBinding.user = it
            })
        }
    }

    @Subscribe
    fun onEvent(event: Any) {
        val mData = event as EventModel
        if (mData.code == MSG_CODE_UPDATE_INFO) {
            mViewModel.getData()
        }
    }

    override fun onDestroy() {
        EventUtil.unRegister(this)
        super.onDestroy()
    }
}