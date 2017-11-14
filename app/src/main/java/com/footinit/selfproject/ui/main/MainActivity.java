package com.footinit.selfproject.ui.main;

import android.content.Context;
import android.content.Intent;

import com.footinit.selfproject.ui.base.BaseActivity;

/**
 * Created by Abhijit on 10-11-2017.
 */


/*
* TODO
* 1. Login with Google(use email) Oauth
* 2. On Logout-> does clear all user data from table
* */

public class MainActivity extends BaseActivity{

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void setUp() {

    }
}
