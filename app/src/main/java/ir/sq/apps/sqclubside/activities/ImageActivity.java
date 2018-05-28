package ir.sq.apps.sqclubside.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.controllers.Connection;
import ir.sq.apps.sqclubside.controllers.ImageHandler;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.controllers.UserHandler;
import ir.sq.apps.sqclubside.models.Club;
import ir.sq.apps.sqclubside.uiControllers.ConnectionUi;
import ir.sq.apps.sqclubside.uiControllers.ImagePreviewAdapter;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class ImageActivity extends AppCompatActivity implements ImagePreviewAdapter.OnItemClickListener {
    @BindView(R.id.image_preview_recyclerview)
    RecyclerView imagePreviewRecyclerview;
    @BindView(R.id.select_image_button)
    ImageView selectImageButton;
    @BindView(R.id.image_header)
    ImageView imageHeader;
    @BindView(R.id.submit_images_button)
    Button submitImagesButton;
    @BindView(R.id.image_header_holder)
    RelativeLayout imageHeaderHolder;

    private List<Bitmap> images = new ArrayList<>();
    private ImagePicker imagePicker;
    private ImagePreviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setRecylerView();
        setUpImagePicker();
        setFonts();
    }

    private void setFonts() {
        submitImagesButton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
    }

    private void setUpImagePicker() {
        ImagePicker.setMinQuality(600, 600);
    }

    private void setRecylerView() {
        adapter = new ImagePreviewAdapter(ImageActivity.this, images, this);
        imagePreviewRecyclerview.setAdapter(adapter);
        imagePreviewRecyclerview.setLayoutManager(new LinearLayoutManager(ImageActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public void onItemClick(int position) {
        imageHeader.setImageBitmap(images.get(position));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bitmap != null) {
            if (images.size() == 0) {
                imageHeaderHolder.setVisibility(View.VISIBLE);
            }
            images.add(bitmap);
            adapter.notifyDataSetChanged();
            imageHeader.setImageBitmap(bitmap);
        }
    }

    @OnClick({R.id.select_image_button, R.id.submit_images_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_image_button:
                ImagePicker.pickImage(this, "Select your image:");
                break;
            case R.id.submit_images_button:
                UserHandler.getInstance().setImages(images);
                UserHandler.getInstance().getmClub().imagesToJson();
//                if (images.size() > 0) {
//                    sendImagesToServer();
//                }
//                startActivity(new Intent(ImageActivity.this, TagsActivity.class));
                break;

        }
    }

    private void sendImagesToServer() {
        Connection sendImages = new Connection(UrlHandler.updateUserURL.toString(), UserHandler.getInstance().getmClub().imagesToJson(), "PUT", ConnectionUi.noneUi(this)) {
            @Override
            protected void onResult(String result) {
                Log.i("Result SITS", result);
            }
        };
        sendImages.execute();
    }
}
