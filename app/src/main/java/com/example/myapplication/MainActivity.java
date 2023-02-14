package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button botoQR;
    private TextView textQR;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        botoQR = findViewById(R.id.buttonOpenScanner);
        textQR = findViewById(R.id.textQR);
        Intent intent = new Intent(this, ScannerActivity.class);

        botoQR.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(intent, requestCode);
                }else{
                    requestPermissionLauncher.launch(android.Manifest.permission.CAMERA);
                }
            }
        }));
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
                if(isGranted == true){
                    System.out.println("Permisos");
                }else{
                    System.out.println("Sin permisos");
                }
            });

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            textQR.setText(data.getData().toString());
        }
    }
}