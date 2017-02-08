package com.dang.agi.alerttheft.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login2);
        Typeface xpressive = Typeface.createFromAsset(getAssets(), "fonts/XpressiveBold.ttf");
        addControls();
        password = new ArrayList<>();

        addEvents();

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
                Toast.makeText(Login.this, "chuyen", Toast.LENGTH_SHORT).show();
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
