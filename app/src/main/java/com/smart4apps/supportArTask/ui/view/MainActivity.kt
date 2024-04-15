package com.smart4apps.supportArTask.ui.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.smart4apps.supportArTask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //for prevent users from taking screenshots or recording the screen while opening the app
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)


    }


    fun showProgress(){
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(){
        binding.progressBar.visibility = View.GONE
    }

}
