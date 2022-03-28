package com.dicoding.githubusers.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.adapter.FollowersFollowingAdapter
import com.dicoding.githubusers.data.User
import com.dicoding.githubusers.databinding.FragmentFollowingBinding
import com.dicoding.githubusers.ui.DetailUser
import com.dicoding.githubusers.ui.viewmodel.UserViewModel

private const val ARGUMENT_USERNAME = "username"
class FollowingFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingAdapter: FollowersFollowingAdapter
    private var username: String = ""

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_USERNAME, username)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARGUMENT_USERNAME) as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        followingAdapter = FollowersFollowingAdapter()
        followingAdapter.setOnItemClickCallback(object : FollowersFollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val moveIntent = Intent(requireContext(), DetailUser::class.java)
                moveIntent.putExtra(DetailUser.EXTRA_USER, data.username)
                startActivity(moveIntent)
            }
        })

        binding.listFollowing.adapter = followingAdapter
        binding.listFollowing.layoutManager = LinearLayoutManager(requireContext())
        userViewModel.setFollowing(username)
        userViewModel.following.observe(requireActivity(), { userItem ->
            if (userItem!= null){
                followingAdapter.listUser = userItem
                progressBarDisplay(false)
            }
        })
        super.onViewCreated(view, savedInstanceState)
        progressBarDisplay(true)
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.listFollowing.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.listFollowing.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}