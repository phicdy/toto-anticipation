package com.phicdy.totoanticipation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.model.TeamInfoMapper
import com.phicdy.totoanticipation.view.fragment.GameHistoryFragment
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment

class TeamInfoActivity : AppCompatActivity() {

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
                val homeFragment = TeamInfoFragment()
                arguments.putString(TeamInfoFragment.ARG_TEAM,
                        intent.getStringExtra(ARG_HOME_TEAM))
                homeFragment.arguments = arguments
                supportFragmentManager.beginTransaction()
                        .add(R.id.fr_content, homeFragment)
                        .commit()
            }
            R.id.navigation_away -> {
                val awayFragment = TeamInfoFragment()
                arguments.putString(TeamInfoFragment.ARG_TEAM,
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
