package com.example.viewmodellivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.viewModels
import com.example.viewmodellivedata.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel1 by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.callingImageDogApi(binding.imageDog)

        viewModel.dogImage.observe(this) { dogImage ->
            when (dogImage) {
                is Response.Error -> Toast.makeText(this , "no" , LENGTH_LONG).show()
                Response.Loading -> Toast.makeText(this , "loading" , LENGTH_LONG).show()
                is Response.Success<Data> -> Picasso.get()
                    .load(dogImage.body?.message)
                    .into(binding.imageDog)
            }
        }

        binding.imageDog.visibility = View.VISIBLE
    }
}