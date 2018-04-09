package br.com.alexandrenavarro.store_mvvm_live.data.source;

import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import io.reactivex.Flowable;

public interface StoreDataSource {

    Flowable<List<App>> loadApplications();
}
