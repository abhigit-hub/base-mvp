package com.footinit.selfproject.data;

import android.content.Context;

import com.footinit.selfproject.data.db.DbHelper;
import com.footinit.selfproject.data.db.model.User;
import com.footinit.selfproject.data.network.ApiHelper;
import com.footinit.selfproject.data.network.model.LoginRequest;
import com.footinit.selfproject.data.pref.PreferenceHelper;
import com.footinit.selfproject.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Abhijit on 08-11-2017.
 */

@Singleton
public class AppDataManager implements DataManager {


    private final Context context;
    private final DbHelper dbHelper;
    private final PreferenceHelper preferenceHelper;
    private final ApiHelper apiHelper;

    @Inject
    AppDataManager(@ApplicationContext Context context,
                   DbHelper dbHelper,
                   PreferenceHelper preferenceHelper,
                   ApiHelper apiHelper) {

        this.context = context;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
        this.apiHelper = apiHelper;
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return dbHelper.insertUser(user);
    }

    @Override
    public Observable<User> getCurrentUser() {
        return dbHelper.getCurrentUser();
    }

    @Override
    public Observable<User> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return apiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Observable<User> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return apiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Observable<User> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return apiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public void setCurrentUserId(Long id) {
        preferenceHelper.setCurrentUserId(id);
    }

    @Override
    public Long getCurrentUserId() {
        return preferenceHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserName(String userName) {
        preferenceHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserName() {
        return preferenceHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        preferenceHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserEmail() {
        return preferenceHelper.getCurrentUserEmail();
    }

    @Override
    public void updateUserInfoInPrefs(Long userId, String userName, String userEmail, LoggedInMode mode) {
        preferenceHelper.updateUserInfoInPrefs(userId, userName, userEmail, mode);
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        preferenceHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return preferenceHelper.getCurrentUserLoggedInMode();
    }
}
