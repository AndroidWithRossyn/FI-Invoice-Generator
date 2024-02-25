package com.invoice.generator.activity;

import static com.invoice.generator.Uitilty.Util.ChangeStatusBarColor;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.invoice.generator.BuildConfig;
import com.invoice.generator.R;
import com.invoice.generator.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        ChangeStatusBarColor(this);

        binding.tvVersionName.setText("version : " + BuildConfig.VERSION_NAME);

        binding.tvDevName.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
