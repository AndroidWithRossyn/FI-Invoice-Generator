package com.invoice.generator.activity;

import static com.invoice.generator.Uitilty.Util.ChangeStatusBarColor;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.invoice.generator.R;
import com.invoice.generator.adapter.RvAdapterMyCustomers;
import com.invoice.generator.ads.MyInterstitialAds;
import com.invoice.generator.databaseHelper.MyDatabaseHelperCustomers;
import com.invoice.generator.databinding.ActivityMyFavCustomersBinding;
import com.invoice.generator.model.MyCustomersList;

import java.util.ArrayList;

public class MyFavCustomersActivity extends AppCompatActivity {
    private ArrayList<String> arrayListId;
    public ArrayList<MyCustomersList> arrayList;
    private ArrayList<String> arrayListMyCustomerAddress;
    private ArrayList<String> arrayListMyCustomerName;
    private ArrayList<String> arrayListMyCustomerPhone;
    private MyDatabaseHelperCustomers myDBCustomer;
    public RvAdapterMyCustomers rvadapterMyCustomers;
    private ActivityMyFavCustomersBinding binding;
    private MyInterstitialAds myInterstitialAds;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_fav_customers);

        ChangeStatusBarColor(this);

        initView();
        storeDataInArrays();
        initClickListener();
        setData();

        MobileAds.initialize(this, initializationStatus -> {
        });
    }

    private void initView() {
        myDBCustomer = new MyDatabaseHelperCustomers(this);
        myDBCustomer = new MyDatabaseHelperCustomers(this);
        arrayList = new ArrayList<>();
        arrayListId = new ArrayList<>();
        arrayListMyCustomerName = new ArrayList<>();
        arrayListMyCustomerAddress = new ArrayList<>();
        arrayListMyCustomerPhone = new ArrayList<>();
        myInterstitialAds = new MyInterstitialAds(MyFavCustomersActivity.this);

        binding.setTitle("Customers");
        binding.setOnBackClick(v -> {
            onBackPressed();
            finish();
        });
        binding.toolbar.ivAdd.setVisibility(View.VISIBLE);
        binding.toolbar.ivBack.setVisibility(View.VISIBLE);
    }

    private void initClickListener() {

        binding.setOnAddClick(v -> {
            final Dialog dialog = new Dialog(MyFavCustomersActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setContentView(R.layout.dialogaddnewcustomer);
            final EditText editText = dialog.findViewById(R.id.et_dialog_customer_name);
            final EditText editText2 = dialog.findViewById(R.id.et_dialog_customer_address);
            final EditText editText3 = dialog.findViewById(R.id.et_dialog_customer_phone);
            dialog.show();
            dialog.setCancelable(false);
            dialog.findViewById(R.id.bt_delete_customer).setVisibility(View.GONE);
            dialog.findViewById(R.id.btn_dialog_add_customer_cancel).setOnClickListener(v1 -> {
                if (myInterstitialAds.getInstance() != null) {
                    myInterstitialAds.getInstance().show(MyFavCustomersActivity.this);
                    myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            myInterstitialAds = new MyInterstitialAds(MyFavCustomersActivity.this);
                            dialog.dismiss();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            myInterstitialAds = new MyInterstitialAds(MyFavCustomersActivity.this);
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.btn_dialog_add_customer).setOnClickListener(view -> {
                if (myInterstitialAds.getInstance() != null) {
                    myInterstitialAds.getInstance().show(MyFavCustomersActivity.this);
                    myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            myInterstitialAds = new MyInterstitialAds(MyFavCustomersActivity.this);
                            if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                                Toast.makeText(MyFavCustomersActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            myDBCustomer.addMyItem(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                            dialog.dismiss();
                            recreate();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            myInterstitialAds = new MyInterstitialAds(MyFavCustomersActivity.this);
                            if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                                Toast.makeText(MyFavCustomersActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            myDBCustomer.addMyItem(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                            dialog.dismiss();
                            recreate();
                        }
                    });
                } else {
                    if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                        Toast.makeText(MyFavCustomersActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    myDBCustomer.addMyItem(editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                    dialog.dismiss();
                    recreate();
                }
            });
        });
    }

    private void setData() {
        rvadapterMyCustomers = new RvAdapterMyCustomers(this, this, arrayListId, arrayListMyCustomerName, arrayListMyCustomerAddress, arrayListMyCustomerPhone);
        binding.rvListCustomers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvListCustomers.setAdapter(rvadapterMyCustomers);
    }

    public void storeDataInArrays() {
        Cursor readAllData = myDBCustomer.readAllData();
        if (readAllData.getCount() == 0) {
            binding.tvNoDataCustomer.setVisibility(View.VISIBLE);
            binding.rvListCustomers.setVisibility(View.GONE);
            return;
        }
        while (readAllData.moveToNext()) {
            arrayListId.add(readAllData.getString(0));
            arrayListMyCustomerName.add(readAllData.getString(1));
            arrayListMyCustomerAddress.add(readAllData.getString(2));
            arrayListMyCustomerPhone.add(readAllData.getString(3));
        }
        binding.tvNoDataCustomer.setVisibility(View.GONE);
        binding.rvListCustomers.setVisibility(View.VISIBLE);
    }
}
