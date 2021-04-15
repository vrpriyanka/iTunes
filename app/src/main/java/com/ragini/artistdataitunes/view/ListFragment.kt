package com.ragini.artistdataitunes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ragini.artistdataitunes.databinding.FragmentListBinding
import com.ragini.artistdataitunes.model.ITunes
import com.ragini.artistdataitunes.di.ApiModule
import com.ragini.artistdataitunes.util.Logger
import com.ragini.artistdataitunes.util.Status
import com.ragini.artistdataitunes.viewmodel.ListViewModel
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ListFragment : Fragment() {
    private lateinit var iTunesListBinding: FragmentListBinding
    private lateinit var iTunesAdapter: ITunesAdapter
    private lateinit var viewModel: ListViewModel
    private var iTunesList = ArrayList<ITunes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        iTunesListBinding = FragmentListBinding.inflate(inflater, container, false)
        return iTunesListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iTunesAdapter = ITunesAdapter(iTunesList)
        val layoutManager = LinearLayoutManager(activity)
        iTunesListBinding.recyclerviewITunes.layoutManager = layoutManager
        iTunesListBinding.recyclerviewITunes.adapter = iTunesAdapter
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filterList = arrayListOf<ITunes>()
        // running a for loop to compare elements.
        for (item in iTunesList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.artistName?.toLowerCase(Locale.ROOT)
                    ?.contains(text.toLowerCase(Locale.ROOT)) == true
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filterList.add(item)
            }
        }
        if (filterList.isNotEmpty()) {
            // at last we are passing that filtered
            // list to our adapter class.
            iTunesAdapter.apply {
                addITunesItems(filterList)
                notifyDataSetChanged()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val searchText = arguments?.getString("Search_Text").toString()
        fetchSearchResult(searchText)
        filter(searchText)
        viewModel.setApiService(ApiModule().provideITunesApiService())
        viewModel.refresh()
    }

    private fun fetchSearchResult(searchKey: String) {
        viewModel.getListITunes(searchKey).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Logger.printLog("LOG_TAG", "data loaded")
                        iTunesListBinding.progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        Logger.printLog("LOG_TAG", "Status.ERROR ")
                        iTunesListBinding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        Logger.printLog("LOG_TAG", "Status.LOADING")
                        iTunesListBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(listITunes: List<ITunes>) {
        iTunesAdapter.apply {
            addITunesItems(listITunes)
            notifyDataSetChanged()
        }
    }
}