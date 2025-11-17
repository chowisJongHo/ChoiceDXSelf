package com.choiceTech.choicedxself.ui.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.choiceTech.choicedxself.R

class SettingsLanguageAdapter: ListAdapter<SelectLanguageItem, SettingsLanguageAdapter.ViewHodler>(SelectLanguageDiffCallback){
    companion object {
        object SelectLanguageDiffCallback: DiffUtil.ItemCallback<SelectLanguageItem>() {
            override fun areItemsTheSame(
                oldItem: SelectLanguageItem, newItem: SelectLanguageItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SelectLanguageItem, newItem: SelectLanguageItem): Boolean {
                return oldItem == newItem
            }

        }
    }
    inner class ViewHodler(view: View): RecyclerView.ViewHolder(view) {
        private val viewContext = itemView.context

        fun bind(selectLanguageItem: SelectLanguageItem) {

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

enum class SelectLanguageItem(
    val text: Int,
    val image: Int
)