package com.gishokalolo.testexpr.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gishokalolo.testexpr.R
import com.gishokalolo.testexpr.databinding.ItemFavoriteBinding
import com.gishokalolo.testexpr.domain.model.ParamsForAdapter

class FavoriteAdapter(private val locationItem: (String) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var data: List<ParamsForAdapter> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(ItemFavoriteBinding.bind(view))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
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

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParamsForAdapter) {
            with(binding) {
                title.text = item.name
                delete.setOnClickListener {
                    locationItem.invoke(item.name)
                }
            }
        }
    }
}