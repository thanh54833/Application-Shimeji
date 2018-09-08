package com.example.test_commit;

/**
 * Created by ThanhHang on 8/1/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.text.TextWatcher;


public class floatingmove extends Service {

    /////////////////////////////////////////////////
    ChatBubble ChatBubble;
    private ListView listView;
    private View btnSend;
    private EditText editText;
    int myMessage = 2;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    String co = "no";
    String msg = "the gio cua thanh !";
    ProgressDialog pDialog;
    String url = "http://sandbox.api.simsimi.com/request.p?key=add03f67-0b96-4937-a6ee-5838bbbcf5bb&lc=vi&ft=1.0&text=";
    //String text="the gioi cua thanh";

    /////////////////////////////////////////////////
    AnimationDrawable progressAnimation;
    /////////////////
    private View mFloatingView;
    CountDownTimer Timer;
    CountDownTimer Timer1;

    int height = 200;
    int width = 200;
    private WindowManager mWindowManager;
    private LinearLayout touchLayout;
    WindowManager.LayoutParams params;


    //////////////////////////

    private WindowManager mWindowManager1;
    private View mFloatingView1;
    CountDownTimer Timer11;
    CountDownTimer Timer12;
    CountDownTimer Timer13;
    int height1 = 200;
    int width1 = 200;
    private LinearLayout touchLayout1;
    WindowManager.LayoutParams params1;
    ImageView mImageViewFilling;
    //////////////////////////
    private Context mContext;
    WindowManager.LayoutParams mParams;

    ///////////////////
    public floatingmove() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
/////////////////////////xoa !/////
        mContext = this;
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            int demx;
            int demy;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;
                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        Toast.makeText(getApplication(),"can :",Toast.LENGTH_SHORT).show();

                    case MotionEvent.ACTION_UP:
*/
                       /* final int[] dem = {0};
                        while(dem[0] <=800)
                        {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dem[0]++;
                                    params.y= params.y+10;
                                    Log.d("params1","x :"+params.x+"y:"+params.y);
                                    mWindowManager.updateViewLayout(mFloatingView, params);

                                }
                            }, 2000);

                        }*/
/*                        progressAnimation.stop();
                        Timer.cancel();

                        Timer = new CountDownTimer(10*(1000-params.y), 100) {
                                public void onTick(long millisUntilFinished) {

                                    if(params.y<=800)
                                    {
                                        params.y= params.y+10;
                                        Log.d("params1","x :"+params.x+"y:"+params.y);
                                        mWindowManager.updateViewLayout(mFloatingView, params);
                                    }
                                    else
                                    {
                                        Timer.cancel();
                                        Log.d("len","di chuyen len !");
                                        ///////////////
                                        Timer13 = new CountDownTimer(3000,100) {
                                            public void onTick(long millisUntilFinished) {

                                                mImageViewFilling.setBackgroundResource(R.drawable.te);
                                                progressAnimation =(AnimationDrawable) mImageViewFilling.getBackground();
                                                progressAnimation.start();
                                            }
                                            public void onFinish() {
                                                mImageViewFilling.setBackgroundResource(R.drawable.khoc);
                                                progressAnimation =(AnimationDrawable) mImageViewFilling.getBackground();
                                                progressAnimation.start();
                                            }
                                        }.start();
                                        ///////////////
                                    }
                                    Log.d("test","thanh !");

                                }
                                public void onFinish() {

                                }
                            }.start();


*/



                       /* TranslateAnimation animation = new TranslateAnimation(0.0f, 50.0f,
                                0.0f, 100.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                        animation.setDuration(5000);  // animation duration
                        animation.setRepeatCount(5);  // animation repeat count
                        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
                        //animation.setFillAfter(true);

                        mImageViewFilling.startAnimation(animation);*/


/*        case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        //Update the layout with new X & Y coordinate
                        //Timer.cancel();
                        Timer13.cancel();
                        if( params.y>800)
                        {
                        }
                        else
                        {
                            progressAnimation.stop();
                            mImageViewFilling.setBackgroundResource(R.drawable.du);
                            mWindowManager.updateViewLayout(mFloatingView, params);
                            Log.d("params","x :"+params.x+"y:"+params.y);
                        }
                        Log.d("tests","move x :"+params.x+" y:"+params.y);
                        return true;
                    }
                return false;
            }
        });
*/

        ///////////////


        /*
        touchLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams( 30
                , LinearLayout.LayoutParams.MATCH_PARENT);
        touchLayout1.setLayoutParams(lp1);
        mWindowManager1 = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams mParams1 = new WindowManager.LayoutParams(
                30, // width of layout 30 px
                WindowManager.LayoutParams.MATCH_PARENT, // height is equal to full screen
                WindowManager.LayoutParams.TYPE_PHONE, // Type Phone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // this window won't ever get key input focus
                PixelFormat.TRANSLUCENT);
        mParams1.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowManager1.addView(touchLayout1, mParams1);

        mFloatingView1 = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget1, null);
        //Add the view to the window.
        ImageView mImageViewFilling1 = (ImageView)  mFloatingView1.findViewById(R.id.filling1);

        AlphaAnimation anim1 = new AlphaAnimation(1.0f, 0.0f);
        anim1.setDuration(1000);
        anim1.setRepeatCount(1);
        anim1.setRepeatMode(Animation.REVERSE);
        //mImageViewFilling.startAnimation(anim);
        ((AnimationDrawable) mImageViewFilling1.getBackground()).start();
        final Handler mHandler1 = new Handler();
        Runnable r1 = new Runnable() {

            public void run() {
                f1();
                mHandler1.postDelayed(this, 1000);
            }
        };
        mHandler1.postDelayed(r1, 1000);
        height1= 300;
        width1=300;
        params1 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        //Specify the view position
        params1.gravity = Gravity.TOP | Gravity.LEFT;
        params1.x =400;
        params1.y = 800;
        //Add the view to the window
        mWindowManager1 = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager1.addView(mFloatingView1, params1);
        //The root element of the collapsed view layout
        //The root element of the expanded view layout
        mFloatingView1.findViewById(R.id.root_container1);
        mFloatingView1.setOnTouchListener(new View.OnTouchListener() {
            private int initialX1;
            private int initialY1;
            private float initialTouchX1;
            private float initialTouchY1;
            int demx;
            int demy;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //remember the initial position.
                        initialX1 = params1.x;
                        initialY1 = params1.y;
                        //get the touch location
                        initialTouchX1 = event.getRawX();
                        initialTouchY1 = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        Toast.makeText(getApplication(),"can :",Toast.LENGTH_SHORT).show();
                    case MotionEvent.ACTION_UP:
                       // Timer.cancel();
                        Timer11 = new CountDownTimer(10*(1000-params1.y), 100) {
                            public void onTick(long millisUntilFinished) {
                                if(params1.y<=800)
                                {
                                    params1.y=params1.y+10;
                                    Log.d("params1","x :"+params1.x+"y:"+params1.y);
                                    mWindowManager1.updateViewLayout(mFloatingView1, params1);
                                }
                                else
                                {
                                    Timer11.cancel();
                                }
                                Log.d("test","thanh !");

                            }
                            public void onFinish() {

                            }
                        }.start();
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params1.x = initialX1 + (int) (event.getRawX() - initialTouchX1);
                        params1.y = initialY1 + (int) (event.getRawY() - initialTouchY1);
                        //Update the layout with new X & Y coordinate
                        //Timer.cancel();
                        if( params1.y>800)
                        {

                        }
                        else
                        {
                            mWindowManager1.updateViewLayout(mFloatingView1, params1);
                            Log.d("params","x :"+params1.x+"y:"+params1.y);
                        }
                        Log.d("tests","move");
                        return true;
                }
                return false;
            }
        });

*/
        //////////////////////
    }
////////////////////////////////////////////////////////////////////////////
//////// floating chat //////////////////////////////////////////////////////////////////////////////////////////////////////

    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer = new CountDownTimer(100, 100) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
            }
        }.start();
        Timer13 = new CountDownTimer(100, 100) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
            }
        }.start();
/////////////////////////////
        touchLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30
                , LinearLayout.LayoutParams.MATCH_PARENT);
        touchLayout.setLayoutParams(lp);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams(
                width,//WindowManager.LayoutParams.WRAP_CONTENT,
                height,//WindowManager.LayoutParams.WRAP_CONTENT,
                //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.TYPE_PHONE,
                //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, // Not displaying keyboard on bg activity's EditText
                //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //Not work with EditText on keyboard
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = 100;
        mParams.y = 100;
        mWindowManager.addView(touchLayout, mParams);
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        mImageViewFilling = (ImageView) mFloatingView.findViewById(R.id.filling);
        mImageViewFilling.setBackgroundResource(R.drawable.khoc);
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
                f();
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(r, 1000);
        height = 300;
        width = 300;
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.TYPE_PHONE,
                //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        /*params.x =500;
        params.y = 800;*/
        params.x = 100;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        mFloatingView.findViewById(R.id.root_container);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ChatBubbles = new ArrayList<>();
        listView = (ListView) mFloatingView.findViewById(R.id.list_msg);
        btnSend = mFloatingView.findViewById(R.id.btn_chat_send);
        editText = (EditText) mFloatingView.findViewById(R.id.msg_type);

        adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);

        listView.setAdapter(adapter);

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //   tvValue.setText(edt1.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplication(), "nhap vao !...", Toast.LENGTH_SHORT).show();
                    editTextReceiveFocus();
                } else {

                    if (myMessage == 2) {
                        ChatBubble = new ChatBubble(editText.getText().toString(), myMessage);
                        ChatBubbles.add(ChatBubble);
                        adapter.notifyDataSetChanged();
                        url = "http://sandbox.api.simsimi.com/request.p?key=add03f67-0b96-4937-a6ee-5838bbbcf5bb&lc=vi&ft=1.0&text=" + editText.getText().toString();
                        editText.setText("");
                        new GetContacts().execute();


                    } else {
                        editText.setText("");
                    }

                    Toast.makeText(getApplication(),editText.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            long startTime = System.currentTimeMillis();
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (System.currentTimeMillis() - startTime <= 30) {
                    return false;
                }
                if (isViewInBounds(mFloatingView, (int) (event.getRawX()), (int) (event.getRawY()))) {
                    editTextReceiveFocus();
                } else {
                    editTextDontReceiveFocus();
                }
                 ////////////////////////////////////////////////////////////////////////


                
                ////////////////////////////////////////////////////////////////

                return false;
            }
        });
        return super.onStartCommand(intent, flags, startId);

    }
    ////open key board////////////////////////////////////////////////////////////////////////////////////////////////
   private boolean isViewInBounds(View view, int x, int y) {
        Rect outRect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }
    private void editTextReceiveFocus() {
        if (!wasInFocus) {
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            mWindowManager.updateViewLayout(mFloatingView,params);
            wasInFocus = true;
        }
    }
    private void editTextDontReceiveFocus() {
        if (wasInFocus) {
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            mWindowManager.updateViewLayout(mFloatingView, params);
            wasInFocus = false;
        }
    }
    private boolean wasInFocus = true;
    ////////////////////////////////////////////////////////////////////////////
public void f(){
    /*
    if(params.y>=780&&params1.y>=780) {


        if (params.x != params1.x) {
            if (params.x > params1.x) {

                Timer1 = new CountDownTimer(2 * (params.x - params1.x), 100) {
                    public void onTick(long millisUntilFinished) {

                        params.x = params.x - 5;
                        Log.d("dichuyen","params :"+params.x+"par1 :"+params1.x);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        Log.d("params1", "x :" + params.x + "y:" + params.y);
                    }

                    public void onFinish() {

                    }

                }.start();



            }

            else {
                Timer1 = new CountDownTimer(2 * (params1.x - params.x), 100) {
                    public void onTick(long millisUntilFinished) {
                        params.x = params.x + 5;
                        Log.d("dichuyen","params :"+params.x+"par1 :"+params1.x);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        Log.d("params1", "x :" + params.x + "y:" + params.y);
                    }

                    public void onFinish() {
                    }
                }.start();
            }
        }
    }



    */

   /* if(params.x > 500 )  {
        Timer1 = new CountDownTimer(30000, 100) {
            public void onTick(long millisUntilFinished) {

                if(params.x>=100)
                {
                    params.x= params.x-2;
                    mWindowManager.updateViewLayout(mFloatingView, params);
                    Log.d("params1","x :"+params.x+"y:"+params.y);
                }
                else
                {
                    Timer1.cancel();
                }
                Log.d("test","thanh !");
            }
            public void onFinish() {
            }
        }.start();
}*/


}
     ////////////////////////
     public void f1(){
        /* if(params1.x > 500 )  {
             Timer12 = new CountDownTimer(30000, 100) {
                 public void onTick(long millisUntilFinished) {

                     if(params1.x>=100)
                     {
                         params1.x= params1.x-2;
                         mWindowManager1.updateViewLayout(mFloatingView1, params1);
                         Log.d("params1","x :"+params1.x+"y:"+params1.y);
                     }
                     else
                     {
                         Timer12.cancel();
                     }
                     Log.d("test","thanh !");
                 }
                 public void onFinish() {
                 }
             }.start();
         }

         if(params1.x>0)
         {

         }

*/
     }
////////////////////////////////////////////////////////////////////////////////////////////////////////

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(floatingmove.this);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject contacts = new JSONObject(jsonStr);
                    msg = contacts.getString("response");
                    Log.d("phamhoaithanh",msg);

                } catch (final JSONException e) {

                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/

                }
            } else {

               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();
            msg="http://www.gioitreviet.net/wp-content/uploads/2017/07/Kim-L%C3%BD.png";
            if(msg.indexOf(".png")>=0)
            {
                Log.d("phamthanhthanh","png!");
                ChatBubble = new ChatBubble(msg,3);
                ChatBubbles.add(ChatBubble);
                adapter.notifyDataSetChanged();
            }
            else
            {
                ChatBubble = new ChatBubble(msg,1);
                ChatBubbles.add(ChatBubble);
                adapter.notifyDataSetChanged();
            }
        }
    }
       /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {

        }
    }
    private boolean isViewCollapsed() {
        //return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
        return mFloatingView1 == null || mFloatingView1.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView1 != null) mWindowManager1.removeView(mFloatingView1);
       // if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}