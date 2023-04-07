package com.example.leadsassessment.ui.home_categoy_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.leadsassessment.R
import com.example.leadsassessment.databinding.FragmentHomeBinding
import com.example.leadsassessment.api.model.product_category.CategoryBaseModelItem
import com.example.leadsassessment.utils.ViewState
import com.example.leadsassessment.utils.hideKeyboard
import com.example.leadsassessment.utils.toast
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private var dataAdapter: HomeCategoryListAdapter = HomeCategoryListAdapter()
    private val viewModel: HomeViewModel by inject()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       // (activity as MainActivity?)?.setActionBarTitle(getString(R.string.toolbar_title_favourite_list)
        //(activity as MainActivity?)?.setActionBarTitle("test")
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        getCategoryList(null)
        initClickLister()
    }


    private fun initView() {
        binding?.categoryListRecyclerView?.let { view ->
            with(view) {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = dataAdapter
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.ShowMessage -> {
                    requireContext().toast(state.message)
                }
                is ViewState.KeyboardState -> {
                    hideKeyboard()
                }
                is ViewState.ProgressState -> {
                    if (state.isShow) {
                        binding?.progressBar?.visibility = View.VISIBLE
                    } else {
                        binding?.progressBar?.visibility = View.GONE
                    }
                }

                else -> {}
            }
        }
    }

    //function ClickListener
    private fun initClickLister() {
        dataAdapter.onItemClick = { model, _ ->
            if (model.products.isNotEmpty()){
                goToProductListFragment(model)
            }else{
                Toast.makeText(activity, "Products Unavailable", Toast.LENGTH_SHORT).show()
            }

        }

    }


    //function for fetch the CategoryList from API
    private fun getCategoryList(categoryId: Int?) {

        viewModel.getCategoryList(categoryId).observe(viewLifecycleOwner) { list ->
                val dataList = list.size
                Timber.d("size: $dataList")
                //Toast.makeText(activity, "Total List = $dataList", Toast.LENGTH_SHORT).show()
                //Timber.d("response:${dataAdapter.initLoad(list)}")
                if (list.isEmpty()) {
                    binding?.emptyView?.visibility = View.VISIBLE
                } else {
                    binding?.emptyView?.visibility = View.GONE
                    dataAdapter.initLoad(list)
                }
            }
    }

    //transfer data model to the ProductListFragment by bundle
    private fun goToProductListFragment(model: CategoryBaseModelItem) {
        val bundle = bundleOf("model" to model)
        Timber.d("modelMenu $bundle")
        // Toast.makeText(activity, model.categoryDetails.first().name, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_nav_dashboard_to_productListFragment, bundle)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}