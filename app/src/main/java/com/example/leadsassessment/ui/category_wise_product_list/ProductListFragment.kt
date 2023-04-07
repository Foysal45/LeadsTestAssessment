package com.example.leadsassessment.ui.category_wise_product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.leadsassessment.R
import com.example.leadsassessment.databinding.FragmentProductListBinding
import com.example.leadsassessment.api.model.product_category.CategoryBaseModelItem
import com.example.leadsassessment.ui.home_categoy_list.HomeViewModel
import com.example.leadsassessment.utils.ViewState
import com.example.leadsassessment.utils.hideKeyboard
import com.example.leadsassessment.utils.toast
import org.koin.android.ext.android.inject
import timber.log.Timber


class ProductListFragment : Fragment() {
    private var binding: FragmentProductListBinding? = null
    private var dataAdapter: ProductListAdapter = ProductListAdapter()
    private val viewModel: HomeViewModel by inject()
    private var model: CategoryBaseModelItem? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentProductListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        receivedDataModelForProductList()
        getCategoryWiseProductList(null)
        initClickLister()
    }


    private fun initView() {
        binding?.productListRecyclerView?.let { view ->
            with(view) {
                setHasFixedSize(true)
                itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = dataAdapter
            }

        }
        viewModel.viewState.observe(viewLifecycleOwner){ state ->
            when (state) {
                is ViewState.ShowMessage -> {
                    requireContext().toast(state.message)
                }
                is ViewState.KeyboardState -> {
                    hideKeyboard()
                }
                is ViewState.ProgressState -> {
                    if (state.isShow) {
                        binding?.progressBarHomeSubFragment?.visibility = View.VISIBLE
                    } else {
                        binding?.progressBarHomeSubFragment?.visibility = View.GONE
                    }
                }

                else -> {}
            }
        }

    }

    //function ClickListener
    private fun initClickLister() {
        dataAdapter.onItemClick = { model, _ ->
            if (!model.products.isNullOrEmpty()){
                goToProductDetailsFragment(model)
            }else{
                Toast.makeText(activity, "Products Unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //function for fetch the ProductList
    private fun getCategoryWiseProductList(categoryId: Int?) {

        viewModel.getCategoryWiseProductList(categoryId).observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()){
                binding?.emptyView?.visibility = View.VISIBLE
            }else{
                binding?.emptyView?.visibility = View.GONE
                dataAdapter.initLoad(list)
            }
        }
    }

    //transfer data model to the ProductDetailsFragment by bundle
    private fun goToProductDetailsFragment(model: CategoryBaseModelItem){
        val bundle = bundleOf("model" to model)
        Timber.d("modelMenu $bundle")
        findNavController().navigate(R.id.action_productListFragment_to_productDetailsFragment,bundle)
    }


    //here received bundle model from HomeFragment
    private fun receivedDataModelForProductList(){
        val bundle: Bundle? = arguments
        bundle?.let {
            this.model = it.getParcelable("model")
            Timber.d("bundleModel $model")
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}