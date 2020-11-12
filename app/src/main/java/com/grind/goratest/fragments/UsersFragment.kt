package com.grind.goratest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grind.goratest.App
import com.grind.goratest.R
import com.grind.goratest.adapters.UsersAdapter
import com.grind.goratest.models.User
import com.grind.goratest.replaceFragment
import com.grind.goratest.viewmodels.UsersViewModel
import java.net.InetAddress
import kotlin.concurrent.thread

class UsersFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_users, null)
        recyclerView = v.findViewById(R.id.rv_users)
        configureRecyclerView(recyclerView)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(App())).get(
            UsersViewModel::class.java
        )

        viewModel.usersData.observe({ lifecycle }, {
            if (it != null) {
                (recyclerView.adapter as UsersAdapter).itemsList.addAll(it)
                (recyclerView.adapter as UsersAdapter).notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })

        if(isInternetAvailable()){
            viewModel.getUsersList()
        } else{
            Toast.makeText(context, "Network is disabled", Toast.LENGTH_SHORT).show()
        }

        return v
    }

    private fun configureRecyclerView(rv: RecyclerView){
        rv.adapter = UsersAdapter(object : UsersAdapter.OnUserClickListener {
            override fun onUserClick(user: User) {
                val fragment = PicturesFragment().apply {
                    arguments = Bundle().apply { putInt("userId", user.id) }
                }
                replaceFragment(fragment, true)
            }
        })

        rv.layoutManager = LinearLayoutManager(context)
    }

    private fun isInternetAvailable(): Boolean {
        var ipAddress: InetAddress? = null
        val thread = thread(start = true) {
            try {
                ipAddress = InetAddress.getByName("google.com")
            } catch (e: Exception) {
                ipAddress = null
            }

        }
        thread.join()
        return ipAddress != null && !ipAddress!!.equals("")
    }
}