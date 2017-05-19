package com.togo.home.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.togo.home.R
import com.togo.home.data.model.SummaryWrapper
import com.togo.home.data.retrofit.ServiceGenerator
import com.togo.home.data.retrofit.response.PatientFirstPageModel
import com.togo.home.data.source.PagesRepository
import com.togo.home.data.source.local.PagesLocalDataSource
import com.togo.home.data.source.remote.PagesRemoteDataSource
import com.togo.home.ui.util.AppFinder
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.content_layout.*

class MainActivity : FragmentActivity() {

    private var hospitalAdapter: HospitalAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    /// AppFinder helper class region
    /// Singleton instance, subscribe with two objects: 1. a consumer, with the name seekingRefresher, to
    /// listen for app info refreshing while seeking the maximize ID. 2. an action, with the name
    /// seekingFinal, to listen for the notification of seeking complete.
    private val appFinder = AppFinder.getInstance()

    @NonNull
    private var packagesRepository: PagesRepository? = null

    @NonNull
    private var compositeDisposable: CompositeDisposable? = null

    private val seekingRefresher = Consumer<SummaryWrapper> { apiResponse ->
        // do nothing
        handleResponse(apiResponse)
    }

    private val seekingFinal = Action { rangeFetch(AppFinder.getInstance().min(), AppFinder.getInstance().max()) }
    /// AppFinder region end

    /// Fetch app first page region
    /// loop a range of app id, filtered by seekCacheChecker that skip those ids that has
    /// been refreshed before, then flatMap to request of app first page info, in the end
    /// consume by final appFirstPageConsumer.
    /// see to method 'rangeFetch'
    private val seekCacheChecker = Predicate<Int> { id -> AppFinder.getInstance().skip(id!!) }

    private val appId2FirstPageMapper = Function<Int, ObservableSource<SummaryWrapper>> { appId -> ServiceGenerator.getInstance().serviceInstance.fetchTogoHome(appId) }

    private val appFirstPageConsumer = Consumer<SummaryWrapper> { apiResponse -> handleResponse(apiResponse) }
    /// Fetch app first page region end

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()
        packagesRepository = PagesRepository.getInstance(PagesRemoteDataSource.getInstance(),
                PagesLocalDataSource.getInstance(applicationContext))

        //        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layoutManager
        hospitalAdapter = HospitalAdapter(this)

        rv.adapter = hospitalAdapter

        packagesRepository!!.refreshFirstPageModels()
    }

    override fun onResume() {
        super.onResume()
        fetchSavedModel()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable!!.clear()
        hospitalAdapter!!.setOnItemClickListener(null)
    }

    private fun rangeFetch(min: Int, max: Int) {
        Log.e(TAG, "rangeFetch fetching app: $min - $max")
        val disposable = Observable.range(min, max)
                .filter(seekCacheChecker)
                .flatMap(appId2FirstPageMapper)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(appFirstPageConsumer)

        compositeDisposable!!.add(disposable)
    }


    private fun handleResponse(apiResponse: SummaryWrapper) {
        val model = apiResponse.data ?: // do nothing is the right behavior?
                return

        hospitalAdapter!!.add(model)
        updateTitle()
        saveModel(model)
        rv.scrollToPosition(hospitalAdapter!!.itemCount - 1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun fetchSavedModel() {
        val disposable = packagesRepository!!.firstPageModels
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { packages ->
                    if (packages.isEmpty()) {
                        appFinder.setScopeHelper(ScopeHelperImpl(applicationContext))
                        appFinder.subscribe(seekingRefresher, seekingFinal)
                    } else {
                        hospitalAdapter!!.addAll(packages)
                        updateTitle()
                    }
                }
        compositeDisposable!!.add(disposable)
    }

    private fun updateTitle() {
        title = getString(R.string.display_title, "" + hospitalAdapter!!.itemCount)
    }

    private fun saveModel(model: PatientFirstPageModel) {
        // save model to local db.
        packagesRepository!!.saveFirstPageModel(model)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
