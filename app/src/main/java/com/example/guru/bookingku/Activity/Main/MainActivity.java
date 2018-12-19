package com.example.guru.bookingku.Activity.Main;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.example.guru.bookingku.Fragment.Base.BaseFragment;
import com.example.guru.bookingku.R;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.bottomnav) BottomNavigationView bottomNavigationView;
    final MainPresenter presenter = new MainPresenter();

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Keluar aplikasi")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        presenter.showHomeFragmentForFirstTime();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
        switch (menuItem.getItemId()){
            case R.id.bottomnav_home:
                presenter.navigateCurrentFragmentToHomeFragment();
                break;
            case R.id.bottomnav_profile:
                presenter.navigateCurrentFragmentToProfileFragment();
                break;
        }
        return true;
    }
    //=========== implement main view ===========//
    @Override
    public void attachHomeFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!fragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.container, fragment)
                    .show(fragment)
                    .commit();
        }
        else {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
    }
    @Override
    public void attachProfileFragment(BaseFragment currentFragment, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!fragment.isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.container, fragment)
                    .show(fragment)
                    .commit();
        }
        else {
            transaction
                    .hide(currentFragment)
                    .show(fragment)
                    .commit();
        }
    }
}
