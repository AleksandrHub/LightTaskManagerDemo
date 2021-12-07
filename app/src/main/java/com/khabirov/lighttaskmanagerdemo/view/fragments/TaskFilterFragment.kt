package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.utils.selectDate
import com.niww.lighttaskmanager.view_model.TaskFilterViewModel
import kotlinx.android.synthetic.main.fragment_container_for_task_list.view.*
import kotlinx.android.synthetic.main.fragment_filter_task.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class TaskFilterFragment : Fragment() {

    private val viewModel by viewModel<TaskFilterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_filter_task, container, false)
//        activity?.windowManager?.updateViewLayout(activity!!, WindowManager.LayoutParams(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN))
        init(root)
        initToolbar(root)
        return root
    }

    private fun init(view: View) {

        viewModel.setLiveData()

        viewModel.taskFilterLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.filterTaskName.isBlank()) view.InpEditText_taskName?.setText(it.filterTaskName)
            else view.InpEditText_taskName?.text?.clear()
            if (!it.filterImplementer.isBlank()) view.InpEditText_implementer?.setText(it.filterImplementer)
            else view.InpEditText_implementer?.text?.clear()
            if (!it.filterEmployer.isBlank()) view.InpEditText_employer?.setText(it.filterEmployer)
            else view.InpEditText_employer?.text?.clear()
        })

        view.btn_assign.setOnClickListener {
            viewModel.setTaskFilter(
                view.InpEditText_taskName?.text.toString(),
                view.InpEditText_dueDate?.text.toString(),
                view.InpEditText_implementer?.text.toString(),
                view.InpEditText_employer?.text.toString())
            findNavController().navigate(R.id.action_navigation_tasks_filter_to_navigation_home)
        }


        view.InpEditText_dueDate.setOnClickListener {
            Toast.makeText(activity,"до ",Toast.LENGTH_LONG).show()
            selectDate(context, R.style.DatePickerDialogTheme, view.InpEditText_dueDate)
            Toast.makeText(activity,"после ",Toast.LENGTH_LONG).show()
        }

        view.iv_clear_filter.setOnClickListener {
            viewModel.clearTaskFilter()
        }

        view.iv_place.setOnClickListener {
            Toast.makeText(context, "click on the place icon",Toast.LENGTH_LONG).show()
        }
    }

    private fun initToolbar(view: View) {
        (activity as? AppCompatActivity)?.setSupportActionBar(view.container_for_tasks_toolbar)
    }
}

