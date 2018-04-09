package br.com.alexandrenavarro.store_mvvm_live;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Observable;
import java.util.Observer;

import br.com.alexandrenavarro.store_mvvm_live.adapter.StoreAdapter;
import br.com.alexandrenavarro.store_mvvm_live.view_model.StoreViewModel;

public class MainActivity extends AppCompatActivity implements Observer {

    private StoreViewModel storeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new StoreAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeViewModel = new StoreViewModel(Injection.provideStoreRepository(getApplicationContext()),
                Injection.provideSchedulerProvider());
        setUpObserver(storeViewModel);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof  StoreViewModel) {
            StoreAdapter adapter = (StoreAdapter) ((RecyclerView) findViewById(R.id.recyclerView)).getAdapter();
            adapter.updateListApp(storeViewModel.getAppList());

        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        storeViewModel.reset();
    }
}