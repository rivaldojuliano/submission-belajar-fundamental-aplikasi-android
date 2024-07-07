package com.rivzdev.githubuserapp.ui.follow.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.databinding.FragmentFollowersBinding
import com.rivzdev.githubuserapp.ui.detail.DetailUserFragment
import com.rivzdev.githubuserapp.ui.detail.viewpager.SectionPagerAdapter
import com.rivzdev.githubuserapp.ui.follow.FollowAdapter

class FollowersFragment() : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        getFollowers()
    }

    private fun getFollowers() {
        val adapter = FollowAdapter()
        binding.rvFollowers.adapter = adapter

        val user = arguments?.getString(SectionPagerAdapter.EXTRA_USER).toString()

        user.let { viewModel.getFollowers(it) }

        viewModel.followers.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            } else {
                binding.rvFollowers.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}