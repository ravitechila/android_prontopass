package info.prontopass.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class LoginActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/User_Login.php?";

	private static String TAG = LoginActivity.class.getSimpleName();
	private Button login;
	private String emailID;
	private String Password;
	EditText emailid, password;
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;

	// temporary string to show the parsed response
	private String request_url;
	private ConnectionDetector cd;
	private SessionPrefs sessionObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		init();

		login_click();

	}

	
	private void login_click() {
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				cd = new ConnectionDetector(getApplicationContext());

				// Check if Internet present
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present

					alertDialog.setTitle("Internet Connection Error");
					alertDialog
							.setMessage("Please connect to working Internet connection");
					alertDialog.show();
				} else {
					emailID = emailid.getText().toString();
					Password = password.getText().toString();
					request_url = String.format(urlJsonObj
							+ "emailID=%s&Password=%s", emailID, Password);
					
					makeJsonObjectRequest();
					
				}
			}
		});
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		showpDialog();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				request_url, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
//						Toast.makeText(getApplicationContext(),
//								"" + response.toString(), 3000).show();

						try {
							// Parsing json object response
							// response will be a json object
							JSONObject data = response.getJSONObject("data");
							String Error_Code = data.getString("Error_Code");
							String Error_Msg = data.getString("Error_Msg");
							String StudentID = data.getString("studentID");
							String StudentName = data.getString("userName");
							String StudentEmail = data.getString("emailID");
							
							if ("1".equals(Error_Code)) {
								hidepDialog();

								sessionObj.setPreference("student_ID", ""+StudentID);
								sessionObj.setPreference("student_NAME", ""+StudentName);
								sessionObj.setPreference("student_EMAIL", ""+StudentEmail);
								

								Intent nextActivity = new Intent(
										getApplicationContext(),
										ProntoPassMainActivity.class);
								startActivity(nextActivity);
								overridePendingTransition(R.anim.lefttoright,
										R.anim.righttoleft);
								LoginActivity.this.finish();
							} else {
								alertDialog.setTitle("Error !");
								alertDialog.setMessage(Error_Msg);
								alertDialog.show();
								hidepDialog();
							}

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						hidepDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	@SuppressWarnings("deprecation")
	private void init() {
		login = (Button) findViewById(R.id.btnSingIn);
		emailid = (EditText) findViewById(R.id.tx_email);
		password = (EditText) findViewById(R.id.tx_password);
		sessionObj = new SessionPrefs(this);
		alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});

	}

	private void showpDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hidepDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}
}
