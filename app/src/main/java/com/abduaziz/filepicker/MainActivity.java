package com.abduaziz.filepicker;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.abduaziz.lib.FilePicker;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "FilePicker";

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonnFilePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });

    }

    void openFilePicker() {
        FilePicker filePicker = new FilePicker();
        filePicker.addOnFilesSelected(new FilePicker.OnFilesSelected() {
            @Override
            public void onFilesSelected(List<File> selectedFiles) {
                for (int i = 0; i < selectedFiles.size(); i++) {
                    Log.d(TAG, "onFilesSelected: filePath = "+selectedFiles.get(i).getAbsolutePath());
                }
            }
        });
        filePicker.show(getSupportFragmentManager(),"FilePicker");
    }
}
