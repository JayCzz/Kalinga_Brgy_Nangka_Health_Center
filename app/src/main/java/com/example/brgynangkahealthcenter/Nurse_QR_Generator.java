package com.example.brgynangkahealthcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Nurse_QR_Generator extends AppCompatActivity {

    ImageView imageView;
    EditText text_generator;
    Button btn_generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nurse_qr_generator);

        // initialize imageView, text_generator, and btn_generator using their respective ids from the XML file
        imageView = findViewById(R.id.image);
        text_generator = findViewById(R.id.gen_text);
        btn_generator = findViewById(R.id.gen_btn);

        // set the click listener for the button
        btn_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = text_generator.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) { // Check if the text is not empty
                    convertoQrCode();
                } else {
                    Toast.makeText(Nurse_QR_Generator.this, "Enter some text to generate QR code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void convertoQrCode() {
        // get the text to be converted to QR code from the EditText
        String text = text_generator.getText().toString().trim();

        try {
            // create a BitMatrix object using the QRCodeWriter class and the text to be encoded
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 512,512);

            // get the width and height of the BitMatrix
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            // create a new Bitmap object with the same dimensions as the BitMatrix
            Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);

            // loop through each pixel in the BitMatrix and set the corresponding pixel in the Bitmap object to either black or white
            for (int x=0; x<width; x++){
                for (int y=0; y<height ; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK:Color.WHITE);
                }
            }

            // set the Bitmap as the image for the ImageView
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
