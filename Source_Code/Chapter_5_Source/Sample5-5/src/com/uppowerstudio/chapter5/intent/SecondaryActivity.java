package com.uppowerstudio.chapter5.intent;

import android.app.Activity;
import android.os.Bundle;

/**
 * Intent�ϥνd��
 * @author UPPower Studio
 *
 */
public class SecondaryActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.secondary);
    }
}