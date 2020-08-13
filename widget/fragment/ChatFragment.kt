package com.yk.silence.customnode.widget.fragment

import com.yk.silence.customnode.R
import com.yk.silence.customnode.base.fg.BaseFragment
import com.yk.silence.customnode.databinding.FragmentChatBinding

class ChatFragment:BaseFragment<FragmentChatBinding>() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun getFragmentID() = R.layout.fragment_chat
}