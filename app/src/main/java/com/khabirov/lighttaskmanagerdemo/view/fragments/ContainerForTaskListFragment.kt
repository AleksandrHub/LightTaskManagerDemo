package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.niww.lighttaskmanager.R
import kotlinx.android.synthetic.main.fragment_container_for_task_list.view.*

class ContainerForTaskListFragment : Fragment() {

    companion object {
        val TAB_TITLES = arrayOf(
            R.string.tab_to_do,
            R.string.tab_in_progress,
            R.string.tab_done
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_container_for_task_list, container, false)
        init(root)
        initToolbar(root)
        return root
    }

    private fun init(view: View) {
        view.fab_add_new_task.setOnClickListener {
            findNavController().navigate(R.id.fragment_new_task)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = view.findViewById<ViewPager2>(R.id.main_view_pager)

        view.main_view_pager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.main_tabs)
        TabLayoutMediator(
            tabs, viewPager
        ) { tab, position -> tab.setText(TAB_TITLES[position]) }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu_profile -> {
                findNavController().navigate(R.id.fragment_profile)
            }
            R.id.item_menu_filter -> {
                findNavController().navigate(R.id.action_navigation_home_to_navigation_tasks_filter)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(view: View) {
        (activity as? AppCompatActivity)?.setSupportActionBar(view.container_for_tasks_toolbar)
    }
}

