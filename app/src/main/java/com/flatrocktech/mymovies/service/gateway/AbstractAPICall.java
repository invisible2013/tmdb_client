package com.flatrocktech.mymovies.service.gateway;

import com.flatrocktech.mymovies.BuildConfig;
import com.flatrocktech.mymovies.R;

import retrofit2.Call;
import retrofit2.Response;

public abstract class AbstractAPICall<T> {

    private ApiInterface apiInterface;

    public AbstractAPICall() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public void call(Callback callback) {
        Call<T> call = buildCaller(apiInterface);
        call.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFailure(R.string.something_went_wrong);
            }
        });
    }

    protected abstract Call<T> buildCaller(ApiInterface apiInterface);

    protected String getApiKey() {
        return BuildConfig.TMDB_KEY;
    }
}
