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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.togo.home.R;
import com.togo.home.data.model.SummaryWrapper;
import com.togo.home.data.remote.response.PatientFirstPageModel;
import com.togo.home.ui.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends FragmentActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.entityCell)
    protected RelativeLayout entityCell;

    @BindView(R.id.coverPhoto)
    protected ImageView coverPhoto;

    @BindView(R.id.displayName)
    protected TextView displayName;

    @BindView(R.id.emergeLine)
    protected TextView emergeLine;

    @BindView(R.id.serviceLine)
    protected TextView serviceLine;

    private Disposable disposable;
    private int ongoingId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tryRequest(4);
    }

    private void tryRequest(int appid) {
        if (appid == 0) {
            Log.e(TAG, "Skip invalid appid = " + appid);
            return;
        }

        if (ongoingId == appid) {
            Log.e(TAG, "Skip for ongoing request appid = " + appid);
            return;
        }

        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }

        ongoingId = appid;
        disposable = App.getRestClient().getWeatherService().fetchTogoHome(appid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        ongoingId = 0;
                    }
                })
                .subscribe(new Consumer<SummaryWrapper>() {
                               @Override
                               public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
                                   handleResponse(apiResponse);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                handleFailure(throwable.getMessage());
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void handleResponse(SummaryWrapper apiResponse) {
        PatientFirstPageModel model = apiResponse.getData();
        if (null == model) {
            // do nothing is the right behavior?
            return;
        }

        getActionBar().setTitle(model.getHospital_name());

        if (!TextUtils.isEmpty(model.getHospital_image())) {
            Picasso.with(this).load(model.getHospital_image()).into(coverPhoto);
        }

        displayName.setText(model.getHospital_name());
        emergeLine.setText(model.getComprehensive_service_telephone());
        serviceLine.setText(model.getEmergency_telephone());

        Log.e(TAG, "Hospital name : " + model.getAboutHospitalName());
        entityCell.setVisibility(View.VISIBLE);
    }

    private void handleFailure(String message) {
        Log.e(TAG, "Error : " + message);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        entityCell.setVisibility(View.GONE);
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
