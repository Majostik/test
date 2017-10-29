package ru.majo.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import ru.majo.test.R;
import ru.majo.test.base.BaseActivity;
import ru.majo.test.ui.users.UsersFragment;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @Bind(R.id.main_toolbar_title)
    TextView mMainToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar(mMainToolbar);
        replaceRootFragment(R.id.main_container, new UsersFragment());

    }

    public void setTitle(String title){
        mMainToolbarTitle.setText(title);
    }
}
