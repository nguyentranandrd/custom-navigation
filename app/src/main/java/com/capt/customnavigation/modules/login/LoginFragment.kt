package com.capt.customnavigation.modules.login

import com.capt.customnavigation.R
import com.capt.customnavigation.base.BaseFragment
import com.capt.customnavigation.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_login

    override fun initListeners() {
        tv_to_home.setOnClickListener {
            findNavController().replace(LoginNavigation.toHome)
        }
    }
}