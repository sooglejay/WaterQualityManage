
package com.gaoxian.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.gaoxian.R;

/**
 * Magnifying view.
 */
public final class MagnifyingView extends View
{
    /** Default scale. */
    private static final float DEFAULT_SCALE = 2.0f;

    /** Default circle radius, in dip. */
    private static final int DEFAULT_CIRCLE_RADIUS = 100;

    /** Default border color. */
//    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_COLOR = R.color.transparent_green_color;

    /** Default border width, in dip. */
    private static final int DEFAULT_BORDER_WIDTH = 2;

    /** Attached view. */
    private View mAttachedView;

    /** Attached view drawable bitmap. */
    private Bitmap mBitmap;

    /** Attached view canvas. */
    private Canvas mCanvas;

    /** Circle clipping path. */
    private Path mClippingPath;

    /** Circle border paint. */
    private Paint mBorderPaint;

    /** Circle center X position. */
    private int mPosX;

    /** Circle center Y position. */
    private int mPosY;

    /** Magnifying enabled status. */
    private boolean mIsMagnifyingEnabled = false;

    /** Magnifying status. */
    private boolean mIsMagnifying = false;

    /** Scale. */
    private float mScale = DEFAULT_SCALE;

    /** Circle radius, in pixels. */
    private int mCircleRadius = dpToPx(DEFAULT_CIRCLE_RADIUS);

    /** Border color. */
    private int mBorderColor = DEFAULT_BORDER_COLOR;

    /** Border width, in pixels. */
    private int mBorderWidth = dpToPx(DEFAULT_BORDER_WIDTH);

    /** Offset X value, in pixels. */
    private int mOffsetX = 0;

    /** Offset Y value, in pixels. */
    private int mOffsetY = 0;

    /**
     * Create a new {@link MagnifyingView} instance.
     * 
     * @param context
     *            the execution context.
     */
    public MagnifyingView(final Context context)
    {
        super(context);
        initialize(context);
    }

    /**
     * Create a new {@link MagnifyingView} instance.
     * 
     * @param context
     *            the execution context.
     * @param attrs
     *            the view attributes set.
     */
    public MagnifyingView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    @Override
    protected void onDraw(final Canvas canvas)
    {
        if (mIsMagnifying)
        {
            // Draw the attached view in a new canvas
            mAttachedView.draw(mCanvas);

            // Draw the circle border
            mBorderPaint.setColor(mBorderColor);
            canvas.drawCircle(mPosX + mOffsetX, mPosY + mOffsetY, mCircleRadius + mBorderWidth, mBorderPaint);

            // Apply the offsets
            canvas.translate(mOffsetX, mOffsetY);

            // Prepare the scaling
            canvas.scale(mScale, mScale, mPosX, mPosY);

            // Apply the clipping region
            canvas.clipPath(mClippingPath);

            // Draw the zoomed, clipped view
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom)
    {
        if ((mBitmap == null) && ((right - left) != 0) && ((top - bottom) != 0))
        {
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        if (mBitmap != null)
        {
            mBitmap.recycle();
            mBitmap = null;
        }

        super.onDetachedFromWindow();
    }

    /**
     * Set the circle radius.
     * 
     * @param circleRadius
     *            the circle radius in dip.
     */
    public void setCircleRadius(final int circleRadius)
    {
        if (circleRadius < 0)
        {
            mCircleRadius = dpToPx(DEFAULT_CIRCLE_RADIUS);
        }
        else
        {
            mCircleRadius = dpToPx(circleRadius);
        }
    }

    /**
     * Set the border color.
     * 
     * @param borderColor
     *            the border color.
     */
    public void setBorderColor(final int borderColor)
    {
        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
    }

    /**
     * Set the border width.
     * 
     * @param borderWidth
     *            the border width in dip.
     */
    public void setBorderWidth(final int borderWidth)
    {
        if (borderWidth < 0)
        {
            mBorderWidth = dpToPx(DEFAULT_BORDER_WIDTH);
        }
        else
        {
            mBorderWidth = dpToPx(borderWidth);
        }
    }

    /**
     * Set the magnifying scale.
     * 
     * @param scale
     *            the scale.
     */
    public void setScale(final float scale)
    {
        if (scale <= 0)
        {
            mScale = DEFAULT_SCALE;
        }
        else
        {
            mScale = scale;
        }
    }

    /**
     * Set the circle offset.
     * 
     * @param offsetX
     *            the offset X value in dip.
     * @param offsetY
     *            the offset Y value in dip.
     */
    public void setOffset(final int offsetX, final int offsetY)
    {
        mOffsetX = dpToPx(offsetX);
        mOffsetY = dpToPx(offsetY);
    }

    /**
     * Enable / disable the magnifying.
     * 
     * @param isEnabled
     *            true to enable the magnifying, false otherwise.
     */
    public void enableMagnifying(final boolean isEnabled)
    {
        mIsMagnifyingEnabled = isEnabled;

        if (mAttachedView != null)
        {
            if (mIsMagnifyingEnabled)
            {
                mAttachedView.setOnTouchListener(new OnTouchListener()
                {
                    @Override
                    public boolean onTouch(final View v, final MotionEvent event)
                    {
                        switch (event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN:
                            case MotionEvent.ACTION_MOVE:
                                magnifyRegion((int) event.getX(), (int) event.getY());
                                return true;
                            case MotionEvent.ACTION_UP:
                                stopMagnifying();
                                return true;

                            default:
                                return false;
                        }
                    }
                });
            }
            else
            {
                mAttachedView.setOnTouchListener(null);
            }
        }
    }

    /**
     * Show the magnifying circle at the provided position.
     * 
     * @param posX
     *            the circle center X position.
     * @param posY
     *            the circle center Y position.
     */
    public void magnifyRegion(final int posX, final int posY)
    {
        mIsMagnifying = true;
        mPosX = posX;
        mPosY = posY;

        // Create a clipping region corresponding to the current position
        mClippingPath.reset();
        mClippingPath.addCircle(mPosX, mPosY, mCircleRadius / mScale, Direction.CCW);

        invalidate();
    }

    /**
     * Stop magnifying.
     */
    public void stopMagnifying()
    {
        mIsMagnifying = false;
        invalidate();
    }

    /**
     * Attache the current view to the provided view.
     * 
     * @param attachedView
     *            the view to attach the current view to.
     */
    public void attach(final View attachedView)
    {
        mAttachedView = attachedView;

        if (mAttachedView != null)
        {
            ViewParent parent = mAttachedView.getParent();

            if (parent instanceof ViewGroup)
            {
                ViewGroup parentGroup = (ViewGroup) parent;
                int positionInGroup = parentGroup.indexOfChild(mAttachedView);

                FrameLayout container = new FrameLayout(mAttachedView.getContext());

                // Replace the current view with a container.
                parentGroup.removeView(mAttachedView);
                parentGroup.addView(container, positionInGroup, mAttachedView.getLayoutParams());

                container
                        .addView(mAttachedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                container.addView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                enableMagnifying(true);
            }
        }
        else
        {
            enableMagnifying(false);
        }
    }

    /**
     * Initialize the view.
     * 
     * @param context
     *            the execution context.
     */
    private void initialize(final Context context)
    {
        mClippingPath = new Path();

        mBorderPaint = new Paint();
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setAntiAlias(true);
        disableHardwareAccelerationWhenNecessary();
    }

    /**
     * Disable hardware acceleration for this view, for devices running on Android versions between
     * {@link Build.VERSION_CODES#HONEYCOMB} and {@link Build.VERSION_CODES#JELLY_BEAN_MR2} (excluded). This class uses
     * a path to clip the image, which in not supported on hardware accelerated applications on devices running these
     * Android versions.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void disableHardwareAccelerationWhenNecessary()
    {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2))
        {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * Conversion between dip and pixels.
     * 
     * @param dp
     *            the dip value to convert into pixels.
     */
    private int dpToPx(final int dp)
    {
        Resources r = getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
