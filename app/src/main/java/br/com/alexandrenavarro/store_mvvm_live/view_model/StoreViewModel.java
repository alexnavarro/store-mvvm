package br.com.alexandrenavarro.store_mvvm_live.view_model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import br.com.alexandrenavarro.store_mvvm_live.data.source.StoreRepository;
import br.com.alexandrenavarro.store_mvvm_live.util.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StoreViewModel extends Observable {

    private List<App> appList;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private StoreRepository repository;
    private BaseSchedulerProvider mSchedulerProvider;

    public StoreViewModel(StoreRepository repository, BaseSchedulerProvider baseSchedulerProvider){
        this.repository = repository;
        this.mSchedulerProvider = baseSchedulerProvider;
        this.appList = new ArrayList<>();
        loadApps();
    }

    private void loadApps(){
        Disposable disposable =  repository.loadApplications()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<App>>() {
                    @Override
                    public void accept(List<App> apps) throws Exception {
                        appList.clear();
                        appList.addAll(apps);
                        setChanged();
                        notifyObservers();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error", throwable.getMessage());
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
//        context = null;
    }

    public List<App> getAppList() {
        return appList;
    }
}