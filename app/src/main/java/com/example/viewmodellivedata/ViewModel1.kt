package com.example.viewmodellivedata

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ViewModel1 : ViewModel() {

    private var _dogImage = MutableLiveData<Response<Data>>()

     val dogImage : LiveData<Response<Data>>
        get() = _dogImage

    private var dogApiService: DogApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dogApiService = retrofit.create(DogApiService::class.java)
    }

    fun callingImageDogApi(dog: ImageView) {
        _dogImage.postValue(Response.Loading)
        viewModelScope.launch {
            try {
                val response = dogApiService.getRandomDogImage()

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