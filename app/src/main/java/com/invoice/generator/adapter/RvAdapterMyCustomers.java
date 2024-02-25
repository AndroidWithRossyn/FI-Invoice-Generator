package com.invoice.generator.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.invoice.generator.R;
import com.invoice.generator.ads.MyInterstitialAds;
import com.invoice.generator.ads.MyNativeAds;
import com.invoice.generator.databaseHelper.MyDatabaseHelperCustomers;
import com.invoice.generator.databinding.CardlayoutmycustomersBinding;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RvAdapterMyCustomers extends RecyclerView.Adapter<RvAdapterMyCustomers.MyViewHolder> {
    private final Activity activity;
    private final ArrayList arrayListMyCustomerAddress;
    private final ArrayList arrayListMyCustomerId;
    private final ArrayList arrayListMyCustomerName;
    private final ArrayList arrayListMyCustomerPhone;
    private final Context context;
    private MyDatabaseHelperCustomers myDB;
    public String myCustomerAddress;
    private String myCustomerId;
    public String myCustomerName;
    public String myCustomerPhone;
    public int position;
    private CardlayoutmycustomersBinding binding;
    private InterstitialAd mInterstitialAd;
    private MyInterstitialAds myInterstitialAds;


    public RvAdapterMyCustomers(Activity activity2, Context context2, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.activity = activity2;
        this.context = context2;
        this.arrayListMyCustomerId = arrayList;
        this.arrayListMyCustomerName = arrayList2;
        this.arrayListMyCustomerAddress = arrayList3;
        this.arrayListMyCustomerPhone = arrayList4;
        myInterstitialAds = new MyInterstitialAds(context2);

    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayoutmycustomers, viewGroup, false);
        binding = DataBindingUtil.bind(view);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        if ((i + 1) % 3 == 0 && i != 0) {
            binding.myFavTemplate.setVisibility(View.VISIBLE);
            new MyNativeAds().initNativeAds(context, binding.myFavTemplate);
        } else {
            binding.myFavTemplate.setVisibility(View.GONE);
        }
        if (getItemViewType(i) != 1) {
            position = i;
            myDB = new MyDatabaseHelperCustomers(context);
            myCustomerId = String.valueOf(arrayListMyCustomerId.get(i));
            myCustomerName = String.valueOf(arrayListMyCustomerName.get(i));
            myCustomerAddress = String.valueOf(arrayListMyCustomerAddress.get(i));
            myCustomerPhone = String.valueOf(arrayListMyCustomerPhone.get(i));
            TextView textView = binding.tvFavCustomerId;
            textView.setText((i + 1) + ". ");
            binding.tvFavCustomerName.setText(myCustomerName);
            binding.tvFavCustomerAddress.setText(myCustomerAddress);
            binding.tvFavCustomerPhone.setText(myCustomerPhone);
            binding.btnDelCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myInterstitialAds.getInstance() != null) {
                        myInterstitialAds.getInstance().show(activity);
                        myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                myInterstitialAds = new MyInterstitialAds(context);
                                RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                                RvAdapterMyCustomers.this.activity.recreate();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                myInterstitialAds = new MyInterstitialAds(context);
                                RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                                RvAdapterMyCustomers.this.activity.recreate();
                            }
                        });
                    } else {
                        RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                        RvAdapterMyCustomers.this.activity.recreate();
                    }
                }
            });
            binding.btnEditCustomer.setOnClickListener(view -> {
                final Dialog dialog = new Dialog(RvAdapterMyCustomers.this.context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.dialogaddnewcustomer);
                final EditText editText = dialog.findViewById(R.id.et_dialog_customer_name);
                final EditText editText2 = dialog.findViewById(R.id.et_dialog_customer_address);
                final EditText editText3 = dialog.findViewById(R.id.et_dialog_customer_phone);
                ((TextView) dialog.findViewById(R.id.tv_dialog_add_customer)).setText("Edit Customer");
                editText.setText(String.valueOf(RvAdapterMyCustomers.this.arrayListMyCustomerName.get(i)));
                editText2.setText(String.valueOf(RvAdapterMyCustomers.this.arrayListMyCustomerAddress.get(i)));
                editText3.setText(String.valueOf(RvAdapterMyCustomers.this.arrayListMyCustomerPhone.get(i)));
                RvAdapterMyCustomers rvadapterMyCustomers = RvAdapterMyCustomers.this;
                rvadapterMyCustomers.myCustomerId = String.valueOf(rvadapterMyCustomers.arrayListMyCustomerId.get(i));
                dialog.setCancelable(false);
                dialog.show();
                dialog.findViewById(R.id.bt_delete_customer).setOnClickListener(view1 -> {
                    if (myInterstitialAds.getInstance() != null) {
                        myInterstitialAds.getInstance().show(activity);
                        myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                myInterstitialAds = new MyInterstitialAds(context);
                                RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                                dialog.dismiss();
                                RvAdapterMyCustomers.this.activity.recreate();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                myInterstitialAds = new MyInterstitialAds(context);
                                RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                                dialog.dismiss();
                                RvAdapterMyCustomers.this.activity.recreate();
                            }
                        });
                    } else {
                        RvAdapterMyCustomers.this.myDB.deleteOneRow(RvAdapterMyCustomers.this.myCustomerId);
                        dialog.dismiss();
                        RvAdapterMyCustomers.this.activity.recreate();
                    }
                });
                dialog.findViewById(R.id.btn_dialog_add_customer_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (myInterstitialAds.getInstance() != null) {
                            myInterstitialAds.getInstance().show(activity);
                            myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    myInterstitialAds = new MyInterstitialAds(context);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    myInterstitialAds = new MyInterstitialAds(context);
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.findViewById(R.id.btn_dialog_add_customer).setOnClickListener(view13 -> {
                    if (myInterstitialAds.getInstance() != null) {
                        myInterstitialAds.getInstance().show(activity);
                        myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                myInterstitialAds = new MyInterstitialAds(context);
                                if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                                    Toast.makeText(RvAdapterMyCustomers.this.context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                RvAdapterMyCustomers.this.myDB.updateData(RvAdapterMyCustomers.this.myCustomerId, editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                                dialog.dismiss();
                                RvAdapterMyCustomers.this.activity.recreate();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                myInterstitialAds = new MyInterstitialAds(context);
                                if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                                    Toast.makeText(RvAdapterMyCustomers.this.context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                RvAdapterMyCustomers.this.myDB.updateData(RvAdapterMyCustomers.this.myCustomerId, editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                                dialog.dismiss();
                                RvAdapterMyCustomers.this.activity.recreate();
                            }
                        });
                    } else {
                        if (editText.getText().toString().length() <= 0 || editText2.getText().toString().length() <= 0 || editText3.getText().toString().length() <= 0) {
                            Toast.makeText(RvAdapterMyCustomers.this.context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        RvAdapterMyCustomers.this.myDB.updateData(RvAdapterMyCustomers.this.myCustomerId, editText.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                        dialog.dismiss();
                        RvAdapterMyCustomers.this.activity.recreate();
                    }
                });
            });
        }
    }

    public int getItemCount() {
        return this.arrayListMyCustomerName.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
