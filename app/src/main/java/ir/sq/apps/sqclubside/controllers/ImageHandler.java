package ir.sq.apps.sqclubside.controllers;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mohammad on 5/28/2018.
 */

public class ImageHandler {

    public static File getImageFile(Context context, Bitmap bitmap) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("temp", ".png", outputDir);
        FileOutputStream fOut = new FileOutputStream(outputFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 95, fOut);
        fOut.flush();
        fOut.close();
        return outputFile;
    }
}
