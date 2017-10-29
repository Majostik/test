package ru.majo.test.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import ru.majo.test.Application;
import ru.majo.test.R;
import ru.majo.test.api.model.User;
import ru.majo.test.base.BaseFragment;
import ru.majo.test.ui.MainActivity;
import ru.majo.test.ui.edit.UserEditFragment;

/**
 * Created by Majo on 28.10.2017.
 */

public class UsersFragment extends BaseFragment implements UsersView{

    @Bind(R.id.users_rv)
    RecyclerView mUserRecyclerView;
    @Bind(R.id.users_fab)
    FloatingActionButton mUserFloatingActionButton;

    @Inject
    UsersPresenter mUsersPresenter;

    private UsersAdapter mUsersAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Application.getComponent(getActivity()).inject(this);
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    protected void initViews() {
        ((MainActivity)getActivity()).setTitle(getString(R.string.users));
        mUsersAdapter = new UsersAdapter(getActivity(), new ArrayList<User>());
        mUsersAdapter.setOnUserClickListener(user -> {
            if (!getResources().getBoolean(R.bool.isLarge)) {
                replaceFragmentWithBackstack(R.id.main_container, UserEditFragment.newInstance(user));
            } else {
                replaceFragment(R.id.detail_container, UserEditFragment.newInstance(user));
            }
        });
        mUsersPresenter.attachView(this);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserRecyclerView.setAdapter(mUsersAdapter);
        mUserFloatingActionButton.setOnClickListener(v -> {
            if (!getResources().getBoolean(R.bool.isLarge)) {
                replaceFragmentWithBackstack(R.id.main_container, UserEditFragment.newInstance(null));
            } else {
                replaceFragment(R.id.detail_container, UserEditFragment.newInstance(null));
            }
        });

        mUsersPresenter.loadUsers();
    }

    @Override
    public void onGetUsersSuccess(ArrayList<User> users) {
        if (getActivity()!=null){
            mUsersAdapter.setUsers(users);
        }
    }

    @Override
    public void onGetUsersError(Throwable throwable) {
        if (getActivity()!=null){
            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
