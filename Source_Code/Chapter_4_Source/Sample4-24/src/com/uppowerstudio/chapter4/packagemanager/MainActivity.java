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
 * �ʸ˸�T�d��
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

// ���PackageManager��H
        PackageManager manager=getPackageManager();
// �q�LPackageManager����t�Τw�w�˪��Ҧ��ʸ˸�T
        List<PackageInfo> list = manager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        StringBuilder sb=new StringBuilder();
// �����@�ʸ˸�T
for(PackageInfo info : list){
	// ����ʸ˦W��
	sb.append("package name:"+info.packageName+"\n");
	ApplicationInfo aInfo=info.applicationInfo;
	// ������Ϊ�label��
	sb.append("application name:"+aInfo.loadLabel(manager)+"\n");
	// �p�G���M�˵{���ʸ˧t�v���ШD�A�h��������
	if(info.permissions != null){
		for(PermissionInfo p : info.permissions){
			//����p.name������ȥi�Ѩ����Ѫ���D�d�ݨ��餺�e
			sb.append("permission:"+p.name+"\n");
		}
	}
	sb.append("\n");
        }
        txt.setText(sb.toString());
    }
}
