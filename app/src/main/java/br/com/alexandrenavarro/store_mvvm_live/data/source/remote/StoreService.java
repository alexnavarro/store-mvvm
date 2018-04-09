package br.com.alexandrenavarro.store_mvvm_live.data.source.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface StoreService {

    @GET("listApps")
    Observable<Result> loadApps();
}