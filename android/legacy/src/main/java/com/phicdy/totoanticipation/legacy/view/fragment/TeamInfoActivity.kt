package com.phicdy.totoanticipation.legacy.view.fragment

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.TeamInfoMapper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TeamInfoActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var provider: AdProvider

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        replaceFragmentWith(item.itemId)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            replaceFragmentWith(navigation.selectedItemId)
            supportFragmentManager.beginTransaction()
                    .add(R.id.adContainer, provider.newFragmentInstance())
                    .commit()
        }
        val title = intent.getStringExtra(ARG_HOME_TEAM) + " vs " +
                intent.getStringExtra(ARG_AWAY_TEAM)
        setTitle(title)

    }

    private fun replaceFragmentWith(menuId: Int) {
        val arguments = Bundle()
        when (menuId) {
            R.id.navigation_history -> {
                val fragment = GameHistoryFragment()
                arguments.apply {
                    putString(GameHistoryFragment.ARG_HOME_TEAM,
                            TeamInfoMapper().fullNameForFootbellGeist(intent.getStringExtra(ARG_HOME_TEAM)))
                    putString(GameHistoryFragment.ARG_AWAY_TEAM,
                            TeamInfoMapper().fullNameForFootbellGeist(intent.getStringExtra(ARG_AWAY_TEAM)))
                }
                fragment.arguments = arguments
                supportFragmentManager.beginTransaction()
                        .add(R.id.fr_content, fragment)
                        .commit()
            }
            R.id.navigation_home -> {
                val homeFragment = TeamInfoDetailFragment()
                arguments.putString(TeamInfoDetailFragment.ARG_TEAM,
                        intent.getStringExtra(ARG_HOME_TEAM))
                homeFragment.arguments = arguments
                supportFragmentManager.beginTransaction()
                        .add(R.id.fr_content, homeFragment)
                        .commit()
            }
            R.id.navigation_away -> {
                val awayFragment = TeamInfoDetailFragment()
                arguments.putString(TeamInfoDetailFragment.ARG_TEAM,
                        intent.getStringExtra(ARG_AWAY_TEAM))
                awayFragment.arguments = arguments
                supportFragmentManager.beginTransaction()
                        .add(R.id.fr_content, awayFragment)
                        .commit()
            }
        }
    }

    companion object {

        const val ARG_HOME_TEAM = "homeTeam"
        const val ARG_AWAY_TEAM = "awayTeam"
    }
}
