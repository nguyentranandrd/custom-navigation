package com.capt.customnavigation.modules.home

import com.capt.customnavigation.R
import com.capt.customnavigation.base.BaseFragment
import com.capt.customnavigation.modules.login.LoginFragment
import com.capt.customnavigation.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_home

    override fun initListeners() {
        tv_back_login.setOnClickListener {
            findNavController().pop()
        }

        tv_to_detail.setOnClickListener {
            findNavController().replace(HomeNavigation.toDetail)
        }

        tv_finish.setOnClickListener {
            findNavController().popUpTo(LoginFragment::class.java, true)
        }
    }
}