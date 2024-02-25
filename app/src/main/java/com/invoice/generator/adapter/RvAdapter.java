package com.invoice.generator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;

import com.invoice.generator.R;
import com.invoice.generator.databinding.CarditemlayoutBinding;
import com.invoice.generator.model.ItemList;

import java.util.ArrayList;
import java.util.Objects;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.PlaceHolder> {
    private final ArrayList<ItemList> arrayList;
    public int color;
    private final Context context;
    public SharedPreferences share, shareFont;
    private CarditemlayoutBinding binding;

    public interface ClickListener {
        void onClick(View view, int i);

        void onLongClick(View view, int i);
    }

    public RvAdapter(Context context2, ArrayList<ItemList> arrayList) {
        this.context = context2;
        this.arrayList = arrayList;
    }

    @NonNull
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.carditemlayout, viewGroup, false);
        binding = DataBindingUtil.bind(view);
        return new PlaceHolder(view);
    }

    @SuppressLint("DefaultLocale")
    public void onBindViewHolder(@NonNull PlaceHolder placeHolder, int i) {
        binding.tvItemName.setText(this.arrayList.get(i).getItemName());
        binding.tvItemCount.setText(this.arrayList.get(i).getItemCount());
        binding.tvItemPrice.setText(this.arrayList.get(i).getItemPrice());
        if (!(this.arrayList.get(i).getItemCount().length() == 0 || this.arrayList.get(i).getItemPrice().length() == 0)) {
            binding.tvItemTotal.setText(String.format("%.2f", this.arrayList.get(i).getItemTotal()));
            binding.tvIdPrinted.setText(String.valueOf(i + 1));
        }
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("colorinfo", 0);
        shareFont = this.context.getSharedPreferences("shopinfo", 0);
        share = sharedPreferences;
        if (sharedPreferences.contains("colorChange")) {
            String string = share.getString("colorChange", "1");
            Objects.requireNonNull(string);
            int parseInt = Integer.parseInt(string);
            color = parseInt;
            if (parseInt == 1) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framedarkblue);
                binding.tvItemName.setBackgroundResource(R.drawable.framedarkblue);
                binding.tvItemCount.setBackgroundResource(R.drawable.framedarkblue);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framedarkblue);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framedarkblue);
            }
            if (color == 2) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framegreen);
                binding.tvItemName.setBackgroundResource(R.drawable.framegreen);
                binding.tvItemCount.setBackgroundResource(R.drawable.framegreen);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framegreen);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framegreen);
            }
            if (color == 3) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framered);
                binding.tvItemName.setBackgroundResource(R.drawable.framered);
                binding.tvItemCount.setBackgroundResource(R.drawable.framered);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framered);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framered);
            }
            if (color == 4) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framepink);
                binding.tvItemName.setBackgroundResource(R.drawable.framepink);
                binding.tvItemCount.setBackgroundResource(R.drawable.framepink);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framepink);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framepink);
            }
            if (color == 5) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.frame_blue);
                binding.tvItemName.setBackgroundResource(R.drawable.frame_blue);
                binding.tvItemCount.setBackgroundResource(R.drawable.frame_blue);
                binding.tvItemPrice.setBackgroundResource(R.drawable.frame_blue);
                binding.tvItemTotal.setBackgroundResource(R.drawable.frame_blue);
            }
            if (color == 6) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framegrey);
                binding.tvItemName.setBackgroundResource(R.drawable.framegrey);
                binding.tvItemCount.setBackgroundResource(R.drawable.framegrey);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framegrey);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framegrey);
            }
            if (color == 7) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.frameorange);
                binding.tvItemName.setBackgroundResource(R.drawable.frameorange);
                binding.tvItemCount.setBackgroundResource(R.drawable.frameorange);
                binding.tvItemPrice.setBackgroundResource(R.drawable.frameorange);
                binding.tvItemTotal.setBackgroundResource(R.drawable.frameorange);
            }
            if (color == 8) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.frameyellow);
                binding.tvItemName.setBackgroundResource(R.drawable.frameyellow);
                binding.tvItemCount.setBackgroundResource(R.drawable.frameyellow);
                binding.tvItemPrice.setBackgroundResource(R.drawable.frameyellow);
                binding.tvItemTotal.setBackgroundResource(R.drawable.frameyellow);
            }
            if (color == 9) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.frameviolet);
                binding.tvItemName.setBackgroundResource(R.drawable.frameviolet);
                binding.tvItemCount.setBackgroundResource(R.drawable.frameviolet);
                binding.tvItemPrice.setBackgroundResource(R.drawable.frameviolet);
                binding.tvItemTotal.setBackgroundResource(R.drawable.frameviolet);
            }
            if (color == 10) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framebrown);
                binding.tvItemName.setBackgroundResource(R.drawable.framebrown);
                binding.tvItemCount.setBackgroundResource(R.drawable.framebrown);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framebrown);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framebrown);
            }
            if (color == 11) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.frameviolet11);
                binding.tvItemName.setBackgroundResource(R.drawable.frameviolet11);
                binding.tvItemCount.setBackgroundResource(R.drawable.frameviolet11);
                binding.tvItemPrice.setBackgroundResource(R.drawable.frameviolet11);
                binding.tvItemTotal.setBackgroundResource(R.drawable.frameviolet11);
            }
            if (color == 12) {
                binding.tvIdPrinted.setBackgroundResource(R.drawable.framegreen11);
                binding.tvItemName.setBackgroundResource(R.drawable.framegreen11);
                binding.tvItemCount.setBackgroundResource(R.drawable.framegreen11);
                binding.tvItemPrice.setBackgroundResource(R.drawable.framegreen11);
                binding.tvItemTotal.setBackgroundResource(R.drawable.framegreen11);
            }
        } if (shareFont.contains("titlefontinfo")) {
            switch (shareFont.getString("titlefontinfo","1")) {
                case "1":
                    setFontInvoice(R.font.poppins_normal);
                    break;
                case "2":
                    setFontInvoice(R.font.montserrat_regular);
                    break;
                case "3":
                    setFontInvoice(R.font.inter_regular);
                    break;
                case "4":
                    setFontInvoice(R.font.roboto_regular);
                    break;
                case "5":
                    setFontInvoice(R.font.moulpali_regular);
                    break;
                case "6":
                    setFontInvoice(R.font.murecho_regular);
                    break;
                case "7":
                    setFontInvoice(R.font.mplus1p_regular);
                    break;
                case "8":
                    setFontInvoice(R.font.port_lligat_sans_regular);
                    break;
                case "9":
                    setFontInvoice(R.font.pontano_sans_regular);
                    break;
                case "10":
                    setFontInvoice(R.font.poly_regular);
                    break;
                default:
                    break;
            }
        }
    }

    public int getItemCount() {
        return this.arrayList.size();
    }

    public static class PlaceHolder extends RecyclerView.ViewHolder {
        SharedPreferences share;

        public PlaceHolder(View view) {
            super(view);
            share = view.getContext().getSharedPreferences("colorinfo", 0);
        }
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private final ClickListener clickListener;
        private final GestureDetector gestureDetector;

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        }

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener2) {
            clickListener = clickListener2;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

                public void onLongPress(MotionEvent motionEvent) {
                    ClickListener clickListener;
                    View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (findChildViewUnder != null && (clickListener = clickListener2) != null) {
                        clickListener.onLongClick(findChildViewUnder, recyclerView.getChildPosition(findChildViewUnder));
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }
    }

    private void setFontInvoice(int fontInvoice) {
        binding.tvIdPrinted.setTypeface(ResourcesCompat.getFont(context, fontInvoice));
        binding.tvItemName.setTypeface(ResourcesCompat.getFont(context, fontInvoice));
        binding.tvItemCount.setTypeface(ResourcesCompat.getFont(context, fontInvoice));
        binding.tvItemPrice.setTypeface(ResourcesCompat.getFont(context, fontInvoice));
        binding.tvItemTotal.setTypeface(ResourcesCompat.getFont(context, fontInvoice));
    }
}
