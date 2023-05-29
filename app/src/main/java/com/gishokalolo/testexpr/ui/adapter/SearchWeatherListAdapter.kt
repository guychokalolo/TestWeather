package com.gishokalolo.testexpr.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gishokalolo.testexpr.R
import com.gishokalolo.testexpr.databinding.ItemWeatherBinding
import com.gishokalolo.testexpr.domain.model.ParamsForAdapter

class SearchWeatherListAdapter(
    private val isFavoriteCheck: (ParamsForAdapter) -> Unit,
    private val city: (String, Long) -> Unit
) :
    RecyclerView.Adapter<SearchWeatherListAdapter.WeatherListViewHolder>() {

    private var data: List<ParamsForAdapter> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherListViewHolder(ItemWeatherBinding.bind(view))
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    /**
     * Получить и обновить список с данными
     *
     * @param newData         список с данными
     */
    fun setData(newData: List<ParamsForAdapter>) {
        val getDiffUtilCallbackDate = getDiffUtilCallbackDate(newData)
        val diffResult = DiffUtil.calculateDiff(getDiffUtilCallbackDate)
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Обновление списка с данными
     *
     * @param newData      список данных
     */
    private fun getDiffUtilCallbackDate(newData: List<ParamsForAdapter>) =
        object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = data.size

            override fun getNewListSize(): Int = newData.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                data[oldItemPosition].name == newData[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                data[oldItemPosition] == newData[newItemPosition]
        }

    inner class WeatherListViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParamsForAdapter) {
            with(binding) {
                title.text = item.name
                checkbox.isChecked = item.isFavorite
                checkbox.setOnCheckedChangeListener { _, b ->
                    isFavoriteCheck.invoke(item.copy(name = item.name, isFavorite = b))
                }
                title.setOnClickListener {
                    city.invoke(item.name, item.id)
                }
            }
        }
    }
}