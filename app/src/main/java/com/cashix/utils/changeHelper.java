package com.cashix.utils;

import androidx.fragment.app.FragmentManager;

public class changeHelper {
    private final FragmentManager fragmentManager;
    private final int id;

    public changeHelper(FragmentManager fragmentManager, int id) {
        this.fragmentManager = fragmentManager;
        this.id = id;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public int getId() {
        return id;
    }
}
