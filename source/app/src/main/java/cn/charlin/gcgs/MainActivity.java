package cn.charlin.gcgs;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Data app;
    NavigationView navigationView;
    public String ad_url="https://gitee.com/CharlinChen/gcg_switch";
    //联网检查更新线程
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            gethandleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //错误日志
        CrashHandler.getInstance().init(getApplicationContext());
        //申请读写限权
        requestPower();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //全局变量
        app = (Data)getApplication();
        app.setQSG_allow(false);
        //检查SDK版本
        app.setLog("Android SDK："+android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT > 29) {
            app.addLog("提示，此Android系统高于Android 11，第三方应用程序已无法读取其他APP的数据文件，请在Android 10及以下的系统中使用本程序！");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Android 11 不兼容提示：");
            builder.setMessage("您使用的手机系统版本高于Android 11！\nGoogle沙盒模式对APP进行了读写限制，对于Android 11及以上的版本，应用程序已经无法访问其他APP的数据。\n请在Android 10及以下的系统中使用本程序。");
            builder.setNegativeButton("我知道了",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Toast.makeText(getApplicationContext(), "知道了就好，现在你可以卸载了……", Toast.LENGTH_LONG).show();

                }
            });
            builder.create().show();
        }
        //检查更新
        app.setApp_ver("23");
        getSourceCode("https://gitee.com/CharlinChen/gcg_switch/raw/master/version.txt");
        //原有操作
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, app.getLog(), Snackbar.LENGTH_SHORT)
                        .setAction("显示完整LOG", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("运行日志")
                                        .setMessage(app.getLog())
                                        .setPositiveButton("确定", null)
                                        .show();
                            }
                            }).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                ChangeIcon();
                //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerLayout = navigationView.getHeaderView(0);
        //AD按钮监听器
        TextView t_ad = (TextView)headerLayout.findViewById(R.id.AD_textView);
        t_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onADClick(view);
            }
        });
        t_ad.getPaint().setFlags( Paint.UNDERLINE_TEXT_FLAG );//下划线
        t_ad.getPaint().setAntiAlias(true);//抗锯齿

        //随机头像
        ChangeIcon();

        //头像按钮监听器
        ImageView img=headerLayout.findViewById(R.id.ICON_imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeIcon();onADClick(view);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("您未授予程序读写权限，程序将无法写入补丁，请给予授权！");
                builder.setNeutralButton("好的",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
                        Toast.makeText(getApplicationContext(), "请重启APP", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                builder.create().show();
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("申请读写权限")
                        .setMessage("请授予读写权限，然后重启APP。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
                                Toast.makeText(getApplicationContext(), "重启APP", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .show();
            }
        }
    }

    public void gethandleMessage(Message msg) {
        String content = (String) msg.obj;
        String[] urlnews = content.split("\n");
        if (!urlnews[0].equals(app.getApp_ver())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("发现新版本！");
            builder.setMessage("当前版本已过时，请前往B站专栏或码云查看最新版本！\n请选择更新方式：");
            builder.setPositiveButton("BiliBili", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Uri uri = Uri.parse("https://www.bilibili.com/read/cv5165668");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("码云", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Uri uri = Uri.parse("https://gitee.com/CharlinChen/gcg_switch");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNeutralButton("不更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Toast.makeText(getApplicationContext(), "过于陈旧的版本容易产生未知错误", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        }
        View headerLayout = navigationView.getHeaderView(0);
        TextView t_ad = (TextView) headerLayout.findViewById(R.id.AD_textView);
        t_ad.setText(urlnews[1]);
        ad_url = urlnews[2];
        if (urlnews[3].equals("=======")) app.setQSG_allow(true);
        else app.setQSG_allow(false);
        //app.addLog(String.valueOf(app.getQSG_allow()));
        String path_charlin="/storage/emulated/0/Android/data/cn.charlin.gcgs/files/";
        String png_zh = getFileMD5(path_charlin+"event_act001.png");
        if (!png_zh.equals(urlnews[4])) {
            deleteSingleFile(path_charlin+"event_act001.png");
            downloadFile("https://gitee.com/CharlinChen/gcg_switch/raw/master/png/event_act001.png", "event_act001.png");
        }
        String png_jp = getFileMD5(path_charlin+"event_act002.png");
        if (!png_jp.equals(urlnews[5])) {
            deleteSingleFile(path_charlin+"event_act002.png");
            downloadFile("https://gitee.com/CharlinChen/gcg_switch/raw/master/png/event_act002.png", "event_act002.png");
        }
    }

    public void getSourceCode(final String urlpath) {
        new Thread(){
            public void run(){
                try {
                    //String path = urlCentent.getText().toString().trim();
                    URL url = new URL(urlpath);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    int responseCode = httpURLConnection.getResponseCode();
                    if(responseCode == 200){
                        InputStream is = httpURLConnection.getInputStream();
                        String content = readStream(is);
                        Message msg = new Message();
                        msg.obj = content;
                        handler.sendMessage(msg);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public String readStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return byteArrayOutputStream.toString();
    }

    public void onADClick(View view){
        app.setAD_Pass(true);
        if(isAvilible(this,"tv.danmaku.bili")){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(ad_url);
            intent.setData(uri);
            intent.setClassName("tv.danmaku.bili","tv.danmaku.bili.ui.intent.IntentHandlerActivity");
            startActivity(intent);}
        else{
            Toast.makeText(this,"未安装BiliBili客户端，用网页打开",Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse(ad_url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private boolean isAvilible(Context context, String packageName){
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if(pinfo != null){
            for(int i = 0; i < pinfo.size(); i++){
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    public void ChangeIcon(){
        View headerLayout = navigationView.getHeaderView(0);
        ImageView img=headerLayout.findViewById(R.id.ICON_imageView);
        Random r = new Random();
        int random = r.nextInt(12);
        switch (random){
            case 0:
                img.setImageResource(R.drawable.icon_01);
                break;
            case 2:
                img.setImageResource(R.drawable.icon_02);
                break;
            case 3:
                img.setImageResource(R.drawable.icon_03);
                break;
            case 4:
                img.setImageResource(R.drawable.icon_04);
                break;
            case 5:
                img.setImageResource(R.drawable.icon_05);
                break;
            case 6:
                img.setImageResource(R.drawable.icon_06);
                break;
            case 7:
                img.setImageResource(R.drawable.icon_07);
                break;
            case 8:
                img.setImageResource(R.drawable.icon_08);
                break;
            case 9:
                img.setImageResource(R.drawable.icon_09);
                break;
            case 10:
                img.setImageResource(R.drawable.icon_10);
                break;
            case 11:
                img.setImageResource(R.drawable.icon_11);
                break;
        }
    }

    private void downloadFile(String url,String fileneme){
        //下载路径，如果路径无效了，可换成你的下载路径
        //url = "http://c.qijingonline.com/test.mkv";
        //创建下载任务,downloadUrl就是下载链接
        app.addLog("下载文件");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalFilesDir(this,"/",fileneme);
        //request.setDestinationInExternalPublicDir("", url.substring(url.lastIndexOf("/") + 1));
        //获取下载管理器
        DownloadManager downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static String getFileMD5(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return "000";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
