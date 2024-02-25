package com.invoice.generator.activity;

import static com.invoice.generator.Uitilty.Util.ChangeStatusBarColor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.MobileAds;
import com.invoice.generator.R;
import com.invoice.generator.adapter.RvAdapterPrinted;
import com.invoice.generator.databaseHelper.MyDatabaseHelper;
import com.invoice.generator.databinding.ActivityPrintedReceiptsBinding;
import com.invoice.generator.model.PrintedList;

import java.util.ArrayList;

public class PrintedReceiptsActivity extends AppCompatActivity {
    private ArrayList<String> advance;
    public ArrayList<PrintedList> arrayList;
    private ArrayList<String> customerAddressList;
    private ArrayList<String> customerNameList;
    private ArrayList<String> customerPhoneList;
    private ArrayList<String> deliFeeList;
    private ArrayList<String> discountList;
    private ArrayList<String> idToSeeList;
    private ArrayList<String> itemCountList;
    private ArrayList<String> itemNameList;
    private ArrayList<String> itemPriceList;
    private ArrayList<String> markList;
    private MyDatabaseHelper myDB;
    private ArrayList<String> orderDateList;
    private ArrayList<String> orderIdList;
    private ArrayList<String> paidStatusList;
    private ArrayList<String> totalPriceList;
    public RvAdapterPrinted rvadapterPrinted;
    private ArrayList<String> tax;
    private ActivityPrintedReceiptsBinding binding;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printed_receipts);

        ChangeStatusBarColor(this);

        initView();
        storeDataInArrays();
        initClickListener();
        setData();

        MobileAds.initialize(this, initializationStatus -> {
        });
    }

    private void initView() {
        myDB = new MyDatabaseHelper(this);
        arrayList = new ArrayList<>();
        customerNameList = new ArrayList<>();
        customerAddressList = new ArrayList<>();
        itemNameList = new ArrayList<>();
        idToSeeList = new ArrayList<>();
        orderIdList = new ArrayList<>();
        orderDateList = new ArrayList<>();
        customerPhoneList = new ArrayList<>();
        deliFeeList = new ArrayList<>();
        advance = new ArrayList<>();
        tax = new ArrayList<>();
        discountList = new ArrayList<>();
        itemCountList = new ArrayList<>();
        itemPriceList = new ArrayList<>();
        markList = new ArrayList<>();
        paidStatusList = new ArrayList<>();
        totalPriceList = new ArrayList<>();

        binding.setTitle("Printed Invoices");
        binding.setOnBackClick(v -> onBackPressed());
        binding.toolbar.ivBack.setVisibility(View.VISIBLE);
        binding.toolbar.ivAdd.setVisibility(View.VISIBLE);
    }

    private void initClickListener() {
        binding.setOnAddClick(v -> {
            startActivity(new Intent(PrintedReceiptsActivity.this, MainActivity.class).putExtra("onBackType", "PrintedReceipt"));
        });
    }

    private void setData() {
        rvadapterPrinted = new RvAdapterPrinted(this, this, idToSeeList, orderIdList, orderDateList, customerNameList, customerAddressList, customerPhoneList, deliFeeList, advance, tax, discountList, itemNameList, itemCountList, itemPriceList, markList, paidStatusList, totalPriceList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvListPrinted.setLayoutManager(linearLayoutManager);
        binding.rvListPrinted.setAdapter(rvadapterPrinted);
    }

    public void storeDataInArrays() {
        Cursor readAllData = myDB.readAllData();
        if (readAllData.getCount() == 0) {
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.rvListPrinted.setVisibility(View.GONE);
            return;
        }
        while (readAllData.moveToNext()) {
            idToSeeList.add(readAllData.getString(0));
            orderIdList.add(readAllData.getString(4));
            orderDateList.add(readAllData.getString(12));
            customerPhoneList.add(readAllData.getString(11));
            customerNameList.add(readAllData.getString(9));
            customerAddressList.add(readAllData.getString(10));
            deliFeeList.add(readAllData.getString(8));
            tax.add(readAllData.getString(6));
            discountList.add(readAllData.getString(7));
            advance.add(readAllData.getString(5));
            itemNameList.add(readAllData.getString(1));
            itemCountList.add(readAllData.getString(2));
            itemPriceList.add(readAllData.getString(3));
            markList.add(readAllData.getString(13));
            paidStatusList.add(readAllData.getString(14));
            totalPriceList.add(readAllData.getString(15));
        }
        binding.tvNoData.setVisibility(View.GONE);
        binding.rvListPrinted.setVisibility(View.VISIBLE);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Intent intent2 = getIntent();
        finish();
        startActivity(intent2);
    }
}
