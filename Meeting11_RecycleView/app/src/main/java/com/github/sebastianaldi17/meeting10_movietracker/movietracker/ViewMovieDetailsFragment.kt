package com.github.sebastianaldi17.meeting10_movietracker.movietracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.github.sebastianaldi17.meeting10_movietracker.R
import com.github.sebastianaldi17.meeting10_movietracker.database.MovieDatabase
import com.github.sebastianaldi17.meeting10_movietracker.databinding.FragmentViewMovieDetailsBinding
import com.github.sebastianaldi17.meeting10_movietracker.moviedetails.MovieDetailsFragmentArgs


class ViewMovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentViewMovieDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_view_movie_details,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val arguments = MovieDetailsFragmentArgs.fromBundle(arguments!!)
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao

        val viewModelFactory =
            MovieTrackerViewModelFactory(dataSource, application, arguments.movieKey)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ViewMovieDetailsFragmentViewModel::class.java)

        viewModel.currentMovie.observe(this, Observer {
            binding.movieName.text = it.movieName
            binding.movieRating.text = it.rating.toString() + " Star(s)"
            binding.movieReview.text = it.review
        })

        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }


}
