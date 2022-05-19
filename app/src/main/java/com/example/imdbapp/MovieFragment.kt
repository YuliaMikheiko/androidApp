package com.example.imdbapp

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

private lateinit var titleText: TextView
private lateinit var yearsText: TextView
private lateinit var genreText: TextView
private lateinit var plotText: TextView
private lateinit var imdbText: TextView
private lateinit var totalSeasonsText: TextView
private lateinit var imageTitle: ImageView


class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        init(view)

        val title = requireArguments().getString("title")
        val years = requireArguments().getString("years")
        val genre = requireArguments().getString("genre")
        val plot = requireArguments().getString("plot")
        val imdb = requireArguments().getString("imdb")
        val totalSeasons = requireArguments().getString("totalSeasons")
        val imageURL = requireArguments().getString("imageTitle")


        //Picasso.with(requireActivity()).load(imageURL).into(imageTitle)
        Picasso.get().load(imageURL).into(imageTitle)

        titleText.text = "Название: $title"
        yearsText.text= "Годы выхода: $years"
        genreText.text = "Жанр: $genre"
        plotText.text = "Сюжет: $plot"
        imdbText.text = "IMDB: $imdb"
        totalSeasonsText.text = "Количество сезонов: $totalSeasons"

        return view
    }

    private fun init(view: View){
        titleText = view.findViewById(R.id.titleText)
        yearsText = view.findViewById(R.id.yearsText)
        genreText = view.findViewById(R.id.genreText)
        plotText = view.findViewById(R.id.plotText)
        imdbText = view.findViewById(R.id.imdbText)
        totalSeasonsText = view.findViewById(R.id.totalSeasonsText)
        imageTitle = view.findViewById(R.id.imageTitle)
    }

}