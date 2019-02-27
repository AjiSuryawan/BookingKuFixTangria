package com.example.guru.bookingku.Activity.Main;

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
import android.widget.TextView;

import com.example.guru.bookingku.Activity.Notification.NotificationActivity;
import com.example.guru.bookingku.Fragment.Base.BaseFragment;
import com.example.guru.bookingku.R;
import com.google.firebase.messaging.FirebaseMessagingService;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    final MainPresenter presenter = new MainPresenter();
    @BindView(R.id.bottomnav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
        }
        return true;
    }

    //=========== implement main view ===========//
    @Override
    public void attachHomeFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.container, fragment)
                    .show(fragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    public void attachProfileFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.container, fragment)
                    .show(fragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    public void attachPromoFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.container, fragment)
                    .show(fragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_notification) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
