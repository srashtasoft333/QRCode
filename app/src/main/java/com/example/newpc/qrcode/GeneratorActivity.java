package com.example.newpc.qrcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GeneratorActivity extends AppCompatActivity {
    EditText text;
    Button gen_btn;
    ImageView image;
    String text2Qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        text = (EditText) findViewById(R.id.text);
        gen_btn = (Button) findViewById(R.id.gen_btn);
        image = (ImageView) findViewById(R.id.image);
     //   Bitmap bitmap = null;

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2Qr = text.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                  //  Bitmap ImageBitmap = Bitmap.createBitmap(180, 40, Bitmap.Config.ARGB_8888);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                 //   Bitmap myLogo = BitmapFactory.decodeResource(getResources(), R.drawable.google_logo);

                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
              //    Bitmap  bitMerged = mergeBitmaps(bitmap,myLogo);
                    for (int i = 0; i < 200; i++) {//width
                        for (int j = 0; j < 200; j++) {//height
                            bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.MAGENTA: Color.WHITE);
                        }
                    }

                    image.setImageBitmap(bitmap);

                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
    }
  /*  public static Bitmap mergeBitmaps(Bitmap qrCode, Bitmap myLogo) {
        Bitmap bmOverlay = Bitmap.createBitmap(qrCode.getWidth(), qrCode.getHeight(), qrCode.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(myLogo, (qrCode.getWidth() - myLogo.getWidth()) / 2, (qrCode.getHeight() - myLogo.getHeight()) / 2, null);
        canvas.drawBitmap(qrCode, new Matrix(), null);
        return bmOverlay;
    }*/
}
