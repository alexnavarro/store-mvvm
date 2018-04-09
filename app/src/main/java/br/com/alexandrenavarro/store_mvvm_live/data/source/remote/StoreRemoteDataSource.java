package br.com.alexandrenavarro.store_mvvm_live.data.source.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import br.com.alexandrenavarro.store_mvvm_live.data.source.StoreDataSource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreRemoteDataSource implements StoreDataSource {

    private static StoreRemoteDataSource INSTANCE;

    public static StoreRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StoreRemoteDataSource();
        }
        return INSTANCE;
    }

    private StoreRemoteDataSource(){}

    public static Retrofit getAdapter() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://ws2.aptoide.com/api/6/bulkRequest/api_list/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public Flowable<List<App>> loadApplications() {
       return getAdapter().create(StoreService.class)
                .loadApps()
                .map(new Function<Result, List<App>>() {
                    @Override
                    public List<App> apply(Result result) throws Exception {
                        return result.responses.listApps.datasets.all.data.list;
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }
}