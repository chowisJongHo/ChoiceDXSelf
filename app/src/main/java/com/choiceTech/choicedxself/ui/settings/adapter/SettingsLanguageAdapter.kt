package com.choiceTech.choicedxself.ui.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choiceTech.choicedxself.R
import com.choiceTech.choicedxself.core.model.settings.Language

class SettingsLanguageAdapter: ListAdapter<Language, SettingsLanguageAdapter.ViewHodler>(LanguageDiffCallback){
    companion object {
        object LanguageDiffCallback: DiffUtil.ItemCallback<Language>() {
            override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHodler(view: View): RecyclerView.ViewHolder(view) {
        private val viewContext = itemView.context

        val flagImageView: ImageView = view.findViewById(R.id.itemLanguageFlag)
        val removeButton: ImageView = view.findViewById(R.id.itemLanguageRemove)

        fun bind(item: Language) {
            flagImageView.setImageResource(item.icon)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingsLanguageAdapter.ViewHodler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return ViewHodler(view)
    }

    override fun onBindViewHolder(holder: SettingsLanguageAdapter.ViewHodler, position: Int) {
        holder.bind(getItem(position))
    }
}