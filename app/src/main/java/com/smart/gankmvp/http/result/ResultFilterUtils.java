package com.smart.gankmvp.http.result;

/**
 * Created by vincentkin038 on 2017/12/1.
 */


import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.smartcentury.kcwork.http.exception.ApiException;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * 请求状态过滤
 *
 * @param <T> 过滤后返回类型
 * @author vincentkin038
 */
public class ResultFilterUtils<T> {

    private static int CODE_200 = 200;
    private static int CODE_201 = 201;
    private static int CODE_500 = 500;

    /**
     * 过滤
     *
     * @param observable 观察者
     * @return 过滤后的数据
     */
    @SuppressWarnings("rawtypes")
    public Observable<T> filterStatus(Observable observable) {
        return observable.map(new ResultFilter<T>());
    }

    /**
     * 过滤数据
     *
     * @param <T> 泛型
     */
    private static class ResultFilter<T> implements Function<Response<T>, T> {
        @Override
        public T apply(@NonNull Response<T> bean) throws Exception {
            LogUtils.d("IBaseModel", "code = " + bean.code());
            long code = bean.code();
//            if (code == CODE_200 || code == CODE_201) {
//                return bean.body();
//            }
//            else if (code == CODE_500) {
//                String errorBody = bean.errorBody().string();
//                if (errorBody != null &&!"".equals(errorBody)) {
//                    throw new ApiException(new JSONObject(errorBody).getString("msg"));
//                } else {
//                    throw new ApiException(bean.code());
//                }
//            }
//            else {
//                throw new ApiException(bean.code());
//            }
            if (code == CODE_200 || code == CODE_201) {
                return bean.body();
            } else {
                throw new ApiException(bean.code());
            }
        }
    }
}
