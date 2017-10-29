package ru.majo.test.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import ru.majo.test.R;


/**
 * Created by Majo on 28.10.2017.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = BaseActivity.class.getName();
    private ProgressDialog dialog;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    public void setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int backCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backCount == 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        });
    }

    public void setUpToolbarWithoutFragments(Toolbar toolbarWithoutFragments){
        setSupportActionBar(toolbarWithoutFragments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarWithoutFragments.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void replaceFragment(int idContainer, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(idContainer, fragment, fragment.getClass().getName().toString()).commit();
    }

    public void replaceRootFragment(int idContainer, Fragment fragment) {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            int id = getSupportFragmentManager().getBackStackEntryAt(0).getId();
            try {
                getSupportFragmentManager().popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
                return;
            }
        }
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(idContainer, fragment, fragment.getClass().getName().toString())
                .commit();
    }

    public void addFragment(int idContainer, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(idContainer, fragment, fragment.getClass().getName().toString()).addToBackStack(fragment.getClass().getName()).commit();
    }


    public void clearBackstack(){
        int stackSize = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }


    public void showDialogProgress(){
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setContentView(R.layout.dialog_loading);
    }

    public void dismissDialogProgress(){
        if (dialog!=null)
            dialog.dismiss();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void noInternetConnection() {
        Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
    }

    public void showSoftKeyboard(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        }
    }
}
