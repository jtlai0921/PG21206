package com.uppowerstudio.chapter8.googlechart;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.util.Log;

/**
 * Google�Ϫ�A��ø�ϵ{���X
 * 
 * @author UPPower Studio
 * 
 */
public class GoogleChart implements StaticConst {
	private Context context;

	public GoogleChart(Context context) {
		this.context=context;
	}

	/**
	 * ����ø�s�Ϫ�ާ@
	 * 
	 * @param charType
	 * @return
	 */
	public InputStream drawChart(String chartType) {
		// �غc�Ω�Ϫ��r��
		StringBuilder url=new StringBuilder(GOOGLE_CHART_URL);
		
		// �Ω�O�sø�s�Ϫ���X�y
		InputStream inputStream=null;
		try {
			// ��u��
			if (CHART_TYPE_LINE.equals(chartType)) {
				// �]�m�Ϫ�j�p
				url.append(PARAM_CHART_SIZE).append("280x200")
					// �]�m�Ϫ�����
					.append(PARAM_CHART_TYPE).append("lc")
					// �]�m�Ϫ���
					.append(PARAM_CHART_DATA).append("t:0,30,45,60,70,90,100|100,80,30,20,5|5,35,45,85,10")
					// �]�m�Ϫ�ø�s�C��
					.append(PARAM_CHART_COLOR).append("FF0000,00FF00,0000CC")
					// �]�m��ܾ�B�ݮy�Ш�׭�
					.append(PARAM_CHART_AXIS).append("x,y")
					// �]�m���D
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_line), ENCODING))
					// �]�m���D�C��Φr��j�p
					.append(PARAM_CHART_TITLE_COLOR_FONT);
					
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_BAR.equals(chartType)) { // ������
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("bvs")					
					.append(PARAM_CHART_DATA).append("t:10,50,60,80,40,55|20,15,40,35,90,20")
					.append(PARAM_CHART_COLOR).append("c6d9fd,4d89f9")
					.append(PARAM_CHART_AXIS).append("x,y")
					// �ۭqX�b�y����ܤ�r
					.append(PARAM_CHART_AXIS_LABEL).append(URLEncoder.encode("0:|"
							+context.getString(R.string.label_jan)
							+"|"
							+context.getString(R.string.label_feb)
							+"|"
							+context.getString(R.string.label_mar)
							+"|"
							+context.getString(R.string.label_apr)
							+"|"
							+context.getString(R.string.label_may)
							+"|"
							+context.getString(R.string.label_jun)))
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_bar), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT)
					// �]�m�Ϩ�
					.append(PARAM_CHART_LEGEND).append(URLEncoder.encode(context.getString(R.string.legend_prod_a) 
							+"|"
							+context.getString(R.string.legend_prod_b)))
					// �]�m�ϥ���ܦ�m
					.append(PARAM_CHART_LEGENT_POSITION).append("r");
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_PIE.equals(chartType)) { // ����
				url.append(PARAM_CHART_SIZE).append("320x200")
					.append(PARAM_CHART_TYPE).append("p3")					
					.append(PARAM_CHART_DATA).append("t:10,30,60")
					.append(PARAM_CHART_COLOR).append("0000FF,ffba51,faf68b")
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_pie), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT)
					// �]�m���ϹϨ�
					.append(PARAM_PIE_GOOGLE_LABEL).append(URLEncoder.encode(
						context.getString(R.string.label_jan)
						+"|"
						+context.getString(R.string.label_feb)
						+"|"
						+context.getString(R.string.label_mar)));
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_MAP.equals(chartType)) { // �a��
				url.append(PARAM_CHART_SIZE).append("320x200")
					.append(PARAM_CHART_TYPE).append("map")
					// �]�m�a���C��M�n���G��ܪ���a�C��
					.append(PARAM_CHART_COLOR).append("B3BCC0|FF0000|0000FF")
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_map), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT)
					.append(PARAM_CHART_LEGEND).append(URLEncoder.encode(context.getString(R.string.legend_china) 
							+"|"
							+context.getString(R.string.legend_america)))					
					.append(PARAM_CHART_LEGENT_POSITION).append("b")
					// �]�m�n���G��ܪ���a
					.append(PARAM_MAP_HIGHLIGHT_COUNTRY).append("CN|US");
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_SCATTER.equals(chartType)) { // ���I��
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("s")					
					.append(PARAM_CHART_DATA).append("t:12,87,75,41,23,96,68,71,34,9|98,60,27,34,56,79,58,74,18,76|84,23,69,81,47,94,60,93,64,54")
					.append(PARAM_CHART_COLOR).append("fde16d|599deb")
					.append(PARAM_CHART_AXIS).append("x,y")
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_scatter), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT)
					.append(PARAM_CHART_LEGEND).append(URLEncoder.encode(context.getString(R.string.legend_prod_a) 
						+"|"
						+context.getString(R.string.legend_prod_b)))					
					.append(PARAM_CHART_LEGENT_POSITION).append("b");
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_VENN.equals(chartType)) { // �ˤ�Ϫ�
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("v")
					.append(PARAM_CHART_DATA).append("t:100,80,60,30,30,30,10")
					.append(PARAM_CHART_COLOR).append("FF6342,ADDE63,63C6DE")
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_venn), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT)
					.append(PARAM_CHART_LEGEND).append(URLEncoder.encode(context.getString(R.string.legend_prod_a) 
							+"|"
							+context.getString(R.string.legend_prod_b)
							+"|"
							+context.getString(R.string.legend_prod_c)))
					.append(PARAM_CHART_LEGENT_POSITION).append("b");
				
				Log.d("ChartAPI", url.toString());
				inputStream=execute(url);
			} else if (CHART_TYPE_RADAR.equals(chartType)) { // �p�F��
				url.append(PARAM_CHART_SIZE).append("280x300")
					.append(PARAM_CHART_TYPE).append("r")
					.append(PARAM_CHART_DATA).append("t:10,20,30,40,50,60,70,80,95")
					.append(PARAM_CHART_AXIS).append("x")
					.append(PARAM_CHART_AXIS_LABEL).append("0:|0|45|90|135|180|225|270|315")
					.append(PARAM_CHART_AXIS_SCOPE).append("0,0.0,360.0")		
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_radar), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT);
				
				Log.d("ChartAPI", url.toString());
				inputStream=execute(url);
			} else if (CHART_TYPE_QR.equals(chartType)) { // �G�����X��
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("qr")
					// �]�m�ͦ��G�����X�����
					.append(PARAM_QR_FORMULA_DATA).append(URLEncoder.encode(context.getString(R.string.qr_data), ENCODING))
					// �]�m�G�����X����X�s�X
					.append(PARAM_QR_OUTPUT_ENCODING);
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_FORMULA.equals(chartType)) { // �ƾǤ�����
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("tx")
					// �]�m�ͦ��ƾǤ��������
					.append(PARAM_QR_FORMULA_DATA).append(URLEncoder.encode("x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}"));
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			} else if (CHART_TYPE_GOOGLE_O_METER.equals(chartType)) { // Google�����L��
				url.append(PARAM_CHART_SIZE).append("280x200")
					.append(PARAM_CHART_TYPE).append("gom")
					.append(PARAM_CHART_DATA).append("t:60")
					.append(PARAM_CHART_AXIS).append("x,y")
					.append(PARAM_CHART_AXIS_LABEL).append(URLEncoder.encode("0:|"
							+context.getString(R.string.label_current)
							+"|1:|"
							+context.getString(R.string.label_low)
							+"|"
							+context.getString(R.string.label_medium)
							+"|"
							+context.getString(R.string.label_high)))
					.append(PARAM_CHART_TITLE).append(URLEncoder.encode(context.getString(R.string.title_google_o_meter), ENCODING))
					.append(PARAM_CHART_TITLE_COLOR_FONT);
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);						
			} else if (CHART_TYPE_COMPLEX.equals(chartType)){ // ����ø�Ͻd��
				url.append(PARAM_CHART_SIZE).append("300x300")
					.append(PARAM_CHART_TYPE).append("lxy")
					.append(PARAM_CHART_DATA).append("t:0|0")
					.append(PARAM_CHART_COLOR).append("F58CA2")
					// �]�m��ƽd��
					.append(PARAM_CHART_DATA_SCOPE).append("-100,100,-75,75")
					// �]�m�ۭq���
					.append(PARAM_CHART_DATA_FUNCATION).append("0,t,0,100,.05,sin(t)*(exp(cos(t))-2.0*cos(4.0*t)-sin(t/12.0)^5)*20|1,t,0,100,.05,cos(t)*(exp(cos(t))-2.0*cos(4.0*t)-sin(t/12.0)^5.0)*20-10")
					// �]�m��R
					.append(PARAM_CHART_SOLID_FILL).append("c,lg,90,FFE7C6,0,76A4FB,1|bg,s,EFEFEF")
					.append(PARAM_CHART_AXIS).append("x,y")
					// �]�m�b��ܼ˦�
					.append(PARAM_CHART_AXIS_LABEL_STYLE).append("0,000000,0,0,_|1,000000,0,0,_");
				
				Log.d("ChartAPI", url.toString());
				// ����ø�s�ާ@
				inputStream=execute(url);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return inputStream;
	}

	/**
	 * ����ø�s�ާ@
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	private InputStream execute(StringBuilder url) throws IOException,
			MalformedURLException, Exception {
		// �i��s��
		HttpURLConnection uc=(HttpURLConnection) new URL(url.toString())
				.openConnection();

		// �]�mURLConnection���󤹳\�����J�B��X�ާ@
		uc.setDoInput(true);
		uc.setDoOutput(true);

		// �����X�y
		InputStream is=uc.getInputStream();
		return is;
	}
}
