package com.phicdy.totoanticipation.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.TeamInfoMapper;
import com.phicdy.totoanticipation.view.fragment.GameHistoryFragment;
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment;

public class TeamInfoActivity extends AppCompatActivity {

    public static final String ARG_HOME_TEAM = "homeTeam";
    public static final String ARG_AWAY_TEAM = "awayTeam";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            replaceFragmentWith(item.getItemId());
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            replaceFragmentWith(navigation.getSelectedItemId());
        }
        String title = getIntent().getStringExtra(ARG_HOME_TEAM) + " vs " +
                getIntent().getStringExtra(ARG_AWAY_TEAM);
        setTitle(title);
    }

    private void replaceFragmentWith(int menuId) {
        Bundle arguments = new Bundle();
        switch (menuId) {
            case R.id.navigation_history:
                GameHistoryFragment fragment = new GameHistoryFragment();
                arguments.putString(GameHistoryFragment.ARG_HOME_TEAM,
                        TeamInfoMapper.fullNameForFootbellGeist(getIntent().getStringExtra(ARG_HOME_TEAM)));
                arguments.putString(GameHistoryFragment.ARG_AWAY_TEAM,
                        TeamInfoMapper.fullNameForFootbellGeist(getIntent().getStringExtra(ARG_AWAY_TEAM)));
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fr_content, fragment)
                        .commit();
                break;
            case R.id.navigation_home:
                TeamInfoFragment homeFragment = new TeamInfoFragment();
                arguments.putString(TeamInfoFragment.ARG_TEAM,
                        getIntent().getStringExtra(ARG_HOME_TEAM));
                homeFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fr_content, homeFragment)
                        .commit();
                break;
            case R.id.navigation_away:
                TeamInfoFragment awayFragment = new TeamInfoFragment();
                arguments.putString(TeamInfoFragment.ARG_TEAM,
                        getIntent().getStringExtra(ARG_AWAY_TEAM));
                awayFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fr_content, awayFragment)
                        .commit();
                break;
        }
    }
}
