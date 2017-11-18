package com.footinit.selfproject.ui.main.opensource;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footinit.selfproject.R;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.di.component.ActivityComponent;
import com.footinit.selfproject.ui.base.BaseFragment;
import com.footinit.selfproject.ui.base.MvpView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class OpenSourceFragment extends BaseFragment
        implements OpenSourceMvpView, OpenSourceAdapter.Callback {

    @Inject
    OpenSourceMvpPresenter<OpenSourceMvpView> presenter;

    @Inject
    OpenSourceAdapter openSourceAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.rv_open_source)
    RecyclerView rvOpenSource;


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

            openSourceAdapter.setCallback(this);
        }

        return view;
    }

    @Override
    public void onOpenSourceEmptyRetryClicked() {

    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvOpenSource.setLayoutManager(linearLayoutManager);
        rvOpenSource.setItemAnimator(new DefaultItemAnimator());
        rvOpenSource.setAdapter(openSourceAdapter);

        presenter.onViewPrepared();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void updateOpenSourceList(List<OpenSource> list) {
        openSourceAdapter.addItems(list);
    }
}
