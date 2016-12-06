package com.github.fcannizzaro.materialtip;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.fcannizzaro.materialtip.util.AnimationAdapter;
import com.github.fcannizzaro.materialtip.util.ButtonListener;

/**
 * Francesco Cannizzaro (fcannizzaro)
 */
@SuppressWarnings("unused")
public class MaterialTip extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {

    private Context context;
    private ButtonListener listener;
    private Button positive, negative;
    private TextView title, text;
    private ImageView icon;
    private View tip;
    private int color, background, titleColor, textColor;
    private float eventY, MIN_DISTANCE;
    private boolean visible;

    public MaterialTip(Context context) {
        super(context);
        init();
    }

    public MaterialTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTip, 0, 0);

        try {

            tip.setVisibility(GONE);
            color = array.getColor(R.styleable.MaterialTip_tip_color, ContextCompat.getColor(context, R.color.colorPrimary));
            background = array.getColor(R.styleable.MaterialTip_tip_background, ContextCompat.getColor(context, R.color.tip_background));
            titleColor = array.getColor(R.styleable.MaterialTip_tip_title_color, ContextCompat.getColor(context, R.color.tip_title));
            textColor = array.getColor(R.styleable.MaterialTip_tip_text_color, ContextCompat.getColor(context, R.color.tip_text));
            title.setText(array.getString(R.styleable.MaterialTip_tip_title));
            text.setText(array.getString(R.styleable.MaterialTip_tip_text));
            positive.setText(array.getString(R.styleable.MaterialTip_tip_positive));
            negative.setText(array.getString(R.styleable.MaterialTip_tip_negative));
            title.setVisibility(array.getBoolean(R.styleable.MaterialTip_tip_title_visible, true) ? VISIBLE : GONE);
            icon.setImageDrawable(array.getDrawable(R.styleable.MaterialTip_tip_icon));

            color();

        } finally {
            array.recycle();
        }

    }

    public MaterialTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        context = getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tip_layout, this, true);

        tip = findViewById(R.id.tip);
        positive = (Button) findViewById(R.id.tip_positive);
        negative = (Button) findViewById(R.id.tip_negative);
        title = (TextView) findViewById(R.id.tip_title);
        text = (TextView) findViewById(R.id.tip_text);
        icon = (ImageView) findViewById(R.id.tip_icon);

        tip.setOnTouchListener(this);
        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

    }

    // Builder Methods

    public void withButtonListener(ButtonListener listener) {
        this.listener = listener;
    }

    // direct

    public MaterialTip withTitleVisible(boolean titleVisible) {
        this.title.setVisibility(titleVisible ? VISIBLE : GONE);
        return this;
    }

    public MaterialTip withTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public MaterialTip withIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
        return this;
    }

    public MaterialTip withText(String text) {
        this.text.setText(text);
        return this;
    }

    public MaterialTip withPositive(String positive) {
        this.positive.setText(positive);
        return this;
    }

    public MaterialTip withNegative(String negative) {
        this.negative.setText(negative);
        return this;
    }

    public MaterialTip withIconRes(@DrawableRes int icon) {
        this.icon.setImageResource(icon);
        return this;
    }

    public MaterialTip withColor(@ColorInt int color) {
        this.color = color;
        color();
        return this;
    }

    public MaterialTip withBackground(@ColorInt int background) {
        this.background = background;
        color();
        return this;
    }

    public MaterialTip withTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        color();
        return this;
    }

    public MaterialTip withTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        color();
        return this;
    }

    // From Resources

    public MaterialTip withColorRes(@ColorRes int color) {
        this.color = ContextCompat.getColor(context, color);
        color();
        return this;
    }

    public MaterialTip withTextColorRes(@ColorRes int textColor) {
        this.textColor = ContextCompat.getColor(context, textColor);
        color();
        return this;
    }

    public MaterialTip withTitleColorRes(@ColorRes int titleColor) {
        this.titleColor = ContextCompat.getColor(context, titleColor);
        color();
        return this;
    }

    public MaterialTip withBackgroundRes(@ColorRes int background) {
        this.background = ContextCompat.getColor(context, background);
        color();
        return this;
    }

    public MaterialTip withTitleRes(@StringRes int title) {
        this.title.setText(context.getText(title));
        return this;
    }

    public MaterialTip withTextRes(@StringRes int text) {
        this.text.setText(context.getText(text));
        return this;
    }

    public MaterialTip withPositiveRes(@StringRes int positive) {
        this.positive.setText(context.getText(positive));
        return this;
    }

    public MaterialTip withNegativeRes(@StringRes int negative) {
        this.negative.setText(context.getText(negative));
        return this;
    }

    public void show() {

        animate()
                .translationY(0)
                .setDuration(300)
                .setListener(new AnimationAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        tip.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visible = true;
                    }
                })
                .start();

    }

    public void hide() {

        animate()
                .translationY(getHeight())
                .setDuration(300)
                .setListener(new AnimationAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        visible = false;
                        tip.setVisibility(GONE);
                    }
                })
                .start();
    }

    public void toggle() {
        if (visible)
            hide();
        else
            show();
    }

    /**
     * If the current Material Tip is visible. <br/>
     * This is useful when back button is pressed and you don't want to quit the application
     */
    public boolean isVisible(){
        return visible;
    }

    private void color() {

        // text color
        title.setTextColor(titleColor);
        text.setTextColor(textColor);
        negative.setTextColor(color);

        // background
        tip.setBackgroundColor(background);
        negative.setBackgroundColor(background);
        positive.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (listener != null) {

            if (id == R.id.tip_positive)
                listener.onPositive(this);

            else if (id == R.id.tip_negative)
                listener.onNegative(this);
        }
        hide();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                eventY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                if (event.getY() - eventY >= MIN_DISTANCE)
                    hide();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MIN_DISTANCE = getHeight() / 4;
        setTranslationY(getHeight());
    }


}
