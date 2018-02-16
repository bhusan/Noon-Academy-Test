package non.app.noon.academy.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import app.noon.academy.R;
import non.app.noon.academy.base.BaseFragment;
import non.app.noon.academy.fragment.FragmentDrawImage;
import non.app.noon.academy.fragment.FragmentNewSubject;
import non.app.noon.academy.fragment.FragmentSchoolSubject;

public class HomeActivity extends AppCompatActivity implements UserInformationCallBack {
    private String email;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (currentFragment instanceof FragmentSchoolSubject)
                        return true;
                    loadFragment(new FragmentSchoolSubject());
                    return true;
                case R.id.navigation_dashboard:
                    if (currentFragment instanceof FragmentNewSubject)
                        return true;
                    loadFragment(new FragmentNewSubject());
                    return true;
                case R.id.navigation_notifications:
                    if (currentFragment instanceof FragmentDrawImage)
                        return true;
                    loadFragment(new FragmentDrawImage());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        email = getIntent().getExtras().getString("email");
        loadFragment(new FragmentSchoolSubject());
    }

    private void loadFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public String getEmail() {
        return email;
    }
}
