package com.togo.home.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.togo.home.R;
import com.togo.home.data.model.SummaryWrapper;
import com.togo.home.data.remote.response.PatientFirstPageModel;
import com.togo.home.ui.app.App;
import com.togo.home.ui.util.AppFinder;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends FragmentActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private HospitalAdapter hospitalAdapter;
    private StaggeredGridLayoutManager layoutManager;

//    private PagingInfo pagingInfo;
//    private boolean isLoading = false;

    private int firstVisibleItem() {
        int[] positions = layoutManager.findFirstVisibleItemPositions(null);
        return positions[1];
    }

    private int lastVisibleItem() {
        int[] positions = layoutManager.findLastVisibleItemPositions(null);
        return positions[0];
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = recyclerView.getAdapter().getItemCount();
            int firstVisibleItem = firstVisibleItem();

            if ((visibleItemCount + firstVisibleItem) >= totalItemCount
                    && totalItemCount > 0
                    /*&& !isLoading
                    && !pagingInfo.isLastPage()*/) {
//                pagingInfo.incrementPage();
                // load page
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        hospitalAdapter = new HospitalAdapter(this);
//        hospitalAdapter.setOnItemClickListener(this);
//        hospitalAdapter.setOnReloadClickListener(this);
//        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setAdapter(hospitalAdapter);

        // Pagination
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        AppFinder.getInstance()
                .subscribe(new Consumer<SummaryWrapper>() {
                               @Override
                               public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
                                   // do nothing
//                                   handleResponse(apiResponse);
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                rangeFetch(AppFinder.getInstance().min(), AppFinder.getInstance().max());
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hospitalAdapter.setOnItemClickListener(null);
        recyclerView.removeOnScrollListener(recyclerViewOnScrollListener);
    }

    private void rangeFetch(int min, int max) {
        Log.e(TAG, "rangeFetch fetching app: " + min + " - " + max);
        Observable.range(min, max)
                .flatMap(new Function<Integer, ObservableSource<SummaryWrapper>>() {
                    @Override
                    public ObservableSource<SummaryWrapper> apply(@NonNull Integer appId) throws Exception {
                        return App.getRestClient().getServiceInstance().fetchTogoHome(appId);
                    }
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SummaryWrapper>() {
                    @Override
                    public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
                        handleResponse(apiResponse);
                    }
                });
    }


    private void handleResponse(SummaryWrapper apiResponse) {
        PatientFirstPageModel model = apiResponse.getData();
        if (null == model) {
            // do nothing is the right behavior?
            return;
        }

        hospitalAdapter.add(model);
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

    public void scrollToTop(){
        recyclerView.scrollToPosition(0);
    }
}
