package com.jikexueyuan.mpermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.txt_getcamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCamera();
            }
        });
    }

    private void getCamera(){
        if(Build.VERSION.CODENAME.equals("MNC")){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                opCamera();
            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
            }
        }else{
            opCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"已获取权限",Toast.LENGTH_SHORT).show();
            opCamera();
        }else {
            Toast.makeText(MainActivity.this,"您拒绝了我",Toast.LENGTH_SHORT).show();
        }
    }

    private  void opCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1009);
    }



}
