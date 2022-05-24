package com.cashix.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cashix.databinding.ActivityMoreBinding;

public class MoreActivity extends AppCompatActivity {
    ActivityMoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , UserProfile.class));
            }
        });
        binding.bankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , BankDetails.class));
            }
        });
        binding.mTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Transactions.class));
            }
        });
        binding.mBillers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.helpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setShowTitle(true);
                builder.setUrlBarHidingEnabled(true);
                CustomTabsIntent intent = builder.build();
                intent.launchUrl(MoreActivity.this, Uri.parse("https://pages.flycricket.io/cashix-web-portal/terms.html"));
            }
        });
        binding.termCondtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setShowTitle(true);
                builder.setUrlBarHidingEnabled(true);
                CustomTabsIntent intent = builder.build();
                intent.launchUrl(MoreActivity.this, Uri.parse("https://pages.flycricket.io/cashix-web-portal/terms.html"));
            }
        });
        binding.privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setShowTitle(true);
                builder.setUrlBarHidingEnabled(true);
                CustomTabsIntent intent = builder.build();
                intent.launchUrl(MoreActivity.this, Uri.parse("https://pages.flycricket.io/cashix-web-portal/terms.html"));
            }
        });
        binding.rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri marketUri = Uri.parse("market://details?id=" + getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }catch(ActivityNotFoundException e) {
                    Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                }
            }
        });
        binding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}