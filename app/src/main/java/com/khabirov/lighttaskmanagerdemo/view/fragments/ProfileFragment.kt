package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.ProfileMenuModel
import com.niww.lighttaskmanager.view.adapters.profile_menu.MenuItemDivider
import com.niww.lighttaskmanager.view.adapters.profile_menu.MenuItemListener
import com.niww.lighttaskmanager.view.adapters.profile_menu.ProfileMenuRVAdapter
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.profile_layout.view.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(),
    MenuItemListener {

    private val auth: FirebaseAuth by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.tv_profile_info_item_name.text = auth.currentUser!!.displayName
        val phoneNumber = auth.currentUser!!.phoneNumber
        if (phoneNumber.equals("")) {
            view.tv_profile_info_item_phone.text = "Укажите номер телефона"
        } else {
            view.tv_profile_info_item_phone.text = phoneNumber
        }

        view.rv_profile_menu.adapter = ProfileMenuRVAdapter(getMenuModels(), this)

        val drawableItem = ContextCompat.getDrawable(requireContext(), R.drawable.menu_item_divider)
        drawableItem?.let {
            val decorator = MenuItemDivider(it)
            view.rv_profile_menu.addItemDecoration(decorator)
        }

        view.iv_profile_back.setOnClickListener {
            findNavController().popBackStack()
        }

        view.iv_profile_settings.setOnClickListener {
            findNavController().navigate(R.id.edit_profile_fragment)
        }
    }

    override fun onMenuItemClick(menu: String) {
        when (menu) {
            "task archive" -> findNavController().navigate(R.id.archiveTasksFragment)
            "notifications" -> Toast.makeText(context, "Notifications", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMenuModels() = listOf(
        ProfileMenuModel(
            R.drawable.ic_task_archive_24,
            requireContext().getString(R.string.task_archive),
            "task archive"
        ),
        ProfileMenuModel(
            R.drawable.ic_bell_24,
            requireContext().getString(R.string.notifications),
            "notifications"
        )
    )
}
