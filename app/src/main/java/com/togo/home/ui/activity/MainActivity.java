package com.togo.home.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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

    @BindView(R.id.entityCell)
    protected RelativeLayout entityCell;

    @BindView(R.id.coverPhoto)
    protected ImageView coverPhoto;

    @BindView(R.id.displayName)
    protected TextView displayName;

    @BindView(R.id.emergentLine)
    protected TextView emergentLine;

    @BindView(R.id.serviceLine)
    protected TextView serviceLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppFinder.getInstance()
                .subscribe(new Consumer<SummaryWrapper>() {
                               @Override
                               public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
                                   handleResponse(apiResponse);
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                rangeFetch(AppFinder.getInstance().min(), AppFinder.getInstance().max());
                            }
                        });
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

//        getActionBar().setTitle(model.getDisplayName());

        if (!TextUtils.isEmpty(model.getCoverPhoto())) {
            Picasso.with(this).load(model.getCoverPhoto()).into(coverPhoto);
        }

        displayName.setText(model.getDisplayName());
        emergentLine.setText(model.getEmergentLine());
        serviceLine.setText(model.getServiceLine());

        Log.e(TAG, "Hospital name : " + model.getAboutHospitalName());
        entityCell.setVisibility(View.VISIBLE);
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
}
