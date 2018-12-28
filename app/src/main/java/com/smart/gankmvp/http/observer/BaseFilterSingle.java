package com.smart.gankmvp.http.observer;

/**
 * Created by vincentkin038 on 2017/12/1.
 */

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 请求状态过滤
 * @param <T> 过滤后返回类型
 * @author vincentkin038
 */
public abstract class BaseFilterSingle<T> implements SingleObserver<T> {

    private final CompositeDisposable mDisposable;

    public BaseFilterSingle(CompositeDisposable disposable) {
        this.mDisposable = disposable;
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        mDisposable.add(disposable);
    }


    @Override
    public void onError(@NonNull Throwable throwable) {

        String error;
        if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException) {
            error = "超时了";
        } else if (throwable instanceof JsonSyntaxException) {
            error = "Json格式出错了";
            //假如导致这个异常触发的原因是服务器的问题，那么应该让服务器知道，所以可以在这里
            //选择上传原始异常描述信息给服务器
        } else {
            error = throwable.getMessage();
        }
        if (error != null) {
            LogUtils.d(error);
        }
    }
}