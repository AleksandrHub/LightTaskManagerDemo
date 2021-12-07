package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.niww.lighttaskmanager.R
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import kotlinx.android.synthetic.main.profile_edit_scroll_view.view.*
import org.koin.android.ext.android.inject

class ProfileFragmentEdit : Fragment() {

    private val auth: FirebaseAuth by inject()

    private val user by lazy {
        auth.currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.iv_profile_edit_back.setOnClickListener {
            findNavController().popBackStack()
        }

        view.btn_profile_edit_confirm.setOnClickListener {
            updatePersonalData(view)
        }
    }

    private fun updatePersonalData(view: View) {
        val name = view.et_profile_edit_user_name.text.toString()
        val phone = view.et_profile_edit_user_phone.text.toString()

        if (name.isNotEmpty()) {
            val updateNameRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
            user!!.updateProfile(updateNameRequest).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Имя обновлено", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}