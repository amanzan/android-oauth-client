
package com.wuman.android.auth;

import android.annotation.TargetApi;
import android.os.Build;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

class FragmentTransactionCompat {

    private final android.app.FragmentTransaction nativeFragmentTransaction;
    private final FragmentTransaction supportFragmentTransaction;

    FragmentTransactionCompat(android.app.FragmentTransaction nativeFragmentTransaction) {
        super();
        this.nativeFragmentTransaction = nativeFragmentTransaction;
        this.supportFragmentTransaction = null;
    }

    FragmentTransactionCompat(FragmentTransaction supportFragmentTransaction) {
        super();
        this.supportFragmentTransaction = supportFragmentTransaction;
        this.nativeFragmentTransaction = null;
    }

    Object getFragmentTransaction() {
        return supportFragmentTransaction != null ?
                supportFragmentTransaction : nativeFragmentTransaction;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    FragmentTransactionCompat remove(FragmentCompat fragment) {
        if (supportFragmentTransaction != null) {
            supportFragmentTransaction
                    .remove((Fragment) fragment.getFragment());
        } else {
            nativeFragmentTransaction.remove((android.app.Fragment) fragment.getFragment());
        }
        return this;
    }

}
