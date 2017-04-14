package com.togo.home.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.togo.home.R;
import com.togo.home.data.model.SummaryWrapper;
import com.togo.home.data.remote.response.PatientFirstPageModel;
import com.togo.home.ui.app.App;
import com.togo.home.ui.util.AppFinder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends Activity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.activity_main_data)
    protected RelativeLayout dataLayout;

    @BindView(R.id.activity_main_weather)
    protected RelativeLayout weatherLayout;

    @BindView(R.id.activity_main_search)
    protected EditText searchEditText;

    @BindView(R.id.activity_main_sys_country_value)
    protected TextView countryTextView;

    @BindView(R.id.activity_main_sys_sunrise_value)
    protected TextView sunriseTextView;

    @BindView(R.id.activity_main_sys_sunset_value)
    protected TextView sunsetTextView;

    @BindView(R.id.activity_main_weather_icon)
    protected ImageView iconImageView;

    @BindView(R.id.activity_main_weather_text)
    protected TextView weatherTextView;

    private Disposable disposable;
    private int ongoingId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        tryRequest(4);
        AppFinder.getInstance()
                .subscribe(new Consumer<SummaryWrapper>() {
                    @Override
                    public void accept(@NonNull SummaryWrapper apiResponse) throws Exception {
                        handleResponse(apiResponse);
                    }
                });
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
        disposable = App.getRestClient().getServiceInstance().fetchTogoHome(appid)
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

    @OnClick(R.id.activity_main_search_button)
    protected void onSearchClick() {
//        if (!searchEditText.getText().toString().equals("")){
//            tryRequest(Integer.parseInt(searchEditText.getText().toString()));
//        }
    }

    private void handleResponse(SummaryWrapper apiResponse) {
        PatientFirstPageModel model = apiResponse.getData();
        if (null == model) {
            // do nothing is the right behavior?
            return;
        }

        getActionBar().setTitle(model.getAboutHospitalName());
        countryTextView.setText(model.getAboutHospitalName());
        if (!TextUtils.isEmpty(model.getHospital_image())) {
            Picasso.with(this).load(model.getHospital_image()).into(iconImageView);
        }
        weatherTextView.setText(model.getEmergency_telephone());
        sunsetTextView.setText(model.getAboutQrCode());
        sunriseTextView.setText(model.getAboutQrCode());

//        final Date sunriseDate = new Date(apiResponse.getSys().getSunriseTime() * 1000);
//        final Date sunsetDate = new Date(apiResponse.getSys().getSunsetTime() * 1000);
//        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh':'mm':'ss a");
//
//        getActionBar().setTitle(apiResponse.getStrCityName());
//        countryTextView.setText(apiResponse.getSys().getStrCountry());
//
//        if (!apiResponse.getWeather().isEmpty())
//        {
//            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + apiResponse.getWeather().get(0).getStrIconName() + ".png").into(iconImageView);
//            weatherTextView.setText(apiResponse.getWeather().get(0).getStrDesc());
//        }
//
//        sunsetTextView.setText(simpleDateFormat.format(sunsetDate));
//        sunriseTextView.setText(simpleDateFormat.format(sunriseDate));

        searchEditText.setText("");
        Log.e(TAG, "Hospital name : " + model.getAboutHospitalName());
        dataLayout.setVisibility(View.VISIBLE);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void handleFailure(String message) {
        Log.e(TAG, "Error : " + message);
        searchEditText.setText("");
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        dataLayout.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.GONE);
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
