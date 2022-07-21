package com.example.challengechapter7.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.data.api.PlayingResponse.Playing
import com.example.challengechapter7.data.api.PopularResponse.Popular
import com.example.challengechapter7.data.api.TopRatedResponse.TopRated
import com.example.challengechapter7.databinding.FragmentMainMovieBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMovieFragment : Fragment() {
    lateinit var binding: FragmentMainMovieBinding
    private lateinit var playingAdapter: MoviePlayingAdapter
    private lateinit var popularAdapter: MoviePopularAdapter
    private lateinit var topRatedAdapter: MovieTopRatedAdapter
    private val viewModel: MainMovieViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
//    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }
//    private val progressBar: ProgressBar by lazy { ProgressBar(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        playingAdapter = MoviePlayingAdapter(
            listener = object: MoviePlayingAdapter.EventListener{
                override fun onClick(itemList: Playing) {
                    val bundle = Bundle()
                    bundle.putInt("tmdbID", itemList.id)
                    findNavController().navigate(R.id.action_mainMovieFragment_to_detailsFragment, bundle)
//                    val action = MainMovieFragmentDirections.actionMainMovieFragmentToDetailsFragment(itemList)
//                    findNavController().navigate(action)
                }
            }
        )
        binding.rvMoviePlaying.adapter = playingAdapter
        popularAdapter = MoviePopularAdapter(
            listener = object: MoviePopularAdapter.EventListener{
                override fun onClick(itemList: Popular) {
                    val bundle = Bundle()
                    bundle.putInt("tmdbID", itemList.id)
                    findNavController().navigate(R.id.action_mainMovieFragment_to_detailsFragment, bundle)
//                    val action = MainMovieFragmentDirections.actionMainMovieFragmentToDetailsFragment(itemList)
//                    findNavController().navigate(action)
                }
            }
        )
        binding.rvMoviePopular.adapter = popularAdapter
        topRatedAdapter = MovieTopRatedAdapter(
            listener = object: MovieTopRatedAdapter.EventListener{
                override fun onClick(itemList: TopRated) {
                    val bundle = Bundle()
                    bundle.putInt("tmdbID", itemList.id)
                    findNavController().navigate(R.id.action_mainMovieFragment_to_detailsFragment, bundle)
//                    val action = MainMovieFragmentDirections.actionMainMovieFragmentToDetailsFragment(itemList)
//                    findNavController().navigate(action)
                }
            }
        )
        binding.rvMovieTopRated.adapter = topRatedAdapter
        viewModel.getMoviePlaying()
        viewModel.getMoviePopular()
        viewModel.getMovieTopRated()

        auth = FirebaseAuth.getInstance()
        loadUser()

        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_mainMovieFragment_to_profileFragment)
        }
    }

    private fun loadUser(){
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("fullName").value}"
                    val photo = "${snapshot.child("photo").value}"

                    binding.tvNamaUser.text = name
                    Glide.with(this@MainMovieFragment)
                        .load(photo)
                        .placeholder(R.drawable.placeholder3)
                        .into(binding.profile)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun bindViewModel(){
        viewModel.showMoviePlaying.observe(viewLifecycleOwner){
            playingAdapter.setData(it.results)
        }

        viewModel.showMoviePopular.observe(viewLifecycleOwner){
            popularAdapter.setData(it.results)
        }

        viewModel.showMovieTopRated.observe(viewLifecycleOwner){
            topRatedAdapter.setData(it.results)
        }

        val progressBar = binding.progressBarMain.progressBar
        viewModel.showLoading.observe(viewLifecycleOwner){
            if (it) {
                progressBar.visibility = View.VISIBLE
//                progressDialog.setMessage("Loading...")
//                progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                progressDialog.show()
            } else {
                progressBar.visibility = View.GONE
//                progressDialog.hide()
            }
        }
    }
}