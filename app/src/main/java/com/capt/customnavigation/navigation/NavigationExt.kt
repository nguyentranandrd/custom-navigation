package com.capt.customnavigation.navigation

import androidx.fragment.app.Fragment

fun Fragment.findNavController(): Navigation {
    if (activity is NavigationContainer)
        return (activity as NavigationContainer).navigation
    throw NavigationNotFoundException()
}

fun Fragment.findParentFragmentNavController(): Navigation {
    if (parentFragment is NavigationContainer)
        return (parentFragment as NavigationContainer).navigation
    throw NavigationNotFoundException()
}