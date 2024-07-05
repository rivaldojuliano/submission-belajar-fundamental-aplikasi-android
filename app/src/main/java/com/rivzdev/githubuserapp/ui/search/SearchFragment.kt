package com.rivzdev.githubuserapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    viewModel.getUser(searchView.text.toString())
                    searchView.hide()
                    false
                }

            val layoutManager = LinearLayoutManager(requireActivity())
            rvUsers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            rvUsers.addItemDecoration(itemDecoration)
        }

        showRecyclerViewUser()
        showLoading()
    }

    private fun showRecyclerViewUser() {
        val adapter = SearchUserAdapter()
        binding.rvUsers.adapter = adapter

        viewModel.items.observe(requireActivity()) {
            adapter.submitList(it)
        }

        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback{
            override fun onItemClicked(item: ItemsItem) {
                val toDetailUserFragment = SearchFragmentDirections.actionSearchFragmentToDetailUserFragment(item)
                findNavController().navigate(toDetailUserFragment)
            }
        })
    }

    private fun showLoading() {
        viewModel.isLoading.observe(requireActivity()) {
            isLoading(it)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}