package com.example.leadsassessment.ui.home_categoy_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.leadsassessment.R
import com.example.leadsassessment.api.model.product_category.CategoryBaseModelItem
import com.example.leadsassessment.databinding.ItemViewHomeCategoryListBinding

class HomeCategoryListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val categoryList: MutableList<CategoryBaseModelItem> = mutableListOf()
    var onItemClick: ((mainContentList: CategoryBaseModelItem, position: Int) -> Unit)? = null


    private val options = RequestOptions()
        .placeholder(R.drawable.avatar)
        .error(R.drawable.avatar)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemViewHomeCategoryListBinding = ItemViewHomeCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewModel) {
            val model = categoryList[position]
            val binding = holder.binding

            if (model.products.size>=0){
                //total available product under the category
                val availableProductNumber = model.products.size
                binding.categoryAvailableProductNumber.visibility = View.VISIBLE
                binding.categoryAvailableProductNumber.text =  availableProductNumber.toString()
            }

            if (model.categoryName !=null){
                binding.categoryTitle.text = model.categoryName
                //val text = model.categoryName + " (" + "<font color='#FFFF00'>" + availableProductNumber + "</font>" + ")"
                //binding.categoryTitle.text = Html.fromHtml(text)
            }

            if (model.imageUrl !=null) {
                Glide.with(binding.categoryProductIcon)
                    .load(model.imageUrl)
                    .apply(options)
                    .into(binding.categoryProductIcon)
            }

        }
    }

    override fun getItemCount(): Int =  categoryList.size

    inner class ViewModel(val binding: ItemViewHomeCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(categoryList[absoluteAdapterPosition], absoluteAdapterPosition)
                }
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun initLoad(list: List<CategoryBaseModelItem>) {
        categoryList.clear()
        categoryList.addAll(list)
        notifyDataSetChanged()
    }
}