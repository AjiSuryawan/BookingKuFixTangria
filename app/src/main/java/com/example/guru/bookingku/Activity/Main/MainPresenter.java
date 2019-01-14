package com.example.guru.bookingku.Activity.Main;

import com.example.guru.bookingku.Fragment.Base.BaseFragment;
import com.example.guru.bookingku.Fragment.Home.HomeFragment;
import com.example.guru.bookingku.Fragment.Profile.ProfileFragment;
import com.example.guru.bookingku.Fragment.PromonInfo.PromoFragment;

public class MainPresenter {

    private MainView view;
    private final BaseFragment homeFragment = new HomeFragment();
    private final BaseFragment promoFragment = new PromoFragment();
    private final BaseFragment profileFragment = new ProfileFragment();
    private BaseFragment currentFragment = null;
    public void onAttach(MainView view) {
        this.view = view;
    }
    public void onDetach() {
        view = null;
    }

    public void showHomeFragmentForFirstTime(){
        currentFragment = homeFragment;
        view.attachHomeFragment(currentFragment, homeFragment);
    }
    public void navigateCurrentFragmentToHomeFragment(){
        view.attachHomeFragment(currentFragment, homeFragment);
        currentFragment = homeFragment;
    }
    public void navigateCurrentFragmentToProfileFragment(){
        view.attachProfileFragment(currentFragment, profileFragment);
        currentFragment = profileFragment;
    }

    public void navigatetopromo(){
        view.attachPromoFragment(currentFragment,promoFragment);;
        currentFragment = promoFragment;
    }
}
