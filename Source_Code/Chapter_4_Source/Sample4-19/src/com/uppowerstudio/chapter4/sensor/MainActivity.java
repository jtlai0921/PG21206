package com.uppowerstudio.chapter4.sensor;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 感測器範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView infoText;
	private TextView accText;
	private TextView lightText;
	private TextView temText;

	private SensorManager manager;
	private Sensor aSensor;
	private Sensor lSensor;
	private Sensor tSensor;

	private long lastUpdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		infoText=(TextView) findViewById(R.id.info);
		accText=(TextView) findViewById(R.id.acc);
		lightText=(TextView) findViewById(R.id.light);
		temText=(TextView) findViewById(R.id.tem);

		// 獲取SensorManager對象
		manager=(SensorManager) getSystemService(SENSOR_SERVICE);
		// 獲取重力加速度感測器
		aSensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 獲取光照感測器
		lSensor=manager.getDefaultSensor(Sensor.TYPE_LIGHT);
		// 獲取溫度感測器
		tSensor=manager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

		// 以中等準確性註冊自訂的監聽器
		manager.registerListener(sensorListener, lSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(sensorListener, aSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(sensorListener, tSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		// 從感測器管理器中獲得全部的感測器清單
		List<Sensor> allSensors=manager.getSensorList(Sensor.TYPE_ALL);
		// 顯示有多少個感測器
		infoText.setText("經檢測該手機有"+allSensors.size()+"個感測器，它們分別是：\n");
		// 顯示每個感測器的具體資訊
		for (Sensor s : allSensors) {
			String tempString="\n"+"  設備名稱："+s.getName()+"\n"
					+ "  設備版本："+s.getVersion()+"\n"+"  供應商："
					+ s.getVendor()+"\n";
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 加速度感測器accelerometer"+tempString);
				break;
			case Sensor.TYPE_GYROSCOPE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 陀螺儀感測器gyroscope"+tempString);
				break;
			case Sensor.TYPE_LIGHT:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 環境光線感測器light"+tempString);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 電磁場感測器magnetic field"+tempString);
				break;
			case Sensor.TYPE_ORIENTATION:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 方向感測器orientation"+tempString);
				break;
			case Sensor.TYPE_PRESSURE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 壓力感測器pressure"+tempString);
				break;
			case Sensor.TYPE_PROXIMITY:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 距離感測器proximity"+tempString);
				break;
			case Sensor.TYPE_TEMPERATURE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 溫度感測器temperature"+tempString);
				break;
			default:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " 未知感測器"+tempString);
				break;
			}
		}
	}

	private SensorEventListener sensorListener=new SensorEventListener() {
		// 感測器值變化時呼叫
		public void onSensorChanged(SensorEvent event) {
			float acc=0;
			float light=0;
			float tem=0;
			// 每5秒掃描一次
			long current=System.currentTimeMillis();
			if (current - lastUpdate > 5000) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					// 計算x,y,z這3個方向上的平均重力加速度
					acc=(event.values[0]+event.values[1]+event.values[2]) / 3;
				} else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
					// 獲取光照強度
					light=event.values[0];
				} else if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
					// 獲取溫度
					tem=event.values[0];
				}

				accText.setText(getString(R.string.acc)+(int) acc);
				lightText.setText(getString(R.string.light)+(int) light);
				temText.setText(getString(R.string.tem)+tem);

				lastUpdate=current;
			}
		}

		// 感測器精度變化時呼叫
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
}
