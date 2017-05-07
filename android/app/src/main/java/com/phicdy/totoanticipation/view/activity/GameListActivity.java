package com.phicdy.totoanticipation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.phicdy.totoanticipation.BuildConfig;
import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.GameListStorage;
import com.phicdy.totoanticipation.model.GameListStorageImpl;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.presenter.GameListPresenter;
import com.phicdy.totoanticipation.view.GameListView;
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class GameListActivity extends AppCompatActivity implements GameListView {

    private boolean mTwoPane;
    private GameListPresenter presenter;
    private GameListStorage storage;
    private RecyclerView recyclerView;
    private SmoothProgressBar progressBar;

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

        recyclerView = (RecyclerView) findViewById(R.id.game_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        progressBar = (SmoothProgressBar) findViewById(R.id.progress);

        final RakutenTotoService service = RakutenTotoService.Factory.create();
        final RakutenTotoRequestExecutor executor = new RakutenTotoRequestExecutor(service);
        storage = new GameListStorageImpl(this);
        presenter = new GameListPresenter(executor, storage);
        presenter.setView(this);
        presenter.onCreate();
    }

    @Override
    public void initList() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter());
    }

    @Override
    public void setTitleFrom(@NonNull String xxTh) {
        setTitle(getString(R.string.top_title, xxTh));
    }

    @Override
    public void startProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgress() {
        progressBar.progressiveStop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 3000);
    }

    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.game = presenter.gameAt(position);
            holder.tvHome.setText(holder.game.homeTeam);
            holder.tvAway.setText(holder.game.awayTeam);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TeamInfoActivity.ARG_HOME_TEAM, holder.game.homeTeam);
                        arguments.putString(TeamInfoActivity.ARG_AWAY_TEAM, holder.game.awayTeam);
                        TeamInfoFragment fragment = new TeamInfoFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TeamInfoActivity.class);
                        intent.putExtra(TeamInfoActivity.ARG_HOME_TEAM, holder.game.homeTeam);
                        intent.putExtra(TeamInfoActivity.ARG_AWAY_TEAM, holder.game.awayTeam);

                        context.startActivity(intent);
                    }
                }
            });

            switch (holder.game.anticipation) {
                case HOME:
                    holder.rbHome.setChecked(true);
                    break;
                case AWAY:
                    holder.rbAway.setChecked(true);
                    break;
                case DRAW:
                    holder.rbDraw.setChecked(true);
                    break;
            }
            holder.rbHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.onHomeRadioButtonClicked(holder.getAdapterPosition(), isChecked);
                }
            });
            holder.rbAway.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.onAwayRadioButtonClicked(holder.getAdapterPosition(), isChecked);
                }
            });
            holder.rbDraw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.onDrawRadioButtonClicked(holder.getAdapterPosition(), isChecked);
                }
            });
        }

        @Override
        public int getItemCount() {
            return presenter.gameSize();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView tvHome;
            final TextView tvAway;
            final RadioButton rbHome;
            final RadioButton rbAway;
            final RadioButton rbDraw;
            Game game;

            ViewHolder(View view) {
                super(view);
                mView = view;
                tvHome = (TextView) view.findViewById(R.id.tv_home);
                tvAway = (TextView) view.findViewById(R.id.tv_away);
                rbHome = (RadioButton) view.findViewById(R.id.rb_home);
                rbAway = (RadioButton) view.findViewById(R.id.rb_away);
                rbDraw = (RadioButton) view.findViewById(R.id.rb_draw);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvAway.getText() + "'";
            }
        }
    }
}
