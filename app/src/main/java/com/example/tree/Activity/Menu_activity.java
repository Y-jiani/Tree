package com.example.tree.Activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tree.Base.BaseActivity;
import com.example.tree.Bean._User;
import com.example.tree.Fragment.Story_fragment;
import com.example.tree.Fragment.Test_fragment;
import com.example.tree.Fragment.Treehole_fragment;
import com.example.tree.R;
import com.example.tree.Utils.Constant;
import com.example.tree.Utils.PhotoPopupWindow;
import com.example.tree.Utils.ScreenUtils;
import com.example.tree.Utils.StackManager;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class Menu_activity extends BaseActivity implements View.OnClickListener{
    private static final String TAG ="Rong" ;
    private StackManager mStackManager;
    private Intent intent;
    private Context mContext = Menu_activity.this;
    String name,email,phone;
    private EditText infodialog_et_name,infodialog_et_phone,infodialog_et_email;
    List<_User> mUserInfo=new ArrayList<>();//存放bmob查询下来所有题目的链表
    private TextView menu_nav_tv_name;
    private _User userinfo,userInfo;
    RadioGroup mRgBottomMenu;
    //数组 存储Fragment
    Fragment[] mFragments;
    //当前Fragent的下标
    private int currentIndex=1;
    private Button menu_toolbar_btn_music;

    //侧拉界面
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private View dialogView;
    private NavigationView navigationView;

//    更换头像
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    private ImageView menu_nav_userIcon;
    private PhotoPopupWindow mPhotoPopupWindow;
    private Uri mImageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUser();
        btnListen();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.menu_activity_layout;
    }
    @Override
    protected void initFindViewById() {
        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);
        //toolbar标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.mine_rb_bg_1);//设置一个导航按钮图标
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //侧拉界面
        drawer_layout = findViewById(R.id.menu_drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        mRgBottomMenu = findViewById(R.id.menu_rg_bottom_menu);
        menu_toolbar_btn_music = findViewById(R.id.menu_toolbar_btn_music);
        navigationView = findViewById(R.id.nav_view);
        menu_toolbar_btn_music.setOnClickListener(this);

        View rootView = LayoutInflater.from(Menu_activity.this)
                .inflate(R.layout.menu_activity_layout, null);
        menu_nav_userIcon = rootView.findViewById(R.id.menu_nav_userIcon);

        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            menu_nav_tv_name = header.findViewById(R.id.menu_nav_tv_name);
            menu_nav_tv_name.setText(Constant.name);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initViews() {
        //将Fragment加入数组
        mFragments = new Fragment[] {
                new Test_fragment(),
                new Treehole_fragment(),
                new Story_fragment(),
        };
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //设置为默认界面 MainHomeFragment
        ft.add(R.id.menu_fl_content,mFragments[1]).commit();
        //RadioGroup选中事件监听 改变fragment
        mRgBottomMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.menu_rb_test:
                        setIndexSelected(0);
                        break;
                    case R.id.menu_ib_treehole:
                        setIndexSelected(1);
                        break;
                    case R.id.menu_rb_story:
                        setIndexSelected(2);
                }
            }
        });
        mStackManager.getStackManager().pushActivity(this);
    }

    public void btnListen(){
//        侧拉按钮监听
        nav_view.setCheckedItem(R.id.mine_testRecord_btn);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawer_layout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.mine_testRecord_btn:
                        intent = new Intent(mContext, Testrecord_activity.class);
                        startActivity(intent);
                        break;
                    case R.id.mine_chengepas_btn:
                        intent = new Intent(mContext, Changepassword_activity.class);
                        startActivity(intent);
                        break;
                    case R.id.mine_logout_btn:
                        intent = new Intent(mContext, Login_activity.class);
                        startActivity(intent);
                        mStackManager.getStackManager().popAllActivity();
                        mStackManager.getStackManager().popAllActivity();
                        break;
                    case R.id.mine_changeinfo_btn:
                        showDialog();
                        break;
                    /*case R.id.mine_changeicon_btn:
                        mPhotoPopupWindow = new PhotoPopupWindow(Menu_activity.this, new View.OnClickListener() {
                            @Override
//                            相册选择监听器
                            public void onClick(View v) {
//                                权限申请
                                if (ContextCompat.checkSelfPermission(Menu_activity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
//                                    权限还没有授予，需要在这里写申请权限的代码
                                    ActivityCompat.requestPermissions(Menu_activity.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                                } else {
//                                    如果权限已经申请过，直接进行图片选择
                                    mPhotoPopupWindow.dismiss();
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
//                                    判断系统中是否有处理该 Intent 的 Activity
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                                    } else {
                                        Toast.makeText(Menu_activity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }, new View.OnClickListener() {
                            @Override
//                            拍照监听器
                            public void onClick(View v) {
//                                权限申请
                                if (ContextCompat.checkSelfPermission(Menu_activity.this,
                                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                        || ContextCompat.checkSelfPermission(Menu_activity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
//                                    权限还没有授予，需要在这里写申请权限的代码
                                    ActivityCompat.requestPermissions(Menu_activity.this,
                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                                } else {
                                    // 权限已经申请，直接拍照
                                    mPhotoPopupWindow.dismiss();
                                    imageCapture();
                                }
                            }
                        });
                        View rootView = LayoutInflater.from(Menu_activity.this)
                                .inflate(R.layout.menu_activity_layout, null);
                        mPhotoPopupWindow.showAtLocation(rootView,
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        break;*/
                }
                return true;
            }
        });
}

//    处理回调结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
//                小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
//                大图切割
                case REQUEST_BIG_IMAGE_CUTTING:
                    /*Bitmap bitmap = BitmapFactory.decodeFile(mImageUri.getEncodedPath());
                    menu_nav_userIcon.setImageBitmap(bitmap);*/
                    break;
//                相册选取
                case REQUEST_IMAGE_GET:
                    try {
//                        startSmallPhotoZoom(data.getData());
                        startBigPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
//                拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
//                    startSmallPhotoZoom(Uri.fromFile(temp));
                    startBigPhotoZoom(temp);
            }
        }
    }


//     判断系统及拍照
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(this,
                    "com.example.tree.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

//    小图模式中，保存图片后，设置到视图中
//    将图片保存设置到视图中
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
//            创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
//                保存图片
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            在视图中显示图片
            menu_nav_userIcon.setImageBitmap(photo);
        }
    }

//    小图模式切割图片
//    此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

//    大图模式切割图片
//    直接创建一个文件将切割后的图片写入
    public void startBigPhotoZoom(File inputFile) {
//        创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }
//        开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(Menu_activity.this, inputFile), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public void startBigPhotoZoom(Uri uri) {
//        创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }
//        开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    //设置Fragment页面
    private void setIndexSelected(int index) {
        if (currentIndex == index) {
            return;
        }
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //隐藏当前Fragment
        ft.hide(mFragments[currentIndex]);
        //判断Fragment是否已经添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.menu_fl_content,mFragments[index]).show(mFragments[index]);
        }else {
            //显示新的Fragment
            ft.show(mFragments[index]);
        }
        ft.commit();
        currentIndex = index;
    }
    private void initUser() {
        BmobQuery<_User> UserInfoBmobQuery = new BmobQuery<_User>();
        UserInfoBmobQuery.setLimit(1);
        UserInfoBmobQuery.addWhereEqualTo("username",Constant.name);
        UserInfoBmobQuery.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> object, BmobException e) {
                if (e == null) {
                    mUserInfo.addAll(object);
                    userinfo = mUserInfo.get(0);
                    Constant.email = userinfo.getEmail();
                    Constant.phone = userinfo.getMobilePhoneNumber();
                } else {
                    Toast.makeText(mContext, "获取信息失败!"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showDialog() {
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.info_dialog_layout,null,false);
        final AlertDialog infoDialog = new AlertDialog.Builder(mContext).setView(dialogView).create();

        infodialog_et_name=(EditText) dialogView.findViewById(R.id.infodialog_et_name);
        infodialog_et_phone=(EditText) dialogView.findViewById(R.id.infodialog_et_phone);
        infodialog_et_email=(EditText) dialogView.findViewById(R.id.infodialog_et_email);
        Button infodialog_btn_confirm = dialogView.findViewById(R.id.infodialog_btn_confirm);
        Button infodialog_btn_cancel = dialogView.findViewById(R.id.infodialog_btn_cancel);

        Log.i(TAG,"phone:"+ Constant.phone);

        infodialog_et_name.setText(Constant.name);
        infodialog_et_phone.setText(Constant.phone);
        infodialog_et_email.setText(Constant.email);

        infodialog_btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = infodialog_et_name.getText().toString();
                email = infodialog_et_email.getText().toString();
                phone = infodialog_et_phone.getText().toString();
                userInfo = new _User();
                userInfo.setSessionToken(Constant.token);
                userInfo.setUsername(name);
                userInfo.setMobilePhoneNumber(phone);
                userInfo.setEmail(email);
                userInfo.update(Constant.id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Constant.name = userInfo.getUsername();
                            Constant.phone = userInfo.getMobilePhoneNumber();
                            Constant.email = userInfo.getEmail();
                            infodialog_et_name.setText(name);
                            infodialog_et_phone.setText(phone);
                            infodialog_et_email.setText(email);
                            if(navigationView.getHeaderCount() > 0) {
                                View header = navigationView.getHeaderView(0);
                                menu_nav_tv_name = header.findViewById(R.id.menu_nav_tv_name);
                                menu_nav_tv_name.setText(Constant.name);
                            }
                            Toast.makeText(mContext, "修改信息成功!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "修改信息失败!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

        infodialog_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        infoDialog.getWindow().setLayout((ScreenUtils.getScreenWidth(mContext) / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    //toolbar按钮的监听
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_toolbar_btn_music:
                Intent intent = new Intent(this, Music_activity.class);
                startActivity(intent);
        }
    }
}