package com.grind.goratest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grind.goratest.models.User
import com.grind.goratest.repositories.DataRepository

class UsersViewModel: ViewModel() {
    val usersData: LiveData<List<User>> = MutableLiveData()
    private val repository = DataRepository()

    fun getUsersList(){
        val usersList = repository.getUsersList()
        (usersData as MutableLiveData).postValue(usersList)
    }
}