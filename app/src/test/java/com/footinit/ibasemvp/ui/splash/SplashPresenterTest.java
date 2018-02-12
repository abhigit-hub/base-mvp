package com.footinit.ibasemvp.ui.splash;

import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Abhijit on 06-02-2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Mock
    SplashMvpView splashMvpView;

    @Mock
    DataManager dataManager;

    @Mock
    SchedulerProvider schedulerProvider;

    private SplashPresenter<SplashMvpView> splashPresenter;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        splashPresenter = new SplashPresenter<>(
                schedulerProvider,
                compositeDisposable,
                dataManager);
    }

    @Test
    public void decideNextActivity_UserNotLoggedIn_OpenLoginActivity() throws InterruptedException {
        when(dataManager.getCurrentUserLoggedInMode()).
                thenReturn(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());


        splashPresenter.onAttach(splashMvpView);
        Thread.sleep(2000);
        verify(splashMvpView).openLoginActivity();
    }

    @Test
    public void decideNextActivity_UserLoggedIn_OpenMainActivity() throws InterruptedException {
        when(dataManager.getCurrentUserLoggedInMode()).
                thenReturn(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_SERVER.getType());

        splashPresenter.onAttach(splashMvpView);
        Thread.sleep(2500);
        verify(splashMvpView).openMainActivity();
    }

}