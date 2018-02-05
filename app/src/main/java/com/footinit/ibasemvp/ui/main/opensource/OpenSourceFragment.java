package com.footinit.ibasemvp.ui.main.opensource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.di.component.ActivityComponent;
import com.footinit.ibasemvp.ui.base.BaseFragment;
import com.footinit.ibasemvp.ui.main.Interactor;
import com.footinit.ibasemvp.ui.main.opensourcedetails.OSDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class OpenSourceFragment extends BaseFragment
        implements OpenSourceMvpView {

    @Inject
    OpenSourceMvpPresenter<OpenSourceMvpView> presenter;
    @Inject
    OpenSourceAdapter openSourceAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rv_open_source)
    RecyclerView rvOpenSource;
    private Interactor.OpenSource callback;

    public static OpenSourceFragment newInstance() {
        Bundle args = new Bundle();
        OpenSourceFragment fragment = new OpenSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_open_source, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            presenter.onAttach(this);

            openSourceAdapter.setCallback((OpenSourcePresenter) presenter);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvOpenSource.setLayoutManager(linearLayoutManager);
        rvOpenSource.setItemAnimator(new DefaultItemAnimator());
        rvOpenSource.setAdapter(openSourceAdapter);

        presenter.fetchOpenSourceList();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void updateOpenSourceList(List<OpenSource> list) {
        openSourceAdapter.updateListItems(list);
    }

    @Override
    public void openOSDetailsActivity(OpenSource openSource) {
        Intent intent = OSDetailActivity.getStartIntent(getContext());
        intent.putExtra(OSDetailActivity.KEY_PARCELABLE_OPEN_SOURCE, openSource);
        startActivity(intent);
    }

    public void onParentCallToFetchList() {
        presenter.fetchOpenSourceList();
    }

    @Override
    public void onOpenSourceListReFetched() {
        if (callback != null)
            callback.onOpenSourceListReFetched();
    }

    @Override
    public void onPullToRefreshEvent(boolean isVisible) {
        if (callback != null)
            callback.updateSwipeRefreshLayoutTwo(isVisible);
    }

    public void setListScrollTop() {
        if (linearLayoutManager != null)
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Interactor.OpenSource) context;
    }

    @Override
    public void onDetach() {
        callback = null;
        openSourceAdapter.removeCallback();
        super.onDetach();
    }
}
