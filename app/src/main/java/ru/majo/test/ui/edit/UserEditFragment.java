package ru.majo.test.ui.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import ru.majo.test.Application;
import ru.majo.test.R;
import ru.majo.test.api.model.User;
import ru.majo.test.base.BaseFragment;
import ru.majo.test.ui.MainActivity;

/**
 * Created by Majo on 28.10.2017.
 */

public class UserEditFragment extends BaseFragment implements UserEditView{

    private final static String EXTRA_USER = "user";

    @Bind(R.id.user_edit_email)
    EditText mUserEmailEditText;
    @Bind(R.id.user_edit_firstname)
    EditText mUserFirstNameEditText;
    @Bind(R.id.user_edit_lastname)
    EditText mUserLastNameEditText;
    @Bind(R.id.user_edit_save)
    Button mUserSaveButton;

    @Inject
    UserEditPresenter mUserEditPresenter;

    private User mUser;

    public static UserEditFragment newInstance(User user){
        UserEditFragment userEditFragment = new UserEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_USER, user);
        userEditFragment.setArguments(bundle);
        return userEditFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Application.getComponent(getActivity()).inject(this);
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    protected void initViews() {
        mUserEditPresenter.attachView(this);
        mUser = (User) getArguments().getSerializable(EXTRA_USER);
        if (mUser != null) {
            if (!getResources().getBoolean(R.bool.isLarge))
                ((MainActivity)getActivity()).setTitle(getString(R.string.edit_user));
            mUserEmailEditText.setText(mUser.getEmail());
            mUserFirstNameEditText.setText(mUser.getFirstName());
            mUserLastNameEditText.setText(mUser.getLastName());

            mUserSaveButton.setOnClickListener(v -> {
                if (isDataValid()) {
                    mUserEditPresenter.editUser(mUser.getId(), mUserFirstNameEditText.getText().toString(),
                            mUserLastNameEditText.getText().toString(),
                            mUserEmailEditText.getText().toString());
                }
            });
        } else {
            if (!getResources().getBoolean(R.bool.isLarge))
                ((MainActivity)getActivity()).setTitle(getString(R.string.create_user));

            mUserSaveButton.setOnClickListener(v -> {
                if (isDataValid()) {
                    mUserEditPresenter.createUser(mUserFirstNameEditText.getText().toString(),
                            mUserLastNameEditText.getText().toString(),
                            mUserEmailEditText.getText().toString());
                }
            });
        }
    }

    private boolean isDataValid(){
        boolean isValid = true;
        if (mUserFirstNameEditText.getText().toString().isEmpty()){
            isValid = false;
            mUserFirstNameEditText.setError(getString(R.string.firstname_error));
        }
        if (mUserLastNameEditText.getText().toString().isEmpty()){
            isValid = false;
            mUserLastNameEditText.setError(getString(R.string.lastname_error));
        }

        if (!isValidEmail(mUserEmailEditText.getText().toString())){
            isValid = false;
            mUserEmailEditText.setError(getString(R.string.email_error));
        }
        return isValid;
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onUserCreateSuccess() {
        if (getActivity()!=null)
            getFragmentManager().popBackStack();
    }

    @Override
    public void onUserCreateError(Throwable throwable) {
        if (getActivity()!=null)
            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
