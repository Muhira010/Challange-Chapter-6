package com.example.challengechapter7.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var genres: GenresAdapter
    companion object{
        const val imageTMDB = "https://image.tmdb.org/t/p/w500/"
    }
    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsMovieViewModel by viewModels()
//    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        val tmdbID = arguments?.getInt("tmdbID")
        if (tmdbID != null) {
            viewModel.getMovieDetails(tmdbID = tmdbID)
            viewModel.getMovieGenres(tmdbID = tmdbID)
        }

        genres = GenresAdapter()
        binding.rvGenres.adapter = genres

        binding.cardBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_mainMovieFragment)
        }
    }

    private fun bindUI(){
        viewModel.showDetailsMovie.observe(viewLifecycleOwner){
            Glide.with(this@DetailsFragment)
                .load(imageTMDB + it.posterPath)
                .into(binding.rivDetailsPoster)
            binding.tvRuntime.text = it.runtime.toString() + " Minutes"
            binding.tvRelease.text = it.releaseDate
            binding.tvTitleDetails.text = it.title
            binding.tvSynopsis.text = it.overview
        }

        val progressBar = binding.progressBarDetails.progressBar
        viewModel.showLoading.observe(viewLifecycleOwner){
            if (it) {
                progressBar.visibility = View.VISIBLE
//                progressDialog.setMessage("Loading...")
//                progressDialog.show()
            } else {
                progressBar.visibility = View.GONE
//                progressDialog.hide()
            }
        }

        viewModel.showGenresMovie.observe(viewLifecycleOwner){
            genres.setData(it.genres)
        }
//        binding.apply {
//            val moviePlaying = args.moviePlaying
//            Glide.with(this@DetailsFragment)
//                .load(imageTMDB + moviePlaying.poster_path)
//                .into(rivDetailsPoster)
//        }
    }
}