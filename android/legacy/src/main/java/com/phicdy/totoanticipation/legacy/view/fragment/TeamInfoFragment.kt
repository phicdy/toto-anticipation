package com.phicdy.totoanticipation.legacy.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.TeamInfoMapper
import dagger.android.support.DaggerFragment

class TeamInfoFragment : DaggerFragment() {

    private val args: TeamInfoFragmentArgs by navArgs()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        replaceFragmentWith(item.itemId)
        true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_team_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "${args.homeTeam} vs ${args.awayTeam}"

        val navigation = view.findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            replaceFragmentWith(navigation.selectedItemId)
        }
    }

    private fun replaceFragmentWith(menuId: Int) {
        val arguments = Bundle()
        when (menuId) {
            R.id.navigation_history -> {
                val fragment = GameHistoryFragment()
                arguments.apply {
                    putString(GameHistoryFragment.ARG_HOME_TEAM,
                            TeamInfoMapper().fullNameForFootbellGeist(args.homeTeam))
                    putString(GameHistoryFragment.ARG_AWAY_TEAM,
                            TeamInfoMapper().fullNameForFootbellGeist(args.awayTeam))
                }
                fragment.arguments = arguments
                parentFragmentManager.beginTransaction()
                        .add(R.id.fr_content, fragment)
                        .commit()
            }
            R.id.navigation_home -> {
                val homeFragment = TeamInfoDetailFragment()
                arguments.putString(TeamInfoDetailFragment.ARG_TEAM, args.homeTeam)
                homeFragment.arguments = arguments
                parentFragmentManager.beginTransaction()
                        .add(R.id.fr_content, homeFragment)
                        .commit()
            }
            R.id.navigation_away -> {
                val awayFragment = TeamInfoDetailFragment()
                arguments.putString(TeamInfoDetailFragment.ARG_TEAM, args.awayTeam)
                awayFragment.arguments = arguments
                parentFragmentManager.beginTransaction()
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
