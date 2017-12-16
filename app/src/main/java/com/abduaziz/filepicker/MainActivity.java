package com.abduaziz.filepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                openFilePicker();
            }
        });

        textView = (TextView) findViewById(R.id.textView);

    }

    void openFilePicker() {
        FilePicker filePicker = new FilePicker();
        filePicker.addOnFilesSelected(new FilePicker.OnFilesSelected() {
            @Override
            public void onFilesSelected(List<File> selectedFiles) {
                String files = "Selected files: \n";
                for (int i = 0; i < selectedFiles.size(); i++) {
                    files += selectedFiles.get(i).getName() + "\n";
                }
                textView.setText(files);
            }
        });
        filePicker.show(getSupportFragmentManager(), "FilePicker");
    }
}
