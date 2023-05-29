package com.gishokalolo.testexpr.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gishokalolo.testexpr.App
import com.gishokalolo.testexpr.R
import com.gishokalolo.testexpr.databinding.FragmentBottomSheetWeatherListBinding
import com.gishokalolo.testexpr.domain.state.DataState
import com.gishokalolo.testexpr.ui.adapter.SearchWeatherListAdapter
import com.gishokalolo.testexpr.ui.viewmodel.SearchWeatherListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchWeatherFragment : Fragment() {
    private var _binding: FragmentBottomSheetWeatherListBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: SearchWeatherListViewModel.Factory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[SearchWeatherListViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchWeatherListAdapter(
            isFavoriteCheck = { params ->
                viewModel.insertFavorite(params)
            },
            city = { city, id ->
                HomeFragment.newInstance(id = id, city = city)
                Navigation.findNavController(view).navigate(R.id.homeFragment)
            }
        )
        binding.recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recycler.adapter = adapter
        binding.searchButton.setOnClickListener {
            viewModel.setCity(binding.searchText.text.toString())
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityFavoriteStateFlow.collect {
                    when (it) {
                        is DataState.Success -> {
                            adapter.setData(it.data)
                        }
                        is DataState.Error -> {
                            Log.d("TAG", "Error ", it.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}