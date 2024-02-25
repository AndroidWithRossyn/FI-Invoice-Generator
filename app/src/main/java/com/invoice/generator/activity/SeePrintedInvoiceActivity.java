package com.invoice.generator.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.invoice.generator.R;
import com.invoice.generator.adapter.RvAdapter;
import com.invoice.generator.ads.MyInterstitialAds;
import com.invoice.generator.databaseHelper.MyDatabaseHelper;
import com.invoice.generator.databaseHelper.MyDatabaseHelperItems;
import com.invoice.generator.databinding.ActivitySeePrintedInvoiceBinding;
import com.invoice.generator.model.ItemList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SeePrintedInvoiceActivity extends AppCompatActivity {
    private String advance;
    private double advanceLong;
    private ArrayList<ItemList> arrayList;
    private double calculateDiscount;
    private double calculateTax;
    private String checkString;
    private String countListString;
    private String customerAddress;
    private String customerName;
    private String customerPhone;
    private String deliFee;
    private double deliFeeLong;
    private String discount;
    private double discountLong;
    private int imageQuality = 100;
    private String itemCount;
    public ArrayList<String> itemCountList;
    private String itemListString;
    private String itemName;
    public ArrayList<String> itemNameList;
    private String itemPrice;
    public ArrayList<String> itemPriceList;
    private int listArray;
    private MyDatabaseHelper myDB = new MyDatabaseHelper(this);
    private String myInvoiceId;
    private String orderDate;
    private String orderId;
    private String paidStatus;
    private String priceListString;
    private SharedPreferences share;
    private String tax;
    private double taxLong;
    private ActivitySeePrintedInvoiceBinding binding;
    private MyInterstitialAds myInterstitialAds;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_printed_invoice);

        initView();
        setData();
        initClickListener();

    }

    private void initView() {
        myInterstitialAds = new MyInterstitialAds(SeePrintedInvoiceActivity.this);
    }

    private void setData() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        float f = (float) point.x;
        float scalingFactor;
        if (f > 1000.0f) {
            scalingFactor = 0.87f;
        } else if (f < 1000.0f && f > 700.0f) {
            scalingFactor = 0.8f;
        } else if (f < 700.0f && f > 400.0f) {
            scalingFactor = 0.67f;
        } else if (f >= 400.0f || f <= 300.0f) {
            scalingFactor = 0.57f;
        } else {
            scalingFactor = 0.67f;
        }
        binding.constraintLayoutPrint2.setScaleX(scalingFactor);
        binding.constraintLayoutPrint2.setScaleY(scalingFactor);
        arrayList = new ArrayList<>();
        binding.rvList2.setMinimumHeight(300);
        RvAdapter rvadapter = new RvAdapter(this, arrayList);
        myDB = new MyDatabaseHelper(this);
        setShopInfo();
        setInvoiceBackground2();
        Locale.setDefault(new Locale("en", "US"));
        itemNameList = new ArrayList<>();
        itemCountList = new ArrayList<>();
        itemPriceList = new ArrayList<>();
        getAndSetIntentData();
        updateTotalPrice();
        binding.rvList2.setAdapter(rvadapter);
        SharedPreferences sharedPreferences = getSharedPreferences("colorinfo", 0);
        share = sharedPreferences;
        if (sharedPreferences.contains("colorChange")) {
            changeInvoiceColor();
        }
        if (share.contains("imagequality")) {
            imageQuality = share.getInt("imagequality", 100);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initClickListener() {
        binding.btnCall2.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2.getText().toString().trim()));
            SeePrintedInvoiceActivity.this.startActivity(intent);
        });
        binding.btnBackSeePrintedInvoice.setOnClickListener(view -> SeePrintedInvoiceActivity.this.onBackPressed());
        binding.btnEditInvoice.setOnClickListener(view -> {
            @SuppressLint("ResourceType") final Dialog dialog = new Dialog(SeePrintedInvoiceActivity.this, 16973841);
            dialog.setContentView(R.layout.dialogeditinvoice);
            dialog.getWindow().getAttributes().dimAmount = 0.7f;
            dialog.getWindow().addFlags(2);
            final EditText editText = dialog.findViewById(R.id.et_update_order_id);
            final EditText editText2 = dialog.findViewById(R.id.et_update_order_date);
            final EditText editText3 = dialog.findViewById(R.id.et_update_customer_name);
            final EditText editText4 = dialog.findViewById(R.id.et_update_customer_address);
            final EditText editText5 = dialog.findViewById(R.id.et_update_customer_phone);
            final EditText editText6 = dialog.findViewById(R.id.et_update_deli_fee);
            final EditText editText7 = dialog.findViewById(R.id.et_update_advance);
            final EditText editText8 = dialog.findViewById(R.id.et_update_tax);
            final EditText editText9 = dialog.findViewById(R.id.et_update_discount);
            Button updateBtn = dialog.findViewById(R.id.bt_update_add_item);
            final CheckBox checkBox = dialog.findViewById(R.id.cb_update_paid_stamp);
            Button button = dialog.findViewById(R.id.dialog_update_invoice_ok);
            Button button2 = dialog.findViewById(R.id.dialog_update_invoice_cancel);
            dialog.setCancelable(false);
            if (SeePrintedInvoiceActivity.this.binding.tvPrintOrderId2.getText().toString().length() > 0) {
                editText.setText(SeePrintedInvoiceActivity.this.binding.tvPrintOrderId2.getText().toString());
            }
            if (SeePrintedInvoiceActivity.this.binding.tvPrintOrderDate2.getText().toString().length() > 0) {
                editText2.setText(SeePrintedInvoiceActivity.this.binding.tvPrintOrderDate2.getText().toString());
            }
            if (SeePrintedInvoiceActivity.this.binding.tvPrintCustomerName2.getText().toString().length() > 0) {
                editText3.setText(SeePrintedInvoiceActivity.this.binding.tvPrintCustomerName2.getText().toString());
            }
            if (SeePrintedInvoiceActivity.this.binding.tvPrintCustomerAddress2.getText().toString().length() > 0) {
                editText4.setText(SeePrintedInvoiceActivity.this.binding.tvPrintCustomerAddress2.getText().toString());
            }
            if (SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2.getText().toString().length() > 0) {
                editText5.setText(SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2.getText().toString());
            }
            if (SeePrintedInvoiceActivity.this.deliFeeLong != 0.0d) {
                editText6.setText(SeePrintedInvoiceActivity.this.deliFee);
            }
            if (SeePrintedInvoiceActivity.this.advanceLong != 0.0d) {
                editText7.setText(SeePrintedInvoiceActivity.this.advance);
            }
            if (SeePrintedInvoiceActivity.this.taxLong != 0.0d) {
                editText8.setText(SeePrintedInvoiceActivity.this.tax);
            }
            if (SeePrintedInvoiceActivity.this.discountLong != 0.0d) {
                editText9.setText(SeePrintedInvoiceActivity.this.discount);
            }
            checkBox.setChecked(SeePrintedInvoiceActivity.this.paidStatus.equals("paid"));
            dialog.show();
            button2.setOnClickListener(v -> {
                if (myInterstitialAds.getInstance() != null) {
                    myInterstitialAds.getInstance().show(SeePrintedInvoiceActivity.this);
                    myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            myInterstitialAds = new MyInterstitialAds(SeePrintedInvoiceActivity.this);
                            dialog.dismiss();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            myInterstitialAds = new MyInterstitialAds(SeePrintedInvoiceActivity.this);
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog.dismiss();
                }
            });
            editText2.setOnClickListener(view12 -> {
                Calendar instance = Calendar.getInstance();
                new DatePickerDialog(SeePrintedInvoiceActivity.this, R.style.DatePickerDialog, (datePicker, i, i2, i3) -> editText2.setText(i3 + "/" + (i2 + 1) + "/" + i), instance.get(1), instance.get(2), instance.get(5)).show();
            });
            updateBtn.setOnClickListener(view13 -> SeePrintedInvoiceActivity.this.addData());
            button.setOnClickListener(view14 -> {

                SeePrintedInvoiceActivity.this.updateSQLite();
                SeePrintedInvoiceActivity.this.updateTotalPrice();
                if (myInterstitialAds.getInstance() != null) {
                    myInterstitialAds.getInstance().show(SeePrintedInvoiceActivity.this);
                    myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            myInterstitialAds = new MyInterstitialAds(SeePrintedInvoiceActivity.this);
                            TextView textView = SeePrintedInvoiceActivity.this.binding.tvPrintOrderId2;
                            textView.setText("" + editText.getText().toString());
                            TextView textView2 = SeePrintedInvoiceActivity.this.binding.tvPrintOrderDate2;
                            textView2.setText("" + editText2.getText().toString());
                            TextView textView3 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerName2;
                            textView3.setText("" + editText3.getText().toString());
                            TextView textView4 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerAddress2;
                            textView4.setText("" + editText4.getText().toString());
                            TextView textView5 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2;
                            textView5.setText("" + editText5.getText().toString());
                            SeePrintedInvoiceActivity.this.deliFee = editText6.getText().toString();
                            SeePrintedInvoiceActivity.this.advance = editText7.getText().toString();
                            SeePrintedInvoiceActivity.this.tax = editText8.getText().toString();
                            SeePrintedInvoiceActivity.this.discount = editText9.getText().toString();
                            if (checkBox.isChecked()) {
                                SeePrintedInvoiceActivity.this.paidStatus = "paid";
                            } else {
                                SeePrintedInvoiceActivity.this.paidStatus = "unpaid";
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            myInterstitialAds = new MyInterstitialAds(SeePrintedInvoiceActivity.this);
                            TextView textView = SeePrintedInvoiceActivity.this.binding.tvPrintOrderId2;
                            textView.setText("" + editText.getText().toString());
                            TextView textView2 = SeePrintedInvoiceActivity.this.binding.tvPrintOrderDate2;
                            textView2.setText("" + editText2.getText().toString());
                            TextView textView3 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerName2;
                            textView3.setText("" + editText3.getText().toString());
                            TextView textView4 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerAddress2;
                            textView4.setText("" + editText4.getText().toString());
                            TextView textView5 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2;
                            textView5.setText("" + editText5.getText().toString());
                            SeePrintedInvoiceActivity.this.deliFee = editText6.getText().toString();
                            SeePrintedInvoiceActivity.this.advance = editText7.getText().toString();
                            SeePrintedInvoiceActivity.this.tax = editText8.getText().toString();
                            SeePrintedInvoiceActivity.this.discount = editText9.getText().toString();
                            if (checkBox.isChecked()) {
                                SeePrintedInvoiceActivity.this.paidStatus = "paid";
                            } else {
                                SeePrintedInvoiceActivity.this.paidStatus = "unpaid";
                            }
                            dialog.dismiss();
                        }
                    });
                } else {
                    TextView textView = SeePrintedInvoiceActivity.this.binding.tvPrintOrderId2;
                    textView.setText("" + editText.getText().toString());
                    TextView textView2 = SeePrintedInvoiceActivity.this.binding.tvPrintOrderDate2;
                    textView2.setText("" + editText2.getText().toString());
                    TextView textView3 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerName2;
                    textView3.setText("" + editText3.getText().toString());
                    TextView textView4 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerAddress2;
                    textView4.setText("" + editText4.getText().toString());
                    TextView textView5 = SeePrintedInvoiceActivity.this.binding.tvPrintCustomerPhone2;
                    textView5.setText("" + editText5.getText().toString());
                    SeePrintedInvoiceActivity.this.deliFee = editText6.getText().toString();
                    SeePrintedInvoiceActivity.this.advance = editText7.getText().toString();
                    SeePrintedInvoiceActivity.this.tax = editText8.getText().toString();
                    SeePrintedInvoiceActivity.this.discount = editText9.getText().toString();
                    if (checkBox.isChecked()) {
                        SeePrintedInvoiceActivity.this.paidStatus = "paid";
                    } else {
                        SeePrintedInvoiceActivity.this.paidStatus = "unpaid";
                    }
                    dialog.dismiss();
                }
            });
        });
        binding.btnPrint2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (SeePrintedInvoiceActivity.this.arrayList.get(0).getItemName().length() == 0) {
                    Toast.makeText(SeePrintedInvoiceActivity.this, "Add items before print!", Toast.LENGTH_SHORT).show();
                } else if (ContextCompat.checkSelfPermission(SeePrintedInvoiceActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    ActivityCompat.requestPermissions(SeePrintedInvoiceActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                } else {
                    Bitmap createBitmapFromLayout = createBitmapFromLayout(SeePrintedInvoiceActivity.this.binding.constraintLayoutPrint2);
                    String str = "invoice" + System.currentTimeMillis();
                    if (Build.VERSION.SDK_INT >= 29) {

                        ContentResolver contentResolver = SeePrintedInvoiceActivity.this.getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_display_name", str + ".jpg");
                        contentValues.put("mime_type", "image/jpg");
                        contentValues.put("relative_path", Environment.DIRECTORY_PICTURES + "/Instant Invoice");
                        Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        try {
                            Objects.requireNonNull(insert);
                            OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                            createBitmapFromLayout.compress(Bitmap.CompressFormat.JPEG, SeePrintedInvoiceActivity.this.imageQuality, openOutputStream);
                            Toast.makeText(SeePrintedInvoiceActivity.this, "Saved to gallery.", Toast.LENGTH_SHORT).show();
                            try {
                                Objects.requireNonNull(openOutputStream);
                                openOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/Instant Invoice");
                        file.mkdirs();
                        File file2 = new File(file, str);
                        if (file2.exists()) {
                            file2.delete();
                        }
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file2);
                            createBitmapFromLayout.compress(Bitmap.CompressFormat.JPEG, SeePrintedInvoiceActivity.this.imageQuality, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Toast.makeText(SeePrintedInvoiceActivity.this, "Saved to gallery.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        MediaScannerConnection.scanFile(SeePrintedInvoiceActivity.this, new String[]{file2.getPath()}, new String[]{"image/jpeg"}, null);
                    }
                    SeePrintedInvoiceActivity.this.finish();
                }
            }

            private Bitmap createBitmapFromLayout(View view) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                view.measure(makeMeasureSpec, makeMeasureSpec);
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                canvas.translate((float) (-view.getScrollX()), (float) (-view.getScrollY()));
                view.draw(canvas);
                return createBitmap;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void setShopInfo() {
        binding.tvShopNameTitle2.setText("Instant Invoice");
        SharedPreferences sharedPreferences = getSharedPreferences("shopinfo", 0);
        if (sharedPreferences.contains("imagePreferance")) {
            byte[] decode = Base64.decode(sharedPreferences.getString("imagePreferance", "default"), 0);
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            binding.ivShopLogo2.setBackgroundColor(0);
            binding.ivShopLogo2.setVisibility(View.VISIBLE);
            binding.ivShopLogo2.setImageBitmap(decodeByteArray);
        }
        if (sharedPreferences.contains("titlefontinfo")) {
            String string = sharedPreferences.getString("titlefontinfo", "0");
            switch (string) {
                case "1":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.poppins_normal));
                    break;
                case ExifInterface.GPS_MEASUREMENT_2D:
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
                    break;
                case ExifInterface.GPS_MEASUREMENT_3D:
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.inter_regular));
                    break;
                case "4":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.roboto_regular));
                    break;
                case "5":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.moulpali_regular));
                    break;
                case "6":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.murecho_regular));
                    break;
                case "7":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.mplus1p_regular));
                    break;
                case "8":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.port_lligat_sans_regular), Typeface.BOLD);
                    break;
                case "9":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.pontano_sans_regular));
                    break;
                case "10":
                    binding.tvShopNameTitle2.setTypeface(ResourcesCompat.getFont(this, R.font.poly_regular));
                    break;
                default:
                    binding.tvShopNameTitle2.setTypeface(null, Typeface.BOLD);
                    break;
            }
        } else {
            binding.tvShopNameTitle2.setTypeface(null, Typeface.BOLD);
        }
        if (sharedPreferences.contains("shopname")) {
            binding.tvShopNameTitle2.setText(sharedPreferences.getString("shopname", "Instant Invoice"));
            binding.tvShopNameSmall2.setText(sharedPreferences.getString("shopname", " "));
        } else {
            binding.tvShopNameTitle2.setText("Instant Invoice");
            binding.tvShopNameSmall2.setText(" ");
        }
        if (sharedPreferences.contains("shopaddress")) {
            binding.tvShopAddress2.setText(sharedPreferences.getString("shopaddress", " "));
        } else {
            binding.tvShopAddress2.setText(" ");
        }
        if (sharedPreferences.contains("shopphone")) {
            binding.tvShopPhone2.setText(sharedPreferences.getString("shopphone", " "));
        } else {
            binding.tvShopPhone2.setText(" ");
        }
        if (sharedPreferences.contains("bottommessage")) {
            binding.tvBottomMessage2.setText(sharedPreferences.getString("bottommessage", "Thanks for shopping with us!"));
        } else {
            binding.tvBottomMessage2.setText("Thanks for shopping with us!");
        }
        String currencyString;
        if (sharedPreferences.contains(FirebaseAnalytics.Param.CURRENCY)) {
            String string2 = sharedPreferences.getString(FirebaseAnalytics.Param.CURRENCY, "");
            currencyString = string2;
            if (string2.equals("No")) {
                binding.tvTotalCurrency2.setText("Total:");
                binding.tvAdvanceCurrency2.setText("Advance:");
                binding.tvBalanceCurrency2.setText("Balance:");
                return;
            }
            TextView textView = binding.tvTotalCurrency2;
            textView.setText("Total " + currencyString + "):");
            TextView textView2 = binding.tvAdvanceCurrency2;
            textView2.setText("Advance " + currencyString + "):");
            TextView textView3 = binding.tvBalanceCurrency2;
            textView3.setText("Balance " + currencyString + "):");
            return;
        }
        binding.tvTotalCurrency2.setText("Total:");
        binding.tvAdvanceCurrency2.setText("Advance:");
        binding.tvBalanceCurrency2.setText("Balance:");
    }

    public void setInvoiceBackground2() {
        SharedPreferences share;
        share = getSharedPreferences("backgroundinfo", 0);
        String backgroundType = share.getString("lastBackgroundClick", "bgColor");
        String backgroundImageCode = share.getString("backgroundmyimgdefault", "1");

        if (backgroundType.equals("bgColor")) {
            switch (share.getString("backgroundchange", "cwhite")) {
                case "cblue":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_bg_blue));
                    break;
                case "cgreen":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_green));
                    break;
                case "cyellow":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_yellow));
                    break;
                case "cpink":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_pink));
                    break;
                case "cviolet":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_violet));
                    break;
                case "corange":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_orange));
                    break;
                case "cgreen2":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_green2));
                    break;
                case "cviolet2":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_violet2));
                    break;
                case "cblue2":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_blue2));
                    break;
                case "cgrey":
                    binding.constraintLayoutPrint2.setBackgroundColor(getResources().getColor(R.color.color_background_grey));
                    break;
            }
        } else if (backgroundType.equals("bgImage")) {
            switch (backgroundImageCode) {
                case "wp1":
                    binding.constraintLayoutPrint2.setBackground(ContextCompat.getDrawable(this, R.drawable.wp1));
                    break;
                case "wp2":
                    binding.constraintLayoutPrint2.setBackground(ContextCompat.getDrawable(this, R.drawable.wp2));
                    break;
                case "wp3":
                    binding.constraintLayoutPrint2.setBackground(ContextCompat.getDrawable(this, R.drawable.wp3));
                    break;
                case "wp4":
                    binding.constraintLayoutPrint2.setBackground(ContextCompat.getDrawable(this, R.drawable.wp4));
                    break;
            }

        } else {
            String encodedString = share.getString("backgroundmyimg", "");
            if (!encodedString.isEmpty()) {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                Drawable dr = new BitmapDrawable(bitmap);
                binding.constraintLayoutPrint2.setBackgroundDrawable(dr);
            }
        }
    }

    public void getAndSetIntentData() {
        if (getIntent().hasExtra("paidstatus")) {
            paidStatus = getIntent().getStringExtra("paidstatus");
        }
        if (getIntent().hasExtra("invoiceid")) {
            myInvoiceId = getIntent().getStringExtra("invoiceid");
        }
        if (getIntent().hasExtra("orderid")) {
            orderId = getIntent().getStringExtra("orderid");
        }
        if (getIntent().hasExtra("orderdate")) {
            orderDate = getIntent().getStringExtra("orderdate");
        }
        if (getIntent().hasExtra("customername")) {
            customerName = getIntent().getStringExtra("customername");
        }
        if (getIntent().hasExtra("customeraddress")) {
            customerAddress = getIntent().getStringExtra("customeraddress");
        }
        if (getIntent().hasExtra("customerphone")) {
            customerPhone = getIntent().getStringExtra("customerphone");
        }
        if (getIntent().hasExtra("delifee")) {
            deliFee = getIntent().getStringExtra("delifee");
        }
        if (getIntent().hasExtra("advance")) {
            advance = getIntent().getStringExtra("advance");
        }
        if (getIntent().hasExtra(FirebaseAnalytics.Param.TAX)) {
            tax = getIntent().getStringExtra(FirebaseAnalytics.Param.TAX);
        }
        if (getIntent().hasExtra("discount")) {
            discount = getIntent().getStringExtra("discount");
        }
        if (getIntent().hasExtra("itemname")) {
            itemName = getIntent().getStringExtra("itemname");
        }
        if (getIntent().hasExtra("itemcount")) {
            itemCount = getIntent().getStringExtra("itemcount");
        }
        if (getIntent().hasExtra("itemprice")) {
            itemPrice = getIntent().getStringExtra("itemprice");
        }
        String str = itemName;
        String str2 = itemCount;
        String str3 = itemPrice;
        String[] separatedItem = str.split("--%%0&%%--\n");
        String[] separatedCount = str2.split("--%%0&%%--\n");
        String[] separatedPrice = str3.split("--%%0&%%--\n");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(separatedItem));
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList(separatedCount));
        ArrayList<String> arrayList3 = new ArrayList<>(Arrays.asList(separatedPrice));
        binding.tvPrintOrderId2.setText(orderId);
        binding.tvPrintOrderDate2.setText(" "+orderDate);
        binding.tvPrintCustomerName2.setText(customerName);
        binding.tvPrintCustomerAddress2.setText(customerAddress);
        binding.tvPrintCustomerPhone2.setText(customerPhone);
        String[] strArr = separatedItem;
        listArray = strArr.length;
        int i = 0;
        if (strArr.length >= 10) {
            while (true) {
                String[] strArr2 = separatedItem;
                if (i >= strArr2.length) {
                    break;
                }
                String str4 = strArr2[i];
                String str5 = separatedCount[i];
                String str6 = separatedPrice[i];
                this.arrayList.add(new ItemList(str4, str5, str6, Double.parseDouble(str5) * Double.parseDouble(str6)));
                i++;
            }
        } else {
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            this.arrayList.add(new ItemList("", "", "", 0.0d));
            itemListString = arrayList.get(0);
            countListString = arrayList2.get(0);
            priceListString = arrayList3.get(0);
            while (i < arrayList.size()) {
                itemListString = arrayList.get(i);
                countListString = arrayList2.get(i);
                priceListString = arrayList3.get(i);
                this.arrayList.set(i, new ItemList(itemListString, countListString, priceListString, Double.parseDouble(countListString) * Double.parseDouble(priceListString)));
                i++;
            }
        }
        RvAdapter rvadapter = new RvAdapter(this, this.arrayList);
        binding.rvList2.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList2.addOnItemTouchListener(new RvAdapter.RecyclerTouchListener(getApplicationContext(), binding.rvList2, new RvAdapter.ClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View view, int i) {
                SeePrintedInvoiceActivity seePrintedInvoiceActivity = SeePrintedInvoiceActivity.this;
                seePrintedInvoiceActivity.checkString = seePrintedInvoiceActivity.arrayList.get(i).getItemName();
                if (SeePrintedInvoiceActivity.this.checkString.length() > 0) {
                    final Dialog dialog = new Dialog(SeePrintedInvoiceActivity.this);
                    dialog.setContentView(R.layout.dialogaddnew);
                    final EditText editText = dialog.findViewById(R.id.et_update_dialog_item);
                    final EditText editText2 = dialog.findViewById(R.id.et_dialog_update_count);
                    final EditText editText3 = dialog.findViewById(R.id.et_dialog_update_price);
                    Button button = dialog.findViewById(R.id.btn_dialog_delete);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                    dialog.show();
                    final String[] strArr = new MyDatabaseHelperItems(SeePrintedInvoiceActivity.this.getApplicationContext()).getAllNames().toArray(new String[0]);
                    dialog.findViewById(R.id.btn_show_spinner_item).setOnClickListener(view1 -> new AlertDialog.Builder(SeePrintedInvoiceActivity.this).setSingleChoiceItems(strArr, -1, (dialogInterface, i1) -> {
                        String[] split = strArr[i1].split("\nPrice: ");
                        String str1 = split[0];
                        editText3.setText(split[1].replaceAll("\\D+", ""));
                        editText.setText(str1);
                    }).setPositiveButton("Ok", (dialogInterface, i1) -> {
                    }).setTitle("Choose from my items").show());
                    ((TextView) dialog.findViewById(R.id.tv_dialog_delete_caption)).setText("Edit Item");
                    editText.setText(SeePrintedInvoiceActivity.this.arrayList.get(i).getItemName());
                    editText2.setText(SeePrintedInvoiceActivity.this.arrayList.get(i).getItemCount());
                    editText3.setText(SeePrintedInvoiceActivity.this.arrayList.get(i).getItemPrice());
                    button.setText("Update");
                    editText2.addTextChangedListener(new TextWatcher() {
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void afterTextChanged(Editable editable) {
                            if (editText2.getText().toString().length() < 1 || Integer.parseInt(editText2.getText().toString()) < 1) {
                                editText2.setText("1");
                            }
                        }
                    });
                    dialog.findViewById(R.id.btn_dialog_count_down).setOnClickListener(view12 -> {
                        if (Integer.parseInt(editText2.getText().toString()) > 1) {
                            editText2.setText(String.valueOf(Integer.parseInt(editText2.getText().toString()) - 1));
                        }
                    });
                    dialog.findViewById(R.id.btn_dialog_count_up).setOnClickListener(view13 -> editText2.setText(String.valueOf(Integer.parseInt(editText2.getText().toString()) + 1)));
                    dialog.findViewById(R.id.btn_dialog_cancel_delete).setOnClickListener(view14 -> dialog.dismiss());
                    final int i2 = i;
                    button.setOnClickListener(view15 -> {
                        if (editText.getText().toString().length() == 0 || editText2.getText().toString().length() == 0 || editText3.getText().toString().length() == 0) {
                            Toast.makeText(SeePrintedInvoiceActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String obj = editText.getText().toString();
                        String obj2 = editText2.getText().toString();
                        String obj3 = editText3.getText().toString();
                        SeePrintedInvoiceActivity.this.arrayList.set(i2, new ItemList(obj, obj2, obj3, Double.parseDouble(obj2) * Double.parseDouble(obj3)));
                        SeePrintedInvoiceActivity.this.binding.rvList2.setAdapter(new RvAdapter(SeePrintedInvoiceActivity.this, SeePrintedInvoiceActivity.this.arrayList));
                        SeePrintedInvoiceActivity.this.updateTotalPrice();
                        dialog.dismiss();
                        SeePrintedInvoiceActivity.this.updateSQLite();
                    });
                }
            }

            public void onLongClick(View view, final int i) {
                SeePrintedInvoiceActivity seePrintedInvoiceActivity = SeePrintedInvoiceActivity.this;
                seePrintedInvoiceActivity.checkString = seePrintedInvoiceActivity.arrayList.get(i).getItemName();
                if (SeePrintedInvoiceActivity.this.checkString.length() > 0) {
                    final Dialog dialog = new Dialog(SeePrintedInvoiceActivity.this);
                    dialog.setContentView(R.layout.dialogdelete);
                    dialog.setCancelable(false);
                    dialog.show();
                    dialog.findViewById(R.id.btn_dialog_cancel_delete).setOnClickListener(view16 -> dialog.dismiss());
                    dialog.findViewById(R.id.btn_dialog_delete).setOnClickListener(view17 -> {
                        if (SeePrintedInvoiceActivity.this.arrayList.size() < 11) {
                            SeePrintedInvoiceActivity.this.arrayList.remove(i);
                            SeePrintedInvoiceActivity.this.arrayList.add(new ItemList("", "", "", 0.0d));
                            SeePrintedInvoiceActivity seePrintedInvoiceActivity1 = SeePrintedInvoiceActivity.this;
                            seePrintedInvoiceActivity1.listArray--;
                        } else {
                            SeePrintedInvoiceActivity.this.arrayList.remove(i);
                        }
                        dialog.dismiss();
                        SeePrintedInvoiceActivity.this.binding.rvList2.setAdapter(new RvAdapter(SeePrintedInvoiceActivity.this, SeePrintedInvoiceActivity.this.arrayList));
                        SeePrintedInvoiceActivity.this.updateTotalPrice();
                        SeePrintedInvoiceActivity.this.updateSQLite();
                    });
                }
            }
        }));
        this.binding.rvList2.setAdapter(rvadapter);
    }

    public void updateSQLite() {
        itemListString = arrayList.get(0).getItemName();
        countListString = arrayList.get(0).getItemCount();
        priceListString = arrayList.get(0).getItemPrice();
        for (int i = 1; i < arrayList.size(); i++) {
            itemListString += "--%%0&%%--\n" + arrayList.get(i).getItemName();
            countListString += "--%%0&%%--\n" + arrayList.get(i).getItemCount();
            priceListString += "--%%0&%%--\n" + arrayList.get(i).getItemPrice();
        }
        myDB.updateData(myInvoiceId, itemListString.split("--%%0&%%--\n--%%0&%%--\n")[0], countListString.split("--%%0&%%--\n--%%0&%%--\n")[0], priceListString.split("--%%0&%%--\n--%%0&%%--\n")[0], binding.tvPrintOrderId2.getText().toString().trim(), advance, tax, discount, deliFee, binding.tvPrintCustomerName2.getText().toString().trim(), binding.tvPrintCustomerAddress2.getText().toString().trim(), binding.tvPrintCustomerPhone2.getText().toString(), binding.tvPrintOrderDate2.getText().toString().trim(), "0", paidStatus, binding.tvBalance2.getText().toString().trim());
    }

    @SuppressLint({"WrongConstant", "SetTextI18n", "DefaultLocale"})
    public void updateTotalPrice() {
        double d;
        int i;
        int i2;
        if (paidStatus.equals("paid")) {
            binding.ivPaid2.setVisibility(View.VISIBLE);
        } else {
            binding.ivPaid2.setVisibility(View.GONE);
        }
        double total = 0.0d;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            total = Double.parseDouble(String.valueOf(total + arrayList.get(i3).getItemTotal()));
        }
        if (deliFee.length() == 0) {
            binding.tvPrintDeliFeeFinal2.setText("0");
            deliFeeLong = 0.0d;
            binding.tvPrintDeliFeeFinal2.setVisibility(View.GONE);
            binding.tvDeliFeeCurrency2.setVisibility(View.GONE);
        } else {
            deliFeeLong = Double.parseDouble(deliFee);
            binding.tvPrintDeliFeeFinal2.setVisibility(View.VISIBLE);
            binding.tvDeliFeeCurrency2.setVisibility(View.VISIBLE);
            String format = String.format("%.2f", deliFeeLong);
            binding.tvPrintDeliFeeFinal2.setText("+" + format);
        }
        double calculateDiscount2;
        if (tax.length() == 0 || discount.length() == 0) {
            binding.tvSubTotal2.setVisibility(View.GONE);
            binding.tvSubTotalCaption2.setVisibility(View.GONE);
            if (tax.length() == 0) {
                taxLong = 0.0d;
                binding.tvTax2.setVisibility(View.GONE);
                binding.tvTaxCaption2.setVisibility(View.GONE);
            }
            if (discount.length() == 0) {
                discountLong = 0.0d;
                binding.tvDiscount2.setVisibility(View.GONE);
                binding.tvDiscountCaption2.setVisibility(View.GONE);
            }
        }
        if (discount.length() > 0) {
            binding.tvSubTotal2.setVisibility(View.VISIBLE);
            binding.tvSubTotalCaption2.setVisibility(View.VISIBLE);
            binding.tvSubTotal2.setText(String.format("%.2f", total));
            discountLong = Double.parseDouble(discount);
            binding.tvDiscount2.setVisibility(View.VISIBLE);
            binding.tvDiscountCaption2.setVisibility(View.VISIBLE);
            binding.tvDiscount2.setText(String.valueOf(discountLong));
            double d2 = (discountLong * total) / 100.0d;
            calculateDiscount = d2;
            String format2 = String.format("%.2f", d2);
            binding.tvDiscountCaption2.setText("Discount -" + discountLong + "%(" + format2 + ")");
            binding.tvDiscount2.setText(String.format("%.2f", total - calculateDiscount));
        }
        if (tax.length() > 0) {
            binding.tvSubTotal2.setVisibility(View.VISIBLE);
            binding.tvSubTotalCaption2.setVisibility(View.VISIBLE);
            binding.tvSubTotal2.setText(String.format("%.2f", total));
            taxLong = Double.parseDouble(tax);
            if (discount.length() == 0) {
                double d3 = (taxLong * total) / 100.0d;
                calculateTax = d3;
                String format3 = String.format("%.2f", d3);
                binding.tvTaxCaption2.setText("Tax +" + taxLong + "%(" + format3 + ")");
                binding.tvTax2.setText(String.format("%.2f", total + calculateTax));
            }
            if (discount.length() > 0) {
                double parseDouble = Double.parseDouble(discount);
                discountLong = parseDouble;
                double d4 = (parseDouble * total) / 100.0d;
                calculateDiscount = d4;
                String format4 = String.format("%.2f", d4);
                binding.tvDiscountCaption2.setText("Discount -" + discountLong + "%(" + format4 + ")");
                binding.tvDiscount2.setText(String.format("%.2f", total - calculateDiscount));
                double d5 = total - calculateDiscount;
                calculateDiscount2 = d5;
                double d6 = (taxLong * d5) / 100.0d;
                calculateTax = d6;
                String format5 = String.format("%.2f", d6);
                binding.tvTaxCaption2.setText("Tax +" + taxLong + "%(" + format5 + ")");
                i2 = 0;
                binding.tvTax2.setText(String.format("%.2f", calculateDiscount2 + calculateTax));
            } else {
                i2 = 0;
            }
            binding.tvTax2.setVisibility(i2);
            binding.tvTaxCaption2.setVisibility(i2);
        }
        if (advance.length() == 0) {
            advanceLong = 0.0d;
            binding.tvAdvance2.setText("-");
            binding.tvBalance2.setText(binding.tvGrandTotals2.getText().toString());
        } else {
            double parseDouble2 = Double.parseDouble(advance);
            advanceLong = parseDouble2;
            String format6 = String.format("%.2f", parseDouble2);
            binding.tvAdvance2.setText("-" + format6);
        }
        if (discount.length() == 0) {
            d = 0.0d;
            calculateDiscount = 0.0d;
        } else {
            d = 0.0d;
        }
        if (tax.length() == 0) {
            calculateTax = d;
        }
        if (deliFee.length() == 0) {
            deliFeeLong = d;
        }
        if (advance.length() == 0) {
            advanceLong = d;
        }
        if (deliFee.length() != 0 || tax.length() <= 0) {
            i = 8;
        } else {
            i = 8;
            binding.tvGrandTotals2.setVisibility(View.GONE);
        }
        if (deliFee.length() == 0 && discount.length() > 0) {
            binding.tvGrandTotals2.setVisibility(i);
        }
        if (deliFee.length() == 0 && tax.length() == 0 && discount.length() == 0) {
            binding.tvGrandTotals2.setVisibility(0);
        }
        binding.tvGrandTotals2.setText(String.format("%.2f", (total - calculateDiscount) + calculateTax + deliFeeLong));
        binding.tvBalance2.setText(String.format("%.2f", Double.parseDouble(binding.tvGrandTotals2.getText().toString()) - advanceLong));
    }

    public void addData() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogaddnew);
        final EditText editText = dialog.findViewById(R.id.et_update_dialog_item);
        final EditText editText2 = dialog.findViewById(R.id.et_dialog_update_count);
        final EditText editText3 = dialog.findViewById(R.id.et_dialog_update_price);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        final String[] strArr = new MyDatabaseHelperItems(getApplicationContext()).getAllNames().toArray(new String[0]);
        dialog.findViewById(R.id.btn_show_spinner_item).setOnClickListener(view -> {
            if (strArr.length > 0) {
                new AlertDialog.Builder(SeePrintedInvoiceActivity.this).setSingleChoiceItems(strArr, -1, (dialogInterface, i) -> {
                    String[] split = strArr[i].split("\nPrice: ");
                    String str = split[0];
                    editText3.setText(split[1].replaceAll("[^0-9.]+", "").trim());
                    editText.setText(str);
                }).setPositiveButton("Ok", (dialogInterface, i) -> {
                }).setTitle("Choose from my items").show();
            } else {
                Toast.makeText(SeePrintedInvoiceActivity.this, "No items added yet!", Toast.LENGTH_SHORT).show();
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (editText2.getText().toString().length() < 1 || Integer.parseInt(editText2.getText().toString()) < 1) {
                    editText2.setText("1");
                }
            }
        });
        dialog.findViewById(R.id.btn_dialog_count_down).setOnClickListener(view -> {
            if (Integer.parseInt(editText2.getText().toString()) > 1) {
                editText2.setText(String.valueOf(Integer.parseInt(editText2.getText().toString()) - 1));
            }
        });
        dialog.findViewById(R.id.btn_dialog_count_up).setOnClickListener(view -> editText2.setText(String.valueOf(Integer.parseInt(editText2.getText().toString()) + 1)));
        dialog.findViewById(R.id.btn_dialog_cancel_delete).setOnClickListener(view -> dialog.dismiss());
        dialog.findViewById(R.id.btn_dialog_delete).setOnClickListener(view -> {
            if (editText.getText().toString().length() == 0 || editText2.getText().toString().length() == 0 || editText3.getText().toString().length() == 0) {
                Toast.makeText(SeePrintedInvoiceActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
            } else if (editText3.getText().toString().equals(".")) {
                Toast.makeText(SeePrintedInvoiceActivity.this, "Enter a valid price!", Toast.LENGTH_SHORT).show();
            } else {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                String obj3 = editText3.getText().toString();
                double parseDouble = Double.parseDouble(obj2) * Double.parseDouble(obj3);
                ItemList itemList = new ItemList(obj, obj2, obj3, parseDouble);
                if (SeePrintedInvoiceActivity.this.listArray < 10) {
                    SeePrintedInvoiceActivity.this.arrayList.set(SeePrintedInvoiceActivity.this.listArray, itemList);
                    SeePrintedInvoiceActivity seePrintedInvoiceActivity = SeePrintedInvoiceActivity.this;
                    SeePrintedInvoiceActivity.this.binding.rvList2.setAdapter(new RvAdapter(seePrintedInvoiceActivity, seePrintedInvoiceActivity.arrayList));
                    SeePrintedInvoiceActivity.this.listArray++;
                } else {
                    SeePrintedInvoiceActivity.this.arrayList.add(new ItemList(obj, obj2, obj3, parseDouble));
                    SeePrintedInvoiceActivity seePrintedInvoiceActivity2 = SeePrintedInvoiceActivity.this;
                    SeePrintedInvoiceActivity.this.binding.rvList2.setAdapter(new RvAdapter(seePrintedInvoiceActivity2, seePrintedInvoiceActivity2.arrayList));
                }
                SeePrintedInvoiceActivity.this.updateTotalPrice();
                dialog.dismiss();
            }
        });
    }

    public void changeInvoiceColor() {
        if (share.contains("colorChange")) {
            String string = share.getString("colorChange", "1");
            Objects.requireNonNull(string);
            int parseInt = Integer.parseInt(string);
            if (parseInt == 1) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_blue1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_blue1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_blue1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_blue1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistdarkblue);
            }
            if (parseInt == 2) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_green1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_green1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_green1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_green1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistgreen);
            }
            if (parseInt == 3) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.colorRed1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.colorRed1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.colorRed1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.colorRed1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistred);
            }
            if (parseInt == 4) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.colorPink1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.colorPink1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.colorPink1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.colorPink1));
                binding.rvList2.setBackgroundResource(R.drawable.frame_rv_list_pink);
            }
            if (parseInt == 5) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_main1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_main1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_main1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_main1));
                binding.rvList2.setBackgroundResource(R.drawable.frame_rv_list_blue);
            }
            if (parseInt == 6) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_grey1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_grey1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_grey1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_grey1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistgrey);
            }
            if (parseInt == 7) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_orange1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_orange1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_orange1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_orange1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistorange);
            }
            if (parseInt == 8) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_yellow1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_yellow1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_yellow1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_yellow1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistyellow);
            }
            if (parseInt == 9) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.colorViolet1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.colorViolet1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.colorViolet1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.colorViolet1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistviolet);
            }
            if (parseInt == 10) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_brown1));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_brown1);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_brown1);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_brown1));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistbrown);
            }
            if (parseInt == 11) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.colorViolet11));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionItem2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionCount2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.colorViolet11);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.colorViolet11);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.colorViolet11));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistviolet11);
            }
            if (parseInt == 12) {
                binding.tvShopNameTitle2.setTextColor(getResources().getColor(R.color.color_green11));
                binding.tvCaptionOrder2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionDeli2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionNumber2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionItem2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionCount2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionPrice2.setBackgroundResource(R.color.color_green11);
                binding.tvCaptionTotal2.setBackgroundResource(R.color.color_green11);
                binding.tvBottomMessage2.setTextColor(getResources().getColor(R.color.color_green11));
                binding.rvList2.setBackgroundResource(R.drawable.framervlistgreen11);
            }
        }
    }
}
