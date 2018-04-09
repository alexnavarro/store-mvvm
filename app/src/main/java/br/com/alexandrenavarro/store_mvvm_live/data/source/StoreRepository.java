package br.com.alexandrenavarro.store_mvvm_live.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

public class StoreRepository implements StoreDataSource{

    @Nullable
    private static StoreRepository INSTANCE = null;

    @NonNull
    private StoreDataSource remoteDataSource;

    public static StoreRepository getInstance(@NonNull StoreDataSource storeDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new StoreRepository(storeDataSource);
        }
        return INSTANCE;
    }

    private StoreRepository(@NonNull StoreDataSource remoteDataSource) {
        this.remoteDataSource = checkNotNull(remoteDataSource);
    }

    @Override
    public Flowable<List<App>> loadApplications() {
        return remoteDataSource.loadApplications();
    }

    public void destroyInstance() {
        INSTANCE = null;
    }
}
