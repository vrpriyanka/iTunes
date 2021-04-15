package com.ragini.artistdataitunes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ragini.artistdataitunes.R
import com.ragini.artistdataitunes.databinding.SearchFragmentBinding
import com.ragini.artistdataitunes.util.Logger

class SearchFragment : Fragment() {
    private lateinit var iTunesSearchBinding: SearchFragmentBinding
    private var searchText: String = " "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        iTunesSearchBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return iTunesSearchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iTunesSearchBinding.searchButton.setOnClickListener {
            searchText = iTunesSearchBinding.enterArtist.text.toString()
            Logger.printLog("LOG_TAG", "in setOnClickListener -----> $searchText")
            val bundle = Bundle()
            bundle.putString("Search_Text", searchText)
            it?.findNavController()?.navigate(R.id.itune_fragment,bundle)
        }
    }
}