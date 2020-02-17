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
    
	// 定義輸出檔案名
	private static final String FILE_NAME="file_output.txt";
	
	// 宣告控制項變數
	private Button saveButton;
	private Button loadButton;
	private EditText txtInput;
	private TextView displayTextView;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 初始化控制項
        saveButton=(Button)findViewById(R.id.button_save_file);
        loadButton=(Button)findViewById(R.id.button_load_file);
        txtInput=(EditText)findViewById(R.id.edit_file_input_data);
        displayTextView=(TextView)findViewById(R.id.file_content);
        
        // 註冊事件監聽器
        saveButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫保存檔案方法
				saveInputDataToFile();
			}
        });
        
        loadButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 呼叫讀取檔案方法
				loadDataFromFile();
			}
        });
    }
    
    /**
     * 保存輸入的資料到file_output.txt文件
     */
    private void saveInputDataToFile(){
    	
    	try {
    		// 構造輸出串流物件
			FileOutputStream fos=openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			
			// 獲取輸入的內容
			String inputFileContent=txtInput.getText().toString();
			// 將字串轉換為byte陣列寫入檔
			fos.write(inputFileContent.getBytes());
			// 關閉檔輸出串流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 從file_output.txt檔案讀取資料
     */
    private void loadDataFromFile(){
    	try {
    		// 構造輸入串流物件
			FileInputStream fis=openFileInput(FILE_NAME);
			
			// 建構byte陣列用於保存讀入的資料
			byte[] buffer=new byte[1024];
			// 讀取資料，並存放於buffer陣列中
			fis.read(buffer);
			
			// 對讀取的資料進行編碼以防止亂碼
			String fileContent=EncodingUtils.getString(buffer, "UTF-8");
			// 顯示在介面上
			displayTextView.setText(fileContent);
			// 設置顯示的顏色為紅色
			displayTextView.setTextColor(Color.RED);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    }    
}
