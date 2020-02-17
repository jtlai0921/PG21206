package com.uppowerstudio.chapter4.packagemanager;

import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 封裝資訊範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView txt=(TextView) findViewById(R.id.txt);

// 獲取PackageManager對象
        PackageManager manager=getPackageManager();
// 通過PackageManager獲取系統已安裝的所有封裝資訊
        List<PackageInfo> list = manager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        StringBuilder sb=new StringBuilder();
// 獲取單一封裝資訊
for(PackageInfo info : list){
	// 獲取封裝名稱
	sb.append("package name:"+info.packageName+"\n");
	ApplicationInfo aInfo=info.applicationInfo;
	// 獲取應用的label值
	sb.append("application name:"+aInfo.loadLabel(manager)+"\n");
	// 如果此套裝程式封裝含權限請求，則獲取並顯示
	if(info.permissions != null){
		for(PermissionInfo p : info.permissions){
			//關於p.name獲取的值可參見本書附錄D查看具體內容
			sb.append("permission:"+p.name+"\n");
		}
	}
	sb.append("\n");
        }
        txt.setText(sb.toString());
    }
}
