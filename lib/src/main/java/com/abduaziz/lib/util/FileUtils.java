package com.abduaziz.lib.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by abduaziz on 10/15/17.
 */

public class FileUtils {

    public static File convertToFile(Context context, Bitmap bitmap) {
        //create a file to write bitmap data
        File f = new File(context.getCacheDir(), System.currentTimeMillis() + "");
        try {
            f.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return f;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getMinutes(String time) {
        long l = Long.valueOf(time);
        return l / 60000;
    }

    public static long getSeconds(String time) {
        long l = Long.valueOf(time);
        return l % 60000 / 1000;
    }

    public static String getSize(String size) {
        long l = Long.valueOf(size);
        return String.valueOf(l / (1024 * 1024));
    }

    public static String getPrettyVideoDuration(String time) {
        if (getSeconds(time) < 10)
            return getMinutes(time) + ":0" + getSeconds(time);
        else
            return getMinutes(time) + ":" + getSeconds(time);
    }

    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = ("kMGTPE").charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
