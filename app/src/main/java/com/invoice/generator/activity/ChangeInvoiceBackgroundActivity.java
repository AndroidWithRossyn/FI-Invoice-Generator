package com.invoice.generator.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.invoice.generator.R;
import com.invoice.generator.databinding.ActivityChangeInvoiceBackgroundBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class ChangeInvoiceBackgroundActivity extends AppCompatActivity {

    public int checkBg;
    public int defaultColor;
    private SharedPreferences share;
    private ActivityChangeInvoiceBackgroundBinding binding;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_invoice_background);

        initView();
        setImageViewImg();
        initClickListener();
    }

    private void initView() {

        share = getSharedPreferences("backgroundinfo", 0);
        defaultColor = ContextCompat.getColor(this, R.color.color_white_1);
    }

    private void initClickListener() {
        binding.tvBtSetDefaultColor.setOnClickListener(v -> {
            SharedPreferences.Editor edit = share.edit();
            edit.remove("backgroundchange");
            edit.apply();
            finish();
        });
        binding.tvBtSetDefaultColor.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cwhite");
            edit.apply();
            finish();
        });
        binding.btBgBlue.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cblue");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgGreen.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cgreen");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgYellow.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cyellow");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgPink.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cpink");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgViolet.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cviolet");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgOrange.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "corange");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgGreen2.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cgreen2");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgViolet2.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cviolet2");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgBlue2.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cblue2");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.btBgGrey.setOnClickListener(view -> {
            SharedPreferences.Editor edit = share.edit();
            edit.putString("backgroundchange", "cgrey");
            edit.putString("lastBackgroundClick", "bgColor");
            edit.apply();
            finish();
        });
        binding.tvBtChangeMyImage.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, "Pick an image"), 333);
        });
        binding.ivBtBgImg1.setOnClickListener(view -> {
            ChangeInvoiceBackgroundActivity changeInvoiceBackgroundActivity = ChangeInvoiceBackgroundActivity.this;
            changeInvoiceBackgroundActivity.share = changeInvoiceBackgroundActivity.getSharedPreferences("backgroundinfo", 0);
            SharedPreferences.Editor edit = share.edit();
            edit.remove("backgroundchange");
            edit.putString("backgroundmyimgdefault", "wp1");
            edit.putString("lastBackgroundClick", "bgImage");
            edit.apply();
            finish();
        });
        binding.ivBtBgImg2.setOnClickListener(view -> {
            ChangeInvoiceBackgroundActivity changeInvoiceBackgroundActivity = ChangeInvoiceBackgroundActivity.this;
            changeInvoiceBackgroundActivity.share = changeInvoiceBackgroundActivity.getSharedPreferences("backgroundinfo", 0);
            SharedPreferences.Editor edit = share.edit();
            edit.remove("backgroundchange");
            edit.putString("backgroundmyimgdefault", "wp2");
            edit.putString("lastBackgroundClick", "bgImage");
            edit.apply();
            finish();
        });
        binding.ivBtBgImg3.setOnClickListener(view -> {
            ChangeInvoiceBackgroundActivity changeInvoiceBackgroundActivity = ChangeInvoiceBackgroundActivity.this;
            changeInvoiceBackgroundActivity.share = changeInvoiceBackgroundActivity.getSharedPreferences("backgroundinfo", 0);
            SharedPreferences.Editor edit = share.edit();
            edit.remove("backgroundchange");
            edit.putString("backgroundmyimgdefault", "wp3");
            edit.putString("lastBackgroundClick", "bgImage");
            edit.apply();
            finish();
        });
        binding.ivBtBgImg4.setOnClickListener(view -> {
            ChangeInvoiceBackgroundActivity changeInvoiceBackgroundActivity = ChangeInvoiceBackgroundActivity.this;
            changeInvoiceBackgroundActivity.share = changeInvoiceBackgroundActivity.getSharedPreferences("backgroundinfo", 0);
            SharedPreferences.Editor edit = share.edit();
            edit.remove("backgroundchange");
            edit.putString("backgroundmyimgdefault", "wp4");
            edit.putString("lastBackgroundClick", "bgImage");
            edit.apply();
            finish();
        });
        binding.ivBtBgMyImage.setOnClickListener(view -> {
            ChangeInvoiceBackgroundActivity changeInvoiceBackgroundActivity = ChangeInvoiceBackgroundActivity.this;
            changeInvoiceBackgroundActivity.share = changeInvoiceBackgroundActivity.getSharedPreferences("backgroundinfo", 0);
            if (share.contains("backgroundmyimg")) {
                byte[] decode = Base64.decode(share.getString("backgroundmyimg", "default"), 0);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                binding.ivBtBgMyImage.setBackground(new BitmapDrawable(getResources(), decodeByteArray));
                SharedPreferences.Editor edit = share.edit();
                edit.remove("backgroundchange");
                edit.remove("backgroundmyimgdefault");
                edit.putString("lastBackgroundClick", "bgMyImage");
                edit.putString("backgroundmyimg", ChangeInvoiceBackgroundActivity.encodeTobase64(decodeByteArray));
                edit.apply();
                finish();
                return;
            }
            Log.d("TAG", "NOIMG");
            Toast.makeText(ChangeInvoiceBackgroundActivity.this, "Choose an image!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setImageViewImg() {
        SharedPreferences sharedPreferences = getSharedPreferences("backgroundinfo", 0);
        share = sharedPreferences;
        if (sharedPreferences.contains("backgroundmyimg")) {
            byte[] decode = Base64.decode(share.getString("backgroundmyimg", "default"), 0);
            binding.ivBtBgMyImage.setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode, 0, decode.length)));
            checkBg = 2;
            binding.ivBtBgMyImage.setOnClickListener(view -> finish());
            return;
        }
        Log.d("TAG", "NOIMG");
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 333 && i2 == -1 && intent != null) {
            CropImage.activity(intent.getData()).setOutputCompressQuality(70).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(10, 14).start(this);
        }
        if (i == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            if (i2 == -1 && activityResult != null) {
                binding.ivBtBgMyImage.setImageURI(activityResult.getUri());
                binding.ivBtBgMyImage.setOnClickListener(view -> {
                    SharedPreferences.Editor edit = share.edit();
                    Bitmap bitmap = ((BitmapDrawable) binding.ivBtBgMyImage.getDrawable()).getBitmap();
                    edit.remove("backgroundchange");
                    edit.remove("backgroundmyimgdefault");
                    edit.putString("backgroundmyimg", ChangeInvoiceBackgroundActivity.encodeTobase64(bitmap));
                    edit.apply();
                    finish();
                });
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
}
