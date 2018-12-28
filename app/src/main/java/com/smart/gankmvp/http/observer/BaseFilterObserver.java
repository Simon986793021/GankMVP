package com.smart.gankmvp.http.observer;

/**
 * Created by vincentkin038 on 2017/12/1.
 */

import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonSyntaxException;
import com.smartcentury.kcwork.R;
import com.smartcentury.kcwork.model.ResultData;
import com.smartcentury.kcwork.model.ResultStatus;
import com.smartcentury.kcwork.util.GetUtil;
import com.smartcentury.kcwork.util.constants.AppConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 请求状态过滤
 * @param <T> 过滤后返回类型
 * @author vincentkin038
 */
public abstract class BaseFilterObserver<T> implements Observer<T> {

    private static String TAG ="FilterObserver";

    private final CompositeDisposable mDisposable;

    private String error;

    public BaseFilterObserver(CompositeDisposable disposable) {
        this.mDisposable = disposable;
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        mDisposable.add(disposable);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable throwable) {


        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException) {
            error = "超时了";
        } else if (throwable instanceof JsonSyntaxException) {
            error = "Json格式出错了";
            //假如导致这个异常触发的原因是服务器的问题，那么应该让服务器知道，所以可以在这里
            //选择上传原始异常描述信息给服务器
        } else {
            if(throwable.getMessage()==null||"".equals(throwable.getMessage())){
                error = GetUtil.INSTANCE.getString(R.string.try_again);
            }else {
                error = throwable.getMessage();
            }
        }
        if (error != null) {
            LogUtils.d(TAG, error);
        }
    }

    public String getError() {
        return error;
    }

    public void onNext(@NonNull ResultStatus t) {
         if(!t.getSuccess()&&needLogBackIn(t)){
            GetUtil.INSTANCE.getMContext().sendBroadcast(new Intent(AppConstants.INSTANCE.getBC_LOG_BACK_IN()));
            return;
         }
    }
    public Boolean needLogBackIn(ResultStatus t){
        return t.getErrorCode()==100006||t.getErrorCode()==999997;
    }
}