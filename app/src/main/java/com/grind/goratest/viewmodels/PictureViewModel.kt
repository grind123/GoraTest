package com.grind.goratest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grind.goratest.models.Picture
import com.grind.goratest.repositories.DataRepository
import com.grind.goratest.utils.PictureLoader

class PictureViewModel(): ViewModel() {
    val picturesData: LiveData<List<Picture>> = MutableLiveData()
    private val repository = DataRepository()

    fun getPicturesList(userId: Int){
        val resultList = mutableListOf<Picture>()
        val albums = repository.getAlbumsListByUserId(userId)
        albums?.parallelStream()?.forEach {
            resultList.addAll(repository.getPhotosListByAlbumId(it.id)!!)
        }
        (picturesData as MutableLiveData).postValue(resultList)
    }
}