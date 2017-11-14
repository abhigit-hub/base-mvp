package com.footinit.selfproject.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.footinit.selfproject.di.component.ActivityComponent;

import butterknife.Unbinder;

/**
 * Created by Abhijit on 08-11-2017.
 */

public abstract class BaseDialog extends DialogFragment implements MvpViewDialog {

    private BaseActivity activity;
    private Unbinder unBinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
            activity.onFragmentAttached();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(relativeLayout);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        }

        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        Fragment prevFragment = manager.findFragmentByTag(tag);

        if (prevFragment != null)
            ft.remove(prevFragment);
        ft.addToBackStack(null);
        show(ft, tag);
    }

    @Override
    public void dismissDialog(String tag) {
        this.dismiss();
        activity.onFragmentDetached(tag);
    }

    @Override
    public void hideLoading() {
        if (activity != null)
            activity.hideLoading();
    }

    @Override
    public void showLoading() {
        if (activity != null)
            activity.showLoading();
    }

    @Override
    public void showMessage(String message) {
        if (activity != null)
            activity.showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int resID) {
        if (activity != null)
            activity.showMessage(resID);
    }

    @Override
    public void onError(String message) {
        if (activity != null)
            activity.onError(message);
    }

    @Override
    public void onError(@StringRes int resID) {
        if (activity != null)
            activity.onError(resID);
    }

    @Override
    public void hideKeyboard() {
        if (activity != null)
            activity.hideKeyboard();
    }

    @Override
    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    protected abstract void setUp(View view);

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (unBinder != null)
            unBinder.unbind();
        super.onDestroy();
    }

    public ActivityComponent getActivityComponent() {
        return activity.getActivityComponent();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }
}
