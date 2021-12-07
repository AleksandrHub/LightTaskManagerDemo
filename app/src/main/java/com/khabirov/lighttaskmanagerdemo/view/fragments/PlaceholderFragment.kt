package com.niww.lighttaskmanager.view.fragments

import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.extensions.hide
import com.niww.lighttaskmanager.extensions.show
import com.niww.lighttaskmanager.model.AppState
import com.niww.lighttaskmanager.model.datasource.Task
import com.niww.lighttaskmanager.utils.AlertDialogFragment
import com.niww.lighttaskmanager.view.adapters.TaskRVAdapter
import com.niww.lighttaskmanager.view.adapters.TaskItemListener
import com.niww.lighttaskmanager.view_model.PageViewModel
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.android.synthetic.main.fragment_task_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class PlaceholderFragment : Fragment(), TaskItemListener {

    companion object {

        private const val TASK = "task"
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    private var fragmentIdex: Int = 0

    private val viewModel by viewModel<PageViewModel>()

    private lateinit var adapter: TaskRVAdapter
    private lateinit var tasksList: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentIdex = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
        viewModel.apply {
            getData(fragmentIdex)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_task_list, container, false)
        init(root)
        return root
    }

    private fun init(view: View) {
        tasksList = ArrayList()
        adapter = TaskRVAdapter(tasksList, this)
        view.task_list_recyclerview.adapter = adapter

        viewModel.getLiveData(fragmentIdex).observe(viewLifecycleOwner, Observer<AppState> {
            renderData(it)
        })

    }

    override fun onTaskClick(task: Task) {
        findNavController().navigate(
            R.id.task_fragment,
            Bundle().apply { putParcelable(TASK, task) })
    }

    override fun onChatClick(task: Task) {
        Toast.makeText(context, task.name, Toast.LENGTH_SHORT).show()
    }

    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data.isNullOrEmpty()) {
                    showDialog(getString(R.string.no_tasks_found))
                } else {
                    showViewWorking()
                    adapter.tasks.clear()
                    adapter.tasks.addAll(data)
                    adapter.notifyDataSetChanged()
                }
            }
            is AppState.Loading -> {
                showViewLoading()
            }
            is AppState.Error -> {
                showDialog(getString(R.string.error_stub) +"\n" + appState.error.message)
            }
        }
    }

    private fun showViewWorking() {
        loading_frame_layout.hide()
        dialog_editText.hide()
        working_linear_layout.show()
        task_list_recyclerview.show()
    }

    private fun showViewLoading() {
        dialog_editText.hide()
        working_linear_layout.hide()
        loading_frame_layout.show()
    }

    private fun showDialog(dialog: String) {
        loading_frame_layout.hide()
        working_linear_layout.hide()
        dialog_frame_layout.show()
        dialog_editText.show()
        dialog_editText.setText(dialog)
    }


}