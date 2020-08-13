package com.yk.silence.customnode.widget.fragment

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseFragment
import com.yk.silence.customnode.databinding.FragmentMineBinding

class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }


    override fun getFragmentID() = R.layout.fragment_mine
}