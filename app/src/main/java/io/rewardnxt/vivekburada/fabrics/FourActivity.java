package io.rewardnxt.vivekburada.fabrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import io.rewardnxt.vivekburada.fabrics.fragments.ProductListFragment;

public class FourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_four);
        SetupFragment();
    }
    private void SetupFragment() {

        Fragment fragment = new ProductListFragment();


//       pitch fragment

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, fragment);
            ft.commit();
        }



    }
}
