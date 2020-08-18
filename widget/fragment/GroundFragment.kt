package com.yk.silence.customnode.widget.fragment

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseFragment
import com.yk.silence.customnode.common.ActivityManager
import com.yk.silence.customnode.databinding.FragmentGroundBinding
import com.yk.silence.customnode.widget.activity.CircleActivity

class GroundFragment : BaseFragment<FragmentGroundBinding>() {

    companion object {
        fun newInstance() = GroundFragment()
    }

    override fun getFragmentID() = R.layout.fragment_ground

    override fun initBinding(mBinding: FragmentGroundBinding) {
        super.initBinding(mBinding)
        mBinding.txtGroundCircle.setOnClickListener {
            ActivityManager.start(CircleActivity::class.java)
        }
    }
}