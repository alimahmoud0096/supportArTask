package com.smart4apps.supportArTask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smart4apps.supportArTask.data.model.Article
import com.smart4apps.supportArTask.databinding.ItemArticleBinding
import javax.inject.Inject

class NewsAdapter @Inject constructor(
) : PagingDataAdapter<Article, NewsAdapter.ArticleViewHolder>(ArticleDiffCallback()) {


    var onFavClicked: ((Article) -> Unit)? = null
        set(value) {
            field = value
        }


    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.publishedAt == newItem.publishedAt

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ) = oldItem == newItem

        override fun getChangePayload(oldItem: Article, newItem: Article): Any? {
            if (oldItem != newItem) {
                return newItem
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    inner class ArticleViewHolder constructor(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article?) {
            if (item != null)
                binding.article = item

            binding.btnFav.setOnClickListener {
                onFavClicked?.let {
                    onFavClicked?.let { it1 -> it1(item!!) }
                }

                item?.isFav = !(item?.isFav)!!
                notifyItemChanged(absoluteAdapterPosition)

            }
        }


    }

}
