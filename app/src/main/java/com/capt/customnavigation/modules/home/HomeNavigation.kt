package com.capt.customnavigation.modules.home

import com.capt.customnavigation.modules.detail.DetailFragment
import com.capt.customnavigation.navigation.Navigation

object HomeNavigation {
    val toDetail = Navigation.Action(fragment = DetailFragment::class.java)
}