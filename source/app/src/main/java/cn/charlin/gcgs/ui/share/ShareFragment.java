package cn.charlin.gcgs.ui.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.charlin.gcgs.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    View view;
    private Button guanzhu_btn;
    private TextView bili_tx,qq_tx,cv_tx;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        view = inflater.inflate(R.layout.fragment_share, container, false);
        guanzhu_btn = (Button) view.findViewById(R.id.guanzhu_button);
        bili_tx = (TextView)view.findViewById(R.id.t_BLBL);
        cv_tx = (TextView)view.findViewById(R.id.t_cv);
        qq_tx = (TextView)view.findViewById(R.id.t_QQ);
//        final TextView textView = root.findViewById(R.id.t_ad);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guanzhu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBiliBiliClick(view);
            }
        });
        bili_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBiliBiliClick(view);
            }
        });
        cv_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCvClick(view);
            }
        });
        qq_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQqQunClick(view);
            }
        });
    }


    private boolean isAvilible(Context context, String packageName){
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if(pinfo != null){
            for(int i = 0; i < pinfo.size(); i++){
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public void onBiliBiliClick(View view){
        if(isAvilible(getActivity(),"tv.danmaku.bili")){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse("http://space.bilibili.com/37664861");
            intent.setData(uri);
            intent.setClassName("tv.danmaku.bili","tv.danmaku.bili.ui.intent.IntentHandlerActivity");
            startActivity(intent);}
        else{
            Toast.makeText(getActivity(),"未安装BiliBili客户端，用网页打开", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("http://space.bilibili.com/37664861");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void onCvClick(View view){
        if(isAvilible(getActivity(),"tv.danmaku.bili")){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse("http://www.bilibili.com/read/cv5165668");
            intent.setData(uri);
            intent.setClassName("tv.danmaku.bili","tv.danmaku.bili.ui.intent.IntentHandlerActivity");
            startActivity(intent);}
        else{
            Toast.makeText(getActivity(),"未安装BiliBili客户端，用网页打开",Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("http://www.bilibili.com/read/cv5165668");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void onQqQunClick(View view){
        Uri uri = Uri.parse("https://jq.qq.com/?_wv=1027&k=52GgjGu");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}