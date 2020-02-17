package com.uppowerstudio.chapter7.recorder.utils;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.StatFs;

/**
 * 公共工具類別
 * @author UPPower Studio
 *
 */
public class CommonUtils {

	// 定義SD卡允許進行儲存操作的最小有效儲存空間，單位為MB
	private static final int MIN_STORAGE_AVAILABLE_SIZE=5;

	// Byte位元組參考量
	private static final long SIZE_BT=1024L;
	// KB位元組參考量
	private static final long SIZE_KB=SIZE_BT * 1024L;
	// MB位元組參考量
	private static final long SIZE_MB=SIZE_KB * 1024L;
	// GB位元組參考量
	private static final long SIZE_GB=SIZE_MB * 1024L;
	// 保留的小數位數
	private static final int SACLE=2;

	/**
	 * 檢查SD卡是否有效
	 * 
	 * @return
	 */
	public static boolean isSdCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 檢查SD卡是否為唯讀狀態
	 * 
	 * @return
	 */
	public static boolean isSdCardReadOnly() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED_READ_ONLY);
	}

	/**
	 * 獲取SD卡有效儲存空間數值
	 * 
	 * @return long-the number of size
	 * 
	 */
	public static long getSdCardAvailableStorageSize() {
		long availabledSize=0L;
		if (isSdCardAvailable()) {
			File path=Environment.getExternalStorageDirectory();
			StatFs statFs=new StatFs(path.getPath());
			long blockSize=statFs.getBlockSize();
			long availableBlocks=statFs.getAvailableBlocks();

			availabledSize=availableBlocks * blockSize;
		}

		return availabledSize;
	}

	/**
	 * 判斷當前SD卡儲存空間是否有效
	 * 
	 * @return
	 */
	public static boolean isSdCardStorageAvailable() {
		long minStorageSizeByte=MIN_STORAGE_AVAILABLE_SIZE * 1024;
		long availableSizeByte=getSdCardAvailableStorageSize();
		return availableSizeByte > minStorageSizeByte;
	}

	/**
	 * 產生對話方塊
	 * 
	 * @param title
	 * @param msg
	 * @param title_icon
	 * @param positiveButtonText
	 * @param negativeButtonText
	 * @param positiveEvent
	 * @param negativeEvent
	 * @return
	 */
	public static Dialog genDialog(Context ctx, String title, String msg,
			int title_icon, int positiveButtonText, int negativeButtonText,
			DialogInterface.OnClickListener positiveEvent,
			DialogInterface.OnClickListener negativeEvent) {
		Builder dialog=new AlertDialog.Builder(ctx).setIcon(title_icon)
				.setTitle(title).setMessage(msg);
		if (positiveEvent !=null) {
			dialog.setPositiveButton(ctx.getString(positiveButtonText),
					positiveEvent);
		}

		if (negativeEvent !=null) {
			dialog.setNegativeButton(ctx.getString(negativeButtonText),
					negativeEvent);
		}

		dialog.setCancelable(false);

		return dialog.create();
	}

	/**
	 * 計算文件大小
	 * @param longSize
	 * @return
	 */
	public static String getFileSize(long longSize) {
		String strSize="";

		if (longSize>=0 && longSize<SIZE_BT) {
			strSize=longSize + " B";
		} else if (longSize>=SIZE_BT&& longSize<SIZE_KB) {
			strSize=longSize/SIZE_BT+" KB";
		} else if (longSize>=SIZE_KB&& longSize<SIZE_MB) {
			strSize=longSize/SIZE_KB+" MB";
		} else if (longSize>=SIZE_MB&& longSize<SIZE_GB) {
			BigDecimal longs=new BigDecimal(Double.valueOf(longSize+"")
					.toString());
			BigDecimal sizeMB=new BigDecimal(Double.valueOf(SIZE_MB+"")
					.toString());
			String result=longs.divide(sizeMB, SACLE,
					BigDecimal.ROUND_HALF_UP).toString();
			strSize=result+" GB";
		}

		return strSize;
	}
	
	/**
	 * 獲取當前日期
	 * @return
	 */
	public static String getCurrentDate() {
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		
		DateFormat dtFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		return dtFmt.format(timestamp);
	}
}
