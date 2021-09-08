package com.example.dharmav10.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dharmav10.R
import com.example.dharmav10.databinding.ItemNewsBinding
import com.example.dharmav10.domain.NewsProperty
import com.prof.rssparser.Article
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(val clickListener: NewsClickListener) : RecyclerView.Adapter<ArticleAdapter.NewsViewHolder>() {


    var news: List<NewsProperty> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val withDataBinding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            NewsViewHolder.LAYOUT,
            parent,
            false
        )
        return NewsViewHolder(withDataBinding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind.also {
            it.news = news[position]
            it.clickListener = clickListener
        }
    }

    override fun getItemCount() = news.size


    class NewsClickListener(val clickListener: (news: NewsProperty) -> Unit) {
        fun onClick(news: NewsProperty) = clickListener(news)
    }

    class NewsViewHolder(val bind: ItemNewsBinding) : RecyclerView.ViewHolder(bind.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.item_news
        }
    }
}
