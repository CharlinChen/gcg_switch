package cn.charlin.gcgs.ui.home;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.charlin.gcgs.Data;
import cn.charlin.gcgs.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView title_img;
    private TextView tx_fhx_sm,tx_fhxq;
    private Switch sw_fhx;
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
    String path_gf_bili,path_charlin;
    String l2d999_md5_gf="7e57aeee823c4c24b448a137c1e689cf";
    String l2d999_md5_rf="f337a96052ef5175aa06d2abd8fd0148";
    String l2d01_102_md5="3f220f18da8e92e7d2315bb28d1c4bd4";
    String l2d01_102_md5_mod="dc2668acaa020d93648e43b725734db7";
    String l2d01_09_md5="03727f4bc35f53fda3ef131fe09e5a83";
    String l2d01_09_md5_mod="52d91453d803c3c98a8b13e0edc2942e";
    String l2d04_11_md5="6407c8988b5bbab80e74b8127423e35a";
    String l2d04_11_md5_mod="f1c6c54fdd92fa9a15087516110ff799";
    String loadimg5_md5="cc990d56c74753435a4c4d034bac89b9";
    String loadimg5_md5_mod="0b8a8ee783c65ac6599b86fdf879b536";
    String l2d8001_07_md5_gf="ec88ac9483b33a64c2a9b0855301dec5";
    String l2d8001_07bg_md5_gf="ae1cc7a95ddd2796e63257fb1f2901bd";
    String l2d8001_07_md5_gfmod="aeea0a4ee25ed43ef743b7615e300dd0";
    String l2d8001_07bg_md5_gfmod="729076d29ee8df8fba384cb6b31e033b";
    String SGgmask1="cbf333083457da66e869041aed179438";
    String SGgmask2="d455d3fafb9f63f8d9321cf6c1901638";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        app = (Data)getActivity().getApplication();
        //变量初始化
        path_gf_bili="/storage/emulated/0/Android/data/com.bilibili.gcg2.bili/files/";
        path_charlin="/storage/emulated/0/Android/data/cn.charlin.gcgs/files/";
        //获取控件ID
        title_img = view.findViewById(R.id.ICON_imageView);
        tx_fhx_sm = view.findViewById(R.id.fhxshuoming_textView);
        tx_fhxq = view.findViewById(R.id.gffanhexie_txq);
        sw_fhx = view.findViewById(R.id.fhx_switch);
        tx_qsg_sm = view.findViewById(R.id.qsgshuoming_textView);
        tx_qsgq = view.findViewById(R.id.gfqsguang_txq);
        sw_qsg = view.findViewById(R.id.qsg_switch);
        ly_qsg = view.findViewById(R.id.ly_qsg);
        tx_xiaoqi_sm = view.findViewById(R.id.gfxiaoqi_textView);
        tx_xiaoqiq = view.findViewById(R.id.gfxiaoqi_txq);
        sw_xiaoqi = view.findViewById(R.id.gfxiaoqi_switch);
        tx_wuxia_sm = view.findViewById(R.id.gfwuxia_textView);
        tx_wuxiaq = view.findViewById(R.id.gfwuxia_txq);
        sw_wuxia = view.findViewById(R.id.gfwuxia_switch);
        tx_ailin_sm = view.findViewById(R.id.gf_ailin_textView);
        tx_ailinq = view.findViewById(R.id.gf_ailin_txq);
        sw_ailin = view.findViewById(R.id.gf_ailin_switch);
        tx_lida_sm = view.findViewById(R.id.gf_lida_textView);
        tx_lidaq = view.findViewById(R.id.gf_lida_txq);
        sw_lida = view.findViewById(R.id.gf_lida_switch);
        ly_lida = view.findViewById(R.id.ly_lida);
        tx_imgload5_sm = view.findViewById(R.id.gf_loadimg5_textView);
        tx_imgload5q = view.findViewById(R.id.gf_loadimg5_txq);
        sw_imgload5 = view.findViewById(R.id.gf_loadimg5_switch);
        //界面初始化
        UI_Update();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tx_fhx_sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFhxSMClick();
            }
        });
        tx_fhxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFhxQClick();
            }
        });
        sw_fhx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFhxSwitchClick();
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
    public void UI_Update(){
        if (fileIsExists(path_charlin+"event_act001.png")){
            title_img.setImageURI(Uri.fromFile(new File(path_charlin+"event_act001.png")));
        }
        app.addLog("国服界面初始化");
        //TODO 反和谐
        app.addLog("检查l2d/girl111/l2d999.u文件MD5");
        String l2d999_md5=getFileMD5(path_gf_bili+"l2d/girl111/l2d999.u");
        if(l2d999_md5 == null){
            app.addLog("l2d999.u文件MD5返回值为空！是否给予了读写权限？");
            sw_fhx.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d999_md5.equals(l2d999_md5_gf)){
            app.addLog("与国服文件相同，处于原始状态。");
            sw_fhx.setChecked(false);
            sw_fhx.setText("当前状态：原始");
        }
        else if(l2d999_md5.equals(l2d999_md5_rf)){
            app.addLog("与日服文件相同，处于反和谐状态。");
            sw_fhx.setChecked(true);
            sw_fhx.setText("当前状态：反和谐");
        }
        else{
            app.addLog("MD5:"+l2d999_md5);
            sw_fhx.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 去圣光
        String QSG_md5=getFileMD5(path_gf_bili+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
        if(QSG_md5 == null)sw_qsg.setEnabled(false);
        else if(QSG_md5.equals(SGgmask1))sw_qsg.setChecked(false);
        else {sw_qsg.setChecked(true);sw_qsg.setText("当前状态：去圣光");}
        if(app.getAD_Pass() && app.getQSG_allow())ly_qsg.setVisibility(View.VISIBLE);
        else ly_qsg.setVisibility(View.GONE);
        //TODO 小七
        app.addLog("检查l2d/girl102/l2d01.u文件MD5");
        String l2d01_102=getFileMD5(path_gf_bili+"l2d/girl102/l2d01.u");
        if(l2d01_102 == null){
            app.addLog("l2d/girl102/l2d01.u文件MD5返回值为空！是否给予了读写权限？");
            sw_xiaoqi.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d01_102.equals(l2d01_102_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_xiaoqi.setChecked(false);
            sw_xiaoqi.setText("当前状态：私服");
        }
        else if(l2d01_102.equals(l2d01_102_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_xiaoqi.setChecked(true);
            sw_xiaoqi.setText("当前状态：水着");
        }
        else{
            app.addLog("MD5:"+l2d01_102);
            sw_xiaoqi.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 无瑕
        app.addLog("检查l2d/girl09/l2d01.u文件MD5");
        String l2d01_09=getFileMD5(path_gf_bili+"l2d/girl09/l2d01.u");
        if(l2d01_09 == null){
            app.addLog("l2d/girl09/l2d01.u文件MD5返回值为空！是否给予了读写权限？");
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d01_09.equals(l2d01_09_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_wuxia.setChecked(false);
            sw_wuxia.setText("当前状态：私服·时无瑕");
        }
        else if(l2d01_09.equals(l2d01_09_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_wuxia.setChecked(true);
            sw_wuxia.setText("当前状态：闪耀·时无瑕");
        }
        else{
            app.addLog("MD5:"+l2d01_09);
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 艾琳
        app.addLog("检查l2d8001.u文件MD5");
        String l2d8001_07=getFileMD5(path_gf_bili+"update/yh70__l2d_195.u");
        String l2d8001_07bg=getFileMD5(path_gf_bili+"update/yh10__tex.u");
        if(l2d8001_07 == null || l2d8001_07bg == null){
            app.addLog("l2d8001.u.u文件MD5返回值为空！是否给予了读写权限？");
            sw_ailin.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d8001_07.equals(l2d8001_07_md5_gf)  && l2d8001_07bg.equals(l2d8001_07bg_md5_gf)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_ailin.setChecked(false);
            sw_ailin.setText("当前状态：出游·艾琳");
        }
        else if(l2d8001_07.equals(l2d8001_07_md5_gfmod) && l2d8001_07bg.equals(l2d8001_07bg_md5_gfmod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_ailin.setChecked(true);
            sw_ailin.setText("当前状态：品枫·艾琳");
        }
        else{
            app.addLog("MD5:"+l2d8001_07+"\nMD5:"+l2d8001_07bg);
            sw_ailin.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //TODO 丽达
        app.addLog("检查l2d/girl11/l2d04.u文件MD5");
        String l2d14_13=getFileMD5(path_gf_bili+ "l2d/girl11/l2d04.u");
        if(l2d14_13 == null){
            app.addLog("l2d/girl11/l2d04.u文件MD5返回值为空！是否给予了读写权限？");
            sw_lida.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(l2d14_13.equals(l2d04_11_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_lida.setChecked(false);
            sw_lida.setText("当前状态：女仆装·丽达");
        }
        else if(l2d14_13.equals(l2d04_11_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_lida.setChecked(true);
            sw_lida.setText("当前状态：清理者·丽达");
        }
        else{
            app.addLog("MD5:"+l2d14_13);
            sw_wuxia.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        //app.addLog(String.valueOf(app.getQSG_allow()));
        //if(app.getQSG_allow())ly_lida.setVisibility(View.VISIBLE);
        //else ly_lida.setVisibility(View.GONE);
        //TODO 加载界面
        app.addLog("检查bg/bg_loading05_png.u文件MD5");
        String bg_loading05=getFileMD5(path_gf_bili+"bg/bg_loading05_png.u");
        if(bg_loading05 == null){
            app.addLog("bg/bg_loading05_png.u文件MD5返回值为空！是否给予了读写权限？");
            sw_imgload5.setEnabled(false);
            app.addLog("MD5检验失败，该选项已锁定，不可更改。");
        }
        else if(bg_loading05.equals(loadimg5_md5)){
            app.addLog("与原始文件相同，处于原始状态。");
            sw_imgload5.setChecked(false);
            sw_imgload5.setText("当前状态：原始");
        }
        else if(bg_loading05.equals(loadimg5_md5_mod)){
            app.addLog("与MOD文件相同，处于MOD状态。");
            sw_imgload5.setChecked(true);
            sw_imgload5.setText("当前状态：已替换");
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

    //TODO 控件对应函数
    public void onFhxSMClick(){
        tx_fhx_sm.setVisibility(View.GONE);
    }
    public void onFhxQClick(){
        tx_fhx_sm.setVisibility(View.VISIBLE);
    }
    public void onFhxSwitchClick(){
        String l2d999_md5=getFileMD5(path_gf_bili+"l2d/girl111/l2d999.u");
        if(l2d999_md5.equals(l2d999_md5_gf)) {
            app.addLog("反和谐操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"l2d/girl111/l2d999.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"l2d/girl111/l2d999.u");
            app.addLog("校验创建的文件");
            l2d999_md5=getFileMD5(path_gf_bili+"l2d/girl111/l2d999.u");
            if(l2d999_md5!=null & l2d999_md5.equals(l2d999_md5_rf)) {
                app.addLog("操作完成！");
                sw_fhx.setText("当前状态：反和谐");
            }else{
                app.addLog("MD5校验失败："+l2d999_md5);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"l2d/girl111/l2d999.u");
                sw_fhx.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d999.u.bak文件MD5");
            String l2d999bak_md5=getFileMD5(path_gf_bili+"l2d/girl111/l2d999.u.bak");
            if(l2d999bak_md5 != null & l2d999bak_md5.equals(l2d999_md5_gf)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"l2d/girl111/l2d999.u");
                sw_fhx.setText("当前状态：正常");
                app.addLog("还原完成");
            }
            else{
                app.addLog("原始文件异常，为保证游戏正常运行，取消还原操作！");
                sw_fhx.setChecked(true);
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
        String SG_md5=getFileMD5(path_gf_bili+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
        if(SG_md5.equals(SGgmask1)) {
            app.addLog("去圣光操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
            fileBackup(path_gf_bili+"mt/live2d/e_ui_change_clothes_mask_mat.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
            copyAssetsSingleFile(path_gf_bili,"mt/live2d/e_ui_change_clothes_mask_mat.u");
            sw_qsg.setText("当前状态：去圣光");
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验mask.u.bak文件MD5");
            String SG1bak_md5=getFileMD5(path_gf_bili+"mt/live2d/e_ui_change_clothes_1_mask_mat.u.bak");
            String SG2bak_md5=getFileMD5(path_gf_bili+"mt/live2d/e_ui_change_clothes_mask_mat.u.bak");
            if(SG1bak_md5 != null & SG2bak_md5 != null
                    & SG1bak_md5.equals(SGgmask1)& SG2bak_md5.equals(SGgmask2)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"mt/live2d/e_ui_change_clothes_1_mask_mat.u");
                Restore_backup(path_gf_bili+"mt/live2d/e_ui_change_clothes_mask_mat.u");
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
        String l2d01_md5=getFileMD5(path_gf_bili+"l2d/girl102/l2d01.u");
        if(l2d01_md5.equals(l2d01_102_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"l2d/girl102/l2d01.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"l2d/girl102/l2d01.u");
            app.addLog("校验创建的文件");
            l2d01_md5=getFileMD5(path_gf_bili+"l2d/girl102/l2d01.u");
            if(l2d01_md5!=null & l2d01_md5.equals(l2d01_102_md5_mod)) {
                app.addLog("操作完成！");
                sw_xiaoqi.setText("当前状态：水着");
            }else{
                app.addLog("MD5校验失败："+l2d01_md5);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"l2d/girl102/l2d01.u");
                sw_xiaoqi.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl102/l2d01.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_gf_bili+"l2d/girl102/l2d01.u.bak");
            if(l2d01bak_md5 != null & l2d01bak_md5.equals(l2d01_102_md5)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"l2d/girl102/l2d01.u");
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
        String l2d01_md5=getFileMD5(path_gf_bili+"l2d/girl09/l2d01.u");
        if(l2d01_md5.equals(l2d01_09_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"l2d/girl09/l2d01.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"l2d/girl09/l2d01.u");
            app.addLog("校验创建的文件");
            l2d01_md5=getFileMD5(path_gf_bili+"l2d/girl09/l2d01.u");
            if(l2d01_md5!=null & l2d01_md5.equals(l2d01_09_md5_mod)) {
                app.addLog("操作完成！");
                sw_wuxia.setText("当前状态：闪耀·时无瑕");
            }else{
                app.addLog("MD5校验失败："+l2d01_md5);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"l2d/girl09/l2d01.u");
                sw_wuxia.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl09/l2d01.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_gf_bili+"l2d/girl09/l2d01.u.bak");
            if(l2d01bak_md5 != null & l2d01bak_md5.equals(l2d01_09_md5)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"l2d/girl09/l2d01.u");
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
        String l2d8001_md5=getFileMD5(path_gf_bili+"update/yh70__l2d_195.u");
        if(l2d8001_md5.equals(l2d8001_07_md5_gf)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"update/yh70__l2d_195.u");
            fileBackup(path_gf_bili+"update/yh10__tex.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"update/yh70__l2d_195.u");
            copyAssetsSingleFile(path_gf_bili,"update/yh10__tex.u");
            app.addLog("校验创建的文件");
            l2d8001_md5=getFileMD5(path_gf_bili+"update/yh70__l2d_195.u");
            if(l2d8001_md5!=null & l2d8001_md5.equals(l2d8001_07_md5_gfmod)) {
                app.addLog("操作完成！");
                sw_ailin.setText("当前状态：品枫·艾琳");
            }else{
                app.addLog("MD5校验失败："+l2d8001_md5);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"update/yh70__l2d_195.u");
                Restore_backup(path_gf_bili+"update/yh10__tex.u");
                sw_ailin.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d8001.u.bak文件MD5");
            String l2d01bak_md5=getFileMD5(path_gf_bili+"update/yh70__l2d_195.u.bak");
            String l2d01bgbak_md5=getFileMD5(path_gf_bili+"update/yh10__tex.u.bak");
            if(l2d01bak_md5 != null & l2d01bgbak_md5 != null & l2d01bak_md5.equals(l2d8001_07_md5_gf)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"update/yh70__l2d_195.u");
                Restore_backup(path_gf_bili+"update/yh10__tex.u");
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
        String l2d13_md5=getFileMD5(path_gf_bili+"l2d/girl11/l2d04.u");
        if(l2d13_md5.equals(l2d04_11_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"l2d/girl11/l2d04.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"l2d/girl11/l2d04.u");
            app.addLog("校验创建的文件");
            l2d13_md5=getFileMD5(path_gf_bili+"l2d/girl11/l2d04.u");
            if(l2d13_md5!=null & l2d13_md5.equals(l2d04_11_md5_mod)) {
                app.addLog("操作完成！");
                sw_lida.setText("当前状态：清理者·丽达");
            }else{
                app.addLog("MD5校验失败："+l2d13_md5);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"l2d/girl11/l2d04.u");
                sw_lida.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验l2d/girl11/l2d04.u.bak文件MD5");
            String l2d13bak_md5=getFileMD5(path_gf_bili+"l2d/girl11/l2d04.u.bak");
            if(l2d13bak_md5 != null & l2d13bak_md5.equals(l2d04_11_md5)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"l2d/girl11/l2d04.u");
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
        String bg_loading05=getFileMD5(path_gf_bili+"bg/bg_loading05_png.u");
        if(bg_loading05.equals(loadimg5_md5)) {
            app.addLog("打MOD操作");
            app.addLog("创建bak备份文件");
            fileBackup(path_gf_bili+"bg/bg_loading05_png.u");
            app.addLog("复制MOD");
            copyAssetsSingleFile(path_gf_bili,"bg/bg_loading05_png.u");
            app.addLog("校验创建的文件");
            bg_loading05=getFileMD5(path_gf_bili+"bg/bg_loading05_png.u");
            if(bg_loading05!=null & bg_loading05.equals(loadimg5_md5_mod)) {
                app.addLog("操作完成！");
                sw_imgload5.setText("当前状态：已更改");
            }else{
                app.addLog("MD5校验失败："+bg_loading05);
                app.addLog("退回操作");
                Restore_backup(path_gf_bili+"bg/bg_loading05_png.u");
                sw_imgload5.setChecked(false);
            }
        }
        else{
            app.addLog("还原操作");
            app.addLog("校验bg/bg_loading05_png.u.bak文件MD5");
            String bg_loading05bak_md5=getFileMD5(path_gf_bili+"bg/bg_loading05_png.u.bak");
            if(bg_loading05bak_md5 != null & bg_loading05bak_md5.equals(loadimg5_md5)){
                app.addLog("还原文件");
                Restore_backup(path_gf_bili+"bg/bg_loading05_png.u");
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