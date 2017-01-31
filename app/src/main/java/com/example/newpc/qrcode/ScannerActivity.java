package com.example.newpc.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannerActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
            cameraView=(SurfaceView)findViewById(R.id.CameraView);
        cameraView.setZOrderMediaOverlay(true);
        surfaceHolder=cameraView.getHolder();
        barcode=new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if(!barcode.isOperational())
        {
            Toast.makeText(getApplicationContext(), "Sorry can't Found detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource=new CameraSource.Builder(this,barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)

                .setRequestedFps(24)
              //  .setAutoFocesEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                        if(ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
            barcode.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                    if(barcodes.size()>0)
                    {
                        Intent intent=new Intent();
                        intent.putExtra("barcode",barcodes.valueAt(0));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });
    }
}
