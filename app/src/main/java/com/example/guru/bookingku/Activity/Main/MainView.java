package com.example.guru.bookingku.Activity.Main;

import com.example.guru.bookingku.Fragment.Base.BaseFragment;

public interface MainView {
    void attachHomeFragment(BaseFragment currentFragment, BaseFragment fragment);
    void attachProfileFragment(BaseFragment currentFragment, BaseFragment fragment);
}
