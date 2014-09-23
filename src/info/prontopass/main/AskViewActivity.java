package info.prontopass.main;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class AskViewActivity extends Activity {

	private TextView btn_login, register;
	// DBAdapter db;
	public boolean emailcheck = true;
	public static String android_id;
	boolean haveConnectedWifi = false;
	boolean haveConnectedMobile = false;

	// private PrefSingleton mMyPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.askview);

		init();

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent PassToHome = new Intent(getApplicationContext(),
						RegistrationActivity.class);
				startActivity(PassToHome);
				overridePendingTransition(R.anim.lefttoright,
						R.anim.righttoleft);
				AskViewActivity.this.finish();
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent PassToHome = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(PassToHome);
				overridePendingTransition(R.anim.lefttoright,
						R.anim.righttoleft);
				AskViewActivity.this.finish();
			}
		});

	}// oncreate ends

	private void init() {

		btn_login = (TextView) findViewById(R.id.signin);
		register = (TextView) findViewById(R.id.signup);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);
	}
}