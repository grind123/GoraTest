package com.grind.goratest.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grind.goratest.R
import com.grind.goratest.models.Picture
import com.grind.goratest.models.User
import com.grind.goratest.utils.PictureLoader

class PicturesAdapter(private val pictureLoader: PictureLoader): RecyclerView.Adapter<PicturesAdapter.PictureHolder>() {

    val itemsList = mutableListOf<Picture>()


    class PictureHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.tv_title)
        val picture: ImageView = view.findViewById(R.id.imv_picture_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureHolder {
        val v = View.inflate(parent.context, R.layout.item_picture, null).apply {
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        }
        return PictureHolder(v)
    }

    override fun onBindViewHolder(holder: PictureHolder, position: Int) {
        val picture = itemsList[position]
        holder.title.text = picture.title
        Thread(Runnable{
            val bitmap = pictureLoader.getPicture(picture)
            holder.picture.post {  holder.picture.setImageBitmap(bitmap) }
        }).start()

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

}