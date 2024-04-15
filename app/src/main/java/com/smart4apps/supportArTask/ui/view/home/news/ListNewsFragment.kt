package com.smart4apps.supportArTask.ui.view.home.news

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.smart4apps.supportArTask.R
import com.smart4apps.supportArTask.databinding.FragmentHomeBinding
import com.smart4apps.supportArTask.databinding.FragmentListNewsBinding
import com.smart4apps.supportArTask.paging.LoaderAdapter
import com.smart4apps.supportArTask.ui.adapter.NewsAdapter
import com.smart4apps.supportArTask.ui.adapter.ViewPagerAdapter
import com.smart4apps.supportArTask.ui.view.home.favourits.FavouritsFragment
import com.smart4apps.supportArTask.ui.viewmodel.MainViewModel
import com.smart4apps.supportArTask.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListNewsFragment : Fragment() {

    private  val TAG = "ListNewsFragment"

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentListNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //we used this condition to verify if app is authorized or not
        init()

        initializeAdapter()
        addTextWatcher()
        initObservers()
    }

    private fun addTextWatcher() {
        binding.editSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.queryLiveData.value=s.toString()

            }

            override fun afterTextChanged(s: Editable?) {


            }

        })
    }


    private fun init() {



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
                       /* if (loadState.append.endOfPaginationReached && newsAdapter.itemCount < 1) {
                            Log.i(TAG, "initializeAdapter: empty list")

                        } else {
                            Log.i(TAG, "initializeAdapter: list present")
                            binding.progressBar.visibility = View.GONE
                        }*/
                        binding.progressBar.visibility = View.GONE
                    }
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
//                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initObservers() {
        mainViewModel.newsList.observe(viewLifecycleOwner) {
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListNewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/) =
            ListNewsFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}