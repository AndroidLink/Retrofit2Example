package com.togo.home.ui.util;

import android.util.Log;

import com.togo.home.data.model.SummaryWrapper;
import com.togo.home.data.remote.TogoService;
import com.togo.home.data.remote.response.PatientFirstPageModel;
import com.togo.home.ui.app.App;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yangfeng on 17-4-14.
 */

/// App Finder, scan possible app id
public class AppFinder {
    private static final String TAG = AppFinder.class.getSimpleName();

    private static final int START_INDEX = 2;

    private static AppFinder appFinder;

    private boolean working;
    private ScopeHelper scopeHelper;

    private TogoService togoService;
    private int tryingId;
    private int lastBackIndex;

    private Disposable disposable;

    private final Stack<Integer> foundCollection = new Stack<>();
    private final Map<Integer, SummaryWrapper> failureMap = new Hashtable<>();

    private Consumer consumer;

    interface ScopeHelper {
        int startIndex();
        int saveLastIndex();
    }

    public static AppFinder getInstance() {
        if (null == appFinder) {
            appFinder = new AppFinder();
        }

        return appFinder;
    }

    private AppFinder() {
        // not instance outside
        togoService = App.getRestClient().getServiceInstance();
    }

    public void setScopeHelper(ScopeHelper helper) {
        scopeHelper = helper;
    }

    public void subscribe() {
        subscribe(null);
    }

    public <T> void subscribe(Consumer<T> consumer) {
        // singleton, should add consumer to support multiple subscribe.
        this.consumer = consumer;

        if (working) {
            // skip
        } else {
            working = true;
            tryingId = getStartIndex();
            tryFinding();
        }
    }

    private void tryFinding() {
        assert(disposable.isDisposed());
//        if (foundCollection.contains(tryingId)) {
//            Log.e(TAG, "skip duplicate request id " + tryingId);
//            return;
//        }

        disposable = togoService.fetchTogoHome(tryingId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
//                        disposable.dispose();
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

    private void handleFailure(String message) {
        Log.e(TAG, message);
        working = false;
    }

    private void handleResponse(SummaryWrapper apiResponse) throws Exception {
        int code = apiResponse.getCode();
        if (code == 0) {
            PatientFirstPageModel model = apiResponse.getData();
            if (null == model) {
                Log.e(TAG, "null data response with code 0");
            }

            // todo: post such model for outside usage
            Log.d(TAG, "get model for " + tryingId + ", " + model.getAboutHospitalName());
            if (consumer != null) {
                consumer.accept(apiResponse);
            }

            nextGreeceTry();
        } else {
            // error code from server
            failureMap.put(tryingId, apiResponse);

            String desc = apiResponse.getCodeDesc();
            Log.e(TAG, tryingId + " : respond code " + code + " " + apiResponse.getCodeDescUser());
            if ("参数app为空".equals(desc)) {
                backRegretTry();
            } else if ("该app(12)对应的医院()不存在".equals(desc)) {
                nextGreeceTry();
            } else {
                // should be continue next or back?
                nextGreeceTry();
                // backRegretTry();
            }
        }
    }

    private String getFailureMapSummary() {
        if (failureMap.isEmpty()) {
            return "empty map";
        } else {
            StringBuilder builder = new StringBuilder("Map size ");
            builder.append(failureMap.size());
            Set<Integer> keySet = new TreeSet<>(failureMap.keySet());
            for (int key : keySet) {
                builder.append("\n");
                builder.append(key)
                        .append(":")
                        .append(failureMap.get(key).getCodeDescUser());
            }
            return builder.toString();
        }
    }

    private void nextGreeceTry() {
        if (tryingId + 1 == lastBackIndex) {
            stop();
            return;
        }

        foundCollection.push(tryingId);
        if (tryingId < lastBackIndex) {
            tryingId = (tryingId + lastBackIndex) / 2;
        } else {
            tryingId *= 2;
        }

        tryFinding();
    }

    // save current trying app id to 'lastBackIndex', and then
    // goto try back in the middle of app id between current and last found peek of 'foundCollection'.
    private void backRegretTry() {
        if (foundCollection.isEmpty()) {
            // do nothing
            Log.i(TAG, "none was explored yet. " + tryingId);
        } else {
            int peek = foundCollection.peek();
            Log.i(TAG, "find backward from " + tryingId + " to " + peek);
            lastBackIndex = tryingId;
            tryingId = (peek + tryingId) / 2;
            if (tryingId > peek) {
                tryFinding();
            } else {
                stop();
                Log.i(TAG, "seem to be last " + tryingId + " : " + peek);
                Log.d(TAG, "found stack: " + foundCollection);
                Log.d(TAG, "failure map: " + getFailureMapSummary());
            }
        }
    }

    private void stop() {
        working = false;
        disposable.dispose();
    }

    private int getStartIndex() {
        if (foundCollection.isEmpty()) {
            if (scopeHelper == null) {
                return START_INDEX;
            } else {
                return scopeHelper.startIndex();
            }
        } else {
            return foundCollection.peek();
        }
    }
}
