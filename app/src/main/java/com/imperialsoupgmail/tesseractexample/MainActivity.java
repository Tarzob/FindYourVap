package com.imperialsoupgmail.tesseractexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    String datapath = "";
    private TessBaseAPI mTess;
    public Bitmap image;
    final int CAMERA_CAPTURE = 1;
    final int CROP_PIC = 2;
    private Uri picUri;
    ImageButton logoButton;
    ImageView imageView;
    ImageButton goButton;

    final int PIC_CROP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)this.findViewById(R.id.imageView);
        goButton=(ImageButton) findViewById(R.id.goButton) ;
        logoButton = (ImageButton) findViewById(R.id.logoButton);

        imageView .setVisibility(View.INVISIBLE);
        goButton.setVisibility(View.INVISIBLE);


        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImage(image);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                photo=getDropShadow3(photo);
                image = photo;
                imageView.setImageBitmap(photo);
                imageView.setVisibility(View.VISIBLE);
                goButton.setVisibility(View.VISIBLE);
                logoButton.setVisibility(View.GONE);
                picUri = data.getData();
                performCrop();
            }

            else if (requestCode == CROP_PIC) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                image = extras.getParcelable("data");
                imageView.setImageBitmap(image);
                processImage(ARGBBitmap(image));
            }
        }
    }
    public void processImage(Bitmap image){

        //initialize Tesseract API
        String language = "eng";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);

         result = null;
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        image = drawable.getBitmap();
        String result = null;
        mTess.setImage(image);
        result = mTess.getUTF8Text();
        result = result.replaceAll("\\s+","");
        Log.e("mon Résultat est :",result);
        Intent i=new Intent(this, InfoActivity.class);
        i.putExtra("nameOfLiquid",result);
        startActivity(i);
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Bitmap getDropShadow3(Bitmap bitmap) {

        if (bitmap==null) return null;
        int think = 6;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int newW = w - (think);
        int newH = h - (think);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w, h, conf);
        Bitmap sbmp = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas c = new Canvas(bmp);

        // Right
        Shader rshader = new LinearGradient(newW, 0, w, 0, Color.GRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(rshader);
        c.drawRect(newW, think, w, newH, paint);

        // Bottom
        Shader bshader = new LinearGradient(0, newH, 0, h, Color.GRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(bshader);
        c.drawRect(think, newH, newW  , h, paint);

        //Corner
        Shader cchader = new LinearGradient(0, newH, 0, h, Color.LTGRAY, Color.LTGRAY, Shader.TileMode.CLAMP);
        paint.setShader(cchader);
        c.drawRect(newW, newH, w  , h, paint);


        c.drawBitmap(sbmp, 0, 0, null);

        return bmp;
    }
    private Bitmap ARGBBitmap(Bitmap img) {
        return img.copy(Bitmap.Config.ARGB_8888,true);
    }

   public void DispatcherFunction(String word){
       word=word.toUpperCase();
        int intIndex = word.indexOf("SCOOBY");
        if(intIndex == - 1){
            Intent intent = new Intent(this, NotFoundActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("nameOfLiquid","Scooby Snack");
            startActivity(intent);
        }
    }


}
