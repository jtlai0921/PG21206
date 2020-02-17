package com.uppowerstudio.chapter8.googlechart;

/**
 * Google圖表服務靜態參數類別
 * @author UPPower Studio
 *
 */
public interface StaticConst {
	// 定義Google圖表類型
	static final String CHART_TYPE_LINE = "lc";
	static final String CHART_TYPE_BAR = "bc";
	static final String CHART_TYPE_PIE = "pc";
	static final String CHART_TYPE_MAP = "map";
	static final String CHART_TYPE_SCATTER  = "sc";
	static final String CHART_TYPE_VENN = "vc";
	static final String CHART_TYPE_RADAR = "rc";
	static final String CHART_TYPE_QR = "qr";
	static final String CHART_TYPE_FORMULA = "fr";
	static final String CHART_TYPE_GOOGLE_O_METER = "gom";
	static final String CHART_TYPE_COMPLEX = "cmp";
	
	// 定義Google圖表接口地址
	static final String GOOGLE_CHART_URL = "http://chart.apis.google.com/chart";
	
	// 定義圖表的大小參數
	static final String PARAM_CHART_SIZE = "?chs=";
	
	// 定義圖表的類型參數
	static final String PARAM_CHART_TYPE = "&cht=";
	
	// 定義除二維條碼外的數據傳遞參數
	static final String PARAM_CHART_DATA = "&chd=";
	
	// 定義傳遞數據的範圍參數
	static final String PARAM_CHART_DATA_SCOPE = "&chds=";
	
	// 定義二維條碼及數學公式的數據傳遞參數
	static final String PARAM_QR_FORMULA_DATA = "&chl=";
	static final String PARAM_PIE_GOOGLE_LABEL = "&chl=";
	
	// 定義圖表顏色參數
	static final String PARAM_CHART_COLOR = "&chco=";
	
	// 定義圖表標題內容參數
	static final String PARAM_CHART_TITLE = "&chtt=";
	
	// 定義圖表標題顏色、字體大小參數
	static final String PARAM_CHART_TITLE_COLOR_FONT = "&chts=0000FF,20";
	
	// 定義圖例的說明文字參數
	static final String PARAM_CHART_LEGEND = "&chdl=";
	
	// 定義圖例的位置參數
	static final String PARAM_CHART_LEGENT_POSITION = "&chdlp=";
	
	// 定義二維條碼圖輸出編碼格式參數
	static final String PARAM_QR_OUTPUT_ENCODING = "&choe=UTF-8";
	
	// 定義圖表輸出格式參數
	static final String PARAM_CHART_OUTPUT_FORMAT = "&chof=png";
	
	// 定義圖表顯示軸刻度參數
	static final String PARAM_CHART_AXIS = "&chxt=";
	
	// 定義圖表軸刻度範圍參數
	static final String PARAM_CHART_AXIS_SCOPE = "&chxr=";
	
	// 定義圖表軸刻度說明文字參數
	static final String PARAM_CHART_AXIS_LABEL = "&chxl=";
	
	// 定義圖表軸樣式參數
	static final String PARAM_CHART_AXIS_LABEL_STYLE = "&chxs=";
	
	// 定義在地圖上高亮顯示的國家參數
	static final String PARAM_MAP_HIGHLIGHT_COUNTRY = "&chld=";
	
	// 定義自定義函數參數
	static final String PARAM_CHART_DATA_FUNCATION = "&chfd=";
	
	// 定義填充參數
	static final String PARAM_CHART_SOLID_FILL = "&chf=";
	
	// 定義編碼格式
	static final String ENCODING = "UTF-8";
}
