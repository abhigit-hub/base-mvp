package com.footinit.ibasemvp.ui.login;

import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

/**
 * Created by Abhijit on 07-02-2018.
 */

@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginMvpView view;

    @Mock
    DataManager dataManager;

    TestScheduler testScheduler;

    LoginPresenter<LoginMvpView> presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);

        presenter = new LoginPresenter<>(testSchedulerProvider,
                compositeDisposable,
                dataManager);
        presenter.onAttach(view);
    }


    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
    }
}