package cn.charlin.gcgs;

import android.app.Application;

public class Data extends Application {
    private String b,app_ver;
    private int p;
    private boolean AD_press,QSG_allow;
    @Override
    public void onCreate(){
        b = "No Log";
        p = 1;
        app_ver = "1";
        AD_press = false;
        QSG_allow = false;
        super.onCreate();
    }
    public void setApp_ver(String c){
        this.app_ver= c;
    }
    public String getApp_ver()
    {
        return this.app_ver;
    }
    public String getLog(){
        return this.b;
    }
    public void setLog(String c){
        this.b= c;
    }
    public void addLog(String c){
        this.b= "\t\t\t\t"+p+">>"+c+"\n"+b;
        p=p+1;
    }
    public void setAD_Pass(boolean c){
        this.AD_press = c;
    }
    public boolean getAD_Pass(){
        return this.AD_press;
    }
    public void setQSG_allow(boolean c){
        this.QSG_allow = c;
    }
    public boolean getQSG_allow(){
        return this.QSG_allow;
    }
}
