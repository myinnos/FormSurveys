package in.myinnos.surveylib.activity;

/**
 * Created by myinnos on 20/02/18.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import in.myinnos.surveylib.R;
import me.pqpo.smartcropperlib.view.CropImageView;

public class CropActivity extends AppCompatActivity {

    private static final String EXTRA_FROM_ALBUM = "extra_from_album";
    private static final String EXTRA_CROPPED_FILE = "extra_cropped_file";
    private static final int REQUEST_CODE_TAKE_PHOTO = 400;
    private static final int REQUEST_CODE_SELECT_ALBUM = 300;

    CropImageView ivCrop;
    Button btnCancel;
    Button btnOk;

    boolean mFromAlbum;
    File mCroppedFile;

    File tempFile;

    public static Intent getJumpIntent(Context context, boolean fromAlbum, File croppedFile) {
        Intent intent = new Intent(context, CropActivity.class);
        intent.putExtra(EXTRA_FROM_ALBUM, fromAlbum);
        intent.putExtra(EXTRA_CROPPED_FILE, croppedFile);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_crop);
        ivCrop = (CropImageView) findViewById(R.id.iv_crop);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (ivCrop.canRightCrop()) {
                    Bitmap crop = ivCrop.crop();
                    if (crop != null) {
                        saveImage(crop, mCroppedFile);
                        setResult(RESULT_OK);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                } else {
                    //Toast.makeText(CropActivity.this, "cannot crop correctly", Toast.LENGTH_SHORT).show();

                    Alerter.create(CropActivity.this)
                            .setTitle("cannot crop correctly")
                            //.setText("Message Cannot be empty!")
                            .setDuration(4000)
                            .setIcon(R.drawable.alerter_ic_face)
                            .setIconColorFilter(getResources().getColor(R.color.white))
                            .enableProgress(true)
                            .enableSwipeToDismiss()
                            .setProgressColorRes(R.color.colorPrimaryDark)
                            .setBackgroundColorRes(R.color.red)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Alerter.hide();
                                }
                            })
                            .show();
                }
            }
        });
        mFromAlbum = getIntent().getBooleanExtra(EXTRA_FROM_ALBUM, true);
        mCroppedFile = (File) getIntent().getSerializableExtra(EXTRA_CROPPED_FILE);
        if (mCroppedFile == null) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        tempFile = new File(getExternalFilesDir("img"), "temp.jpg");

        selectPhoto();
    }

    private void selectPhoto() {
        if (mFromAlbum) {
            Intent selectIntent = new Intent(Intent.ACTION_PICK);
            selectIntent.setType("image/*");
            if (selectIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(selectIntent, REQUEST_CODE_SELECT_ALBUM);
            }
        } else {
            Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(),
                    "com.rubanbridge.associate.provider", tempFile));

            if (startCameraIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(startCameraIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        Bitmap selectedBitmap = null;
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && tempFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(tempFile.getPath(), options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = calculateSampleSize(options);
            selectedBitmap = BitmapFactory.decodeFile(tempFile.getPath(), options);
        } else if (requestCode == REQUEST_CODE_SELECT_ALBUM && data != null && data.getData() != null) {
            ContentResolver cr = getContentResolver();
            Uri bmpUri = data.getData();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(cr.openInputStream(bmpUri), new Rect(), options);
                options.inJustDecodeBounds = false;
                options.inSampleSize = calculateSampleSize(options);
                selectedBitmap = BitmapFactory.decodeStream(cr.openInputStream(bmpUri), new Rect(), options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (selectedBitmap != null) {
            ivCrop.setImageToCrop(selectedBitmap);
        }
    }


    private void saveImage(Bitmap bitmap, File saveFile) {
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateSampleSize(BitmapFactory.Options options) {
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int sampleSize = 1;
        int destHeight = 1000;
        int destWidth = 1000;
        if (outHeight > destHeight || outWidth > destHeight) {
            if (outHeight > outWidth) {
                sampleSize = outHeight / destHeight;
            } else {
                sampleSize = outWidth / destWidth;
            }
        }
        if (sampleSize < 1) {
            sampleSize = 1;
        }
        return sampleSize;
    }
}