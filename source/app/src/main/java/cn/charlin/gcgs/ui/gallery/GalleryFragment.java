package cn.charlin.gcgs.ui.gallery;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.charlin.gcgs.Data;
import cn.charlin.gcgs.MainActivity;
import cn.charlin.gcgs.R;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private ImageView title_img;
    private TextView tx_hh_sm,tx_hhq;
    private Switch sw_hh;
    private TextView tx_qsg_sm,tx_qsgq;
    private Switch sw_qsg;
    private LinearLayout ly_qsg;
    private TextView tx_xiaoqi_sm,tx_xiaoqiq;
    private Switch sw_xiaoqi;
    private TextView tx_wuxia_sm,tx_wuxiaq;
    private Switch sw_wuxia;
    private TextView tx_ailin_sm,tx_ailinq;
    private Switch sw_ailin;
    private TextView tx_lida_sm,tx_lidaq;
    private Switch sw_lida;
    private LinearLayout ly_lida;
    private TextView tx_imgload5_sm,tx_imgload5q;
    private Switch sw_imgload5;

    View view;
    Data app;
    String path_rf,path_charlin;
    private long downloadId;
    private DownloadManager downloadManager;

    String lang_md5_trn="";
    String lang_md5_jp="";
    String l2d01_102_md5="3f220f18da8e92e7d2315bb28d1c4bd4";
    String l2d01_102_md5_mod="dc2668acaa020d93648e43b725734db7";
    String l2d01_09_md5="03727f4bc35f53fda3ef131fe09e5a83";
    String l2d01_09_md5_mod="52d91453d803c3c98a8b13e0edc2942e";
    String l2d04_11_md5="6407c8988b5bbab80e74b8127423e35a";
    String l2d04_11_md5_mod="f1c6c54fdd92fa9a15087516110ff799";
    String loadimg5_md5="cc990d56c74753435a4c4d034bac89b9";
    String loadimg5_md5_mod="0b8a8ee783c65ac6599b86fdf879b536";
    String l2d8001_07_md5_rf="b3707f5728306cffe7d522d51534db44";
    String l2d8001_07bg_md5_rf="f57c5ef1960d94b2359306703602c111";
    String l2d8001_07_md5_rfmod="aeea0a4ee25ed43ef743b7615e300dd0";
    String l2d8001_07bg_md5_rfmod="ffb65844170c929bf0dbe79545eecd2c";
    String SGgmask1="bfcdba2906b6c9818ea8256d4e2ffbf6";
    String SGgmask2="d6c3cf6e43bbc2da5155e15d9a1bcbaf";

    //联网检查更新线程
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            gethandleMessage(msg);
        }
    };
    //定时器
    final Handler mHandler = new Handler();
    Runnable r_download = new Runnable() {
        @Override
        public void run() {
            queryDonloadsize();
            //每隔1s循环执行run方法
            mHandler.postDelayed(this, 1000);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        app = (Data)getActivity().getApplication();
        //变量初始化
        path_rf="/storage/emulated/0/Android/data/com.seasun.gcg2.gp.jp/files/";
        path_charlin="/storage/emulated/0/Android/data/cn.charlin.gcgs/files/";
        //获取控件ID
        title_img = view.findViewById(R.id.ICON_imageView);
        tx_hh_sm = view.findViewById(R.id.hhshuoming_textView);
        tx_hhq = view.findViewById(R.id.rfhanhua_txq);
        sw_hh = view.findViewById(R.id.hh_switch);
        tx_qsg_sm = view.findViewById(R.id.qsgshuoming_textView);
        tx_qsgq = view.findViewById(R.id.gfqsguang_txq);
        sw_qsg = view.findViewById(R.id.qsg_switch);
        ly_qsg = view.findViewById(R.id.ly_qsg);
        tx_xiaoqi_sm = view.findViewById(R.id.rfxiaoqi_textView);
        tx_xiaoqiq = view.findViewById(R.id.rfxiaoqi_txq);
        sw_xiaoqi = view.findViewById(R.id.rfxiaoqi_switch);
        tx_wuxia_sm = view.findViewById(R.id.rfwuxia_textView);
        tx_wuxiaq = view.findViewById(R.id.rfwuxia_txq);
        sw_wuxia = view.findViewById(R.id.rfwuxia_switch);
        tx_ailin_sm = view.findViewById(R.id.gf_ailin_textView);
        tx_ailinq = view.findViewById(R.id.gf_ailin_txq);
        sw_ailin = view.findViewById(R.id.gf_ailin_switch);
        tx_lida_sm = view.findViewById(R.id.gf_lida_textView);
        tx_lidaq = view.findViewById(R.id.gf_lida_txq);
        sw_lida = view.findViewById(R.id.gf_lida_switch);
        ly_lida = view.findViewById(R.id.ly_lida);
        tx_imgload5_sm = view.findViewById(R.id.rf_loadimg5_textView);
        tx_imgload5q = view.findViewById(R.id.rf_loadimg5_txq);
        sw_imgload5 = view.findViewById(R.id.rf_loadimg5_switch);
        //界面初始化
        UI_Init();
        //检查lang.u的更新
        getLangFileInfo("https://gitee.com/CharlinChen/gcg_switch/raw/master/lang.txt");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tx_hh_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHhSMClick();
            }
        });
        tx_hhq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHhQClick();
            }
        });
        sw_hh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHhSwitchClick();
            }
        });

        tx_qsg_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQsgSMClick();
            }
        });
        tx_qsgq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQsgQClick();
            }
        });
        sw_qsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQsgSwitchClick();
            }
        });

        tx_xiaoqi_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onXiaoqiSMClick();
            }
        });
        tx_xiaoqiq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onXiaoqiQClick();
            }
        });
        sw_xiaoqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onXiaoqiSwitchClick();
            }
        });

        tx_wuxia_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWuxiaSMClick();
            }
        });
        tx_wuxiaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWuxiaQClick();
            }
        });
        sw_wuxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWuxiaSwitchClick();
            }
        });

        tx_ailin_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAilinSMClick();
            }
        });
        tx_ailinq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAilinQClick();
            }
        });
        sw_ailin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAilinSwitchClick();
            }
        });

        tx_lida_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlidaSMClick();
            }
        });
        tx_lidaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlidaQClick();
            }
        });
        sw_lida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlidaSwitchClick();
            }
        });

        tx_imgload5_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLimg5SMClick();
            }
        });
        tx_imgload5q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLimg5QClick();
            }
        });
        sw_imgload5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLimg5SwitchClick();
            }
        });

    }

    //TODO 封装好的内部函数
    public void UI_Init(){
        if (fileIsExists(path_charlin+"event_act002.png")){
            title_img.setImageURI(Uri.fromFile(new File(path_charlin+"event_act002.png")));
        }
        app.addLog("界面初始化……");
        sw_hh.setEnabled(false);
        sw_hh.setText("等待网络连接……");
        sw_qsg.setEnabled(false);
        sw_xiaoqi.setEnabled(false);
        sw_wuxia.setEnabled(false);
        sw_ailin.setEnabled(false);
        sw_lida.setEnabled(false);
        sw_imgload5.setEnabled(false);
    }

    public void UI_Update(){
        app.addLog("日服界面初始化");
        //TODO 汉化
        app.addLog("检查lang.u文件MD5");
        String lang_md5=getFileMD5(path_rf+"lang.u");
        String lang_md5_bak=getFileMD5(path_rf+"lang.u.bak");
        if(lang_md5_bak == null)lang_md5_bak="000";
        if(lang_md5 == null){
            app.addLog("lang.u文件MD5返回值为空！是否给予了读写权限？");
            sw_hh.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(lang_md5.equals(lang_md5_jp)){
            app.addLog("与日文件相同，处于原始状态。");
            sw_hh.setChecked(false);
            sw_hh.setText("当前状态：原始");
            sw_hh.setEnabled(true);
        }
        else if(lang_md5.equals(lang_md5_trn)){
            if(lang_md5_bak.equals(lang_md5_jp)) {
                app.addLog("与汉化文件相同，处于汉化状态。");
                sw_hh.setChecked(true);
                sw_hh.setText("当前状态：汉化");
                sw_hh.setEnabled(true);
            }
            else{
                app.addLog("与老版本汉化文件相同，处于汉化状态。");
                sw_hh.setChecked(true);
                sw_hh.setText("当前状态：强制汉化");
                sw_hh.setEnabled(true);
            }
        }
        else{
            if(lang_md5_bak.equals(lang_md5_jp)){
                app.addLog("MD5:"+lang_md5);
                sw_hh.setChecked(false);
                sw_hh.setText("等待汉化包制作完成");
                app.addLog("MD5检验失败，但可以强制汉化。");
                sw_hh.setEnabled(true);
            }
            else{
                app.addLog("MD5:"+lang_md5);
                sw_hh.setChecked(false);
                sw_hh.setText("量子叠加态");
                app.addLog("MD5检验失败，但可以强制汉化。");
                sw_hh.setEnabled(true);
            }

        }
        //TODO 去圣光
        String QSG_md5=getFileMD5(path_rf+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
        if(QSG_md5 == null)sw_qsg.setEnabled(false);
        else if(QSG_md5.equals(SGgmask1)){
            sw_qsg.setChecked(false);
            sw_qsg.setEnabled(true);
        }
        else {
            sw_qsg.setChecked(true);
            sw_qsg.setText("当前状态：去圣光");
            sw_qsg.setEnabled(true);
        }
        if(app.getAD_Pass() && app.getQSG_allow())ly_qsg.setVisibility(View.VISIBLE);
        else ly_qsg.setVisibility(View.GONE);
        //TODO 小七
        app.addLog("检查l2d/girl102/l2d01.u文件MD5");
        String l2d01_102=getFileMD5(path_rf+"l2d/girl102/l2d01.u");
        if(l2d01_102 == null){
            app.addLog("l2d/girl102/l2d01.u文件MD5返回值为空！是否给予了读写权限？");
            sw_xiaoqi.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d01_102.equals(l2d01_102_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_xiaoqi.setChecked(false);
            sw_xiaoqi.setText("当前状态：私服");
            sw_xiaoqi.setEnabled(true);
        }
        else if(l2d01_102.equals(l2d01_102_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_xiaoqi.setChecked(true);
            sw_xiaoqi.setText("当前状态：水着");
            sw_xiaoqi.setEnabled(true);
        }
        else{
            app.addLog("MD5:"+l2d01_102);
            sw_xiaoqi.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 无瑕
        app.addLog("检查l2d/girl09/l2d01.u文件MD5");
        String l2d01_09=getFileMD5(path_rf+"l2d/girl09/l2d01.u");
        if(l2d01_09 == null){
            app.addLog("l2d/girl09/l2d01.u文件MD5返回值为空！是否给予了读写权限？");
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d01_09.equals(l2d01_09_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_wuxia.setChecked(false);
            sw_wuxia.setText("当前状态：私服·时无瑕");
            sw_wuxia.setEnabled(true);
        }
        else if(l2d01_09.equals(l2d01_09_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_wuxia.setChecked(true);
            sw_wuxia.setText("当前状态：闪耀·时无瑕");
            sw_wuxia.setEnabled(true);
        }
        else{
            app.addLog("MD5:"+l2d01_09);
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 艾琳
        app.addLog("检查l2d8001.u文件MD5");
        String l2d8001_07=getFileMD5(path_rf+"update/zst0518__l2d_371.u");
        String l2d8001_07bg=getFileMD5(path_rf+"l2dbg/bg_l2d_girl0780011_png.u");
        if(l2d8001_07 == null || l2d8001_07bg == null){
            app.addLog("l2d8001.u文件MD5返回值为空！是否给予了读写权限？");
            sw_ailin.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d8001_07.equals(l2d8001_07_md5_rf)  && l2d8001_07bg.equals(l2d8001_07bg_md5_rf)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_ailin.setChecked(false);
            sw_ailin.setText("当前状态：出游·艾琳");
            sw_ailin.setEnabled(true);
        }
        else if(l2d8001_07.equals(l2d8001_07_md5_rfmod) && l2d8001_07bg.equals(l2d8001_07bg_md5_rfmod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_ailin.setChecked(true);
            sw_ailin.setText("当前状态：品枫·艾琳");
            sw_ailin.setEnabled(true);
        }
        else{
            app.addLog("MD5:"+l2d8001_07+"\nMD5:"+l2d8001_07bg);
            sw_ailin.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 丽达
        app.addLog("检查l2d/girl11/l2d04.u文件MD5");
        String l2d14_13=getFileMD5(path_rf+ "l2d/girl11/l2d04.u");
        if(l2d14_13 == null){
            app.addLog("l2d/girl11/l2d04.u文件MD5返回值为空！是否给予了读写权限？");
            sw_lida.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d14_13.equals(l2d04_11_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_lida.setChecked(false);
            sw_lida.setText("当前状态：女仆装·丽达");
            sw_lida.setEnabled(true);
        }
        else if(l2d14_13.equals(l2d04_11_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_lida.setChecked(true);
            sw_lida.setText("当前状态：清理者·丽达");
            sw_lida.setEnabled(true);
        }
        else{
            app.addLog("MD5:"+l2d14_13);
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //app.addLog(String.valueOf(app.getQSG_allow()));
        if(app.getQSG_allow())ly_lida.setVisibility(View.VISIBLE);
        else ly_lida.setVisibility(View.GONE);
        //TODO 加载界面
        app.addLog("检查bg/bg_loading05_png.u文件MD5");
        String bg_loading05=getFileMD5(path_rf+"bg/bg_loading05_png.u");
        if(bg_loading05 == null){
            app.addLog("bg/bg_loading05_png.u文件MD5返回值为空！是否给予了读写权限？");
            sw_imgload5.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(bg_loading05.equals(loadimg5_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_imgload5.setChecked(false);
            sw_imgload5.setText("当前状态：原始");
            sw_imgload5.setEnabled(true);
        }
        else if(bg_loading05.equals(loadimg5_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_imgload5.setChecked(true);
            sw_imgload5.setText("当前状态：已替换");
            sw_imgload5.setEnabled(true);
        }
        else{
            app.addLog("MD5:"+bg_loading05);
            sw_imgload5.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
    }

    public static String getFileMD5(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return null;
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

    private boolean copyAssetsSingleFile(String outPath, String fileName) {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                app.addLog("copyAssetsSingleFile: cannot create directory.");
                return false;
            }
        }
        try {
            InputStream inputStream = getActivity().getAssets().open(fileName);
            File outFile = new File(file, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            // Transfer bytes from inputStream to fileOutputStream
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    private File renameFile(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return null;
        }

        if (TextUtils.isEmpty(newPath)) {
            return null;
        }
        if(fileIsExists(newPath)){
            app.addLog("重命名失败，目标文件已存在！");
            return null;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        boolean b = oldFile.renameTo(newFile);
        File file2 = new File(newPath);
        return file2;
    }

    public boolean fileIsExists(String strFile) {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
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

    public void fileBackup(String strFile){
        if(fileIsExists(strFile+".bak")){
            app.addLog(strFile+".bak文件已存在，删除之");
            deleteSingleFile(strFile+".bak");
        }
        app.addLog("重命名"+strFile+"为"+strFile+".bak");
        renameFile(strFile,strFile+".bak");
    }

    public void Restore_backup(String strFile){
        if(fileIsExists(strFile)){
            app.addLog(strFile+"文件已存在，删除之");
            deleteSingleFile(strFile);
        }
        app.addLog("重命名"+strFile+".bak为"+strFile);
        renameFile(strFile+".bak",strFile);
    }

    private void downloadFile(String url,String filename){
        //下载路径，如果路径无效了，可换成你的下载路径
        //url = "http://c.qijingonline.com/test.mkv";
        //创建下载任务,downloadUrl就是下载链接
        app.addLog("开始下载"+filename);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalFilesDir(getActivity(),"/",filename);
        //request.setDestinationInExternalPublicDir("", url.substring(url.lastIndexOf("/") + 1));
        //获取下载管理器
        // 通知栏中将出现的内容
        //request.setTitle("汉化包");
        //request.setDescription("下载一个大文件");
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadManager= (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadId = downloadManager.enqueue(request);
        //主线程中调用：
        mHandler.postDelayed(r_download, 5000);//延时100毫秒
    }

    // 查询下载进度，文件总大小多少，已经下载多少？
    private void queryDonloadsize() {
        try {
            DownloadManager.Query downloadQuery = new DownloadManager.Query();
            downloadQuery.setFilterById(downloadId);
            Cursor cursor = downloadManager.query(downloadQuery);
            if (cursor != null && cursor.moveToFirst()) {
//            int fileName = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//            int fileUri = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
//            String fn = cursor.getString(fileName);
//            String fu = cursor.getString(fileUri);

                int totalSizeBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int bytesDownloadSoFarIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

                // 下载的文件总大小
                int totalSizeBytes = cursor.getInt(totalSizeBytesIndex);

                // 截止目前已经下载的文件总大小
                int bytesDownloadSoFar = cursor.getInt(bytesDownloadSoFarIndex);

                //Log.d(this.getClass().getName(),"from " + fu + " 下载到本地 " + fn + " 文件总大小:" + totalSizeBytes + " 已经下载:" + bytesDownloadSoFar);
                NumberFormat nf = new DecimalFormat("##.####MB");
                sw_hh.setText(nf.format(1.0*bytesDownloadSoFar / 1024 / 1024) + "/" + nf.format(1.0*totalSizeBytes / 1024 / 1024));
                if (bytesDownloadSoFar == totalSizeBytes) {
                    UI_Update();
                    mHandler.removeCallbacks(r_download);
                }
                cursor.close();
            }
        }catch(Exception e){
            app.addLog(e.toString());
        }

    }

    public void gethandleMessage(Message msg) {
        String content = (String) msg.obj;
        String[] urlnews=content.split("\n");
        String ver = urlnews[0];
        final String dlurlp = urlnews[1];
        final String dlurlg = urlnews[2];
        lang_md5_trn = urlnews[3];
        lang_md5_jp = urlnews[4];
        String Local_lang_md5_trn = getFileMD5(path_charlin+"lang.u");
        String Local_lang_md5_jp = getFileMD5(path_rf+"lang.u");
        app.addLog("检查汉化包的更新……");
        if(Local_lang_md5_trn == null || !Local_lang_md5_trn.equals(lang_md5_trn)){
            if(Local_lang_md5_jp == null){//判断是否存在日服lang.u，防止空内容异常
                app.addLog("在目录"+path_rf+"下没有找到lang.u文件，您可能没有安装日服或者没有下载完整游戏数据。");
            }
            //如果之前有强制汉化则还原
            else if(!Local_lang_md5_jp.equals(lang_md5_jp) && !Local_lang_md5_jp.equals(lang_md5_trn)){
                String Local_lang_md5_jp_bak = getFileMD5(path_rf+"lang.u.bak");
                if(Local_lang_md5_jp_bak != null && Local_lang_md5_jp_bak.equals(lang_md5_jp)){
                    //lang.u为之前的汉化且lang.u.bak为日服文件，则还原该文件。
                    app.addLog("自动还原汉化操作");
                    Restore_backup(path_rf+"lang.u");
                    sw_hh.setText("当前状态：正常");
                    app.addLog("还原完成");
                    sw_hh.setChecked(false);
                }
            }
            deleteSingleFile(path_charlin+"lang.u");
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("发现新的汉化包！");
            builder.setMessage("已自动将旧版汉化还原为日文，因为找到了新的汉化包，版本：\n\n\t\t\t\t"+ver+"\n\n是否下载更新？\n这将耗费3MB左右的流量。\n请在下载完成后回到此页面。\n若高速下载失败请选择普通下载！");
            builder.setPositiveButton("高速下载",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    downloadFile(dlurlg,"lang.u");
                    sw_hh.setEnabled(false);
                    sw_hh.setText("正在后台下载更新……");

                }
            });
            builder.setNegativeButton("普通下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    downloadFile(dlurlp,"lang.u");
                    sw_hh.setEnabled(false);
                    sw_hh.setText("正在后台下载更新……");
                }
            });
            builder.setNeutralButton("不更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    sw_hh.setEnabled(false);
                    sw_hh.setText("汉化未更新");    }
            });
            builder.create().show();
        }
        else{
            app.addLog("已是最新。");
        }
        UI_Update();
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

    public void getLangFileInfo(final String urlpath) {
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

    //TODO 控件对应函数
    public void onHhSMClick(){
        tx_hh_sm.setVisibility(View.GONE);
    }
    public void onHhQClick(){
        tx_hh_sm.setVisibility(View.VISIBLE);
    }
    public void onHhSwitchClick(){
        String lang_md5=getFileMD5(path_rf+"lang.u");
        String lang_md5_bak=getFileMD5(path_rf+"lang.u.bak");
        if(lang_md5_bak == null)lang_md5_bak="000";
        if(lang_md5.equals(lang_md5_jp)) {
            app.addLog("汉化操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"lang.u");
            app.addLog("复制MOD");
            copyFile(path_charlin+"lang.u",path_rf+"lang.u");
            //copyAssetsSingleFile(path_rf,"lang.u");
            app.addLog("校验创建的文件");
            lang_md5=getFileMD5(path_rf+"lang.u");
            if(lang_md5!=null & lang_md5.equals(lang_md5_trn)) {
                app.addLog("操作完成！");
                sw_hh.setText("当前状态：汉化");
            }else{
                app.addLog("MD5校验失败："+lang_md5);
                app.addLog("退回操作");
                Restore_backup(path_rf+"lang.u");
                sw_hh.setChecked(false);
            }
        }
        else if(lang_md5.equals(lang_md5_trn)){
            if(lang_md5_bak.equals(lang_md5_jp)) {
                app.addLog("还原汉化操作");
                app.addLog("校验lang.u.bak文件MD5");
                String langbak_md5 = getFileMD5(path_rf + "lang.u.bak");
                if (langbak_md5 != null & langbak_md5.equals(lang_md5_jp)) {
                    app.addLog("还原文件");
                    Restore_backup(path_rf + "lang.u");
                    sw_hh.setText("当前状态：正常");
                    app.addLog("还原完成");
                } else {
                    app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                    sw_hh.setChecked(true);
                }
            }
            else{
                app.addLog("还原强制汉化操作");
                Restore_backup(path_rf + "lang.u");
                sw_hh.setText("当前状态：正常（汉化待更新）");
                app.addLog("还原完成");
            }
        }
        else{
            if(lang_md5_bak.equals(lang_md5_jp)){
                app.addLog("强制汉化操作");
                app.addLog("创建bak备份文件");
                fileBackup(path_rf+"lang.u");
                app.addLog("复制MOD");
                copyFile(path_charlin+"lang.u",path_rf+"lang.u");
                app.addLog("操作完成！");
                sw_hh.setText("当前状态：强制汉化");
            }
            else{
                app.addLog("汉化操作");
                app.addLog("创建bak备份文件");
                fileBackup(path_rf+"lang.u");
                app.addLog("复制MOD");
                copyFile(path_charlin+"lang.u",path_rf+"lang.u");
                app.addLog("操作完成！");
                sw_hh.setText("当前状态：汉化");
            }
        }
    }

    public void onQsgSMClick(){
        tx_qsg_sm.setVisibility(View.GONE);
    }
    public void onQsgQClick(){
        tx_qsg_sm.setVisibility(View.VISIBLE);
    }
    public void onQsgSwitchClick(){
        String SG_md5=getFileMD5(path_rf+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
        if(SG_md5.equals(SGgmask1)) {
            app.addLog("去圣光操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
            fileBackup(path_rf+"mt/live2d/e_ui_change_clothes_mask_mat.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
            copyAssetsSingleFile(path_rf,"mt/live2d/e_ui_change_clothes_mask_mat.u");
            sw_qsg.setText("当前状态：去圣光");
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验mask.u.bak文件MD5");
            String SG1bak_md5=getFileMD5(path_rf+"mt/live2d/e_ui_change_clothes_1_mask_mat.u.bak");
            String SG2bak_md5=getFileMD5(path_rf+"mt/live2d/e_ui_change_clothes_mask_mat.u.bak");
            if(SG1bak_md5 != null & SG2bak_md5 != null
                    & SG1bak_md5.equals(SGgmask1)& SG2bak_md5.equals(SGgmask2)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
                Restore_backup(path_rf+"mt/live2d/e_ui_change_clothes_mask_mat.u");
                sw_qsg.setText("当前状态：正常");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_qsg.setChecked(true);
            }

        }
    }

    public void onXiaoqiSMClick(){
        tx_xiaoqi_sm.setVisibility(View.GONE);
    }
    public void onXiaoqiQClick(){
        tx_xiaoqi_sm.setVisibility(View.VISIBLE);
    }
    public void onXiaoqiSwitchClick(){
        String l2d01_md5=getFileMD5(path_rf+"l2d/girl102/l2d01.u");
        if(l2d01_md5.equals(l2d01_102_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"l2d/girl102/l2d01.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"l2d/girl102/l2d01.u");
            app.addLog("校验创建的文件");
            l2d01_md5=getFileMD5(path_rf+"l2d/girl102/l2d01.u");
            if(l2d01_md5!=null & l2d01_md5.equals(l2d01_102_md5_mod)) {
                app.addLog("操作完成！");
                sw_xiaoqi.setText("当前状态：水着");
            }else{
                app.addLog("MD5校验失败："+l2d01_md5);
                app.addLog("退回操作");
                Restore_backup(path_rf+"l2d/girl102/l2d01.u");
                sw_xiaoqi.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl102/l2d01.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_rf+"l2d/girl102/l2d01.u.bak");
            if(l2d01bak_md5 != null & l2d01bak_md5.equals(l2d01_102_md5)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"l2d/girl102/l2d01.u");
                sw_xiaoqi.setText("当前状态：私服");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_xiaoqi.setChecked(true);
            }

        }

    }

    public void onWuxiaSMClick(){
        tx_wuxia_sm.setVisibility(View.GONE);
    }
    public void onWuxiaQClick(){
        tx_wuxia_sm.setVisibility(View.VISIBLE);
    }
    public void onWuxiaSwitchClick(){
        String l2d01_md5=getFileMD5(path_rf+"l2d/girl09/l2d01.u");
        if(l2d01_md5.equals(l2d01_09_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"l2d/girl09/l2d01.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"l2d/girl09/l2d01.u");
            app.addLog("校验创建的文件");
            l2d01_md5=getFileMD5(path_rf+"l2d/girl09/l2d01.u");
            if(l2d01_md5!=null & l2d01_md5.equals(l2d01_09_md5_mod)) {
                app.addLog("操作完成！");
                sw_wuxia.setText("当前状态：闪耀·时无瑕");
            }else{
                app.addLog("MD5校验失败："+l2d01_md5);
                app.addLog("退回操作");
                Restore_backup(path_rf+"l2d/girl09/l2d01.u");
                sw_wuxia.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl09/l2d01.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_rf+"l2d/girl09/l2d01.u.bak");
            if(l2d01bak_md5 != null & l2d01bak_md5.equals(l2d01_09_md5)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"l2d/girl09/l2d01.u");
                sw_wuxia.setText("当前状态：私服·时无瑕");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_wuxia.setChecked(true);
            }

        }

    }

    public void onAilinSMClick(){
        tx_ailin_sm.setVisibility(View.GONE);
    }
    public void onAilinQClick(){
        tx_ailin_sm.setVisibility(View.VISIBLE);
    }
    public void onAilinSwitchClick(){
        String l2d8001_md5=getFileMD5(path_rf+"update/zst0518__l2d_371.u");
        if(l2d8001_md5.equals(l2d8001_07_md5_rf)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"update/zst0518__l2d_371.u");
            fileBackup(path_rf+"l2dbg/bg_l2d_girl0780011_png.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"update/yh70__l2d_195.u");
            renameFile(path_rf+"update/yh70__l2d_195.u",path_rf+"update/zst0518__l2d_371.u");
            copyAssetsSingleFile(path_rf,"l2dbg/bg_l2d_girl0780011_png.u");
            app.addLog("校验创建的文件");
            l2d8001_md5=getFileMD5(path_rf+"l2dbg/bg_l2d_girl0780011_png.u");
            if(l2d8001_md5!=null & l2d8001_md5.equals(l2d8001_07bg_md5_rfmod)) {
                app.addLog("操作完成！");
                sw_ailin.setText("当前状态：品枫·艾琳");
            }else{
                app.addLog("MD5校验失败："+l2d8001_md5);
                app.addLog("退回操作");
                Restore_backup(path_rf+"update/zst0518__l2d_371.u");
                Restore_backup(path_rf+"l2dbg/bg_l2d_girl0780011_png.u");
                sw_ailin.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d8001.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_rf+"update/zst0518__l2d_371.u.bak");
            String l2d01bgbak_md5=getFileMD5(path_rf+"l2dbg/bg_l2d_girl0780011_png.u.bak");
            if(l2d01bak_md5 != null & l2d01bgbak_md5 != null & l2d01bak_md5.equals(l2d8001_07_md5_rf)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"update/zst0518__l2d_371.u");
                Restore_backup(path_rf+"l2dbg/bg_l2d_girl0780011_png.u");
                sw_ailin.setText("当前状态：出游·艾琳");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_ailin.setChecked(true);
            }

        }

    }

    public void onlidaSMClick(){
        tx_lida_sm.setVisibility(View.GONE);
    }
    public void onlidaQClick(){
        tx_lida_sm.setVisibility(View.VISIBLE);
    }
    public void onlidaSwitchClick(){
        if(!app.getQSG_allow()){
            ly_lida.setVisibility(View.GONE);
            return;
        }
        String l2d13_md5=getFileMD5(path_rf+"l2d/girl11/l2d04.u");
        if(l2d13_md5.equals(l2d04_11_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"l2d/girl11/l2d04.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"l2d/girl11/l2d04.u");
            app.addLog("校验创建的文件");
            l2d13_md5=getFileMD5(path_rf+"l2d/girl11/l2d04.u");
            if(l2d13_md5!=null & l2d13_md5.equals(l2d04_11_md5_mod)) {
                app.addLog("操作完成！");
                sw_lida.setText("当前状态：清理者·丽达");
            }else{
                app.addLog("MD5校验失败："+l2d13_md5);
                app.addLog("退回操作");
                Restore_backup(path_rf+"l2d/girl11/l2d04.u");
                sw_lida.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl11/l2d04.u.bak文件MD5");
            String l2d13bak_md5=getFileMD5(path_rf+"l2d/girl11/l2d04.u.bak");
            if(l2d13bak_md5 != null & l2d13bak_md5.equals(l2d04_11_md5)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"l2d/girl11/l2d04.u");
                sw_lida.setText("当前状态：女仆装·丽达");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_lida.setChecked(true);
            }

        }

    }

    public void onLimg5SMClick(){
        tx_imgload5_sm.setVisibility(View.GONE);
    }
    public void onLimg5QClick(){
        tx_imgload5_sm.setVisibility(View.VISIBLE);
    }
    public void onLimg5SwitchClick(){
        String bg_loading05=getFileMD5(path_rf+"bg/bg_loading05_png.u");
        if(bg_loading05.equals(loadimg5_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_rf+"bg/bg_loading05_png.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_rf,"bg/bg_loading05_png.u");
            app.addLog("校验创建的文件");
            bg_loading05=getFileMD5(path_rf+"bg/bg_loading05_png.u");
            if(bg_loading05!=null & bg_loading05.equals(loadimg5_md5_mod)) {
                app.addLog("操作完成！");
                sw_imgload5.setText("当前状态：已更改");
            }else{
                app.addLog("MD5校验失败："+bg_loading05);
                app.addLog("退回操作");
                Restore_backup(path_rf+"bg/bg_loading05_png.u");
                sw_imgload5.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验bg/bg_loading05_png.u.bak文件MD5");
            String bg_loading05bak_md5=getFileMD5(path_rf+"bg/bg_loading05_png.u.bak");
            if(bg_loading05bak_md5 != null & bg_loading05bak_md5.equals(loadimg5_md5)){
                app.addLog("还原文件");
                Restore_backup(path_rf+"bg/bg_loading05_png.u");
                sw_imgload5.setText("当前状态：已还原");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_imgload5.setChecked(true);
            }

        }

    }


}