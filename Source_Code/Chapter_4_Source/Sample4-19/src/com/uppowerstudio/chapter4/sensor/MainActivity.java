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
 * �P�����d��
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

		// ���SensorManager��H
		manager=(SensorManager) getSystemService(SENSOR_SERVICE);
		// ������O�[�t�׷P����
		aSensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// ������ӷP����
		lSensor=manager.getDefaultSensor(Sensor.TYPE_LIGHT);
		// ����ū׷P����
		tSensor=manager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

		// �H�����ǽT�ʵ��U�ۭq����ť��
		manager.registerListener(sensorListener, lSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(sensorListener, aSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(sensorListener, tSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		// �q�P�����޲z������o�������P�����M��
		List<Sensor> allSensors=manager.getSensorList(Sensor.TYPE_ALL);
		// ��ܦ��h�֭ӷP����
		infoText.setText("�g�˴��Ӥ����"+allSensors.size()+"�ӷP�����A���̤��O�O�G\n");
		// ��ܨC�ӷP�����������T
		for (Sensor s : allSensors) {
			String tempString="\n"+"  �]�ƦW�١G"+s.getName()+"\n"
					+ "  �]�ƪ����G"+s.getVersion()+"\n"+"  �����ӡG"
					+ s.getVendor()+"\n";
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �[�t�׷P����accelerometer"+tempString);
				break;
			case Sensor.TYPE_GYROSCOPE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �������P����gyroscope"+tempString);
				break;
			case Sensor.TYPE_LIGHT:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " ���ҥ��u�P����light"+tempString);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �q�ϳ��P����magnetic field"+tempString);
				break;
			case Sensor.TYPE_ORIENTATION:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " ��V�P����orientation"+tempString);
				break;
			case Sensor.TYPE_PRESSURE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " ���O�P����pressure"+tempString);
				break;
			case Sensor.TYPE_PROXIMITY:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �Z���P����proximity"+tempString);
				break;
			case Sensor.TYPE_TEMPERATURE:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �ū׷P����temperature"+tempString);
				break;
			default:
				infoText.setText(infoText.getText().toString()+s.getType()
						+ " �����P����"+tempString);
				break;
			}
		}
	}

	private SensorEventListener sensorListener=new SensorEventListener() {
		// �P�������ܤƮɩI�s
		public void onSensorChanged(SensorEvent event) {
			float acc=0;
			float light=0;
			float tem=0;
			// �C5���y�@��
			long current=System.currentTimeMillis();
			if (current - lastUpdate > 5000) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					// �p��x,y,z�o3�Ӥ�V�W���������O�[�t��
					acc=(event.values[0]+event.values[1]+event.values[2]) / 3;
				} else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
					// ������ӱj��
					light=event.values[0];
				} else if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
					// ����ū�
					tem=event.values[0];
				}

				accText.setText(getString(R.string.acc)+(int) acc);
				lightText.setText(getString(R.string.light)+(int) light);
				temText.setText(getString(R.string.tem)+tem);

				lastUpdate=current;
			}
		}

		// �P��������ܤƮɩI�s
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};
}
