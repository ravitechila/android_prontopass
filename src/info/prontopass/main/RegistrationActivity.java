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

public class RegistrationActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/User_Registration.php?";

	private static String TAG = RegistrationActivity.class.getSimpleName();
	private Button Register;
	private EditText et_Name, et_Qualification, et_emailID, et_DOB,
			et_ContactNo, et_Address, et_Password, et_repassword;
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;
	private ConnectionDetector cd;
	// temporary string to show the parsed response
	private String request_url;
	private SessionPrefs sessionObj;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		init();

		Register.setOnClickListener(new View.OnClickListener() {

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

					request_url = String
							.format(urlJsonObj
									+ "Name=%s&Qualification=%s&emailID=%s&DOB=%s&contactNO=%s&Address=%s&Password=%s",
									et_Name.getText().toString(),
									et_Qualification.getText().toString(),
									et_emailID.getText().toString(), et_DOB
											.getText().toString(), et_ContactNo
											.getText().toString(), et_Address
											.getText().toString(), et_Password
											.getText().toString());
					Toast.makeText(getApplicationContext(), "" + request_url,
							2000).show();
					Log.e("req_URL----->", "" + request_url);
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
						Toast.makeText(getApplicationContext(),
								"" + response.toString(), 3000).show();

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
								sessionObj.setPreference("student_ID", ""
										+ StudentID);
								sessionObj.setPreference("student_NAME", ""+StudentName);
								sessionObj.setPreference("student_EMAIL", ""+StudentEmail);
								Intent nextActivity = new Intent(
										getApplicationContext(),
										ProntoPassMainActivity.class);
								startActivity(nextActivity);
								overridePendingTransition(R.anim.lefttoright,
										R.anim.righttoleft);
								RegistrationActivity.this.finish();
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
		Register = (Button) findViewById(R.id.Register);
		et_Name = (EditText) findViewById(R.id.tx_name);
		et_Qualification = (EditText) findViewById(R.id.tx_qualification);
		et_emailID = (EditText) findViewById(R.id.tx_emailadd);
		et_ContactNo = (EditText) findViewById(R.id.tx_mobnumber);
		et_DOB = (EditText) findViewById(R.id.tx_dob);
		et_Address = (EditText) findViewById(R.id.tx_address);
		et_Password = (EditText) findViewById(R.id.tx_password);
		et_repassword = (EditText) findViewById(R.id.tx_repassword);
		sessionObj = new SessionPrefs(this);

		alertDialog = new AlertDialog.Builder(RegistrationActivity.this)
				.create();

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Submitting results...");
		pDialog.setCancelable(false);

		alertDialog = new AlertDialog.Builder(RegistrationActivity.this)
				.create();

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
