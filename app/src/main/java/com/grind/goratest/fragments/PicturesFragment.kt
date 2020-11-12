package com.grind.goratest.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grind.goratest.App
import com.grind.goratest.R
import com.grind.goratest.adapters.PicturesAdapter
import com.grind.goratest.toDp
import com.grind.goratest.utils.PictureLoader
import com.grind.goratest.viewmodels.PictureViewModel
import kotlin.concurrent.thread

class PicturesFragment: Fragment() {

    private lateinit var backButton: View
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PictureViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = View.inflate(context, R.layout.fragment_pictures, null)
        backButton = v.findViewById(R.id.ll_back_button)
        progressBar = v.findViewById(R.id.progress_bar)
        recyclerView = v.findViewById(R.id.rv_photos)

        configureRecyclerView(recyclerView)
        backButton.setOnClickListener { fragmentManager?.popBackStack() }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(App())).get(
            PictureViewModel::class.java
        )

        viewModel.picturesData.observe({lifecycle}, Observer {
            (recyclerView.adapter as PicturesAdapter).itemsList = it
            progressBar.visibility = View.GONE
            (recyclerView.adapter as PicturesAdapter).notifyDataSetChanged()
        })

        thread (start = true){
            viewModel.getPicturesList(arguments?.getInt("userId")?: 0)
        }

        return v
    }

    private fun configureRecyclerView(rv: RecyclerView){
        rv.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = 8.toDp(context!!)
                outRect.top = 8.toDp(context!!)
            }
        })
        rv.adapter = PicturesAdapter(PictureLoader(context!!))
        rv.layoutManager = LinearLayoutManager(context)
    }
}