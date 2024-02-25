package com.invoice.generator.activity;

import static com.invoice.generator.Uitilty.Util.ChangeStatusBarColor;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.invoice.generator.R;
import com.invoice.generator.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {
    private ActivityHelpBinding binding;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help);

        ChangeStatusBarColor(this);
        initView();
        initClickListener();
        setData();
    }

    private void initView() {
        binding.setTitle("How to use");
        binding.toolbar.ivBack.setVisibility(View.VISIBLE);
        binding.setOnBackClick(v -> onBackPressed());
    }

    private void initClickListener() {
    }

    private void setData() {
        binding.tvHelpPrint.setText("Instant Invoice Generator is the perfect invoice generator for everyone. Easily create, send, share and print invoices on your mobile. Manage all your invoices on the go so you can receive payments faster. \n\nInstant Invoice Generator has a wide selection of professional templates that can be used for free for any need. \n\nAll invoice fields such as invoice labels, title, date, due date, items, and others can be customized as needed. The invoice generator can also be equipped with a company stamp. \n\nPlease noted that you first need to give STORAGE PERMISSIONS so that the app can save invoices in your device storage. \n\nThe printed invoices are stored in the app so that you can review them later. \n\nYou can edit or delete the unwanted invoices from the stored list.");
    }
}
