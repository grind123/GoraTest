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

//        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//        }

        replaceFragment(UsersFragment(), false)

    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            val repository = DataRepository()
//
//            val usersList = repository.getUsersList()
//            Log.i("URL", "$usersList")
//            val photosList = repository.getPhotosListByAlbumId(1)
//
//
//            val loader = PictureLoader(this)
//            val bitmap = loader.getPicture(photosList?.first()!!)
//            Log.i("URL", "bitmap - ${bitmap?.byteCount}")
//        }
//    }
}