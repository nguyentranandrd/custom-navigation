package com.capt.customnavigation.modules.detail

import com.capt.customnavigation.R
import com.capt.customnavigation.base.BaseFragment
import com.capt.customnavigation.modules.login.LoginFragment
import com.capt.customnavigation.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.tv_back_login

class DetailFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_detail

    override fun initListeners() {
        tv_back_home.setOnClickListener {
            findNavController().pop()
        }
        tv_back_login.setOnClickListener {
            findNavController().popUpTo(LoginFragment::class.java)
        }
        tv_finish.setOnClickListener {
            findNavController().popUpTo(LoginFragment::class.java, true)
        }
    }
}