package com.grind.goratest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.grind.goratest.fragments.UsersFragment

fun AppCompatActivity.replaceFragment(fragment: Fragment, addToBackStack: Boolean){
    val transaction = supportFragmentManager.beginTransaction()
        .replace(R.id.main_container, fragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

    if(addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)

    transaction.commitAllowingStateLoss()
}

fun Fragment.replaceFragment(fragment: Fragment, addToBackStack: Boolean){
    val transaction = fragmentManager?.beginTransaction()
        ?.replace(R.id.main_container, fragment)
        ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

    if(addToBackStack) transaction?.addToBackStack(fragment::class.java.simpleName)

    transaction?.commitAllowingStateLoss()
}

fun Int.toDp(context: Context): Int{
    return (this * context.resources.displayMetrics.density).toInt()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(UsersFragment(), false)

    }
}