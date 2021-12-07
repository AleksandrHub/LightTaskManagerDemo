package com.niww.lighttaskmanager.view.adapters.profile_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.ProfileMenuModel
import kotlinx.android.synthetic.main.profile_menu_item.view.*

class ProfileMenuRVAdapter(
    private val data: List<ProfileMenuModel>,
    private val listener: MenuItemListener
) :
    RecyclerView.Adapter<ProfileMenuRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_menu_item, parent, false), listener
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(val view: View, private val listener: MenuItemListener) :
        RecyclerView.ViewHolder(view) {

        fun bind(profileMenu: ProfileMenuModel) {
            view.profile_menu_item_root.setOnClickListener { listener.onMenuItemClick(profileMenu.id) }
            view.profile_menu_item_icon.setBackgroundResource(profileMenu.icon)
            view.profile_menu_item_text.text = profileMenu.text
        }
    }
}