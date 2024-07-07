package com.rivzdev.githubuserapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rivzdev.githubuserapp.R
import com.rivzdev.githubuserapp.data.source.remote.ItemsItem
import com.rivzdev.githubuserapp.databinding.FragmentDetailUserBinding
import com.rivzdev.githubuserapp.ui.detail.viewpager.SectionPagerAdapter

class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<UserDetailViewModel>()

    private val args by navArgs<DetailUserFragmentArgs>()
    private lateinit var user: ItemsItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDetailUser()
        setTabLayoutWithViewPager()
    }

    private fun getDetailUser() {
        user = args.user

        user.login?.let { viewModel.getDetailUser(it) }

        viewModel.detailUser.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(requireActivity())
                    .load(it.avatarUrl)
                    .circleCrop()
                    .apply(RequestOptions().override(170, 170))
                    .into(binding.ivProfile)

                binding.tvUsername.text = it.login.toString()
                binding.tvPublicRepository.text = it.publicRepository.toString()
                binding.tvFollowers.text = it.followers.toString()
                binding.tvFollowing.text = it.following.toString()
                binding.tvName.text = it.name.toString()
            }
        }
    }

    private fun setTabLayoutWithViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(this, args.user)
        val viewpager: ViewPager2 = requireView().findViewById(R.id.view_pager)
        viewpager.adapter = sectionPagerAdapter
        val tabs: TabLayout = requireView().findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}