[![](https://jitpack.io/v/AbduazizKayumov/FilePicker.svg)](https://jitpack.io/#AbduazizKayumov/FilePicker)

# FilePicker
Light and easy to implement file picker library for Android.

![alt text](https://github.com/AbduazizKayumov/FilePicker/blob/master/art/lib.gif)

## Setup
**Step 1.** Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2.** Add the dependency
```
	dependencies {
	        compile 'com.github.AbduazizKayumov:FilePicker:v1.0.1'
	}
```
## Usage
1. Create a new file picker.
2. Implement OnFilesSelected interface
3. Call show().

```java
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
```

IMPORTANT: In Android Marshmallow or higher, make sure READ_EXTERNAL_STORAGE permission is granted to your app.

To check for permissions, try below code in your activity:
```java
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
```
Have a look at sample code in /app folder for more.

[License](/LICENCE)
