package com.phicdy.totoanticipation.view.activity

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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.phicdy.totoanticipation.BuildConfig
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.advertisement.AdViewHolder
import com.phicdy.totoanticipation.model.Game
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor
import com.phicdy.totoanticipation.model.JLeagueService
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor
import com.phicdy.totoanticipation.model.RakutenTotoService
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.model.storage.SettingStorage
import com.phicdy.totoanticipation.model.storage.SettingStorageImpl
import com.phicdy.totoanticipation.presenter.GameListPresenter
import com.phicdy.totoanticipation.view.GameListView
import com.phicdy.totoanticipation.view.fragment.TeamInfoFragment
import dagger.android.support.DaggerAppCompatActivity
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import javax.inject.Inject

class GameListActivity : DaggerAppCompatActivity(), GameListView {
    private var mTwoPane: Boolean = false
    private lateinit var presenter: GameListPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SimpleItemRecyclerViewAdapter
    private lateinit var progressBar: SmoothProgressBar

    @Inject
    lateinit var adProvider: AdProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { presenter.onFabClicked() }

        recyclerView = findViewById(R.id.game_list)

        if (findViewById<View>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        progressBar = findViewById(R.id.progress)

        val rakutenTotoService = RakutenTotoService.Factory.create()
        val rakutenTotoRequestExecutor = RakutenTotoRequestExecutor(rakutenTotoService)
        val jLeagueService = JLeagueService.Factory.create()
        val jLeagueRequestExecutor = JLeagueRequestExecutor(jLeagueService)
        val storage = GameListStorageImpl(this)
        val settingStorage = SettingStorageImpl(this) as SettingStorage
        presenter = GameListPresenter(this, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                DeadlineAlarm(this), settingStorage)
        presenter.onCreate()
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
        adapter = SimpleItemRecyclerViewAdapter()
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
}
