package com.ifree.uu.uubuy.service.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ifree.uu.uubuy.dialog.ProgressDialog;
import com.ifree.uu.uubuy.service.entity.CityInfoEntity;
import com.ifree.uu.uubuy.service.manager.DataManager;
import com.ifree.uu.uubuy.service.view.CityInfoView;
import com.ifree.uu.uubuy.service.view.View;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/8/20.
 * Description:
 */
public class CityInfoPresenter implements Presenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private CityInfoView mCityInfoView;
    private CityInfoEntity mCityInfoEntity;

    public CityInfoPresenter(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mCityInfoView = (CityInfoView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getSearchCityInfo(String mContent){
        final Dialog dialog = ProgressDialog.createLoadingDialog(mContext,mContent);
        dialog.show();
        mCompositeSubscription.add(manager.getSearchCityInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityInfoEntity>() {
                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                        if (mCityInfoEntity != null){
                            mCityInfoView.onSuccess(mCityInfoEntity);
                            Log.i("TAG", "onCompleted: " + new Gson().toJson(mCityInfoEntity));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        mCityInfoView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(CityInfoEntity CityInfoEntity) {
                        mCityInfoEntity = CityInfoEntity;
                    }
                }));
    }
}
