package com.uppowerstudio.chapter5.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Intent�ϥνd��
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
	
	// �ŧiButton�����ܼ�
	private Button btnStartActivity;
	private Button btnStartService;
	private Button btnStopService;
	private Button btnCallDial;
	private Button btnCallPhone;
	private Button btnSendSms;
	private Button btnSendEmail;
	private Button btnSearchMarket;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        // ���Jmain.xml�G�����
        setContentView(R.layout.main);
        
        // �I�s��l��Button��k
        initButtonControllers();
        
        // �I�s���UButton���@�U�ƥ�B�z��k
        registerButtonHandler();
    }
    
    /**
     * ��l��Button
     */
    private void initButtonControllers(){
    	btnStartActivity=(Button)findViewById(R.id.start_activity_button);
    	btnStartService=(Button)findViewById(R.id.start_service_button);
    	btnStopService=(Button)findViewById(R.id.stop_service_button);
    	btnCallDial=(Button)findViewById(R.id.call_dial_button);
    	btnCallPhone=(Button)findViewById(R.id.call_phone_button);
    	btnSendSms=(Button)findViewById(R.id.send_sms_button);
    	btnSendEmail=(Button)findViewById(R.id.send_email_button);
    	btnSearchMarket=(Button)findViewById(R.id.market_search_button);    	
    }
    
    /**
     * ���UButton���@�U�ƥ�
     */
    private void registerButtonHandler(){
    	// ���U���Ұʷs��Activity�����s���@�U�B�z�ƥ�
    	btnStartActivity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �ϥΦۭq��Action�غcIntent����
				// Action�r��ݭn�P�bAndroidManifest.xml���ϥ�<action>�w�q���ؼиs�ե�W��				//�@�P
				Intent intent=new Intent("action_secondary_activity");
				
				// �I�s��k�Ұʷs��Activity
				startActivity(intent); 
			}
    	});
    	
    	// ���U���Ұʷs��Service�����s���@�U�B�z�ƥ�
    	btnStartService.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �s�W�n�Ұ�Service��Intent��H
				Intent service=new Intent(MainActivity.this, CountService.class);
				
				// �Ұ�Service
				startService(service);
				
			}
    	});
    	
    	// ���U������w�Ұʪ�Service�����s���@�U�B�z�ƥ�
    	btnStopService.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �s�W�n���Service��Intent��H
				Intent service=new Intent(MainActivity.this, CountService.class);
				
				// ����Service
				stopService(service);
			}
    	});
    	
    	// ���U���I�s�q�ܼ������������s���@�U�B�z�ƥ�
    	btnCallDial.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �غc�q�ܸ��XURI�A�q�ܸ��X��5556
				Uri teleUri=Uri.parse("tel:5556");
				// �c��Intent����A�ϥΨt�Φ۱a��ACTION_DIAL�ʧ@
				Intent intent=new Intent(Intent.ACTION_DIAL, teleUri);
				// �Ұ�Activity�I�s��������
				startActivity(intent);
			}
    	});
    	
    	// ���U�������q�ܡ����s���@�U�B�z�ƥ�
    	btnCallPhone.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �غc�q�ܸ��XURI�A�q�ܸ��X��5556
				Uri teleUri=Uri.parse("tel:5556");
				// �غcIntent����A�ϥΨt�Φ۱a��ACTION_CALL�ʧ@
				Intent intent=new Intent(Intent.ACTION_CALL, teleUri);
				// �Ұ�Activity�����q��
				startActivity(intent);
			}
    	});
    	
    	// ���U���I�s�o�e²�T���������s���@�U�B�z�ƥ�
    	btnSendSms.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �غc�o�e²�T�����XURI�A�q�ܸ��X��5556
				Uri uri=Uri.parse("smsto://5556");
				// �غcIntent����A�ϥΨt�Φ۱a��ACTION_SENDTO�ʧ@
				Intent intent=new Intent(Intent.ACTION_SENDTO, uri);
				// �]�m�o�e²�T�����e
				intent.putExtra("sms_body", "SMS²�T�o�e����");
				// �Ұ�Activity�o�e²�T
				startActivity(intent);  
				
			}
    	});
    	
    	// ���U���I�s�o�e�q�l�l�󤶭������s���@�U�B�z�ƥ�
    	btnSendEmail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				// �غcIntent����A�ϥΨt�Φ۱a��ACTION_SEND�ʧ@
				Intent intent=new Intent(Intent.ACTION_SEND);
				// �]�m����H��}
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"zhang@abc.com"});
				// �]�m�۰e�H��}
				intent.putExtra(Intent.EXTRA_CC, new String[]{"li@abc.com"});
				// �]�m�o�e�l�󪺥D�D
				intent.putExtra(Intent.EXTRA_SUBJECT, "���թI�s Intent�覡�o�e�l��");
				// �]�m�o�e�l�󪺤��e
				intent.putExtra(Intent.EXTRA_TEXT, "���ըϥ�����Intent�I�s�覡���}�l��o�e����");
				// �]�m�l�󪺽s�X
				intent.setType("message/rfc822");
				
				// �Ұ�Activity�o�e�l��
				startActivity(intent);			
			}
    	});
    	
    	// ���U���j�M�������Ρ����s���@�U�B�z�ƥ�
    	btnSearchMarket.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �غc�j�MURI��H�A�j�M�ʸ˦W��com.uppowerstudio.mapview������
				Uri uri=Uri.parse("market://search?q=pname:com.uppowerstudio.mapview");  
				// �غcIntent����A�ϥΨt�Φ۱a��ACTION_VIEW�ʧ@
				Intent intent=new Intent(Intent.ACTION_VIEW, uri);   
				// �Ұ�Activity�}�l�j�M
				startActivity(intent);				
			}
    	});
    	
    }
}
