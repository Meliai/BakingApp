package com.rudainc.bakingapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.utils.BakingKeys;


public abstract class BaseActivity extends AppCompatActivity implements BakingKeys {

    private View mCustomSnackBarView;


    private Snackbar initSnackBar(String message) {
        View snackBarParent = null;
        if (mCustomSnackBarView != null) {
            snackBarParent = mCustomSnackBarView;
        } else if (findViewById(android.R.id.content) != null) {
            snackBarParent = findViewById(android.R.id.content);
        }
        if (snackBarParent != null) {
            Snackbar snackbar = Snackbar.make(snackBarParent, message, Snackbar.LENGTH_SHORT).setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            return snackbar;
        }
        return null;
    }

    public void showSnackBar(String message, boolean isError) {
        Snackbar snackbar = initSnackBar(message);
        snackbar.getView().setBackgroundColor(isError ? ContextCompat.getColor(this, R.color.colorRed) : ContextCompat.getColor(this, R.color.colorGreen));

        if (snackbar != null)
            snackbar.show();

    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
