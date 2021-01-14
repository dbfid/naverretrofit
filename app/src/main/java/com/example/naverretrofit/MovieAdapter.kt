package com.example.naverretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieItemHolder>() {

    private lateinit var callback: (Movie) -> Unit
    private val items: ArrayList<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false
        )

        return MovieItemHolder(view).also {
            it.itemView.setOnClickListener { position ->
                callback(items[it.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class MovieItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val tvYear = itemView.findViewById<TextView>(R.id.tv_year)
        private val tvDirector = itemView.findViewById<TextView>(R.id.tv_director)
        private val tvActor = itemView.findViewById<TextView>(R.id.tv_actor)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.iv_poster)
        private val rating = itemView.findViewById<RatingBar>(R.id.ratingBar)

        fun bind(movie: Movie) {
            with(movie) {
                Glide.with(itemView).load(image)
                    .placeholder(R.drawable.ic_default)
                    .into(ivPoster)
                tvTitle.text = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT)
                tvYear.text = pubDate
                tvActor.text = actor
                tvDirector.text = director
                rating.rating = (userRating.toFloatOrNull() ?: 0f) /2 //2로 해야지 별이 제대로 나옴
            }
        }
    }

    fun setItems(items: List<Movie>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItemClickListener(callback: (Movie) -> Unit) {
        this.callback = callback
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}