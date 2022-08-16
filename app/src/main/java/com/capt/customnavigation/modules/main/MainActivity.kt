package com.capt.customnavigation.modules.main

import com.capt.customnavigation.R
import com.capt.customnavigation.base.BaseActivity
import com.capt.customnavigation.navigation.Navigation
import com.capt.customnavigation.navigation.NavigationContainer

class MainActivity : BaseActivity(), NavigationContainer {
    override val layoutId = R.layout.activity_main
    override lateinit var navigation: Navigation

    override fun initViews() {
        navigation = Navigation(
            supportFragmentManager,
            lifecycle,
            R.id.container,
            MainNavigation.toLogin
        ) {
            finish()
        }
    }
}