package com.abduaziz.filepicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.abduaziz.lib.FilePicker;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "FilePicker";

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonnFilePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndOpenFilePicker();
            }
        });

        textView = (TextView) findViewById(R.id.textView);

    }

    public static final int FILE_PICKER_CODE = 25;

    //check and open file explorer
    void checkAndOpenFilePicker() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FILE_PICKER_CODE);
            return;
        } else {
            // Android version < 6.0 or the permission is already granted.
            FilePicker filePicker = new FilePicker();
            filePicker.addOnFilesSelected(new FilePicker.OnFilesSelected() {
                @Override
                public void onFilesSelected(List<File> selectedFiles) {

                    //print names of selected files
                    for (int i = 0; i < selectedFiles.size(); i++) {
                        Log.d(TAG, "onFilesSelected: file = " + selectedFiles.get(i).getName());
                    }

                }
            });
            filePicker.show(getSupportFragmentManager(), "FilePicker");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == FILE_PICKER_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                checkAndOpenFilePicker();
            } else {
                Toast.makeText(this, "Unless you grant permission, we cannot pick files from storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
