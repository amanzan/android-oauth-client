
package com.wuman.android.auth;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class DialogFragmentCompat extends FragmentCompat {

    private DialogFragment supportDialogFragment;
    private android.app.DialogFragment nativeDialogFragment;

    DialogFragmentCompat(DialogFragment supportDialogFragment) {
        super(supportDialogFragment);
        this.supportDialogFragment = supportDialogFragment;
    }

    DialogFragmentCompat(android.app.DialogFragment nativeDialogFragment) {
        super(nativeDialogFragment);
        this.nativeDialogFragment = nativeDialogFragment;
    }

    static {
        // hack to force ProGuard to consider newInstance() used, since
        // otherwise it would be stripped out
        DialogFragmentCompat.newInstance(null);
    }

    static DialogFragmentCompat newInstance(Object fragment) {
        if (fragment == null) {
            return null;
        } else if (fragment instanceof DialogFragment) {
            return new DialogFragmentCompat((DialogFragment) fragment);
        } else if (fragment instanceof android.app.DialogFragment) {
            return new DialogFragmentCompat((android.app.DialogFragment) fragment);
        } else {
            return null;
        }
    }

    final void show(FragmentManagerCompat manager, String tag) {
        if (supportDialogFragment != null) {
            supportDialogFragment.show(
                    (FragmentManager) manager.getFragmentManager(), tag);
        } else {
            nativeDialogFragment.show(
                    (android.app.FragmentManager) manager.getFragmentManager(), tag);
        }
    }

    final void show(FragmentTransactionCompat transaction, String tag) {
        if (supportDialogFragment != null) {
            supportDialogFragment.show((FragmentTransaction)
                    transaction.getFragmentTransaction(), tag);
        } else {
            nativeDialogFragment.show(
                    (android.app.FragmentTransaction) transaction.getFragmentTransaction(), tag);
        }
    }

    final void showAllowingStateLoss(FragmentTransactionCompat transaction, String tag) {
        if (supportDialogFragment != null) {
            ((FragmentTransaction) transaction.getFragmentTransaction())
                .add(supportDialogFragment, tag);
            ((FragmentTransaction) transaction.getFragmentTransaction())
                .commitAllowingStateLoss();
        } else {
            ((android.app.FragmentTransaction) transaction.getFragmentTransaction())
                .add(nativeDialogFragment, tag);
             ((android.app.FragmentTransaction) transaction.getFragmentTransaction())
                .commitAllowingStateLoss();
        }
    }

    final void dismiss() {
        if (supportDialogFragment != null) {
            supportDialogFragment.dismiss();
        } else {
            nativeDialogFragment.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    final void dismissAllowingStateLoss() {
        if (supportDialogFragment != null) {
            supportDialogFragment.dismissAllowingStateLoss();
        } else {
            nativeDialogFragment.dismissAllowingStateLoss();
        }
    }

    final Dialog getDialog() {
        if (supportDialogFragment != null) {
           return supportDialogFragment.getDialog();
        } else {
           return nativeDialogFragment.getDialog();
        }
    }

    Dialog onCreateDialog(Bundle savedInstanceState) {
        if (supportDialogFragment != null) {
            return new Dialog(supportDialogFragment.getActivity(), supportDialogFragment.getTheme());
        } else {
            return new Dialog(nativeDialogFragment.getActivity(), nativeDialogFragment.getTheme());
        }
    }

    void onCancel(DialogInterface dialog) {
    }

    public static interface BaseDialogFragmentImpl {
        void setDialogFragmentCompat(DialogFragmentCompat compat);
    }

    public static class NativeDialogFragmentImpl extends android.app.DialogFragment implements
            BaseDialogFragmentImpl {
        private DialogFragmentCompat mCompat;

        @Override
        public void setDialogFragmentCompat(DialogFragmentCompat compat) {
            mCompat = compat;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (mCompat == null) {
                return null;
            }
            return mCompat.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            if (mCompat == null) {
                return;
            }
            mCompat.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	if (getDialog() == null ) {
                setShowsDialog(false);
            }
            super.onActivityCreated(savedInstanceState);
            if (mCompat == null) {
                return;
            }
            mCompat.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroy() {
            if (mCompat != null) {
                mCompat.onDestroy();
            }
            super.onDestroy();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (mCompat == null) {
                return null;
            }
            return mCompat.onCreateDialog(savedInstanceState);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (mCompat != null) {
                mCompat.onCancel(dialog);
            }
        }

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onDestroyView() {
            if (getDialog() != null && getRetainInstance()) {
                getDialog().setDismissMessage(null);
            }
            super.onDestroyView();
        }

    }

    public static class SupportDialogFragmentImpl extends DialogFragment
            implements BaseDialogFragmentImpl {
        private DialogFragmentCompat mCompat;

        @Override
        public void setDialogFragmentCompat(DialogFragmentCompat compat) {
            mCompat = compat;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (mCompat == null) {
                return null;
            }
            return mCompat.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            if (mCompat == null) {
                return;
            }
            mCompat.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	if (getDialog() == null ) {
                setShowsDialog(false);
            }
            super.onActivityCreated(savedInstanceState);
            if (mCompat == null) {
                return;
            }
            mCompat.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroy() {
            if (mCompat != null) {
                mCompat.onDestroy();
            }
            super.onDestroy();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            if (mCompat == null) {
                return null;
            }
            return mCompat.onCreateDialog(savedInstanceState);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (mCompat != null) {
                mCompat.onCancel(dialog);
            }
        }

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onDestroyView() {
            if (getDialog() != null && getRetainInstance()) {
                getDialog().setDismissMessage(null);
            }
            super.onDestroyView();
        }

    }

}
