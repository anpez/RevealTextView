package com.antonionicolaspina.revealtextview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public final class RevealTextView extends TextView implements Runnable, ValueAnimator.AnimatorUpdateListener {
  private int animationDuration = 300;
  private String text;
  private int red;
  private int green;
  private int blue;
  private double[] alphas;

  public RevealTextView(Context context) {
    super(context);
    init(null);
  }

  public RevealTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context.getTheme().obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
  }

  public RevealTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context.getTheme().obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public RevealTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context.getTheme().obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
  }

  protected void init(TypedArray attrs) {
    try {
      animationDuration = attrs.getInteger(R.styleable.RevealTextView_duration, animationDuration);
      text = attrs.getString(R.styleable.RevealTextView_text);
    } finally {
      attrs.recycle();
    }

    alphas = new double[text.length()];
    for(int i=0; i<text.length(); i++) {
      alphas[i] = Math.random() - 1.0f;
    }

    redraw();
  }

  protected void redraw() {
    setText(text);

    if (null != text) {
      post(this);
    }
  }

  @Override
  public void run() {
    final int color = getCurrentTextColor();

    red   = Color.red(color);
    green = Color.green(color);
    blue  = Color.blue(color);

    ValueAnimator animator = ValueAnimator.ofFloat(0f, 2f);
    animator.setDuration(animationDuration);
    animator.addUpdateListener(this);
    animator.start();
  }

  protected int clamp(double value)
  {
    return (int)(255f * Math.min(Math.max(value, 0f), 1f));
  }

  @Override
  public void onAnimationUpdate(ValueAnimator valueAnimator) {
    final float value = (float) valueAnimator.getAnimatedValue();
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    for(int i=0; i<text.length(); i++) {
      builder.setSpan(new ForegroundColorSpan(Color.argb(clamp(value + alphas[i]), red, green, blue)), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    setText(builder);
  }
}
