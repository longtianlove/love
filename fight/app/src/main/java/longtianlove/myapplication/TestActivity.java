package longtianlove.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import longtianlove.myapplication.test.TestHandlerActivity;
import longtianlove.qrcode.QRCodeAcitivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 58 on 2016/12/15.
 */

public class TestActivity  extends Activity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    View handler_text;
    View qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initListener();
    }
    void initView(){
        handler_text=this.findViewById(R.id.handler_text);
        qrcode=this.findViewById(R.id.qrcode);
    }
    void initListener(){
        handler_text.setOnClickListener(this);
        qrcode.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQrcodePermissions();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQrcodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.handler_text:
                intent.setClass(TestActivity.this, TestHandlerActivity.class);
                startActivity(intent);
                break;
            case R.id.qrcode:
                intent.setClass(TestActivity.this, QRCodeAcitivity.class);
                startActivity(intent);
        }
    }
}
