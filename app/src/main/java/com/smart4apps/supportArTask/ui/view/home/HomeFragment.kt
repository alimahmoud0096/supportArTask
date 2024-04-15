package com.smart4apps.supportArTask.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.smart4apps.supportArTask.BuildConfig
import com.smart4apps.supportArTask.R
import com.smart4apps.supportArTask.databinding.FragmentHomeBinding
import com.smart4apps.supportArTask.ui.adapter.NewsAdapter
import com.smart4apps.supportArTask.ui.adapter.ViewPagerAdapter
import com.smart4apps.supportArTask.ui.view.home.favourits.FavouritsFragment
import com.smart4apps.supportArTask.ui.view.home.news.ListNewsFragment
import com.smart4apps.supportArTask.ui.viewmodel.MainViewModel
import com.smart4apps.supportArTask.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //we used this condition to verify if app is authorized or not
        init()

    }


    private fun init() {
        val fragments = listOf(
            ListNewsFragment.newInstance(),
            FavouritsFragment.newInstance()
        ) // Replace with your fragment instances
        val adapter = ViewPagerAdapter(fragments, this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Set tab text or icon here if needed
            tab.text =
                when (position) {
                    0 -> getText(R.string.search_)
                    else -> getText(R.string.my_favourits)
                }
        }.attach()

    }


}