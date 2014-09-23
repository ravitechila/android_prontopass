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

public class ResultActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/Add-Score-Detail.php?";

	private static String TAG = ResultActivity.class.getSimpleName();
	private Button NextTest;
	private String Correct, strCorrect, strWrong, Wrong;
	private TextView et_Correct, et_Wrong;
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;

	// temporary string to show the parsed response
	private String request_url;
	private ConnectionDetector cd;
	private SessionPrefs sessionObj;

	private String stud_id, subject_id, sub_subject_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_page);

		init();
		strCorrect = getIntent().getExtras().getString("strCorrect");
		strWrong = getIntent().getExtras().getString("strWrong");
		et_Correct.setText("" + strCorrect);
		et_Wrong.setText("" + strWrong);
		submit_click();

	}

	private void submit_click() {
		NextTest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			
				cd = new ConnectionDetector(getApplicationContext());

				// Check if Internet present
				if (!cd.isConnectingToInternet()) {

					alertDialog.setTitle("Internet Connection Error");
					alertDialog
							.setMessage("Please connect to working Internet connection");
					alertDialog.show();
				} else {
					Correct = et_Correct.getText().toString();
					Wrong = et_Wrong.getText().toString();

					
					request_url = String
							.format(urlJsonObj
									+ "correctAnswers=%s&wrongAnswers=%s&studentID=%s&subjectID=%s&subTopicID=%s",
									Correct, Wrong, stud_id, subject_id,
									sub_subject_id);
					Log.d("+++>>>>>", ""+request_url);
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

						try { // Parsing json object response // response will
								// be a json object
							JSONObject data = response.getJSONObject("data");
							String Error_Code = data.getString("Error_Code");
							String Error_Msg = data.getString("Error_Msg");

							if ("1".equals(Error_Code)) {
								hidepDialog();
								Toast.makeText(getApplicationContext(),
										"Result Saved Successfully",
										Toast.LENGTH_SHORT).show();
								Intent nextActivity = new Intent(
										getApplicationContext(),
										ProntoPassMainActivity.class);
								startActivity(nextActivity);
								ResultActivity.this.finish();
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
								error.getMessage(), Toast.LENGTH_SHORT).show(); // hide
																				// the
																				// progress
																				// dialog
																				// hidepDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	@SuppressWarnings("deprecation")
	private void init() {
		sessionObj = new SessionPrefs(this);
		stud_id = sessionObj.getPreference("student_ID");
		subject_id = sessionObj.getPreference("subjectID");
		sub_subject_id = sessionObj.getPreference("subTopicID");

		NextTest = (Button) findViewById(R.id.btnSingIn);
		et_Correct = (TextView) findViewById(R.id.correctanswer);
		et_Wrong = (TextView) findViewById(R.id.wronganswer);

		alertDialog = new AlertDialog.Builder(ResultActivity.this).create();

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		alertDialog = new AlertDialog.Builder(ResultActivity.this).create();

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
