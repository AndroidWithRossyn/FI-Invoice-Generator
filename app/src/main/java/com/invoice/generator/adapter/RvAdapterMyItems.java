package com.invoice.generator.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.invoice.generator.R;
import com.invoice.generator.Uitilty.OnItemClick;
import com.invoice.generator.ads.MyNativeAds;
import com.invoice.generator.databaseHelper.MyDatabaseHelperItems;
import com.invoice.generator.databinding.CardlayoutmyitemsBinding;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RvAdapterMyItems extends RecyclerView.Adapter<RvAdapterMyItems.MyViewHolder> {
    Activity activity;
    private final ArrayList arrayListId;
    private final ArrayList arrayListMyItemInStock;
    private final ArrayList arrayListMyItemName;
    private final ArrayList arrayListMyItemPic;
    private final ArrayList arrayListMyItemPrice;
    private final Context context;
    String currencyString;
    String myItemId;
    String myItemInStock;
    String myItemName;
    String myItemPrice;
    SharedPreferences share2;
    private CardlayoutmyitemsBinding binding;
    MyDatabaseHelperItems myDBItem;
    private final OnItemClick mCallback;
    private InterstitialAd minterstitialAd;

    public RvAdapterMyItems(Activity activity2, Context context2, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, OnItemClick listener) {
        this.activity = activity2;
        this.context = context2;
        this.arrayListId = arrayList;
        this.arrayListMyItemPic = arrayList2;
        this.arrayListMyItemName = arrayList3;
        this.arrayListMyItemPrice = arrayList4;
        this.arrayListMyItemInStock = arrayList5;
        this.mCallback = listener;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayoutmyitems, viewGroup, false);
        binding = DataBindingUtil.bind(view);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        if ((i + 1) % 3 == 0 && i != 0) {
            binding.myItemTemplate.setVisibility(View.VISIBLE);
            new MyNativeAds().initNativeAds(context, binding.myItemTemplate);
        } else {
            binding.myItemTemplate.setVisibility(View.GONE);
        }
        if (getItemViewType(i) != 1) {
            myDBItem = new MyDatabaseHelperItems(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences("shopinfo", 0);
            share2 = sharedPreferences;
            if (sharedPreferences.contains(FirebaseAnalytics.Param.CURRENCY)) {
                String string = share2.getString(FirebaseAnalytics.Param.CURRENCY, "");
                currencyString = string;
                if (string.equals("No")) {
                    currencyString = "";
                }
            } else {
                currencyString = "";
            }
            myItemId = String.valueOf(arrayListId.get(i));
            myItemName = String.valueOf(arrayListMyItemName.get(i));
            myItemPrice = arrayListMyItemPrice.get(i) + " " + currencyString;
            myItemInStock = "Instock: " + arrayListMyItemInStock.get(i) + " pcs";
            TextView textView = binding.tvCustomerNamePrinted;
            textView.setText(myItemName);
            binding.tvItemPrinted.setText(myItemPrice + ")");
            binding.btnGotoEditInvoice.setOnClickListener(view -> {
                Log.d("ghdjsdd", "onBindViewHolder: " + i);
                mCallback.onClick(i);
            });
        }
    }

    public int getItemCount() {
        return arrayListId.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
