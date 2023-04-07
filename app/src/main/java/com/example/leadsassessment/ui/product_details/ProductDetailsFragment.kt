package com.example.leadsassessment.ui.product_details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.leadsassessment.R
import com.example.leadsassessment.api.model.product_category.CategoryBaseModelItem
import com.example.leadsassessment.databinding.FragmentProductDetailsBinding
import com.example.leadsassessment.ui.home_categoy_list.HomeViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber

class ProductDetailsFragment : Fragment() {

    private var binding: FragmentProductDetailsBinding? = null
    private val viewModel: HomeViewModel by inject()
    private var model: CategoryBaseModelItem? = null
    private var favouriteCount: Int = 0

    private val options = RequestOptions()
        .placeholder(R.drawable.avatar)
        .error(R.drawable.avatar)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentProductDetailsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    @SuppressLint("HardwareIds")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        invisible()
        receivedProductsDetailsDataClass()
        getProductDetails(model?.products?.first()?.id)
        initClickLister()


        //Text justificationMode for align/formatted text start & end properly !!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding?.productDetails?.justificationMode =
                LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

    }


    //function for fetch product details
    private fun getProductDetails(categoryId: Int?) {
        viewModel.getProductDetails(categoryId).observe(viewLifecycleOwner) { list ->

                if (!list.first().products.first().name.isNullOrEmpty()) {
                    binding?.productName?.isVisible = true
                    binding?.productDetails?.isVisible = true
                    binding?.separator?.isVisible = true
                    binding?.iconShare?.isVisible = true
                    binding?.clickFavouriteIcon?.isVisible = true

                    binding?.productName?.text = list.first().products.first().name
                    binding?.productDetails?.text = list.first().products.first().description

                    if (!list.first().products.first().imageURL.isNullOrEmpty()) {
                        Glide.with(binding!!.productImage)
                            .load(list.first().products.first().imageURL)
                            .apply(options)
                            .into(binding!!.productImage)
                    }
                }
        }
    }

    //initially all views are Invisible  And after Data Loaded views will be Visible
    private fun invisible() {
        binding?.productName?.isVisible = false
        binding?.productDetails?.isVisible = false
        binding?.separator?.isVisible = false
        binding?.iconShare?.isVisible = false
        binding?.clickFavouriteIcon?.isVisible = false

    }


    //function ClickListener
    private fun initClickLister() {
        binding?.clickFavouriteIcon?.setOnClickListener {
            favouriteCount++
            if (favouriteCount % 2 == 0) {
                binding?.clickFavouriteIcon?.setImageResource(R.drawable.favourite_unselect)
            } else {
                binding?.clickFavouriteIcon?.setImageResource(R.drawable.favourite_select)
            }
        }

        binding?.iconShare?.setOnClickListener {
            context?.let { it1 -> shareProduct(it1) }
        }

        binding?.btnCheckout?.setOnClickListener{
            Toast.makeText(activity, "Due to API issue Checkout not working !!", Toast.LENGTH_SHORT).show()
        }

    }


    //here received bundle model from ProductListFragment
    private fun receivedProductsDetailsDataClass() {
        val bundle: Bundle? = arguments
        bundle?.let {
            this.model = it.getParcelable("model")
            Timber.d("bundleModel $model")
        }

    }


    //function for share the specific product to others
    private fun shareProduct(context: Context) {
        val appName = context.getString(R.string.app_name)
        val appPlayStoreLink = ""

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, appName)
            putExtra(Intent.EXTRA_TEXT, appPlayStoreLink)
        }
        context.startActivity(Intent.createChooser(sendIntent, null))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}