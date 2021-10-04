package com.assignment.giphyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.assignment.giphyapp.data.model.SavedGif
import com.assignment.giphyapp.databinding.FragmentUserBinding
import com.assignment.giphyapp.ui.adapter.SavedGifAdapter
import com.assignment.giphyapp.ui.viewmodel.GifViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class UserGifFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val gifViewModel: GifViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var savedGifAdapter: SavedGifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //list the saved gifs from room database
        getSavedGifs()
    }

    private fun getSavedGifs() {
        //observe live data and set the data to a recycler view
        gifViewModel.loadSavedGifs().observe(viewLifecycleOwner, {
            setUpRecyclerView(it)
        })
    }

    private fun setUpRecyclerView(savedGifData: List<SavedGif>) {
        with(binding) {
            //show no data found text header if data is empty
            if (savedGifData.isNullOrEmpty()) {
                tvNotFound.visibility = View.VISIBLE
                rvSavedGifs.visibility = View.GONE
            } else {
                tvNotFound.visibility = View.GONE
                rvSavedGifs.visibility = View.VISIBLE
                rvSavedGifs.layoutManager = GridLayoutManager(requireContext(), 2)
                savedGifAdapter = SavedGifAdapter(savedGifData, gifViewModel)
                rvSavedGifs.adapter = savedGifAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}