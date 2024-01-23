package com.example.dikshatest.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dikshatest.ui.detail.fragment.DescriptionFragment
import com.example.dikshatest.ui.detail.fragment.ReviewFragment
import com.example.dikshatest.ui.detail.fragment.OtherFragment

class DetailPageAdapter(val fm: FragmentActivity) :  FragmentStateAdapter(fm){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DescriptionFragment()
            1 -> ReviewFragment()
            2 -> OtherFragment()
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }
}