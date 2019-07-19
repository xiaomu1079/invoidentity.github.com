package com.example.invo.plantidentification.ui.plant;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.invo.plantidentification.BuildConfig;
import com.example.invo.plantidentification.R;
import com.example.invo.plantidentification.ui.util.BitmapToByte;
import com.example.invo.plantidentification.ui.util.BitmapUtil;
import com.example.invo.plantidentification.ui.util.Classify;
import com.example.invo.plantidentification.ui.util.Constants;
import com.example.invo.plantidentification.ui.util.DataUtil;
import com.example.invo.plantidentification.ui.util.LineProgress;
import com.example.invo.plantidentification.ui.util.PhotoProcess;
import com.example.invo.plantidentification.ui.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlantFragment extends Fragment {

    private PlantViewModel mViewModel;
    private ImageView mImage, mChooseLocalImage, mCapture;
    private View line;
    private String imagePath = null;
    private Bitmap mBitmap = null;//待处理的位图，默认为空
    private JSONObject json;
    private static final int ERROR = 5;

    private LineProgress lineHandler = new LineProgress();
    private BitmapUtil mBu = new BitmapUtil();
    private DataUtil mDu = new DataUtil();
    private PhotoProcess photoProcess = new PhotoProcess();
    private BitmapToByte bitmapToByte = new BitmapToByte();
    private Classify classify = new Classify();

    public static PlantFragment newInstance() {
        return new PlantFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plant_fragment, container, false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        mImage = view.findViewById(R.id.initImage_1);
        mImage.setImageResource(R.drawable.wallpaper);
        line = view.findViewById(R.id.progress_line_1);
        mChooseLocalImage = view.findViewById(R.id.local_image_1);
        mChooseLocalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhoto();
            }
        });

        mCapture = view.findViewById(R.id.capture_image_1);
        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });

        final ImageView mUpload = view.findViewById(R.id.upload_1);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPhoto();
            }
        });
        return view;
    }

   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlantViewModel.class);
        // TODO: Use the ViewModel
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/

    private void getPhoto() {
        //读取本地照片
        Intent chooseImage = new Intent(Intent.ACTION_PICK);
        chooseImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (chooseImage.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(chooseImage, Constants.ACTION_CHOOSE_IMAGE);
        } else {
            ToastUtil.showToast(getActivity(), "访问图库失败！");
        }
    }

    private void capturePhoto() {
        //拍摄照片
        Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "photo_invo.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (capture.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(capture, Constants.REQUEST_CODE_TAKE_PICTURE);
        } else {
            ToastUtil.showToast(getActivity(), "没有读取到拍摄图片！");
        }
    }

    private void processPhoto() {
        if (mBitmap == null) {
            ToastUtil.showToast(getActivity(), "没有位图！");
            return;
        } else {
            lineHandler.lineProgress(mImage, line);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    classify.detected();
                    try {
                        int what = services();
                        Message message = Message.obtain();
                        message.what = what;
                        message.obj = json;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message message = Message.obtain();
                        message.what = ERROR;
                        message.obj = e;
                        mHandler.sendMessage(message);
                    }
                }
            }).start();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lineHandler.getLineAnimation().cancel();
            line.setVisibility(View.INVISIBLE);
            try {

                JSONObject jsonObject1 = (JSONObject) msg.obj;
                JSONArray jsonArray1 = new JSONArray(jsonObject1.optString("result"));
                String name1 = jsonArray1.optJSONObject(0).optString("name");
                String score1 = jsonArray1.optJSONObject(0).optString("score");
                String[] mitems1 = {"名称：" + name1, "可能性：" + score1};
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(getActivity());
                alertDialog1.setTitle("植物识别报告").setItems(mitems1, null).create().show();
            } catch (JSONException e) {
                e.printStackTrace();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("识别报告").setMessage("图片无法解析").create().show();
            }
        }
    };

    protected int services() {
        classify.detected();
        if (imagePath == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.id.initImage_1);
            json = classify.getClient().plantDetect(bitmapToByte.getArrays(), new HashMap<String, String>());
            return 1;
        } else {
            json = classify.getClient().plantDetect(bitmapToByte.getArrays(), new HashMap<String, String>());
            return 1;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //log对象
        if (BuildConfig.DEBUG) Log.d("MainActivity", "requestCode:" + requestCode);
        if (BuildConfig.DEBUG) Log.d("MainActivity", "resultCode:" + resultCode);
        if (BuildConfig.DEBUG) Log.d("MainActivity", "data:" + data);

        switch (requestCode) {
            case 1:
                if (requestCode == Constants.ACTION_CHOOSE_IMAGE) {
                    if (data == null) {
                        ToastUtil.showToast(getActivity(), "没有选中内容！");
                        return;
                    } else {
                        Uri uri = data.getData();
                        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToNext();
                        imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                        cursor.close();
                    }
                }
                break;
            case 2:
                if (requestCode == Constants.REQUEST_CODE_TAKE_PICTURE) {
                    if (data == null) {
                        ToastUtil.showToast(getActivity(), "没有拍摄内容！");
                        return;
                    } else {
                        if (data.hasExtra("data")) {
                            mDu.setData(data);
                            mBitmap = (Bitmap) mDu.getData().getExtras().get("data");
                        } else {
                            ToastUtil.showToast(getActivity(), "没有捕捉到二次图像");
                        }
                    }
                }
                break;
        }

        if (requestCode == Constants.ACTION_CHOOSE_IMAGE || requestCode == Constants.REQUEST_CODE_TAKE_PICTURE) {
            //设置bitmap到imageView上
            if (requestCode == Constants.ACTION_CHOOSE_IMAGE) {
                photoProcess.handle_photo(mBitmap, imagePath);
                mBitmap = photoProcess.getBitmap();
                bitmapToByte.bitmapToByte(mBitmap);
                mBitmap = bitmapToByte.getBitmap();
            } else if (requestCode == Constants.REQUEST_CODE_TAKE_PICTURE) {
                bitmapToByte.bitmapToByte(mBitmap);
                mBitmap = bitmapToByte.getBitmap();
            }
            mImage.setImageBitmap(mBitmap);
            mBu.setBitmap(mBitmap);
        }
    }
}
