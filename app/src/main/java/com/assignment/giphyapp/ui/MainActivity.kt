package com.assignment.giphyapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.assignment.giphyapp.R
import com.assignment.giphyapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = resources.getString(R.string.giphy_app)
        //set up tab layout and view pager
        setUpViewPager()
    }

    private fun setUpViewPager() {
        //set up view pager adapter
        binding.viewPager.adapter = GiphyViewPagerAdapter(this)
        binding.layoutTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //modify custom tab layout's text when it is selected
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.apply {
                    tab.customView?.findViewById<TextView>(R.id.tv_label)?.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.apply {
                    tab.customView?.findViewById<TextView>(R.id.tv_label)?.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.gold
                        )
                    )
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        TabLayoutMediator(
            binding.layoutTab,
            binding.viewPager
        ) { tab, position ->
            //set up custom tab view for tab layout where we can style at our ease
            tab.setCustomView(R.layout.item_tab_bar)
            tab.view.apply {
                when (position) {
                    0 -> {
                        tab.customView?.findViewById<TextView>(R.id.tv_label)?.text =
                            resources.getString(R.string.all_gifs)
                    }
                    1 -> {
                        tab.customView?.findViewById<TextView>(R.id.tv_label)?.text =
                            resources.getString(R.string.saved_gifs)
                    }
                }
            }
        }.attach()
    }

    private inner class GiphyViewPagerAdapter(
        fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity) {
        private val items = 2

        override fun getItemCount(): Int {
            return items
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = RemoteGifFragment()
                1 -> fragment = UserGifFragment()
            }
            return fragment!!
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}