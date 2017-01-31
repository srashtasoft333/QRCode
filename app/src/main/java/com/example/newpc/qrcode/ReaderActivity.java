package com.example.newpc.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;


public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        resultView=(TextView)findViewById(R.id.Scanneresult);
        final Activity activity = this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();*/
                Intent scan=new Intent(ReaderActivity.this,ScannerActivity.class);
                startActivityForResult(scan,REQUEST_CODE);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE && resultCode==RESULT_OK){
            if(data != null){
               // Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                final Barcode barcode=data.getParcelableExtra("barcode");
                resultView.post(new Runnable() {
                    @Override
                    public void run() {
                        resultView.setText(barcode.displayValue);
                    }
                });
            }

        }

    }
}
