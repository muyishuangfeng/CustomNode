package com.yk.silence.customnode.widget.fragment

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseFragment
import com.yk.silence.customnode.databinding.FragmentGroundBinding

class GroundFragment:BaseFragment<FragmentGroundBinding>() {

    companion object {
        fun newInstance() = GroundFragment()
    }

    override fun getFragmentID() = R.layout.fragment_ground
}