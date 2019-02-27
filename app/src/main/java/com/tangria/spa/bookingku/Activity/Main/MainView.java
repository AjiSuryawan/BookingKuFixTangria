package com.tangria.spa.bookingku.Activity.Main;

import com.tangria.spa.bookingku.Fragment.Base.BaseFragment;

public interface MainView {
    void attachHomeFragment(BaseFragment currentFragment, BaseFragment fragment);
    void attachProfileFragment(BaseFragment currentFragment, BaseFragment fragment);
    void attachPromoFragment(BaseFragment currentFragment, BaseFragment fragment);
}
