package com.dang.agi.alerttheft.view;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dang.agi.alerttheft.MainActivity;
import com.dang.agi.alerttheft.R;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnExit;
    ImageButton ibtncancle, ibtnForgotPassword;
    ImageView img1, img2, img3, img4;
    public static final int KEY_CLEAR = -1;
    public static final int KEY_FORGOT = -2;
    ArrayList<Integer> password;
    Intent intent;
    private KeyguardManager.KeyguardLock lock;
    private PowerManager powerManager;
    private PowerManager.WakeLock wake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //makeFullScreen();
        setContentView(R.layout.activity_login2);
        addControls();
        password = new ArrayList<>();
        intent = getIntent();
        addEvents();

    }
    /*public void makeFullScreen(){
        lock = ((KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock(KEYGUARD_SERVICE);
        powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
        wake = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");

        lock.disableKeyguard();
        wake.acquire();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
              .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void addEvents() {
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        ibtnForgotPassword.setOnClickListener(this);
        ibtncancle.setOnClickListener(this);
    }

    private void addControls() {
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn3 = (Button) findViewById(R.id.button3);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btnExit = (Button) findViewById(R.id.button0);
        ibtncancle = (ImageButton) findViewById(R.id.iBtnbackspace);
        ibtnForgotPassword = (ImageButton) findViewById(R.id.ibtnForgotPassword);
        img1 = (ImageView) findViewById(R.id.imageview_circle1);
        img2 = (ImageView) findViewById(R.id.imageview_circle2);
        img3 = (ImageView) findViewById(R.id.imageview_circle3);
        img4 = (ImageView) findViewById(R.id.imageview_circle4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0: {
                new KeyPressAsynTask().execute(0);
            }
            break;
            case R.id.button1: {
                new KeyPressAsynTask().execute(1);
            }
            break;
            case R.id.button2: {
                new KeyPressAsynTask().execute(2);
            }
            break;
            case R.id.button3: {
                new KeyPressAsynTask().execute(3);
            }
            break;
            case R.id.button4: {
                new KeyPressAsynTask().execute(4);
            }
            break;
            case R.id.button5: {
                new KeyPressAsynTask().execute(5);
            }
            break;
            case R.id.button6: {
                new KeyPressAsynTask().execute(6);
            }
            break;
            case R.id.button7: {
                new KeyPressAsynTask().execute(7);
            }
            break;
            case R.id.button8: {
                new KeyPressAsynTask().execute(8);
            }
            break;
            case R.id.button9: {
                new KeyPressAsynTask().execute(9);
            }
            break;
            case R.id.iBtnbackspace: {
                new KeyPressAsynTask().execute(-1);
            }
            break;
            case R.id.ibtnForgotPassword: {
                new KeyPressAsynTask().execute(-2);
            }
            break;
            default:
                break;
        }
    }

    class KeyPressAsynTask extends AsyncTask<Integer, ArrayList<Integer>, ArrayList<Integer>> {


        @Override
        protected void onProgressUpdate(ArrayList<Integer>... values) {
            super.onProgressUpdate(values);
            ArrayList value = values[0];
            int size = value.size();
            int lastIndex = size-1;
            int lastNumber = (int) value.get(lastIndex);
            if (lastNumber == -2) {
                value.remove(lastIndex);
            }else if (lastNumber==-1){
                value.remove(value.size()-1);
                if (value.size()!=0){
                    value.remove(value.size()-1);
                    int pIndex = (4 - value.size());
                    if (pIndex <=4)
                        setUnpassword(pIndex);
                }
            }else{
               if (size<=4)
                   setImagePassword(value);
            }

        }
        public void setImagePassword(ArrayList<Integer>  arr){
            String password = "";
           ImageView[] imgs = new ImageView[]{img1, img2, img3, img4};
            for (int i = 0; i < arr.size(); i++) {
                imgs[i].setBackgroundResource(R.drawable.circle2);
                password += arr.get(i);
            }
            if (arr.size() == 4) {
                clearPassword();
                if (Integer.parseInt(password)==1111) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        public void setUnpassword(int size){
           ImageView[] imgs = new ImageView[]{img4, img3, img2, img1};
            for (int i =0; i <size; i ++) {
                imgs[i].setBackgroundResource(R.drawable.circle);
            }
        }
        public void clearPassword(){
            ImageView[] imgs = new ImageView[]{img4, img3, img2, img1};
            imgs[0].setBackgroundResource(R.drawable.circle);
            imgs[1].setBackgroundResource(R.drawable.circle);
            imgs[2].setBackgroundResource(R.drawable.circle);
            imgs[3].setBackgroundResource(R.drawable.circle);
        }
        @Override
        protected ArrayList<Integer> doInBackground(Integer... integers) {
            int key = integers[0];
            password.add(key);
            publishProgress(password);
            return password;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> s) {
            super.onPostExecute(s);
        }
    }

}
