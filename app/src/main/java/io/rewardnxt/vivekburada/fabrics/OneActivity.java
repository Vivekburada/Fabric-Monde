package io.rewardnxt.vivekburada.fabrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class OneActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;

    private final int REQUEST_READWRITE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_one);
        ButterKnife.bind(this);
        requestLocationPermission();
    }
    @OnClick(R.id.button) public void Share(View view)
    {
        View content = findViewById(R.id.whatsapp);
        content.setDrawingCacheEnabled(true);
        Bitmap bitmap = content.getDrawingCache();
      File cachePath = new File(Environment.getExternalStorageDirectory() + "/amidala.jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        Uri photoURI = FileProvider.getUriForFile(OneActivity.this,  "io.rewardnxt.vivekburada.fabrics.fileprovider", cachePath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, photoURI);
        startActivity(Intent.createChooser(share,"Share via"));
         */

        Uri imgUri = FileProvider.getUriForFile(OneActivity.this,  "io.rewardnxt.vivekburada.fabrics.fileprovider", cachePath);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        whatsappIntent.setType("image/*");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {

            OneActivity.this.startActivity(Intent.createChooser(whatsappIntent, "Share!"));

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
