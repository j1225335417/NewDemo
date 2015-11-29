package com.jikexueyuan.validatecode;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_RECEIVED_CODE = 1;

    private EditText et_ValidateCode = null;

    private SmsObserver mObserver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECEIVED_CODE) {
                String code = (String)msg.obj;
                //update the UI
                et_ValidateCode.setText(code);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
//        getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_ValidateCode = (EditText)findViewById(R.id.et_validateCode);

        mObserver = new SmsObserver(MainActivity.this, mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
