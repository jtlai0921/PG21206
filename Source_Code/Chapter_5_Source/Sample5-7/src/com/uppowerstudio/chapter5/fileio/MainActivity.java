package com.uppowerstudio.chapter5.fileio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    
	// �w�q��X�ɮצW
	private static final String FILE_NAME="file_output.txt";
	
	// �ŧi����ܼ�
	private Button saveButton;
	private Button loadButton;
	private EditText txtInput;
	private TextView displayTextView;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ��l�Ʊ��
        saveButton=(Button)findViewById(R.id.button_save_file);
        loadButton=(Button)findViewById(R.id.button_load_file);
        txtInput=(EditText)findViewById(R.id.edit_file_input_data);
        displayTextView=(TextView)findViewById(R.id.file_content);
        
        // ���U�ƥ��ť��
        saveButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �I�s�O�s�ɮפ�k
				saveInputDataToFile();
			}
        });
        
        loadButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �I�sŪ���ɮפ�k
				loadDataFromFile();
			}
        });
    }
    
    /**
     * �O�s��J����ƨ�file_output.txt���
     */
    private void saveInputDataToFile(){
    	
    	try {
    		// �c�y��X��y����
			FileOutputStream fos=openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			
			// �����J�����e
			String inputFileContent=txtInput.getText().toString();
			// �N�r���ഫ��byte�}�C�g�J��
			fos.write(inputFileContent.getBytes());
			// �����ɿ�X��y
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * �qfile_output.txt�ɮ�Ū�����
     */
    private void loadDataFromFile(){
    	try {
    		// �c�y��J��y����
			FileInputStream fis=openFileInput(FILE_NAME);
			
			// �غcbyte�}�C�Ω�O�sŪ�J�����
			byte[] buffer=new byte[1024];
			// Ū����ơA�æs���buffer�}�C��
			fis.read(buffer);
			
			// ��Ū������ƶi��s�X�H����ýX
			String fileContent=EncodingUtils.getString(buffer, "UTF-8");
			// ��ܦb�����W
			displayTextView.setText(fileContent);
			// �]�m��ܪ��C�⬰����
			displayTextView.setTextColor(Color.RED);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    }    
}
