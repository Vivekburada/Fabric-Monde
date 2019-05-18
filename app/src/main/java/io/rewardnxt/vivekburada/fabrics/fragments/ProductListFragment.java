package io.rewardnxt.vivekburada.fabrics.fragments;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.rewardnxt.vivekburada.fabrics.OneActivity;
import io.rewardnxt.vivekburada.fabrics.R;
import io.rewardnxt.vivekburada.fabrics.models.Categories;
import io.rewardnxt.vivekburada.fabrics.models.Products;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.widget.Toast.*;


public class ProductListFragment extends Fragment {

    private LinearLayout linear_progressbar;


    private TextView toolBarTxt;
    private  String content;

    private RecyclerView recyclerView;
    private RecycleAdapter_AddProduct mAdapter;
    private int status_code;
    private String token,totalPriceOfProducts;

    private final int REQUEST_READWRITE_PERMISSION = 1;



    private TextView quantityOfTotalProduct,priceOfTotalProduct,next;
    private Categories categories;

    private  String t ="Share";
    private int[] IMAGES = {R.drawable.first,R.drawable.second,R.drawable.third};
    private int[] WhatsApp= {R.drawable.whatsapp,R.drawable.whatsapp,R.drawable.whatsapp};
    private String[] NamES = {"Dark Blue 100% Cotton Organic Fabric","Brown 100% Cotton Organic Fabric","Pink And Grey 100% Cotton Natural Dye Fabric"};
    private String[] Width = {"44''", "44''","44''","44''","44''","44''"};
    private String[] Composition = {"100% Org Cot", "100% Org Cot","Cotton", };
    private String[] PRICE = {"1566","50","30"};
    private String[] Code = {"ccb3or002","ccb3or016","cca1nd017"};

    private View view;

    Animation startAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list_products, container, false);
        requestLocationPermission();


        startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);


        initComponent(view);


        categories = new Categories();
        categories.productsArrayList = new ArrayList<>();


        for (int i=0; i<NamES.length ; i++){
            Products products = new Products();
            products.setName(NamES[i]);
            products.setPrice(PRICE[i]);
            products.setImage(IMAGES[i]);
            products.setComp(Composition[i]);
            products.setWhatsApp(WhatsApp[i]);
            products.setCode(Code[i]);
            Log.e("a", "onCreateView: "+Code[i] );
            categories.productsArrayList.add(products);
        }


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);




//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ProductArrayList productsForSend = new ProductArrayList();
//                productsForSend =  productsToBeSend(categoriesAndProducts);
//                PrefUtils.setProducts(productsForSend, getActivity());
//
//                if (productsForSend.getProductsArrayList().size() >0){
//                    Intent it = new Intent(getActivity(), ProductSummaryActivity.class);
//                    startActivity(it);
//                }else {
//                    Toast.makeText(getActivity(), "Please Select The Products", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });




        mAdapter = new RecycleAdapter_AddProduct(getActivity(),categories);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        return view;

    }

    private void initComponent(View view) {


    }

    public class RecycleAdapter_AddProduct extends RecyclerView.Adapter<RecycleAdapter_AddProduct.MyViewHolder> {

        Context context;
        boolean showingFirst = true;
        private Categories categories;

        int recentPos = -1;



        public class MyViewHolder extends RecyclerView.ViewHolder {




            ImageView image;
            ImageView whatsapp;
            TextView title;
            TextView price;
            TextView quantityTxt;
            TextView composition;
            TextView code;
            private LinearLayout llMinus,llPlus;
            int quantity;
            

            public MyViewHolder(View view) {
                super(view);
                code = (TextView) view.findViewById(R.id.code);
                composition = (TextView) view.findViewById(R.id.composition);
                image = (ImageView) view.findViewById(R.id.image);
                whatsapp = (ImageView) view.findViewById(R.id.share);
                title = (TextView) view.findViewById(R.id.title);
                price = (TextView) view.findViewById(R.id.price);
                quantityTxt = (TextView) view.findViewById(R.id.quantityTxt);
                llPlus = (LinearLayout)view.findViewById(R.id.llPlus);
                llMinus = (LinearLayout)view.findViewById(R.id.llMinus);
            }

        }



        public RecycleAdapter_AddProduct(Context context, Categories categories) {
            this.categories = categories;
            this.context = context;
        }

        @Override
        public RecycleAdapter_AddProduct.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_add_product, parent, false);



            return new RecycleAdapter_AddProduct.MyViewHolder(itemView);


        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final RecycleAdapter_AddProduct.MyViewHolder holder, final int position) {
//            Products movie = productsList.get(position);
          holder.image.setImageResource(categories.getProductsArrayList().get(position).getImage());
            holder.whatsapp.setImageResource(categories.getProductsArrayList().get(position).getWhatsApp());
            holder.code.setText(categories.getProductsArrayList().get(position).getCode());
            holder.title.setText(categories.getProductsArrayList().get(position).getName());
            holder.price.setText(categories.getProductsArrayList().get(position).getPrice());
            holder.composition.setText(categories.getProductsArrayList().get(position).getComp());
            holder.quantityTxt.setText(categories.getProductsArrayList().get(position).getQuantity() + "");

              holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            int totalPrice = holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice());


            if (position == recentPos) {
                Log.e("pos", "" + recentPos);
                // start animation
                holder.quantityTxt.startAnimation(startAnimation);

            } else {
                holder.quantityTxt.clearAnimation();

            }

            if (categories.getProductsArrayList().get(position).getQuantity() > 0){
                holder.quantityTxt.setVisibility(View.VISIBLE);
                holder.llMinus.setVisibility(View.VISIBLE);
            }else {
                holder.quantityTxt.setVisibility(View.GONE);
                holder.llMinus.setVisibility(View.GONE);
            }


            categories.getProductsArrayList().get(position).setPriceAsPerQuantity(""+ totalPrice);


            holder.llPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.quantity <10){


                        recentPos = position;
                        holder.quantity = holder.quantity + 1;
                        categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        categories.getProductsArrayList().get(position).setPriceAsPerQuantity(""+holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice()));

                        holder.quantityTxt.setText("" + holder.quantity);
                    }


                    notifyDataSetChanged();

                }
            });

            holder.whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    content = holder.title.getText()+"  Cost per width  44'' INR: "+holder.price.getText()+". Quantity you have selected :"+holder.quantity + ".    Click to go to the product:  www.fabricmonde.com/products/"+holder.code.getText();

                    holder.image.setDrawingCacheEnabled(true);
                    Bitmap bitmap = holder.image.getDrawingCache();
                    File cachePath = new File(Environment.getExternalStorageDirectory() + "/share.jpg");
                    try {
                        cachePath.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(cachePath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Uri imgUri = FileProvider.getUriForFile(getContext(),  "io.rewardnxt.vivekburada.fabrics.fileprovider", cachePath);
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, content);
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                    whatsappIntent.setType("image/*");
                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    try {

                        getActivity().startActivity(Intent.createChooser(whatsappIntent, "Share!"));

                    } catch (android.content.ActivityNotFoundException ex) {

                        Toast.makeText(getContext(), "WhatsApp Not installed!  "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Clicked to share!", Toast.LENGTH_SHORT).show();

                }
            });


            holder.llMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.quantity > 0 && holder.quantity <= 10){

                        recentPos = position;

                        holder.quantity = holder.quantity - 1;
                        categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        categories.getProductsArrayList().get(position).setPriceAsPerQuantity(""+holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice()));

                        holder.quantityTxt.setText("" + holder.quantity);


                    }

                    notifyDataSetChanged();

                }
            });



        }

        @Override
        public int getItemCount() {
            return categories.getProductsArrayList().size();
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
        if(EasyPermissions.hasPermissions(getContext(), perms)) {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_READWRITE_PERMISSION, perms);
        }
    }

}

