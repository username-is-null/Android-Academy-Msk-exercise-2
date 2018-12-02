package com.androidacademy.msk.exerciseproject.screen.newslist

import kotlinx.android.synthetic.main.fragment_news_list.*
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.di.Injector
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen.ScreenState.*
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen.ViewVisibilitySwitcher
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_error.*
import javax.inject.Inject

class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    private lateinit var adapter: NewsAdapter
    private lateinit var visibilitySwitcher: ViewVisibilitySwitcher
    private lateinit var listener: ItemClickListener
    private var position: Int = 0

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsListPresenter

    @ProvidePresenter
    internal fun providePresenter(): NewsListPresenter? {
        Injector.getInstance().dbAndNetworkComponent.inject(this)
        return presenter
    }

    companion object {
        private const val MIN_WIDTH_IN_DP = 300
        private const val KEY_LIST_POSITION = "KEY_LIST_POSITION"
        private const val TABLET_WIDTH = 720

        fun newInstance() = NewsListFragment()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = savedInstanceState?.getInt(KEY_LIST_POSITION) ?: 0
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visibilitySwitcher = ViewVisibilitySwitcher(
                recyclerview_newslist,
                progressbar_newslist,
                errorview_newslist,
                emptyview_newslist)

        button_viewerror_try_again?.setOnClickListener { presenter.onFabClicked() }

        fab_newslist.setOnClickListener { presenter.onFabClicked() }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupRecyclerView(recyclerview_newslist)
    }

    override fun onResume() {
        super.onResume()
        if (position != 0) {
            recyclerview_newslist.layoutManager?.scrollToPosition(position)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuitem_open_about -> {
                startActivity(AboutActivity.getStartIntent(activity!!))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
        val layoutManager = recyclerview_newslist.layoutManager
        position = if (layoutManager is LinearLayoutManager) {
            layoutManager.findFirstCompletelyVisibleItemPosition()
        } else {
            (layoutManager as StaggeredGridLayoutManager).findFirstCompletelyVisibleItemPositions(null)[0]
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_LIST_POSITION, position)
    }

    override fun showNews(news: List<DbNewsItem>) {
        visibilitySwitcher.setUiState(HAS_DATA)
        adapter.addListData(news)
        recyclerview_newslist.scrollToPosition(0)
    }

    override fun showError() {
        visibilitySwitcher.setUiState(ERROR)
    }

    override fun showEmptyView() {
        visibilitySwitcher.setUiState(EMPTY)
    }

    override fun showProgressBar() {
        visibilitySwitcher.setUiState(LOADING)
    }

    override fun openDetailsScreen(id: Int) {
        listener.onNewItemClicked(id)
    }

    override fun setCurrentSectionInSpinner(position: Int) {
        spinner_newslist.setSelection(position)
    }

    fun spinnerItemClicked(id: Int) {
        presenter.onSpinnerItemClicked(Section.values()[id])
    }

    private fun setItemDecoration(recyclerView: RecyclerView) {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val dividerDrawable = ContextCompat.getDrawable(context!!, R.drawable.divider)
        if (dividerDrawable != null) {
            decoration.setDrawable(dividerDrawable)
        }
        recyclerView.addItemDecoration(decoration)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                when {
                    dy > 0 -> fab_newslist.hide()
                    dy < 0 -> fab_newslist.show()
                }
            }
        })

        setLayoutManager(recyclerView)
        setItemDecoration(recyclerView)
        val clickListener = object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(id: Int) {
                presenter.onItemClicked(id)
            }
        }
        adapter = NewsAdapter(clickListener, context!!)
        recyclerView.adapter = adapter
    }

    private fun setLayoutManager(recyclerView: RecyclerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density

        val layoutManager: RecyclerView.LayoutManager
        layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                screenWidthInDp >= TABLET_WIDTH || screenWidthInDp < MIN_WIDTH_IN_DP) {
            LinearLayoutManager(context)
        } else {
            val snapCount = screenWidthInDp.toInt() / MIN_WIDTH_IN_DP
            StaggeredGridLayoutManager(snapCount, StaggeredGridLayoutManager.VERTICAL)
        }

        recyclerView.layoutManager = layoutManager
    }

//    private fun setupSpinner(spinner: Spinner) {
//        presenter.onSetupSpinnerPosition()
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                presenter.onSpinnerItemClicked(Section.values()[position])
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }
//    }


    interface ItemClickListener {
        fun onNewItemClicked(id: Int)
    }
}