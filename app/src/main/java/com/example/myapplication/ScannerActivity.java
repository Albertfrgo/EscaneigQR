package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
public class ScannerActivity extends AppCompatActivity {
    private CodeScanner codiEscaner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_activity);
        CodeScannerView vistaEscaner = findViewById(R.id.ScannerView);
        codiEscaner = new CodeScanner(this, vistaEscaner);
        codiEscaner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(result.getText()));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });

        vistaEscaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codiEscaner.startPreview();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        codiEscaner.startPreview();
    }

    @Override
    protected void onPause(){
        codiEscaner.releaseResources();
        super.onPause();
    }

}