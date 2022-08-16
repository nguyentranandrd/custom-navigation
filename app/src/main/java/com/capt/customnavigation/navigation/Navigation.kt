package com.capt.customnavigation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.capt.customnavigation.R

class Navigation(
    val fm: FragmentManager,
    lifecycle: Lifecycle,
    private val rootId: Int,
    action: Action,
    private val onStackClear: () -> Unit
) {
    private var isReady = false

    private var unCommittedTransaction: FragmentTransaction? = null

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> isReady = true
                    Lifecycle.Event.ON_START -> {
                        isReady = true
                        resolveUncommittedTransition()
                    }
                    Lifecycle.Event.ON_STOP -> isReady = false
                    else -> {
                        // Do nothing
                    }
                }
            }
        })
        if (fragmentCount == 0) {
            replace(action)
        } else {
            for (frg in fm.fragments) {
                registerFragmentDestroyed(frg)
            }
        }
    }

    fun resolveUncommittedTransition() {
        unCommittedTransaction?.apply {
            commit()
            unCommittedTransaction = null
        }
    }

    val fragmentCount: Int
        get() = fm.backStackEntryCount

    private fun show(
        action: Action,
        isReplace: Boolean
    ) {
        val fragment = initializeFragment(action.fragment)!!
        val tag = action.fragment.name
        val transaction = fm.beginTransaction().setCustomAnimations(
            action.enterAnim, action.exitAnim,
            action.popEnterAnim, action.popExitAnim
        )
        if (fragment.isAdded) return
        fragment.arguments = action.arguments
        if (isReplace) {
            transaction.replace(rootId, fragment, tag)
        } else {
            for (lastFragment in fm.fragments) {
                if (lastFragment.isVisible)
                    transaction.hide(lastFragment)
            }
            transaction.add(rootId, fragment, tag)
        }
        if (action.addToBackStack) {
            transaction.addToBackStack(tag)
        }
        if (isReady)
            transaction.commit()
        else
            unCommittedTransaction = transaction
        registerFragmentDestroyed(fragment)
    }

    private fun registerFragmentDestroyed(fragment: Fragment) {
        fragment.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    if (fm.fragments.size == 0) {
                        onStackClear()
                    }
                    if (fm.fragments.size >= 2)
                        for (i in 0..fm.fragments.size - 2) {
                            val lastFragment = fm.fragments[i]
                            if (lastFragment.isVisible)
                                fm.beginTransaction().hide(lastFragment).commit()
                        }
                }
            }
        })
    }

    private fun <F : Fragment?> initializeFragment(fragmentClazz: Class<F>): F? {
        return try {
            val tag = fragmentClazz.name
            var fragment = fragmentClazz.cast(fm.findFragmentByTag(tag))
            if (fragment == null) fragment = fragmentClazz.getConstructor().newInstance()
            fragment
        } catch (e: Exception) {
            throw FragmentNotFoundException()
        }
    }

    fun add(action: Action) {
        show(action, false)
    }

    fun replace(action: Action) {
        show(action, true)
    }

    fun pop() {
        fm.popBackStack()
    }

    @JvmOverloads
    fun popUpTo(fragmentClazz: Class<out Fragment?>, inclusive: Boolean = false) {
        val tag = fragmentClazz.name
        fm.popBackStack(tag, if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
    }

    data class Action(
        var fragment: Class<out Fragment>,
        var enterAnim: Int = R.anim.translate_enter,
        var exitAnim: Int = R.anim.translate_exit,
        var popEnterAnim: Int = R.anim.translate_pop_enter,
        var popExitAnim: Int = R.anim.translate_pop_exit,
        var arguments: Bundle? = null,
        var addToBackStack: Boolean = true
    ) {
        fun arguments(arguments: Bundle): Action {
            this.arguments = arguments
            return this
        }

        fun fragment(fragment: Class<out Fragment>): Action {
            this.fragment = fragment
            return this
        }

        fun addToBackStack(addToBackStack: Boolean): Action {
            this.addToBackStack = addToBackStack
            return this
        }

        fun enterAnim(enterAnim: Int): Action {
            this.enterAnim = enterAnim
            return this
        }

        fun exitAnim(exitAnim: Int): Action {
            this.exitAnim = exitAnim
            return this
        }

        fun popEnterAnim(popEnterAnim: Int): Action {
            this.popEnterAnim = popEnterAnim
            return this
        }

        fun popExitAnim(popExitAnim: Int): Action {
            this.popExitAnim = popExitAnim
            return this
        }
    }
}