package br.com.alexandrenavarro.store_mvvm_live.data.source;

import android.content.Context;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StoreRepositoryTest {

    private static List<App> APPS = Lists.newArrayList(new App(), new App(), new App());

    @Mock
    private StoreDataSource remoteDataSource;

    @Mock
    private Context context;

    private StoreRepository repository;

    private TestSubscriber<List<App>> mStoreAppsTestSubscriber;

    @Before
    public void setupRepository(){
        MockitoAnnotations.initMocks(this);
        repository = StoreRepository.getInstance(remoteDataSource);
        mStoreAppsTestSubscriber = new TestSubscriber<>();
    }

    @After
    public void destroyRepositoryInstance() {
        repository.destroyInstance();
    }

    @Test
    public void getStoreApps_requestsAllAppsRemoteDataSource() {
        setAppsAvailable(remoteDataSource, APPS);
        repository.loadApplications().subscribe(mStoreAppsTestSubscriber);

        verify(remoteDataSource).loadApplications();
        mStoreAppsTestSubscriber.assertValue(APPS);
    }

    @Test
    public void getStoreApps_requestsAllAppsRemoteDataSource_GetEmptyList() {
        setAppsNotAvailable(remoteDataSource);
        repository.loadApplications().subscribe(mStoreAppsTestSubscriber);

        verify(remoteDataSource).loadApplications();
        mStoreAppsTestSubscriber.assertValue(new ArrayList<App>());
    }

    private void setAppsAvailable(StoreDataSource dataSource, List<App> apps) {
        when(dataSource.loadApplications()).thenReturn(Flowable.just(apps));

    }

    private void setAppsNotAvailable(StoreDataSource dataSource) {
        when(dataSource.loadApplications()).thenReturn(Flowable.just(Collections.<App>emptyList()));    }
}