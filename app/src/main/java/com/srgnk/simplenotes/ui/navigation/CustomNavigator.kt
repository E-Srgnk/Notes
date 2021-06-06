package com.srgnk.simplenotes.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.srgnk.simplenotes.R

class CustomNavigator(activity: FragmentActivity, containerId: Int) : AppNavigator(activity, containerId) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        fragmentTransaction.setCustomAnimations(
            R.anim.to_left_in,
            R.anim.to_left_out,
            R.anim.to_right_in,
            R.anim.to_right_out
        )
        super.setupFragmentTransaction(screen, fragmentTransaction, currentFragment, nextFragment)
    }
}