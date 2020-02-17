package com.uppowerstudio.chapter5.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Bundle��Ʀ@�νd��
 * @author UPPower Studio
 *
 */
public class DisplayDataActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ���J�G����display.xml
        setContentView(R.layout.display);
        
        // ��l��TextView����Ω���ܦ@�θ��
        TextView textView=(TextView)findViewById(R.id.display_shared_data);
        
        // �qIntent�����T�����oBundle����
        Bundle sharedBundle=getIntent().getExtras();
        if (sharedBundle != null){
        	// �qBundle��������
        	CharSequence sharedInputData=sharedBundle.getCharSequence("shared_data");

        	// ��ܦ@�θ��
        	textView.setText(sharedInputData);
        }
        
    }
}
