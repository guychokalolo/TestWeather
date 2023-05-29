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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gishokalolo.testexpr.App
import com.gishokalolo.testexpr.databinding.FragmnetFavoriteBinding
import com.gishokalolo.testexpr.domain.state.DataState
import com.gishokalolo.testexpr.ui.adapter.FavoriteAdapter
import com.gishokalolo.testexpr.ui.viewmodel.FavoriteViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private var _binding: FragmnetFavoriteBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: FavoriteViewModel.Factory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmnetFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoriteAdapter(locationItem = { location ->
            viewModel.deleteFavorite(location)
        })
        binding.recyclerFavorite.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerFavorite.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cityFavoriteStateFlow.collect {
                    when (it) {
                        is DataState.Loading -> {
                            //progressBar.visibility = View.VISIBLE
                        }
                        is DataState.Success -> {
                            //progressBar.visibility = View.GONE
                            adapter.setData(it.data)
                        }
                        is DataState.Error -> {
                            Log.d("TAG", "Error ", it.message)
                        }
                        is DataState.Empty -> {
                        }
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