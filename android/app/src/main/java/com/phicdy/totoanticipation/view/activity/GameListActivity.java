package com.phicdy.totoanticipation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.Game;
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor;
import com.phicdy.totoanticipation.model.JLeagueService;
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor;
import com.phicdy.totoanticipation.model.RakutenTotoService;
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl;
import com.phicdy.totoanticipation.model.storage.SettingStorage;
import com.phicdy.totoanticipation.model.storage.SettingStorageImpl;
import com.phicdy.totoanticipation.presenter.GameListPresenter;
import com.phicdy.totoanticipation.view.GameListView;
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment;

import java.util.concurrent.Executor;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class GameListActivity extends AppCompatActivity implements GameListView {

    private boolean mTwoPane;
    private GameListPresenter presenter;
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter adapter;
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
                presenter.onFabClicked();
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

        final RakutenTotoService rakutenTotoService = RakutenTotoService.Factory.create();
        final RakutenTotoRequestExecutor rakutenTotoRequestExecutor =
                new RakutenTotoRequestExecutor(rakutenTotoService);
        final JLeagueService jLeagueService = JLeagueService.Factory.create();
        final JLeagueRequestExecutor jLeagueRequestExecutor = new JLeagueRequestExecutor(jLeagueService);
        GameListStorage storage = new GameListStorageImpl(this);
        SettingStorage settingStorage = new SettingStorageImpl(this);
        presenter = new GameListPresenter(this, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                settingStorage.isDeadlineNotify(), new DeadlineAlarm(this));
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_auto_anticipation:
                presenter.onOptionsAutoAnticipationSelected();
                break;
            case R.id.menu_setting:
                presenter.onOptionsSettingSelected();
                break;
        }
        return false;
    }

    @Override
    public void initList() {
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new SimpleItemRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setTitleFrom(@NonNull String xxTh, @NonNull String deadline) {
        setTitle(getString(R.string.top_title, xxTh, deadline));
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

    @Override
    public void startTotoAnticipationActivity(@NonNull String totoNum) {
        Intent intent = new Intent(this, TotoAnticipationActivity.class);
        intent.putExtra(TotoAnticipationActivity.KEY_TOTO_NUM, totoNum);
        startActivity(intent);
    }

    @Override
    public void goToSetting() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void showAnticipationStart() {
        View view = findViewById(android.R.id.content);
        if (view == null) return;
        Snackbar.make(view, R.string.start_auto_anticipation, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAnticipationFinish() {
        View view = findViewById(android.R.id.content);
        if (view == null) return;
        Snackbar.make(view, R.string.finish_auto_anticipation, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
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
            if (holder.game != null) {
                holder.tvHome.setText(getString(R.string.team_label, String.valueOf(holder.game.getHomeRanking()), holder.game.getHomeTeam()));
                holder.tvAway.setText(getString(R.string.team_label, String.valueOf(holder.game.getAwayRanking()), holder.game.getAwayTeam()));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TeamInfoActivity.ARG_HOME_TEAM, holder.game.getHomeTeam());
                        arguments.putString(TeamInfoActivity.ARG_AWAY_TEAM, holder.game.getAwayTeam());
                        TeamInfoFragment fragment = new TeamInfoFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TeamInfoActivity.class);
                        intent.putExtra(TeamInfoActivity.ARG_HOME_TEAM, holder.game.getHomeTeam());
                        intent.putExtra(TeamInfoActivity.ARG_AWAY_TEAM, holder.game.getAwayTeam());

                        context.startActivity(intent);
                    }
                }
            });

            switch (holder.game.getAnticipation()) {
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
