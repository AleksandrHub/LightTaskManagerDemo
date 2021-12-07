package com.niww.lighttaskmanager.view.fragments

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.niww.lighttaskmanager.R

class SectionsPagerAdapter( fm: Fragment)
    : FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position)
    }

}