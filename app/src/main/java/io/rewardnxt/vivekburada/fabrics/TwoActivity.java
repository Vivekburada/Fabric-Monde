package io.rewardnxt.vivekburada.fabrics;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TwoActivity extends AppCompatActivity {
    @BindView(R.id.whatsapp)
    ImageView whatsapp;


    private File imagePath;
    private String TAG = "FABRICS";
    private final int REQUEST_READWRITE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_two);
        ButterKnife.bind(this);
        requestLocationPermission();


    }
    @OnClick(R.id.whatsapp) public void Share(View view)
    {
        Bitmap b = takeScreenshot();
        saveBitmap(b);
        shareWhatsApp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.provider_paths.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Bitmap b = takeScreenshot();
            saveBitmap(b);
            shareIt();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        Log.e(TAG, "saveBitmap: "+imagePath.toString() );
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Log.e(TAG, "shareIt: "+imagePath.toString() );
        Uri photoURI = FileProvider.getUriForFile(TwoActivity.this,  "io.rewardnxt.vivekburada.fabrics.fileprovider", imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.setType("image/*");
        String shareBody = "Successfully demonstrated Task 2";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Fabric Monde");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private  void shareWhatsApp()
    {
        /**
         * For whatsApp Business use - com.whatsapp.w4b
         */
       Uri imgUri = FileProvider.getUriForFile(TwoActivity.this,  "io.rewardnxt.vivekburada.fabrics.fileprovider", imagePath);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Successfully demonstrated Task 2");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        whatsappIntent.setType("image/*");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {

            TwoActivity.this.startActivity(Intent.createChooser(whatsappIntent, "Share!"));

        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(this, "WhatsApp Not installed!  "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @AfterPermissionGranted(REQUEST_READWRITE_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_READWRITE_PERMISSION, perms);
        }
    }

}
