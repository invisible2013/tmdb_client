package com.flatrocktech.mymovies.service.gateway;

public interface Callback<T> {
    void onSuccess(T data);

    void onFailure(int message);
}
