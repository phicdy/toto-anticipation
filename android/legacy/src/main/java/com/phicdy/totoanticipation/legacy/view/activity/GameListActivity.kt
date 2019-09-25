package com.phicdy.totoanticipation.legacy.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.advertisement.AdViewHolder
import com.phicdy.totoanticipation.di_common.ActivityScope
import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.legacy.BuildConfig
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorage
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.legacy.model.storage.SettingStorage
import com.phicdy.totoanticipation.legacy.model.storage.SettingStorageImpl
import com.phicdy.totoanticipation.legacy.presenter.GameListPresenter
import com.phicdy.totoanticipation.legacy.view.GameListView
import com.phicdy.totoanticipation.legacy.view.fragment.TeamInfoFragment
import dagger.Module
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import javax.inject.Inject

class GameListActivity : DaggerAppCompatActivity(), GameListView {
    private var mTwoPane: Boolean = false
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.game_list) }
    private val adapter by lazy { SimpleItemRecyclerViewAdapter() }
    private val progressBar by lazy { findViewById<SmoothProgressBar>(R.id.progress) }
    private val fab by lazy { findViewById<FloatingActionButton>(R.id.fab) }

    @Inject
    lateinit var presenter: GameListPresenter

    @Inject
    lateinit var adProvider: AdProvider

    private var isAnticipationMenuVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { presenter.onFabClicked() }

        if (findViewById<View>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        presenter.onCreate()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_auto_anticipation)?.isVisible = isAnticipationMenuVisible
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.game_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_auto_anticipation -> presenter.onOptionsAutoAnticipationSelected()
            R.id.menu_setting -> presenter.onOptionsSettingSelected()
        }
        return false
    }

    override fun initList() {
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = adapter
    }

    override fun setTitleFrom(xxTh: String, deadline: String) {
        title = getString(R.string.top_title, xxTh, deadline)
    }

    override fun startProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        progressBar.progressiveStop()
        val handler = Handler()
        handler.postDelayed({ progressBar.visibility = View.GONE }, 3000)
    }

    override fun startTotoAnticipationActivity(totoNum: String) {
        val intent: Intent
        if (BuildConfig.FLAVOR == "googlePlay") {
            // Google Play forbids to upload gambling app for Japan, open external browser
            val totoTopUrl = "http://sp.toto-dream.com/dci/sp/I/IMA/IMA01.do?op=inittotoSP&holdCntId=$totoNum"
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(totoTopUrl))
        } else {
            intent = Intent(this, TotoAnticipationActivity::class.java)
            intent.putExtra(TotoAnticipationActivity.KEY_TOTO_NUM, totoNum)
        }
        startActivity(intent)
    }

    override fun goToSetting() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun showAnticipationStart() {
        showSnackbar(R.string.start_auto_anticipation, Snackbar.LENGTH_SHORT)
    }

    override fun showAnticipationFinish() {
        showSnackbar(R.string.finish_auto_anticipation, Snackbar.LENGTH_SHORT)
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    override fun showAnticipationNotSupport() {
        showSnackbar(R.string.anticipation_not_support, Snackbar.LENGTH_SHORT)
    }

    private fun showSnackbar(@StringRes res: Int, @SnackbarLength length: Int) {
        val view = findViewById<View>(android.R.id.content) ?: return
        Snackbar.make(view, res, length).show()
    }

    override fun showPrivacyPolicyDialog() {
        val alert = AlertDialog.Builder(this)
                .setMessage(Html.fromHtml(getString(R.string.privacy_policy_message)))
                .setCancelable(false)
                .setPositiveButton(R.string.accept) { _, _ ->
                    presenter.onPrivacyPolicyAccepted()
                }
                .setNegativeButton(R.string.not_accept) { _, _ ->
                    finish()
                }
                .create()
        alert.show()
        alert.findViewById<TextView>(android.R.id.message)?.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun hideList() {
        recyclerView.visibility = View.GONE
    }

    override fun hideFab() {
        fab.hide()
    }

    override fun hideAnticipationMenu() {
        isAnticipationMenuVisible = false
        invalidateOptionsMenu()
    }

    override fun showEmptyView() {
        val empty = findViewById<ConstraintLayout>(R.id.empty)
        empty.visibility = View.VISIBLE
    }

    @IntDef(Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG)
    internal annotation class SnackbarLength

    internal inner class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val VIEW_TYPE_CONTENT = 1
        private val VIEW_TYPE_AD = 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                VIEW_TYPE_CONTENT -> {
                    val view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.game_list_content, parent, false)
                    ViewHolder(view)
                }
                VIEW_TYPE_AD -> adProvider.newViewHolderInstance(parent)
                else -> throw IllegalStateException()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is ViewHolder -> {
                    val game = presenter.gameAt(position)
                    holder.tvHome.text = when (game.homeRanking) {
                        Game.defaultRank -> getString(R.string.team_label, "- ", game.homeTeam)
                        else -> getString(R.string.team_label, game.homeRanking.toString(), game.homeTeam)
                    }
                    holder.tvAway.text = when (game.awayRanking) {
                        Game.defaultRank -> getString(R.string.team_label, "- ", game.awayTeam)
                        else -> getString(R.string.team_label, game.awayRanking.toString(), game.awayTeam)
                    }

                    holder.mView.setOnClickListener(View.OnClickListener { v ->
                        if (game.homeRanking == Game.defaultRank || game.awayRanking == Game.defaultRank) {
                            showSnackbar(R.string.not_support_foreign_league, Snackbar.LENGTH_SHORT)
                            return@OnClickListener
                        }
                        if (mTwoPane) {
                            val arguments = Bundle().apply {
                                putString(TeamInfoActivity.ARG_HOME_TEAM, game.homeTeam)
                                putString(TeamInfoActivity.ARG_AWAY_TEAM, game.awayTeam)
                            }
                            val fragment = TeamInfoFragment()
                            fragment.arguments = arguments
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit()
                        } else {
                            val context = v.context
                            val intent = Intent(context, TeamInfoActivity::class.java).apply {
                                putExtra(TeamInfoActivity.ARG_HOME_TEAM, game.homeTeam)
                                putExtra(TeamInfoActivity.ARG_AWAY_TEAM, game.awayTeam)
                            }

                            context.startActivity(intent)
                        }
                    })

                    when (game.anticipation) {
                        Game.Anticipation.HOME -> holder.rbHome.isChecked = true
                        Game.Anticipation.AWAY -> holder.rbAway.isChecked = true
                        Game.Anticipation.DRAW -> holder.rbDraw.isChecked = true
                    }
                    holder.rbHome.setOnCheckedChangeListener { _, isChecked -> presenter.onHomeRadioButtonClicked(holder.adapterPosition, isChecked) }
                    holder.rbAway.setOnCheckedChangeListener { _, isChecked -> presenter.onAwayRadioButtonClicked(holder.adapterPosition, isChecked) }
                    holder.rbDraw.setOnCheckedChangeListener { _, isChecked -> presenter.onDrawRadioButtonClicked(holder.adapterPosition, isChecked) }
                }
                is AdViewHolder -> holder.bind()
            }
        }

        override fun getItemCount(): Int {
            return presenter.gameSize() + 1 // 1 for Ad
        }

        override fun getItemViewType(position: Int) = if (position == presenter.gameSize()) VIEW_TYPE_AD else VIEW_TYPE_CONTENT
    }

    internal inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvHome: TextView = mView.findViewById(R.id.tv_home)
        val tvAway: TextView = mView.findViewById(R.id.tv_away)
        val rbHome: RadioButton = mView.findViewById(R.id.rb_home)
        val rbAway: RadioButton = mView.findViewById(R.id.rb_away)
        val rbDraw: RadioButton = mView.findViewById(R.id.rb_draw)

        override fun toString(): String {
            return super.toString() + " '" + tvAway.text + "'"
        }
    }

    @Module
    class GameListActivityModule {
        @Provides
        @ActivityScope
        fun provideGameListView(activity: GameListActivity): GameListView = activity

        @Provides
        @ActivityScope
        fun provideGameListStorage(activity: GameListActivity): GameListStorage = GameListStorageImpl(activity)

        @Provides
        @ActivityScope
        fun provideDeadlineAlarm(activity: GameListActivity): DeadlineAlarm = DeadlineAlarm(activity)

        @Provides
        @ActivityScope
        fun provideSettingStorage(activity: GameListActivity): SettingStorage = SettingStorageImpl(activity)
    }
}
