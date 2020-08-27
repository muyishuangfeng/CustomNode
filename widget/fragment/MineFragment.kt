package com.yk.silence.customnode.widget.fragment

import androidx.lifecycle.Observer
import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseFragment
import com.yk.silence.customnode.base.fg.BaseVMFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.FragmentMineBinding
import com.yk.silence.customnode.util.glide.GlideUtils
import com.yk.silence.customnode.viewmodel.mine.MineViewModel
import com.yk.silence.customnode.widget.activity.MySelfInfoActivity

class MineFragment : BaseVMFragment<MineViewModel, FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun getFragmentID() = R.layout.fragment_mine

    override fun viewModelClass() = MineViewModel::class.java

    override fun initBinding(mBinding: FragmentMineBinding) {
        super.initBinding(mBinding)
        mBinding.imgMineAvatar.setOnClickListener {
            ActivityManager.start(MySelfInfoActivity::class.java)
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
                if (it != null)
                    GlideUtils.loadPathWithOutCache(
                        requireActivity(),
                        it.user_avatar,
                        mBinding.imgMineAvatar
                    )
            })
        }
    }
}