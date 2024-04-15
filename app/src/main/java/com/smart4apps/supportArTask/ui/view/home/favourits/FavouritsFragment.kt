package com.smart4apps.supportArTask.ui.view.home.favourits

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.smart4apps.supportArTask.R
import com.smart4apps.supportArTask.databinding.FragmentFavouritsBinding
import com.smart4apps.supportArTask.databinding.FragmentHomeBinding
import com.smart4apps.supportArTask.paging.ArticlePagingSource
import com.smart4apps.supportArTask.paging.LoaderAdapter
import com.smart4apps.supportArTask.ui.adapter.NewsAdapter
import com.smart4apps.supportArTask.ui.adapter.ViewPagerAdapter
import com.smart4apps.supportArTask.ui.view.home.news.ListNewsFragment
import com.smart4apps.supportArTask.ui.viewmodel.MainViewModel
import com.smart4apps.supportArTask.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FavouritsFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFavouritsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //we used this condition to verify if app is authorized or not
        initializeAdapter()
        initObservers()
        mainViewModel.getAllFavArticles()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.articlesListLiv.collectLatest { result ->
                    Log.d("TAG", "initObservers: ${result.size}")
                    Utils.createPagingData(result).collect {
                        newsAdapter.submitData(it)
                    }
                }
            }

        }
    }


    private fun initializeAdapter() {

        newsAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rv.apply {
//            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
        }

        newsAdapter.onFavClicked={
            if(it.isFav){
                mainViewModel.removeArticle(it)
            }else{
                mainViewModel.insertArticle(it)
            }
        }

        newsAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    if (loadState.source.refresh is LoadState.NotLoading) {
                        binding.rv.visibility = View.VISIBLE

                    }
                }

                is LoadState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }

                is LoadState.Error -> {
//                    binding.progressBar.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
//                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouritsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/) =
            FavouritsFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}