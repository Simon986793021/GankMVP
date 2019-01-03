package com.smart.gankmvp.main.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.smart.gankmvp.main.gank.GankFragment

open class ViewPagerFgAdapter(
    supportFragmentManager: FragmentManager,
    private val fragmentList: Array<GankFragment>,
    private val tag: String
) :
    FragmentPagerAdapter(supportFragmentManager) {

    override fun getItem(position: Int): Fragment {

        return fragmentList!![position]
    }


    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (tag == "main_view_pager") {
            when (position) {
                0 -> return "知乎"
                1 -> return "干货"
                2 -> return "满足你的好奇心"
            }
        }
        return null
    }
}
