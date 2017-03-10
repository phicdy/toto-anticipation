package com.phicdy.totoanticipation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.game_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game("鹿島アントラーズ", "東京FC"));
        games.add(new Game("鹿島アントラーズ", "東京FC"));
        games.add(new Game("鹿島アントラーズ", "東京FC"));
        games.add(new Game("鹿島アントラーズ", "東京FC"));
        games.add(new Game("鹿島アントラーズ", "東京FC"));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(games));
    }

    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Game> games;

        SimpleItemRecyclerViewAdapter(List<Game> games) {
            this.games = games;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.game = games.get(position);
            holder.tvHome.setText(holder.game.homeTeam);
            holder.tvAway.setText(holder.game.awayTeam);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TeamInfoFragment.ARG_HOME_TEAM, holder.game.homeTeam);
                        arguments.putString(TeamInfoFragment.ARG_HOME_TEAM, holder.game.awayTeam);
                        TeamInfoFragment fragment = new TeamInfoFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TeamInfoActivity.class);
                        intent.putExtra(TeamInfoFragment.ARG_HOME_TEAM, holder.game.homeTeam);
                        intent.putExtra(TeamInfoFragment.ARG_HOME_TEAM, holder.game.awayTeam);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return games.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView tvHome;
            final TextView tvAway;
            Game game;

            ViewHolder(View view) {
                super(view);
                mView = view;
                tvHome = (TextView) view.findViewById(R.id.tv_home);
                tvAway = (TextView) view.findViewById(R.id.tv_away);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvAway.getText() + "'";
            }
        }
    }
}
