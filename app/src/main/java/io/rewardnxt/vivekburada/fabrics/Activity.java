package io.rewardnxt.vivekburada.fabrics;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.widget.Toast.LENGTH_SHORT;

public class Activity extends AppCompatActivity {

    @BindView(R.id.one) Button one;
    @BindView(R.id.two) Button two;
    @BindView(R.id.three) Button three;
    @BindView(R.id.four) Button four;



    private Intent I;
    private String TAG = "FABRICS-ACTIVITY";
    private final int REQUEST_READWRITE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        requestLocationPermission();
    }
    @OnClick({ R.id.one, R.id.two, R.id.three,R.id.four})
    public void onItemClicked(View view) {

        switch (view.getId()) {
            case R.id.one:

                Toast.makeText(this, "ONE", LENGTH_SHORT).show();
                I = new Intent(Activity.this, OneActivity.class);
                Activity.this.startActivity(I);
                break;

            case R.id.two:

                Toast.makeText(this, "TWO", LENGTH_SHORT).show();
                I = new Intent(Activity.this, TwoActivity.class);
                Activity.this.startActivity(I);
                break;

            case R.id.three:

                Toast.makeText(this, "THREE", LENGTH_SHORT).show();
                I = new Intent(Activity.this, ThreeActivity.class);
                Activity.this.startActivity(I);
                break;

            case R.id.four:

                Toast.makeText(this, "FOUR", LENGTH_SHORT).show();
                I = new Intent(Activity.this, FourActivity.class);
                Activity.this.startActivity(I);
                break;
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
