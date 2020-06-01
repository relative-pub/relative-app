package br.com.devteam.relative.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.devteam.relative.R
import br.com.devteam.relative.databinding.CategoryFragmentBinding
import br.com.devteam.relative.domain.Category
import br.com.devteam.relative.interfaces.LoadingActivity
import br.com.devteam.relative.view.adapter.SearchAdapter
import br.com.devteam.relative.viewmodel.CategoryViewModel
import br.com.devteam.relative.viewmodel.UserCategory
import kotlinx.android.synthetic.main.category_fragment.*

class CategoryFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var binding: CategoryFragmentBinding

    var laodingActivity: LoadingActivity? = null

    private val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoryFragmentBinding.inflate(inflater, container, false)
        binding.fragment = this@CategoryFragment
        binding.lifecycleOwner = this

        laodingActivity = activity as LoadingActivity

//        configureRecyclerView()
        val rcvCategory = binding.rcvCategory
        rcvCategory.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        showCategories()

        return binding.root
    }

    private fun configureRecyclerView() {
        rcv_category.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
    }

    private fun showCategories() {
        viewModel.userCategories.observe(requireActivity(), Observer { userCategories ->
            val adapter = SearchAdapter(userCategories, viewModel, {
//                val intentSpotifyTrack = Intent("SPOTMUSICK_TRACK")
//                intentSpotifyTrack.addCategory("SPOTMUSICK_TRACK_DETAIL")
//                intentSpotifyTrack.putExtra("track", it)
//                startActivity(intentSpotifyTrack)
            }, { userCategory: UserCategory, b: Boolean ->
                laodingActivity!!.showLoading()
                if (!userCategory.checked) {
                    viewModel.addCategoryToUserProfile(currentUserCategory = userCategory) {
                        laodingActivity!!.hideLoading()
                    }
                } else {
                    viewModel.removeCategoryFromUserProfile(currentUserCategory = userCategory) {
                        laodingActivity!!.hideLoading()
                    }
                }
                Log.println(
                    Log.WARN,
                    "change-checkbox",
                    "Categoria : ${userCategory.category.name}, checked ? ${b.toString()}"
                )

            })
            rcv_category.adapter = adapter
        })


    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // TODO: Use the ViewModel
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbar, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
//            val editext = searchView.findViewById<EditText>(android.widget.SearchView.NO_ID)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.discovery(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    if(newText!!.isNotEmpty()){
//                        val search = newText.toLowerCase()
//
//                    }else{
//                    }
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

}
