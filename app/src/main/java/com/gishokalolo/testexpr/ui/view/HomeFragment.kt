package com.gishokalolo.testexpr.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gishokalolo.testexpr.App
import com.gishokalolo.testexpr.R
import com.gishokalolo.testexpr.databinding.FragmentHomeBinding
import com.gishokalolo.testexpr.ui.viewmodel.HomeViewMode
import javax.inject.Inject

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewMode.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.component?.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel =
            ViewModelProvider(this, viewModelFactory)[HomeViewMode::class.java]
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel?.let { observeViewModel(it) }
    }

    private fun observeViewModel(viewModel: HomeViewMode) {
        val lifecycleOwner = this.viewLifecycleOwner
        val id = arguments?.getLong(ID)
        val city = arguments?.getString(CITY)
        println("city fragment $city")
        viewModel.setWeather(id, city)
        viewModel.cityEntity.observe(lifecycleOwner) {
            with(binding) {
                nameRegion.text = it.name
                temperature.text = "${ it.temperature} C"
                wind.text = "${ it.wind} kmph"
                precip.text = "${ it.precip}mm"
                Glide.with(requireContext())
                    .load(it.image)
                    .fitCenter()
                    .override(4000, 300)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageWeather)
            }
        }
        binding.searchButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.navigate_to_fragmentWeatherList)
        }
    }

    companion object {
        private const val ID = "ID"
        private const val CITY = "CITY"

        fun newInstance(id: Long, city: String): HomeFragment {
            val bundle = Bundle().apply {
                putLong(ID, id)
                putString(CITY, city)
            }
            return HomeFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}