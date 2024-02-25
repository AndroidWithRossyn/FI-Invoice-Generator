package com.invoice.generator.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.invoice.generator.R;
import com.invoice.generator.ads.MyInterstitialAds;
import com.invoice.generator.databinding.ActivityAddShopInfoBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class AddShopInfoActivity extends AppCompatActivity {
    private String currencyString;
    public String itemNAme;
    private SharedPreferences share;
    private int spinnerPosition;
    private ActivityAddShopInfoBinding binding;
    private MyInterstitialAds myInterstitialAds;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_shop_info);

        initView();
        initClickListener();
    }

    private void initView() {
        binding.setTitle("Instant Invoice");
        share = getSharedPreferences("shopinfo", 0);
        myInterstitialAds = new MyInterstitialAds(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.spinnerCurrencyEdit.setAdapter(adapter);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initClickListener() {
        binding.btnCancelInfo.setOnClickListener(view -> {
            if (myInterstitialAds.getInstance() != null) {
                myInterstitialAds.getInstance().show(this);
                myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        myInterstitialAds = new MyInterstitialAds(AddShopInfoActivity.this);
                        startActivity(new Intent(AddShopInfoActivity.this, MainActivity.class));
                        finish();
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        myInterstitialAds = new MyInterstitialAds(AddShopInfoActivity.this);
                        startActivity(new Intent(AddShopInfoActivity.this, MainActivity.class));
                        finish();
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
                    }
                });
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
            }
        });
        binding.spinnerCurrencyEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                String[] split = adapterView.getSelectedItem().toString().split(" ");
                currencyString = split[1];
                spinnerPosition = adapterView.getSelectedItemPosition();
                if (currencyString.equals("Own")) {
                    binding.etCreateCurrency.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btRemoveAddIcon.setOnClickListener(view -> {
            binding.btRemoveAddIcon.setVisibility(View.INVISIBLE);
            binding.ivAddLogo.setImageBitmap(null);
            binding.ivAddLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_company_logo));
        });
        binding.btbUpdateInfo.setOnClickListener(view -> {

            if (myInterstitialAds.getInstance() != null) {
                myInterstitialAds.getInstance().show(AddShopInfoActivity.this);
                myInterstitialAds.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        myInterstitialAds = new MyInterstitialAds(AddShopInfoActivity.this);
                        SharedPreferences.Editor edit = share.edit();
                        if (currencyString.equals("No")) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, "No");
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else if (!currencyString.equals("Own")) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, currencyString);
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else if (binding.etCreateCurrency.getText().toString().length() != 0) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, binding.etCreateCurrency.getText().toString());
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else {
                            Toast.makeText(AddShopInfoActivity.this, "Type your own currency or \nChoose currency from the list", Toast.LENGTH_SHORT).show();
                        }
                        if (binding.etShopNameEdit.getText().toString().length() > 0) {
                            edit.putString("shopname", binding.etShopNameEdit.getText().toString().trim());
                        }
                        if (binding.btRemoveAddIcon.getVisibility() != View.VISIBLE) {
                            Log.d("TAG", "NOIMG");
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) binding.ivAddLogo.getDrawable()).getBitmap();
                            edit.putString("namePreferance", itemNAme);
                            edit.putString("imagePreferance", encodeTobase64(bitmap));
                        }
                        edit.putString("shopaddress", binding.etShopAddressEdit.getText().toString().trim());
                        edit.putString("shopphone", binding.etShopPhoneEdit.getText().toString().trim());
                        edit.putString("bottommessage", binding.etBottomMessageEdit.getText().toString().trim());
                        edit.apply();
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
                        startActivity(new Intent(AddShopInfoActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        myInterstitialAds = new MyInterstitialAds(AddShopInfoActivity.this);
                        SharedPreferences.Editor edit = share.edit();
                        if (currencyString.equals("No")) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, "No");
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else if (!currencyString.equals("Own")) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, currencyString);
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else if (binding.etCreateCurrency.getText().toString().length() != 0) {
                            edit.putString(FirebaseAnalytics.Param.CURRENCY, binding.etCreateCurrency.getText().toString());
                            edit.putString("currencyposition", String.valueOf(spinnerPosition));
                        } else {
                            Toast.makeText(AddShopInfoActivity.this, "Type your own currency or \nChoose currency from the list", Toast.LENGTH_SHORT).show();
                        }
                        if (binding.etShopNameEdit.getText().toString().length() > 0) {
                            edit.putString("shopname", binding.etShopNameEdit.getText().toString().trim());
                        }
                        if (binding.btRemoveAddIcon.getVisibility() != View.VISIBLE) {
                            Log.d("TAG", "NOIMG");
                        } else {
                            Bitmap bitmap = ((BitmapDrawable) binding.ivAddLogo.getDrawable()).getBitmap();
                            edit.putString("namePreferance", itemNAme);
                            edit.putString("imagePreferance", encodeTobase64(bitmap));
                        }
                        edit.putString("shopaddress", binding.etShopAddressEdit.getText().toString().trim());
                        edit.putString("shopphone", binding.etShopPhoneEdit.getText().toString().trim());
                        edit.putString("bottommessage", binding.etBottomMessageEdit.getText().toString().trim());
                        edit.apply();
                        getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
                        startActivity(new Intent(AddShopInfoActivity.this, MainActivity.class));
                        finish();
                    }
                });
            } else {
                SharedPreferences.Editor edit = share.edit();
                if (currencyString.equals("No")) {
                    edit.putString(FirebaseAnalytics.Param.CURRENCY, "No");
                    edit.putString("currencyposition", String.valueOf(spinnerPosition));
                } else if (!currencyString.equals("Own")) {
                    edit.putString(FirebaseAnalytics.Param.CURRENCY, currencyString);
                    edit.putString("currencyposition", String.valueOf(spinnerPosition));
                } else if (binding.etCreateCurrency.getText().toString().length() != 0) {
                    edit.putString(FirebaseAnalytics.Param.CURRENCY, binding.etCreateCurrency.getText().toString());
                    edit.putString("currencyposition", String.valueOf(spinnerPosition));
                } else {
                    Toast.makeText(this, "Type your own currency or \nChoose currency from the list", Toast.LENGTH_SHORT).show();
                }
                if (binding.etShopNameEdit.getText().toString().length() > 0) {
                    edit.putString("shopname", binding.etShopNameEdit.getText().toString().trim());
                }
                if (binding.btRemoveAddIcon.getVisibility() != View.VISIBLE) {
                    Log.d("TAG", "NOIMG");
                } else {
                    Bitmap bitmap = ((BitmapDrawable) binding.ivAddLogo.getDrawable()).getBitmap();
                    edit.putString("namePreferance", itemNAme);
                    edit.putString("imagePreferance", encodeTobase64(bitmap));
                }
                edit.putString("shopaddress", binding.etShopAddressEdit.getText().toString().trim());
                edit.putString("shopphone", binding.etShopPhoneEdit.getText().toString().trim());
                edit.putString("bottommessage", binding.etBottomMessageEdit.getText().toString().trim());
                edit.apply();
                getSharedPreferences("PREFERENCE", 0).edit().putBoolean("isFirstRun", false).apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
        binding.ivAddLogo.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, "Pick an image"), 123);
        });
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 123 && i2 == -1 && intent != null) {
            CropImage.activity(intent.getData()).setOutputCompressQuality(70).setOutputCompressFormat(Bitmap.CompressFormat.PNG).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);
        }
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1 && activityResult != null) {
                binding.ivAddLogo.setImageURI(activityResult.getUri());
                binding.btRemoveAddIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    public static String encodeTobase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        Log.d("Image Log:", encodeToString);
        return encodeToString;
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
