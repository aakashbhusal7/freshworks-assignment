package com.assignment.giphyapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.giphyapp.data.model.GifData
import com.assignment.giphyapp.data.model.TrendingGifResponse
import com.assignment.giphyapp.databinding.FragmentRemoteBinding
import com.assignment.giphyapp.ext.Resource
import com.assignment.giphyapp.ext.hideKeyboard
import com.assignment.giphyapp.ui.adapter.RemoteGifAdapter
import com.assignment.giphyapp.ui.viewmodel.GifViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class RemoteGifFragment : Fragment(), CoroutineScope {

    private var _binding: FragmentRemoteBinding? = null
    private val gifViewModel: GifViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var gifAdapter: RemoteGifAdapter

    //variables for storing pagination states
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    //define query page size as it is the max limit for an api call to load data
    val queryPageSize = 50

    //start the offset of next page to 50
    var nextPage = 50

    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRemoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //check keyboard tap status to remove the keyboard if open
        // when tapped outside the search bar
        checkKeyboard()
        //function for setting up click on search bar and call api accordingly
        handleSearchQuery()
        //handle pagination services
        checkPagination()
        //load trending gifs if user doesn't type text into a search bar
        if (searchQuery.isEmpty()) getTrendingGifs() else getSearchedGifs()
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private fun checkKeyboard() {
        binding.etSearchQuery.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) view.hideKeyboard(0)
        }
        binding.parentConstraint.setOnClickListener {
            it.hideKeyboard(0)
        }
    }

    private fun checkPagination() {
        //observe the livedata from pagination as we add to it
        //when we scroll down the bottom after it reaches a certain point
        //and modify the loading state of pagination progress bar
        gifViewModel.paginating.observe(viewLifecycleOwner, {
            if (it) {
                binding.paginationProgressBar.visibility = View.VISIBLE
                isLoading
            } else {
                binding.paginationProgressBar.visibility = View.GONE
                !isLoading
            }
        })
    }

    private fun handleSearchQuery() {
        with(binding) {
            ivCancel.setOnClickListener {
                //clear the search text on cross icon click and reset the next page value
                etSearchQuery.text.clear()
                nextPage = 0
            }
            etSearchQuery.addTextChangedListener(object : TextWatcher {
                //variable to hold the value for searched query on text change listener
                private var searchFor = ""

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(
                    query: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    //reset the next page value, i.e. offset during api call
                    nextPage = 0
                    val searchText = query.toString().trim()
                    //call trending gifs if search field is empty
                    if (searchText.isEmpty()) {
                        observeTrendingGifs().observe(viewLifecycleOwner, {
                            it?.let { resource ->
                                handleInitialData(resource)
                            }
                        })
                    }
                    if (searchText.length > 1) ivCancel.visibility =
                        View.VISIBLE else ivCancel.visibility = View.GONE
                    if (searchText == searchFor)
                        return
                    searchFor = searchText
                    launch {
                        //debounce operation to prevent unnecessary api calls
                        delay(delayTime)
                        if (searchText != searchFor)
                            return@launch
                        if (view != null) {
                            if (searchText.isNotEmpty())
                                observerSearchedGifs(searchText).observe(viewLifecycleOwner, {
                                    it?.let { resource ->
                                        handleInitialData(resource)
                                    }
                                })
                        }
                    }
                }

                override fun afterTextChanged(query: Editable?) {
                    searchQuery = query.toString()
                }
            })
        }
    }

    //api call for trending gifs
    private fun observeTrendingGifs(): LiveData<Resource<TrendingGifResponse>> {
        return gifViewModel.retrieveTrendingGifs(
            limit = queryPageSize,
            page = nextPage
        )
    }

    //api call for searched gifs
    private fun observerSearchedGifs(searchQuery: String): LiveData<Resource<TrendingGifResponse>> {
        return gifViewModel.retrieveSearchedGifs(
            query = searchQuery,
            limit = queryPageSize,
            page = nextPage
        )
    }

    private fun getTrendingGifs() {
        //if live data to hold gifs is empty, then hit fresh api call otherwise set adapter accordingly
        //to list the data in a recycler view
        gifViewModel.trendingGifs.value.apply {
            if (this != null && this.isNotEmpty()) setAdapter(this)
            else {
                //offset at default is zero since we don't need to define offset for the 1st api call
                gifViewModel.retrieveTrendingGifs(
                    limit = queryPageSize
                ).observe(viewLifecycleOwner, {
                    it?.let { resource ->
                        handleInitialData(resource)
                    }
                })
            }
        }
    }

    private fun getSearchedGifs() {
        gifViewModel.trendingGifs.value.apply {
            if (this != null && this.isNotEmpty()) setAdapter(this)
            else {
                gifViewModel.retrieveSearchedGifs(
                    limit = queryPageSize,
                    query = searchQuery
                ).observe(viewLifecycleOwner, {
                    it?.let { resource ->
                        handleInitialData(resource)
                    }
                })
            }
        }
    }

    private fun handleInitialData(resource: Resource<TrendingGifResponse>) {
        //hide the progress bar after the completion of operation
        binding.progressBar.visibility = View.GONE
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                with(binding) {
                    resource.data?.let {
                        rvGifs.visibility = View.VISIBLE
                        tvNotFound.visibility = View.GONE
                        val data = it.gifData
                        //store the data in live data which we can observe
                        gifViewModel.trendingGifs.value = data
                        isLastPage =
                            it.pagination?.count == it.pagination?.totalCount
                        //set up recycler view to list the stored data from a live data
                        setAdapter(data)
                    }
                }
            }
            Resource.Status.ERROR, Resource.Status.NO_INTERNET -> {
                resource.message?.let {
                    Toast.makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Resource.Status.LOADING -> if (binding.etSearchQuery.text.isEmpty()) binding.progressBar.visibility =
                View.VISIBLE
        }
    }

    private fun setAdapter(data: List<GifData>?) {
        if (data.isNullOrEmpty()) {
            binding.tvNotFound.visibility = View.VISIBLE
            binding.rvGifs.visibility = View.GONE
        } else {
            //set up a recycler view
            binding.tvNotFound.visibility = View.GONE
            binding.rvGifs.layoutManager = LinearLayoutManager(requireContext())
            gifAdapter = RemoteGifAdapter(gifViewModel, viewLifecycleOwner)
            gifAdapter.setOnItemClickListener {
            }
            binding.rvGifs.adapter = gifAdapter
            gifAdapter.differ.submitList(data)
            //add scroll listener for pagination
            binding.rvGifs.addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            //store values to check pagination eligibility
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLastPage && !isLoading
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= queryPageSize
            //check pagination eligibility
            val shouldPaginate = isNotLoadingAndNotLastPage &&
                    isAtLastItem &&
                    isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                Timber.d("pagination")
                isScrolling = false
                if (searchQuery.isEmpty()) {
                    observeTrendingGifs().observe(viewLifecycleOwner, {
                        it?.let { resource ->
                            handlePaginationData(resource)
                        }
                    })
                } else {
                    observerSearchedGifs(searchQuery).observe(viewLifecycleOwner, {
                        it?.let { resource ->
                            handlePaginationData(resource)
                        }
                    })
                }
            }
        }
    }

    private fun handlePaginationData(resource: Resource<TrendingGifResponse>) {
        gifViewModel.paginating.value = false
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                nextPage += 50
                val newData = resource.data?.gifData
                val oldData = gifViewModel.trendingGifs.value
                val data = newData?.let { oldData?.plus(it) }
                //update the pagination data to view model so that starting point
                //of next page is updated accordingly
                gifViewModel.trendingGifs.value = data
                isLastPage =
                    resource.data?.pagination?.count == resource.data?.pagination?.totalCount
                //call the adapter function with new data
                gifAdapter.differ.submitList(data)
            }
            Resource.Status.ERROR, Resource.Status.NO_INTERNET -> {
            }
            Resource.Status.LOADING -> gifViewModel.paginating.value = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //delay value which would act as a debounce during search api call
    companion object {
        const val delayTime: Long = 1000L
    }

}