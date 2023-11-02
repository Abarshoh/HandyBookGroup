package com.akbar.handybook.adapters

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akbar.handybook.ui.CommentsFragment
import com.akbar.handybook.ui.InfoFragment
import com.akbar.handybook.ui.IqtibosFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, var id:Int) : FragmentStateAdapter(fragmentManager, lifecycle) {
    private val count = 3
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return InfoFragment.newInstance(id.toString())
            1->return CommentsFragment.newInstance(id.toString(), "")
        }
        return IqtibosFragment.newInstance(id.toString(), "")
    }

}