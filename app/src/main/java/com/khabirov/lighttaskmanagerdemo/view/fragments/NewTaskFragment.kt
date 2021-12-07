package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.Task
import com.niww.lighttaskmanager.repository.FirebaseRepository
import com.niww.lighttaskmanager.utils.selectDate
import kotlinx.android.synthetic.main.fragment_container_for_task_list.view.*
import kotlinx.android.synthetic.main.fragment_new_task.view.*
import kotlinx.android.synthetic.main.new_task_screen_layout.*
import kotlinx.android.synthetic.main.new_task_screen_layout.view.*
import org.koin.android.ext.android.inject
import java.util.*

class NewTaskFragment : Fragment() {

    private val auth: FirebaseAuth by inject()
    private val repo: FirebaseRepository by inject()

    private val TAG: String = "EEE"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new_task, container, false)





//        view?.address_edit_text?.text = tempAddr



        initUi(root)

        return root
    }


    private fun initUi(view: View){
        view.show_map_field.setOnClickListener {
            findNavController().navigate(R.id.mapsFragment) }



        initToolbar(view)
        initDatePicker(view)
    }

    private fun initToolbar(view: View) {
        (activity as? AppCompatActivity)?.setSupportActionBar(view.container_for_tasks_toolbar)
        view.fragment_new_task_arrow_back_icon.setOnClickListener {
            findNavController().navigate(R.id.navigation_fragment_new_task_to_home)
        }
        view.fragment_new_task_done_icon.setOnClickListener {
            if (validate(view)) {
                repo.addNewTask(
                    Task(
                        UUID.randomUUID().toString(),
                        0,
                        view.task_name_edit_text.text.toString(),
                        address_edit_text.text.toString(),
                        auth.currentUser!!.displayName.toString(),
                        view.performer_edit_text.text.toString(),
                        description_edit_text.text.toString(),
                        task_target_date_text_view.text.toString(),
                        false
                    )
                )
                findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validate(view: View): Boolean {
        if (view.task_name_edit_text.text!!.isNotEmpty()
            && view.task_target_date_text_view.text!!.isNotEmpty()
            && view.performer_edit_text.text!!.isNotEmpty()
            && view.address_edit_text.text!!.isNotEmpty()
            && view.description_edit_text.text!!.isNotEmpty()
        ) {
            return true
        }
        return false
    }

    private fun initDatePicker(view: View) {
        view.task_target_date_text_view.setOnClickListener {
            selectDate(context, R.style.DatePickerDialogTheme, task_target_date_text_view)
        }
    }
}