package com.adam.processbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wuzongheng on 16/6/5.
 */
public class ProcessElement extends RelativeLayout {
    private Context context;
    private TextView textView;
    private ImageView background;
    private int color;
    private Bitmap currBmp;
    private int textSizeSelected = 14, textSizeUnSelected = 14;
    public static final int STATUS_NEW = 1, STATUS_SELECTED = 2, STATUS_OLD = 3;

    public ProcessElement(Context context, CharSequence title) {
        super(context);
        init(context, title);
    }

    private void init(Context context, CharSequence title){
        this.context = context;

        textView = new TextView(context);
        background = new ImageView(context);

        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(context.getResources().getColor(android.R.color.white));

        background.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setScaleType(ImageView.ScaleType.FIT_XY);

        addView(background);
        addView(textView);
        setGravity(Gravity.CENTER);
    }

    public void setSelectedColor(int color){
        this.color = color;
    }

    public void setSelectedDrawable(Drawable drawable){
        // 这是一个坑,如果直接用Drawable的话,只有第一次绘制的时候能显示,其余都无法显示,是因为一个Drawable对象只能绘制一次,所以这里转换成Bitmap可以多次绘制
        currBmp = drawable2Bitmap(drawable);
    }

    public void setTextSize(int textSizeSelected, int textSizeUnSelected){
        this.textSizeSelected = textSizeSelected;
        this.textSizeUnSelected = textSizeUnSelected;

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeUnSelected);
    }

    public void setStatus(int status){
        if(status == STATUS_NEW){
            background.setImageAlpha(0);
            background.setBackgroundColor(Color.TRANSPARENT);

            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeUnSelected);
        }else if(status == STATUS_SELECTED){
            if(currBmp == null){
                background.setImageResource(R.mipmap.plan_arrow);
            }else{
                background.setImageBitmap(currBmp);
            }
            background.setImageAlpha(255);
            background.setBackgroundColor(Color.TRANSPARENT);

            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeSelected);
        }else if(status == STATUS_OLD){
            background.setImageAlpha(0);
            background.setBackgroundColor(color);

            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeUnSelected);
        }
    }

    Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
}
