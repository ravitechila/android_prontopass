package info.prontopass.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 1500;
	private SessionPrefs sessionObj;
	private String studentID;
	private Button sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//sp = (Button) findViewById(R.id.splash);

		sessionObj = new SessionPrefs(this);
		
		studentID = sessionObj.getPreference("student_ID");
		
//		sp.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.rotate));

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if ("".equals(studentID)) {
					Intent i = new Intent(SplashScreen.this,
							AskViewActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.lefttoright,
							R.anim.righttoleft);
					

				} else {
					Intent PassToHome = new Intent(getApplicationContext(),
							ProntoPassMainActivity.class);
					PassToHome.putExtra("studentID", ""+studentID);
					startActivity(PassToHome);
					overridePendingTransition(R.anim.lefttoright,
							R.anim.righttoleft);
					
				}
				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}
