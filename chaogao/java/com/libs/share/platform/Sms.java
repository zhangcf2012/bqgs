/**
 * 
 */
package com.libs.share.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Sms {

	private Context context;

	public Sms(Context context) {
		this.context = context;
	}

	public void share(String text) {
		try {
			Uri uri = Uri.parse("smsto:");
			Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			it.putExtra("sms_body", text);
			((Activity) context).startActivity(it);
		} catch (Exception e) {
			System.out.println("sms error :" + e.getMessage());
		}
	}

}
