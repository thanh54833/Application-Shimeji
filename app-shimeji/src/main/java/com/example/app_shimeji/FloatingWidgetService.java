package com.example.app_shimeji;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by sonu on 28/03/17.
 */

public class FloatingWidgetService extends Service implements SensorEventListener {
    private WindowManager mWindowManager;
    private View mFloatingWidgetView, collapsedView, expandedView;

    private ImageView remove_image_view;

    private Point szWindow = new Point();
    private View removeFloatingWidgetView;

    private int widthScreen = 0;
    private int heightScreen = 0;

    private int withFloating = 0;
    private int heightFloating = 0;

    private int x_init_cord, y_init_cord, x_init_margin, y_init_margin;

    //Variable to check if the Floating widget view is on left side or in right side
    // initially we are displaying Floating widget view to Left side so set it to true
    private boolean isLeft = true;

    private AnimationDrawable progressAnimation;

    //add ...
    private WindowManager.LayoutParams layoutParams;


    private SensorManager sensorManager;
    Sensor acceleremoter;

    private int movingSenser;
    int intergerMax = Integer.MAX_VALUE;

    private String RECORD="TOUCH";

    //Get Floating widget view params
    public FloatingWidgetService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //init WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        getWindowManagerDefaultDisplay();

        //Init LayoutInflater
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        addRemoveView(inflater);

        addFloatingWidgetView(inflater);

        implementTouchListenerToFloatingWidgetView();

        widthScreen = getScreenWidth();
        heightScreen = getScreenHeight();

        Log.d("getScreenWidth", "getScreenWidth :" + widthScreen + "  -  getScreenHeight :" + heightScreen);

        //add ...

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acceleremoter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acceleremoter, SensorManager.SENSOR_DELAY_NORMAL);
        // add layout ...
        layoutParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

        init();

    }

    CountDownTimer movingBotTop = new CountDownTimer(intergerMax, 50) {
        public void onTick(final long millisUntilFinished) {

            int width = getScreenWidth();
            int height = getScreenHeight();


            //layoutParams.x=200;
            layoutParams.y -= 1;
            if (layoutParams.y <= 0)
            {
                movingBotTop.cancel();
            }

            mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);

        }

        public void onFinish() {
        }
    };


    private int record_Status=0;

    public void init() {

        final Handler mHandler = new Handler();

            Runnable r = new Runnable() {
                public void run() {
                    mHandler.postDelayed(this, 1000);

                    if(layoutParams.y<=0){

                        recoderLocation="TOP";
                        record_Status=0;
                        movingBotTop.cancel();

                        Log.d("TOP","TOP");

                    }
                    else {

                        recoderLocation="BOTTOM";
                        Log.d("TOP","BOTTOM");
                    }

                    //movoing bot - top...
                    Log.d("layoutParams.x","layoutParams.x :"+layoutParams.y);

                    if(layoutParams.x==0&&layoutParams.y>0) {

                        Log.d("action","bot - top ");

                        // add ...
                        RECORD="NOTOUCH";

                        Log.d("handler", "layoutParams.x :" + layoutParams.x + " - layoutParams.y :" + layoutParams.y + " - full :");
                        mImageViewFilling.clearAnimation();


                        mImageViewFilling.setBackgroundResource(R.drawable.bobtl);

                        //margin left image ...
                        FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                        layoutParamsImage.setMargins(-62, 1, 1, 1);
                        mImageViewFilling.setLayoutParams(layoutParamsImage);

                        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                        progressAnimation.start();

                        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        anim.setRepeatCount(1);
                        anim.setRepeatMode(Animation.REVERSE);
                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

                        final Handler mHandler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                mHandler.postDelayed(this, 1000);
                            }
                        };
                        mHandler.postDelayed(r, 1000);
                        // add
                        if (layoutParams.y >=0) {

                            if (record_Status == 0) {
                                record_Status = 1;

                                //layoutParams.x-=200;
                                movingBotTop.cancel();
                                movingBotTop.start();
                            }

                        } else {
                            movingBotTop.cancel();
                            //mImageViewFilling.clearAnimation();
                        }
                        //Log.d("status","status :"+record_Status);
                        mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                    }
                    // moving bot - top ...
                    else if((layoutParams.x+MAX_WIDTH_IMAGE)>=getScreenWidth()&&layoutParams.y>0)
                    {

                        Log.d("action","bot - top ");

                        if (record_Status == 0) {
                            record_Status = 1;

                            RECORD = "NOTOUCH";
                            layoutParams.x = getScreenWidth() - MAX_WIDTH_IMAGE + 62;//getScreenWidth()-MAX_WIDTH_IMAGE;

                            Log.d("handler", "layoutParams.x 1:" + layoutParams.x + " - layoutParams.y :" + layoutParams.y + " - full :");
                            mImageViewFilling.clearAnimation();

                            mImageViewFilling.setBackgroundResource(R.drawable.bobtr);

                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, 1, -62, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                            Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                            progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                            progressAnimation.start();

                            AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                            anim.setDuration(1000);
                            anim.setRepeatCount(1);
                            anim.setRepeatMode(Animation.REVERSE);
                            ((AnimationDrawable) mImageViewFilling.getBackground()).start();

                            final Handler mHandler = new Handler();
                            Runnable r = new Runnable() {
                                public void run() {
                                    mHandler.postDelayed(this, 1000);
                                }
                            };

                            mHandler.postDelayed(r, 1000);

                            // add
                            if (layoutParams.y >= 0) {

                                //layoutParams.x=100;
                                movingBotTop.cancel();
                                movingBotTop.start();
                                Log.d("start123456", "start +++ ");

                            } else {
                                movingBotTop.cancel();
                                //mImageViewFilling.clearAnimation();
                            }

                            //Log.d("status","status :"+record_Status);
                            mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);

                        }

                    }
                    // Top to left , right ...
                    else  if(layoutParams.y<=0&&layoutParams.x<=0||layoutParams.y<=0&&(layoutParams.x+MAX_WIDTH_IMAGE)>=getScreenWidth())//(layoutParams.x+MAX_WIDTH_IMAGE)>=getScreenWidth())
                    {
                        Log.d("action","Top to left - right ");

                        if(record_Status==0) {

                            record_Status=1;

                            movingBotTop.cancel();

                            //record_Status=1;

                            RECORD = "TOUCH";

                            timer.cancel();
                            //layoutParams.y=0;
                            Log.d("handler", "layoutParams.x 1:" + layoutParams.x + " - layoutParams.y :" + layoutParams.y + " - full :");
                            mImageViewFilling.clearAnimation();

                            mImageViewFilling.setBackgroundResource(R.drawable.botrl);
                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, -45, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                            Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                            progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                            progressAnimation.start();

                            AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                            anim.setDuration(1000);
                            anim.setRepeatCount(1);
                            anim.setRepeatMode(Animation.REVERSE);

                            ((AnimationDrawable) mImageViewFilling.getBackground()).start();

                            final Handler mHandler = new Handler();
                            Runnable r = new Runnable() {
                                public void run() {
                                    mHandler.postDelayed(this, 1000);
                                }
                            };

                            mHandler.postDelayed(r, 1000);
                            mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);


                        }
                    }

                    else {

                        record_Status = 0;
                        movingBotTop.cancel();

                        RECORD="TOUCH";

                    }
                    //

                    Log.d("log","record_Status :"+record_Status+" - RECORD :"+RECORD+" - layoutParams.x :"+layoutParams.x+" - layoutParams.y :"+layoutParams.y);


                }
            };
            mHandler.postDelayed(r, 1000);


    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /*  Add Remove View to Window Manager  */
    private View addRemoveView(LayoutInflater inflater) {
        //Inflate the removing view layout we created
        removeFloatingWidgetView = inflater.inflate(R.layout.remove_floating_widget_layout, null);

        //Add the view to the window.
        WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        paramRemove.gravity = Gravity.BOTTOM | Gravity.LEFT;

        //Initially the Removing widget view is not visible, so set visibility to GONE
        removeFloatingWidgetView.setVisibility(View.GONE);
        remove_image_view = (ImageView) removeFloatingWidgetView.findViewById(R.id.remove_img);

        //Add the view to the window
        mWindowManager.addView(removeFloatingWidgetView, paramRemove);



        return remove_image_view;
    }

    private int MAX_WIDTH_IMAGE = 150;
    private int MAX_HEIGHT_IMAGE = 150;

    private ImageView mImageViewFilling;

    /*  Add Floating Widget View to Window Manager  */
    private void addFloatingWidgetView(LayoutInflater inflater) {
        //Inflate the floating view layout we created
        mFloatingWidgetView = inflater.inflate(R.layout.floating_widget_layout, null);

        layoutParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

        //add ...
        mImageViewFilling = (ImageView) mFloatingWidgetView.findViewById(R.id.collapsed_iv);
        mImageViewFilling.clearAnimation();

        mImageViewFilling.setBackgroundResource(R.drawable.botrl);

        //margin left image ...
        FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
        layoutParamsImage.setMargins(1, 1, 1, 1);
        mImageViewFilling.setLayoutParams(layoutParamsImage);


        mImageViewFilling.getLayoutParams().width = MAX_WIDTH_IMAGE;
        mImageViewFilling.getLayoutParams().height = MAX_HEIGHT_IMAGE;

        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
        progressAnimation.start();

        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(1000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(Animation.REVERSE);
        //mImageViewFilling.startAnimation(anim);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
        final Handler mHandler = new Handler();
        Runnable r = new Runnable() {

            public void run() {

                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(r, 1000);


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;

        //Initially view will be added to top-left corner, you change x-y coordinates according to your need
        params.x = 100;
        params.y = 100;

        //Add the view to the window
        mWindowManager.addView(mFloatingWidgetView, params);

        //find id of collapsed view layout
        collapsedView = mFloatingWidgetView.findViewById(R.id.collapse_view);

    }

    private void getWindowManagerDefaultDisplay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            mWindowManager.getDefaultDisplay().getSize(szWindow);
        else {
            int w = mWindowManager.getDefaultDisplay().getWidth();
            int h = mWindowManager.getDefaultDisplay().getHeight();
            szWindow.set(w, h);
        }
    }


    CountDownTimer timer = new CountDownTimer(intergerMax, 10) {
        public void onTick(final long millisUntilFinished) {

            int width = getScreenWidth();
            int height = getScreenHeight();
            //layoutParams.x =0;
            //layoutParams.y +=(int)(intergerMax-millisUntilFinished);
            layoutParams.y += 10;
            Log.d("time-time", "time :" + layoutParams.y + " width :" + width + " height :" + height);

            if (layoutParams.y > height - MAX_HEIGHT_IMAGE - 100) {
                timer.cancel();
                //add ...
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                        mImageViewFilling.clearAnimation();


                        mImageViewFilling.setBackgroundResource(R.drawable.te);
                        //margin left image ...
                        FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                        layoutParamsImage.setMargins(1, 1, 1, 1);
                        mImageViewFilling.setLayoutParams(layoutParamsImage);



                        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                        progressAnimation.start();

                        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        anim.setRepeatCount(1);
                        anim.setRepeatMode(Animation.REVERSE);
                        //mImageViewFilling.startAnimation(anim);
                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

                        final Handler mHandler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                mHandler.postDelayed(this, 1000);
                            }
                        };
                        mHandler.postDelayed(r, 1000);
                        Log.d("resule", "resule +" + millisUntilFinished);

                    }

                    @Override
                    public void onFinish() {

                        mImageViewFilling.clearAnimation();
                        mImageViewFilling.setBackgroundResource(R.drawable.khoc);
                        //margin left image ...
                        FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                        layoutParamsImage.setMargins(1, 1, 1, 1);
                        mImageViewFilling.setLayoutParams(layoutParamsImage);


                        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                        progressAnimation.start();

                        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        anim.setRepeatCount(1);
                        anim.setRepeatMode(Animation.REVERSE);
                        //mImageViewFilling.startAnimation(anim);
                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
                        final Handler mHandler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                mHandler.postDelayed(this, 1000);
                            }
                        };
                        mHandler.postDelayed(r, 1000);

                    }
                }.start();

            }
            mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
        }

        public void onFinish() {
        }
    };


    CountDownTimer timerAuto = new CountDownTimer(intergerMax, 10) {
        public void onTick(final long millisUntilFinished) {

            int width = getScreenWidth();
            int height = getScreenHeight();
            //layoutParams.x =0;
            //layoutParams.y +=(int)(intergerMax-millisUntilFinished);
            layoutParams.y += 10;
            Log.d("time-time", "time :" + layoutParams.y + " width :" + width + " height :" + height);

            mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);

        }

        public void onFinish() {
        }
    };

    /*  Implement Touch Listener to Floating Widget Root View  */
    private void implementTouchListenerToFloatingWidgetView() {


           //Drag and move floating view using user's touch action.
            mFloatingWidgetView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            long time_start = 0, time_end = 0;
            boolean isLongClick = false;//variable to judge if user click long press
            boolean inBounded = false;//variable to judge if floating view is bounded to remove view
            int remove_img_width = 0, remove_img_height = 0;

            Handler handler_longClick = new Handler();
            Runnable runnable_longClick = new Runnable() {
                @Override
                public void run() {
                    //On Floating Widget Long Click

                    //Set isLongClick as true
                    isLongClick = true;

                    //Set remove widget view visibility to VISIBLE
                    removeFloatingWidgetView.setVisibility(View.VISIBLE);

                    onFloatingWidgetLongClick();
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                //get the touch location coordinates
                int x_cord = (int) event.getRawX();
                int y_cord = (int) event.getRawY();
                int x_cord_Destination, y_cord_Destination;

                //add ...
                RECORD="NOTOUCH";

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        time_start = System.currentTimeMillis();
                        handler_longClick.postDelayed(runnable_longClick, 600);

                        remove_img_width = remove_image_view.getLayoutParams().width;
                        remove_img_height = remove_image_view.getLayoutParams().height;

                        x_init_cord = x_cord;
                        y_init_cord = y_cord;
                        //remember the initial position.
                        x_init_margin = layoutParams.x;
                        y_init_margin = layoutParams.y;

                        timer.cancel();

                        return true;

                    case MotionEvent.ACTION_UP:

                        isLongClick = false;
                        removeFloatingWidgetView.setVisibility(View.GONE);
                        remove_image_view.getLayoutParams().height = remove_img_height;
                        remove_image_view.getLayoutParams().width = remove_img_width;
                        handler_longClick.removeCallbacks(runnable_longClick);
                        //If user drag and drop the floating widget view into remove view then stop the service
                        if (inBounded) {
                            stopSelf();
                            inBounded = false;
                            break;
                        }

                        Log.d("remove_img_height", "remove_img_height :" + layoutParams.y);
                        Log.d("remove_img_width", "remove_img_width :" + layoutParams.y);

                        //if ((layoutParams.y + MAX_HEIGHT_IMAGE) < (heightScreen - getStatusBarHeight() - 100)) {
                        if (true)//layoutParams.y>=0)
                        {

                            timer.cancel();
                            timer.start();

                            mImageViewFilling.clearAnimation();
                            mImageViewFilling.setBackgroundResource(R.drawable.shime4);
                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, 1, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                        }

                        return true;

                    case MotionEvent.ACTION_MOVE:

                        int x_diff_move = x_cord - x_init_cord;
                        int y_diff_move = y_cord - y_init_cord;

                        x_cord_Destination = x_init_margin + x_diff_move;
                        y_cord_Destination = y_init_margin + y_diff_move;

                        //If user long click the floating view, update remove view
                        if (isLongClick) {

                            int x_bound_left = szWindow.x / 2 - (int) (remove_img_width * 1.5);
                            int x_bound_right = szWindow.x / 2 + (int) (remove_img_width * 1.5);
                            int y_bound_top = (int) (remove_img_height * 1.5);
                            // int y_bound_top = szWindow.y - (int) (remove_img_height * 1.5);
                            Log.d("bound", "x_cord :" + x_cord + " -  x_bound_left :" + x_bound_left + " - x_bound_right :" + x_bound_right + " - y_cord :" + y_cord + " - y_bound_top :" + y_bound_top);

                            //If Floating view comes under Remove View update Window Manager
                            if ((x_cord >= x_bound_left && x_cord <= x_bound_right) && y_cord <= y_bound_top) {
                                inBounded = true;
                                int x_cord_remove = (int) ((szWindow.x - (remove_img_height * 1.5)) / 2);
                                int y_cord_remove = (int) (szWindow.y - ((remove_img_width * 1.5) + getStatusBarHeight()));

                                if (remove_image_view.getLayoutParams().height == remove_img_height) {
                                    remove_image_view.getLayoutParams().height = (int) (remove_img_height * 1.5);
                                    remove_image_view.getLayoutParams().width = (int) (remove_img_width * 1.5);

                                    WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeFloatingWidgetView.getLayoutParams();
                                    param_remove.x = x_cord_remove;
                                    param_remove.y = y_cord_remove;

                                    mWindowManager.updateViewLayout(removeFloatingWidgetView, param_remove);
                                }
                                layoutParams.x = x_cord_remove + (Math.abs(removeFloatingWidgetView.getWidth() - mFloatingWidgetView.getWidth())) / 2;
                                layoutParams.y = y_cord_remove + (Math.abs(removeFloatingWidgetView.getHeight() - mFloatingWidgetView.getHeight())) / 2;

                                //Update the layout with new X & Y coordinate
                                mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                                break;
                            }

                            if ((layoutParams.y + MAX_HEIGHT_IMAGE) < (heightScreen - getStatusBarHeight() - 100)) {

                                mImageViewFilling.clearAnimation();

                                mImageViewFilling.setBackgroundResource(R.drawable.shime4);
                                //margin left image ...
                                FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                                layoutParamsImage.setMargins(1, 1, 1, 1);
                                mImageViewFilling.setLayoutParams(layoutParamsImage);

                            }
                            if (x_cord_Destination >= 0 && x_cord_Destination <= widthScreen - 200 && y_cord_Destination <= heightScreen - 200) {
                                layoutParams.x = x_cord_Destination;
                                layoutParams.y = y_cord_Destination;
                            }


                        }
                        Log.d("layoutParams", " layoutParams.x  :" + layoutParams.x + " - layoutParams.y :" + layoutParams.y);
                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                        return true;

                }
                return false;




            }
        });
    }
    /*  on Floating Widget Long Click, increase the size of remove view as it look like taking focus */
    private void onFloatingWidgetLongClick() {
        //Get remove Floating view params
        WindowManager.LayoutParams removeParams = (WindowManager.LayoutParams) removeFloatingWidgetView.getLayoutParams();

        //get x and y coordinates of remove view
        int x_cord = (szWindow.x - removeFloatingWidgetView.getWidth()) / 2;
        int y_cord = szWindow.y - (removeFloatingWidgetView.getHeight() + getStatusBarHeight());


        removeParams.x = x_cord;
        removeParams.y = y_cord;

        //Update Remove view params
        mWindowManager.updateViewLayout(removeFloatingWidgetView, removeParams);
    }
    /*  Reset position of Floating Widget view on dragging  */
    private void resetPosition(int x_cord_now) {
        if (x_cord_now <= szWindow.x / 2) {
            isLeft = true;
            moveToLeft(x_cord_now);
        } else {
            isLeft = false;
            moveToRight(x_cord_now);
        }
    }
    private int getStatusBarHeight2() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /*  Method to move the Floating widget view to Left  */
    private void moveToLeft(final int current_x_cord) {
        final int x = szWindow.x - current_x_cord;

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = 0 - (int) (current_x_cord * current_x_cord * step);

                //If you want bounce effect uncomment below line and comment above line
                // mParams.x = 0 - (int) (double) bounceValue(step, x);

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }

            public void onFinish() {
                mParams.x = 0;

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }
        }.start();
    }

    /*  Method to move the Floating widget view to Right  */
    private void moveToRight(final int current_x_cord) {

        new CountDownTimer(500, 5) {
            //get params of Floating Widget view
            WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

            public void onTick(long t) {
                long step = (500 - t) / 5;

                mParams.x = (int) (szWindow.x + (current_x_cord * current_x_cord * step) - mFloatingWidgetView.getWidth());

                //If you want bounce effect uncomment below line and comment above line
                //  mParams.x = szWindow.x + (int) (double) bounceValue(step, x_cord_now) - mFloatingWidgetView.getWidth();

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }

            public void onFinish() {
                mParams.x = szWindow.x - mFloatingWidgetView.getWidth();

                //Update window manager for Floating Widget
                mWindowManager.updateViewLayout(mFloatingWidgetView, mParams);
            }
        }.start();
    }


    /*  Detect if the floating view is collapsed or expanded */
    private boolean isViewCollapsed() {
        return mFloatingWidgetView == null || mFloatingWidgetView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }


    /*  return status bar height on basis of device display metrics  */
    private int getStatusBarHeight() {
        return (int) Math.ceil(25 * getApplicationContext().getResources().getDisplayMetrics().density);
    }


    /*  Update Floating Widget view coordinates on Configuration change  */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        getWindowManagerDefaultDisplay();

        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mFloatingWidgetView.getLayoutParams();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (layoutParams.y + (mFloatingWidgetView.getHeight() + getStatusBarHeight()) > szWindow.y) {
                layoutParams.y = szWindow.y - (mFloatingWidgetView.getHeight() + getStatusBarHeight());
                mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
            }

            if (layoutParams.x != 0 && layoutParams.x < szWindow.x) {
                resetPosition(szWindow.x);
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (layoutParams.x > szWindow.x) {
                resetPosition(szWindow.x);
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*  on destroy remove both view from window manager */
        if (mFloatingWidgetView != null)
            mWindowManager.removeView(mFloatingWidgetView);

        if (removeFloatingWidgetView != null)
            mWindowManager.removeView(removeFloatingWidgetView);
    }


    private int recordSensor;
    private int cut=5;
    private int cord=3;
    private int time=6;

    private String recoderLocation="";

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(RECORD=="TOUCH") {

            movingSenser = (int) sensorEvent.values[0];
            if (movingSenser > 0) {


                if (movingSenser > cord) {
                    recordSensor += 1;
                    if (recordSensor > time) {
                        if (recordSensor > 15) {
                            recordSensor = 15;
                        }
                        Log.d("log", "---- left :" + movingSenser + " - " + recordSensor);

                        mImageViewFilling.clearAnimation();

                        if(recoderLocation.equals("TOP"))
                        {
                            mImageViewFilling.setBackgroundResource(R.drawable.botrl);

                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, -45, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                        }
                        else {

                            mImageViewFilling.setBackgroundResource(R.drawable.boleft);

                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, 1, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                        }



                        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                        progressAnimation.start();

                        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        anim.setRepeatCount(1);
                        anim.setRepeatMode(Animation.REVERSE);

                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
                        final Handler mHandler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                mHandler.postDelayed(this, 1000);
                            }
                        };
                        mHandler.postDelayed(r, 1000);

                        int width = getScreenWidth();
                        int height = getScreenHeight();

                        layoutParams.x -= cut;

                        Log.d("sensor", "time :" + layoutParams.x + " width :" + width + " height :" + height);
                        if (layoutParams.x <= 0) {

                            layoutParams.x = 0;
                        }
                        mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);
                    }
                } else {
                    recordSensor = 0;
                }



            } else {
                if (Math.abs(movingSenser) > cord) {


                    recordSensor += 1;
                    if (recordSensor > time) {
                        if (recordSensor > 15) {
                            recordSensor = 15;
                        }
                        Log.d("log", "---- right :" + movingSenser + " - " + recordSensor);

                        mImageViewFilling.clearAnimation();


                        if(recoderLocation.equals("TOP"))
                        {
                            mImageViewFilling.setBackgroundResource(R.drawable.botlr);

                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, -45, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);
                        }
                        else {
                            mImageViewFilling.setBackgroundResource(R.drawable.boright);

                            //margin left image ...
                            FrameLayout.LayoutParams layoutParamsImage = (FrameLayout.LayoutParams) mImageViewFilling.getLayoutParams();
                            layoutParamsImage.setMargins(1, 1, 1, 1);
                            mImageViewFilling.setLayoutParams(layoutParamsImage);

                        }



                        Log.d("getMax", "getMax :" + mImageViewFilling.getMaxWidth() + " - " + mImageViewFilling.getMaxHeight() + " - " + getStatusBarHeight2());

                        progressAnimation = (AnimationDrawable) mImageViewFilling.getBackground();
                        progressAnimation.start();

                        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                        anim.setDuration(1000);
                        anim.setRepeatCount(1);
                        anim.setRepeatMode(Animation.REVERSE);
                        //mImageViewFilling.startAnimation(anim);
                        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
                        final Handler mHandler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                mHandler.postDelayed(this, 1000);
                            }
                        };
                        mHandler.postDelayed(r, 1000);


                        int width = getScreenWidth();
                        int height = getScreenHeight();

                        layoutParams.x += cut;
                        Log.d("sensor", "time :" + layoutParams.x + " width :" + width + " height :" + height);

                        if (layoutParams.x >= (width - MAX_HEIGHT_IMAGE)) {

                            layoutParams.x = width - MAX_HEIGHT_IMAGE;

                        }

                        mWindowManager.updateViewLayout(mFloatingWidgetView, layoutParams);




                    }
                } else {
                    recordSensor = 0;
                }
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
