package com.uppowerstudio.chapter6.wifimanager.utils;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.uppowerstudio.chapter6.wifimanager.R;

public class CommonUtils {
	public static String convertFrequency(int freq) {
		DecimalFormat df = new DecimalFormat("###.0");

		return df.format(freq / 1000.00);
	}

	public static boolean isEmptyString(String str) {
		if (str != null) {
			if (str.equals("")) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}
	
	public static Dialog genDialog(Context ctx, String title, String msg, int title_icon,
			OnClickListener positiveEvent, OnClickListener negativeEvent) {
		Builder dialog = new AlertDialog.Builder(ctx)
				.setIcon(title_icon).setTitle(title).setMessage(msg);
		if (positiveEvent != null) {
			dialog.setPositiveButton(ctx.getString(R.string.submit),
					positiveEvent);
		}

		if (negativeEvent != null) {
			dialog.setNegativeButton(ctx.getString(R.string.cancel),
					negativeEvent);
		}

		dialog.setCancelable(false);
		return dialog.create();
	}
}
