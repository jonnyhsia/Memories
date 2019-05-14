package com.jonnyhsia.appcore.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;
import androidx.collection.LruCache;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class Drawables {

    private static LruCache<Key, Drawable> sCachedDrawable = new LruCache<>(10);
    private final int[] mColors;

    private float[] mRadii;
    private int mRadius;

    private float mGradientCenterX = 0.5f;
    private float mGradientCenterY = 0.5f;
    private float mGradientRadius = 0f;
    private int mGradientType = GradientDrawable.LINEAR_GRADIENT;

    private int mShape;
    private int mStrokeWidth;
    private int mStrokeColor;
    private GradientDrawable.Orientation mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;

    private Drawables(int shape, int solidColor) {
        mColors = new int[]{solidColor};
        mShape = shape;
    }

    private Drawables(int shape, int[] colors) {
        mColors = colors;
        mShape = shape;
    }

    public static Drawables ofRectangle(int solidColor) {
        return new Drawables(GradientDrawable.RECTANGLE, solidColor);
    }

    public static Drawables ofRectangle(int... colors) {
        return new Drawables(GradientDrawable.RECTANGLE, colors);
    }

    public static Drawables ofOval(int solidColor) {
        return new Drawables(GradientDrawable.OVAL, solidColor);
    }

    public static Drawables ofOval(int... colors) {
        return new Drawables(GradientDrawable.OVAL, colors);
    }

    public static Drawables ofLine(int solidColor) {
        return new Drawables(GradientDrawable.LINE, solidColor);
    }

    public static Drawables ofLine(int... colors) {
        return new Drawables(GradientDrawable.LINE, colors);
    }

    public static Drawables ofRing(int solidColor) {
        return new Drawables(GradientDrawable.RING, solidColor);
    }

    public static Drawables ofRing(int... colors) {
        return new Drawables(GradientDrawable.RING, colors);
    }

    public Drawables setAngle(int angle) {
        angle %= 360;
        if (angle % 45 != 0) {
            throw new IllegalArgumentException("angle 必须是 45 的整数倍");
        }

        switch (angle) {
            case 0:
                mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 45:
                mOrientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 90:
                mOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 135:
                mOrientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 180:
                mOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 225:
                mOrientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 270:
                mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 315:
                mOrientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return this;
    }

    public Drawables setCorners(int radius) {
        mRadius = radius;
        mRadii = null;
        return this;
    }

    public Drawables setCorners(int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        mRadius = 0;
        mRadii = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
        return this;
    }

    public Drawables setGradientCenter(float centerX, float centerY) {
        mGradientCenterX = centerX;
        mGradientCenterY = centerY;
        return this;
    }

    public Drawables setGradientRadius(float gradientRadius) {
        mGradientRadius = gradientRadius;
        return this;
    }

    public Drawables setGradientType(int gradientType) {
        mGradientType = gradientType;
        return this;
    }

    public Drawables setStroke(int strokeColor, int strokeWidth) {
        mStrokeColor = strokeColor;
        mStrokeWidth = strokeWidth;
        return this;
    }

    public Drawables setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        return this;
    }

    public Drawables setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    @NotNull
    public Drawable build() {
        final Key drawableKey = new Key(mColors, mRadius, mRadii, mGradientCenterX, mGradientCenterY, mGradientRadius, mShape, mStrokeColor, mStrokeWidth, mOrientation);
        final Drawable cacheDrawable = sCachedDrawable.get(drawableKey);
        if (cacheDrawable != null) {
            return cacheDrawable.mutate();
        }

        final GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(mShape);
        if (mStrokeWidth > 0) {
            drawable.setStroke(mStrokeWidth, mStrokeColor);
        }
        if (mRadius > 0) {
            drawable.setCornerRadius(mRadius);
        } else if (mRadii != null) {
            drawable.setCornerRadii(mRadii);
        }

        if (mColors.length == 1) {
            // 纯色
            drawable.setColor(mColors[0]);
        } else {
            // 渐变
            drawable.setGradientCenter(mGradientCenterX, mGradientCenterY);
            drawable.setColors(mColors);
            drawable.setGradientType(mGradientType);
            drawable.setOrientation(mOrientation);
            if (mGradientType == GradientDrawable.RADIAL_GRADIENT) {
                drawable.setGradientRadius(mGradientRadius);
            }
        }

        sCachedDrawable.put(drawableKey, drawable);

        return drawable;
    }

    public static class Key {

        private final int[] mColors;
        private final int mRadius;
        private final float mGradientCenterX;
        private final float mGradientCenterY;
        private final float mGradientRadius;
        private final int mShape;
        private final int mStrokeColor;
        private final int mStrokeWidth;
        private final GradientDrawable.Orientation mOrientation;
        private float[] mRadii;
        private int hash = 0;

        Key(int[] solidColor, int radius, float[] radii, float gradientCenterX, float gradientCenterY, float gradientRadius, int shape, int strokeColor, int strokeWidth, GradientDrawable.Orientation orientation) {
            mColors = solidColor;
            mRadius = radius;
            mRadii = radii;
            mGradientCenterX = gradientCenterX;
            mGradientCenterY = gradientCenterY;
            mGradientRadius = gradientRadius;
            mShape = shape;
            mStrokeColor = strokeColor;
            mStrokeWidth = strokeWidth;
            mOrientation = orientation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;

            Key key = (Key) o;
            return mRadius == key.mRadius &&
                    Float.compare(key.mGradientCenterX, mGradientCenterX) == 0 &&
                    Float.compare(key.mGradientCenterY, mGradientCenterY) == 0 &&
                    Float.compare(key.mGradientRadius, mGradientRadius) == 0 &&
                    mShape == key.mShape &&
                    mStrokeColor == key.mStrokeColor &&
                    mStrokeWidth == key.mStrokeWidth &&
                    Arrays.equals(mColors, key.mColors) &&
                    Arrays.equals(mRadii, key.mRadii) &&
                    mOrientation == key.mOrientation;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                int result = Objects.hash(mRadius, mGradientCenterX, mGradientCenterY, mGradientRadius, mShape, mStrokeColor, mStrokeWidth, mOrientation);
                result = 31 * result + Arrays.hashCode(mColors);
                result = 31 * result + Arrays.hashCode(mRadii);
                hash = result;
            }
            return hash;
        }
    }
}
