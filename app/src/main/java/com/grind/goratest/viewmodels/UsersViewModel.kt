package com.grind.goratest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grind.goratest.models.User
import com.grind.goratest.repositories.DataRepository
import com.grind.goratest.utils.SingleLiveEvent

class UsersViewModel: ViewModel() {
    val usersData: LiveData<List<User>> = SingleLiveEvent()
    private val repository = DataRepository()

    fun getUsersList(){
        val usersList = repository.getUsersList()
        (usersData as SingleLiveEvent).postValue(usersList)
    }
}