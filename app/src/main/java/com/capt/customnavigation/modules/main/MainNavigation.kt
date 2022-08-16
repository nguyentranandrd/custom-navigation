package com.capt.customnavigation.modules.main

import com.capt.customnavigation.modules.login.LoginFragment
import com.capt.customnavigation.navigation.Navigation

object MainNavigation {
    val toLogin = Navigation.Action(fragment = LoginFragment::class.java)
}