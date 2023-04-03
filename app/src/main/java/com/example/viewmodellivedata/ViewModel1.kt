package com.example.viewmodellivedata

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ViewModel1 : ViewModel() {

    private val apiRetrofit = ApiRetrofit().dogApiServiceCallBack()

    private var _dogImage = MutableLiveData<Response<Data>>()

     val dogImage : LiveData<Response<Data>>
        get() = _dogImage


    fun callingImageDogApi(dog: ImageView) {
        _dogImage.postValue(Response.Loading)
        viewModelScope.launch {
            try {
                val response = apiRetrofit.getRandomDogImage()

                if (response.isSuccessful) {
                    val dogImage = response.body()
                    _dogImage.postValue(Response.Success(response.code(), dogImage))
                    Log.e("ViewModel1","Success: ${response.code()}")

                } else {
                    _dogImage.postValue(Response.Error(500, response.message()))
                    Log.e("ViewModel1","Error: ${response.message()}")
                }

            } catch (e:Exception){
                _dogImage.postValue(Response.Error(500,"Not Success") )
                Log.e("ViewModel1","Error: ${e.message}")
            }
        }
    }
}