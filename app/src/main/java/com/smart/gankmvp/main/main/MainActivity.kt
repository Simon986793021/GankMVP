package com.smart.gankmvp.main.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.smart.gankmvp.R
import com.smart.gankmvp.base.BaseActivity
import com.smart.gankmvp.main.daily.DailyFragment
import com.smart.gankmvp.main.gank.GankFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<IMainView, MainActivityPresenter>() {

    protected override fun createPresenter(): MainActivityPresenter? {
        return null
    }

    protected override fun provideContentViewId(): Int {
        return R.layout.activity_main
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabView()

    }


    //初始化Tab滑动
    private fun initTabView() {
        val items:Array<Fragment> = arrayOf(GankFragment(),DailyFragment())
        content_viewPager.adapter = ViewPagerFgAdapter(supportFragmentManager,items,"main_view_pager")
        tabLayout!!.setupWithViewPager(content_viewPager)




//        //fragmentList.add(ZhihuFragment())
//        val gankFragment:GankFragment = GankFragment()
//        val add = fragmentList.add(gankFragment)
     //   fragmentList.add(DailyFragment())
//        content_viewPager!!.offscreenPageLimit = 1//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
//        content_viewPager!!.adapter =
//                ViewPagerFgAdapter(getSupportFragmentManager(), fragmentList, "main_view_pager")//给ViewPager设置适配器
//        tabLayout!!.setupWithViewPager(content_viewPager)//将TabLayout和ViewPager关联起来
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.today_github) {
            val github_trending = "https://github.com/trending"
         //   startActivity(GankWebActivity.newIntent(this, github_trending))
            return true
        } else if (item.itemId == R.id.about_me) {
           // startActivity(Intent(this, AboutMeActivity::class.java))
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

}
