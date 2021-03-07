package com.example.confroid_project.confroidUtils;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    public void saveToFile(String content, Context ctx) {

        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput("content.xml", Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            Toast.makeText(ctx, "Saved to " + ctx.getFilesDir() + "/content.xml",
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
