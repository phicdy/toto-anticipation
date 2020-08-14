package com.phicdy.totoanticipation.legacy.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.advertisement.AdViewHolder
import com.phicdy.totoanticipation.di_common.FragmentScope
import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.legacy.BuildConfig
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.legacy.model.storage.SettingStorageImpl
import com.phicdy.totoanticipation.legacy.presenter.GameListPresenter
import com.phicdy.totoanticipation.legacy.view.GameListView
import com.phicdy.totoanticipation.legacy.view.activity.TotoAnticipationActivity
import com.phicdy.totoanticipation.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.storage.GameListStorage
import com.phicdy.totoanticipation.storage.SettingStorage
import dagger.Provides
import dagger.android.support.DaggerFragment
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GameListFragment : GameListView, DaggerFragment(), CoroutineScope {

    private var mTwoPane: Boolean = false
    private lateinit var recyclerView: RecyclerView
    private val adapter by lazy { SimpleItemRecyclerViewAdapter() }
    private lateinit var progressBar: SmoothProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var content: View
    private lateinit var empty: ConstraintLayout

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    @Inject
    lateinit var presenter: GameListPresenter

    @Inject
    lateinit var adProvider: AdProvider

    private var isAnticipationMenuVisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.title = activity?.title

        recyclerView = view.findViewById(R.id.game_list)
        progressBar = view.findViewById(R.id.progress)
        content = view.findViewById(R.id.content)
        empty = view.findViewById(R.id.empty)
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener { presenter.onFabClicked() }

        if (view.findViewById<View>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        launch {
            presenter.onCreate()
        }
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_auto_anticipation)?.isVisible = isAnticipationMenuVisible
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_list, menu)
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
        activity?.title = getString(R.string.top_title, xxTh, deadline)
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
            val totoTopUrl = "https://sp.toto-dream.com/dcs/subos/screen/si01/ssin026/PGSSIN02601InittotoSP.form?holdCntId=$totoNum"
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(totoTopUrl))
        } else {
            intent = Intent(activity, TotoAnticipationActivity::class.java)
            intent.putExtra(TotoAnticipationActivity.KEY_TOTO_NUM, totoNum)
        }
        startActivity(intent)
    }

    override fun goToSetting() {
        findNavController().navigate(R.id.action_gameListFragment_to_settingFragment)
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

    private fun showSnackbar(@StringRes res: Int, @GameListFragment.SnackbarLength length: Int) {
        Snackbar.make(content, res, length).show()
    }

    override fun showPrivacyPolicyDialog() {
        val alert = AlertDialog.Builder(activity!!)
                .setMessage(Html.fromHtml(getString(R.string.privacy_policy_message)))
                .setCancelable(false)
                .setPositiveButton(R.string.accept) { _, _ ->
                    presenter.onPrivacyPolicyAccepted()
                }
                .setNegativeButton(R.string.not_accept) { _, _ ->
                    activity?.finish()
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
        activity?.invalidateOptionsMenu()
    }

    override fun showEmptyView() {
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

                    holder.mView.setOnClickListener { view ->
                        if (game.homeRanking == Game.defaultRank || game.awayRanking == Game.defaultRank) {
                            showSnackbar(R.string.not_support_foreign_league, Snackbar.LENGTH_SHORT)
                            return@setOnClickListener
                        }
                        view.findNavController().navigate(
                                GameListFragmentDirections.actionGameListFragmentToTeamInfoFragment(game.homeTeam, game.awayTeam)
                        )
                    }

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

    @dagger.Module
    object Module {
        @Provides
        @JvmStatic
        @FragmentScope
        fun provideGameListView(fragment: GameListFragment): GameListView = fragment

        @Provides
        @JvmStatic
        @FragmentScope
        fun provideGameListStorage(fragment: GameListFragment): GameListStorage = GameListStorageImpl(fragment.requireContext())

        @Provides
        @JvmStatic
        @FragmentScope
        fun provideDeadlineAlarm(fragment: GameListFragment): DeadlineAlarm = DeadlineAlarm(fragment.requireContext())

        @Provides
        @JvmStatic
        @FragmentScope
        fun provideSettingStorage(fragment: GameListFragment): SettingStorage = SettingStorageImpl(fragment.requireContext())
    }
}
