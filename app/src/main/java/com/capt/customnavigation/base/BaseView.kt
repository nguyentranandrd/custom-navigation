package com.capt.customnavigation.base

interface BaseView {
    val layoutId: Int
    fun initialize() {
        initViews()
        initComponent()
        initListeners()
        initData()
    }

    fun initViews() {

    }

    fun initComponent() {

    }

    fun initListeners() {

    }

    fun initData() {

    }


}