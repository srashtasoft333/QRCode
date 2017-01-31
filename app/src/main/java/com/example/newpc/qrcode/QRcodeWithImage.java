package com.example.newpc.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

import static android.graphics.Color.BLACK;

public class QRcodeWithImage extends AppCompatActivity {
    EditText textedit;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_with_image2);

        textedit=(EditText)findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        Hashtable hints = null;
        String encoding = guessAppropriateEncoding(textedit.getText().toString());
        if (encoding != null) {
            hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
          BitMatrix result = writer.encode(textedit.getText().toString(), BarcodeFormat.QR_CODE, 200,200, hints);
            int width = result.getWidth();
            int height = result.getHeight();
             int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {

                    if (result.get(x, y) == true) {
                        pixels[offset + x] = BLACK;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            Bitmap myLogo = BitmapFactory.decodeResource(getResources(), R.drawable.google_logo);
            Bitmap   bitMerged = mergeBitmaps(bitmap,myLogo);
            image.setImageBitmap(bitMerged);
        } catch (WriterException e) {
            e.printStackTrace();
        }





    }

    private Bitmap mergeBitmaps(Bitmap qrCode, Bitmap mysymbol) {


        Bitmap bmOverlay = Bitmap.createBitmap(qrCode.getWidth(), qrCode.getHeight(), qrCode.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(mysymbol, (qrCode.getWidth() - mysymbol.getWidth()) / 2, (qrCode.getHeight() - mysymbol.getHeight()) / 2, null);
        canvas.drawBitmap(qrCode, new Matrix(), null);
        return bmOverlay;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
}
