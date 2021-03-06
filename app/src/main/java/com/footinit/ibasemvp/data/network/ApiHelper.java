package com.footinit.ibasemvp.data.network;

import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.data.db.model.User;
import com.footinit.ibasemvp.data.network.model.LoginRequest;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Abhijit on 10-11-2017.
 */

public interface ApiHelper {

    Observable<User> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Observable<User> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Observable<User> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Observable<List<Blog>> doBlogListApiCall();

    Observable<List<OpenSource>> doOpenSourceListCall();
}
