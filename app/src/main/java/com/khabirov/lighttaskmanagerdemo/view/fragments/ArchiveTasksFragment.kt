package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.datasource.Task
import com.niww.lighttaskmanager.view.adapters.TaskRVAdapter
import com.niww.lighttaskmanager.view.adapters.TaskItemListener
import com.niww.lighttaskmanager.view_model.ArchiveTasksViewModel
import kotlinx.android.synthetic.main.fragment_archive_tasks.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArchiveTasksFragment : Fragment(), TaskItemListener {

    private val viewModel by viewModel<ArchiveTasksViewModel>()

    private lateinit var adapter: TaskRVAdapter
    private lateinit var archiveTasksList: ArrayList<Task>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_archive_tasks, container, false)
        init(view)
        initToolbar(view)
        return view
    }

    private fun init(view: View) {
        archiveTasksList = ArrayList()
        adapter = TaskRVAdapter(archiveTasksList, this)
        view.archive_tasks_recycler_view.adapter = adapter

        viewModel.archiveTasksLiveData.observe(viewLifecycleOwner, Observer<List<Task>> {
            adapter.tasks.clear()
            adapter.tasks.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.updateTasksLiveData()
    }

    private fun initToolbar(view: View) {
        (activity as? AppCompatActivity)?.setSupportActionBar(view.archive_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_profile -> {
                findNavController().navigate(R.id.navigation_fragment_archive_tasks_to_fragment_profile)
            }
            R.id.item_menu_filter -> {
                findNavController().navigate(R.id.navigation_fragment_archive_tasks_to_fragment_profile)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTaskClick(task: Task) {
        TODO("Not yet implemented")
    }

    override fun onChatClick(task: Task) {
        TODO("Not yet implemented")
    }

}