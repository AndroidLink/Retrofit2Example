package com.togo.home.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.togo.home.R;
import com.togo.home.data.model.SummaryWrapper;
import com.togo.home.data.remote.response.PatientFirstPageModel;
import com.togo.home.ui.app.App;
import com.togo.home.ui.util.AppFinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends FragmentActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private HospitalAdapter hospitalAdapter;
    private LinearLayoutManager layoutManager;

    /// AppFinder helper class region
    /// Singleton instance, subscribe with two objects: 1. a consumer, with the name seekingRefresher, to
    /// listen for app info refreshing while seeking the maximize ID. 2. an action, with the name
    /// seekingFinal, to listen for the notification of seeking complete.
    private final AppFinder appFinder = AppFinder.getInstance();

    private final Consumer seekingRefresher = new Consumer<SummaryWrapper>() {
        @Override
        public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
            // do nothing
            handleResponse(apiResponse);
        }
    };

    private final Action seekingFinal = new Action() {
        @Override
        public void run() throws Exception {
            rangeFetch(AppFinder.getInstance().min(), AppFinder.getInstance().max());
        }
    };
    /// AppFinder region end

    /// Fetch app first page region
    /// loop a range of app id, filtered by seekCacheChecker that skip those ids that has
    /// been refreshed before, then flatMap to request of app first page info, in the end
    /// consume by final appFirstPageConsumer.
    /// see to method 'rangeFetch'
    private final Predicate seekCacheChecker = new Predicate<Integer>() {
        @Override
        public boolean test(@NonNull Integer id) throws Exception {
            return AppFinder.getInstance().skip(id);
        }
    };

    private final Function appId2FirstPageMapper = new Function<Integer, ObservableSource<SummaryWrapper>>() {
        @Override
        public ObservableSource<SummaryWrapper> apply(@NonNull Integer appId) throws Exception {
            return App.getRestClient().getServiceInstance().fetchTogoHome(appId);
        }
    };

    private final Consumer appFirstPageConsumer = new Consumer<SummaryWrapper>() {
        @Override
        public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
            handleResponse(apiResponse);
        }
    };
    /// Fetch app first page region end


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        hospitalAdapter = new HospitalAdapter(this);

        recyclerView.setAdapter(hospitalAdapter);

        fetchSavedModel();
        appFinder.setScopeHelper(new ScopeHelperImpl(getApplicationContext()));
        appFinder.subscribe(seekingRefresher, seekingFinal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hospitalAdapter.setOnItemClickListener(null);
    }

    private void rangeFetch(int min, int max) {
        Log.e(TAG, "rangeFetch fetching app: " + min + " - " + max);
        Observable.range(min, max)
                .filter(seekCacheChecker)
                .flatMap(appId2FirstPageMapper)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(appFirstPageConsumer);
    }


    private void handleResponse(SummaryWrapper apiResponse) {
        PatientFirstPageModel model = apiResponse.getData();
        if (null == model) {
            // do nothing is the right behavior?
            return;
        }

        hospitalAdapter.add(model);
        saveModel(model);
        recyclerView.scrollToPosition(hospitalAdapter.getItemCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchSavedModel() {
        List allSavedModel = querySavedModelFromLocalDb();
        hospitalAdapter.addAll(allSavedModel);
    }

    private List<PatientFirstPageModel> querySavedModelFromLocalDb() {
        return new ArrayList<>();
    }

    private void saveModel(PatientFirstPageModel model) {
        // save model to local db.
    }
}
