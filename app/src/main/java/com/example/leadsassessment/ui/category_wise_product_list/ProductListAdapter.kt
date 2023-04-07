package com.example.leadsassessment.ui.category_wise_product_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.leadsassessment.R
import com.example.leadsassessment.databinding.ItemViewHomeCategoryListBinding
import com.example.leadsassessment.api.model.product_category.CategoryBaseModelItem

class ProductListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val productList: MutableList<CategoryBaseModelItem> = mutableListOf()
    var onItemClick: ((productList: CategoryBaseModelItem, position: Int) -> Unit)? = null


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
            val model = productList[position]
            val binding = holder.binding

            if (!model.products.isNullOrEmpty()){
                binding.categoryTitle.text = model.products.first().name
                //val text = model.categoryName + " (" + "<font color='#FFFF00'>" + availableProductNumber + "</font>" + ")"
                //binding.categoryTitle.text = Html.fromHtml(text)
            }
            if (!model.products.isNullOrEmpty()) {
                Glide.with(binding.categoryProductIcon)
                    .load(model.products.first().imageURL)
                    .apply(options)
                    .into(binding.categoryProductIcon)
            }

        }
    }

    override fun getItemCount(): Int =  productList.size

    inner class ViewModel(val binding: ItemViewHomeCategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(productList[absoluteAdapterPosition], absoluteAdapterPosition)
                }
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun initLoad(list: List<CategoryBaseModelItem>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }
}