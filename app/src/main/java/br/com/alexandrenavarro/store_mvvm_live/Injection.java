package br.com.alexandrenavarro.store_mvvm_live;

import android.content.Context;
import android.support.annotation.NonNull;

import br.com.alexandrenavarro.store_mvvm_live.data.source.StoreRepository;
import br.com.alexandrenavarro.store_mvvm_live.data.source.remote.StoreRemoteDataSource;
import br.com.alexandrenavarro.store_mvvm_live.util.BaseSchedulerProvider;
import br.com.alexandrenavarro.store_mvvm_live.util.SchedulerProvider;

import static com.google.common.base.Preconditions.checkNotNull;


public class Injection {

    public static StoreRepository provideStoreRepository(@NonNull Context context) {
        checkNotNull(context);
        return StoreRepository.getInstance(StoreRemoteDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
