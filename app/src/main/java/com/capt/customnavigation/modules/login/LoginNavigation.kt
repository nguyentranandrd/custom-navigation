package com.capt.customnavigation.modules.login

import com.capt.customnavigation.modules.home.HomeFragment
import com.capt.customnavigation.navigation.Navigation

object LoginNavigation {
    val toHome = Navigation.Action(fragment = HomeFragment::class.java)
}