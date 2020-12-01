package com.tangria.spa.bookingku.Activity.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tangria.spa.bookingku.Activity.Notification.NotificationActivity;
import com.tangria.spa.bookingku.Fragment.Base.BaseFragment;
import com.tangria.spa.bookingku.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    final MainPresenter presenter = new MainPresenter();
    @BindView(R.id.bottomnav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbarNotif)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ivToNotification)
    ImageView ivToNotification;

    //
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Keluar aplikasi")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tvTitle.setText("Home");
        tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        presenter.onAttach(this);
        presenter.showHomeFragmentForFirstTime();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Log.e(TAG, "onCreate: " + getSharedPreferences("firebase_token", MODE_PRIVATE).getString("firebase_token", ""));

        ivToNotification = findViewById(R.id.ivToNotification);
        ivToNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
    }

    //=========== lifecycle ===========//
    @Override
    protected void onResume() {
        super.onResume();
        presenter.onAttach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onDetach();
    }

    //=========== on clicked item bottom nav ===========//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.bottomnav_home:
                presenter.navigateCurrentFragmentToHomeFragment();
                tvTitle.setText("HOME");

                break;
            case R.id.bottomnav_profile:
                presenter.navigateCurrentFragmentToProfileFragment();
                tvTitle.setText("PROFILE");
                break;

            case R.id.bottomnav_promo:
                presenter.navigatetopromo();
                tvTitle.setText("INFO");
                break;

            case R.id.bottomnav_history:
                presenter.navigateToHistory();
                tvTitle.setText("HISTORY");
                break;

        }
        return true;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_toolbar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_notification) {
//            Intent intent = new Intent(this, NotificationActivity.class);
//            startActivity(intent);
//        }
//        return true;
//    }

    @Override
    public void goToFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
