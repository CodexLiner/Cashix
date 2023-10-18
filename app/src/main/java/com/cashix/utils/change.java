package com.cashix.utils;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cashix.R;

public class change {
    private static FragmentManager manager;
    private static int id;

    public change(changeHelper changeHelper) {
        manager = changeHelper.getFragmentManager();
        id = changeHelper.getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void go(@NonNull Class<? extends Fragment> fragment) {
        manager.beginTransaction()
                .addToBackStack(fragment.toString())
                .setReorderingAllowed(true)
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(id, fragment, null)
                .commit();
    }

    public void goWithParams(Object fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(id, (Fragment) fragment, null);
        ft.addToBackStack(fragment.toString());
        ft.commit();
    }
}
