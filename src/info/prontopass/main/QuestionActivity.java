package info.prontopass.main;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.protopass.pojo.QuestionPaper;

public class QuestionActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/Get_Question_Answer_Detail.php?";

	private static String TAG = QuestionActivity.class.getSimpleName();
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;
	private TextView btn_Submit, Question, AnswerDesc;
	private RadioGroup radioGroup;
	private RadioButton Ans1, Ans2, Ans3, Ans4;
	// temporary string to show the parsed response
	private String request_url;
	private ConnectionDetector cd;
	private String mainSubject, subSubject;
	ArrayList<QuestionPaper> arr;
	private int count = 0;
	private int resultCorrectAnswer, resultWrongAnswer;
	private int radioID;
	private TextView Showcase_Correct, Showcase_Wrong, Showcase_Total,
			Showcase_Done;
	private SessionPrefs sessionObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quesitons);

		init();

		Question_Fetch();

		btn_Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				radioID = radioGroup.getCheckedRadioButtonId();

				if ("NEXT".equals(btn_Submit.getText().toString())) {
					if (count == arr.size()) {
						Intent nextActivity = new Intent(
								getApplicationContext(), ResultActivity.class);

						nextActivity.putExtra("strCorrect", ""
								+ resultCorrectAnswer);
						nextActivity.putExtra("strWrong", ""
								+ resultWrongAnswer);
						startActivity(nextActivity);

					} else {
						updateCorrectWrong();
						Ans2.setTextColor(getResources()
								.getColor(R.color.black));

						Ans1.setTextColor(getResources()
								.getColor(R.color.black));
						Ans3.setTextColor(getResources()
								.getColor(R.color.black));
						Ans4.setTextColor(getResources()
								.getColor(R.color.black));

						Question.setText(arr.get(count).getQuestion());

						ArrayList<String> random = new ArrayList<String>();

						random.add("" + arr.get(count).getAns1());
						random.add("" + arr.get(count).getCorrectAns2());
						random.add("" + arr.get(count).getAns3());
						random.add("" + arr.get(count).getAns4());

						Random ran = new Random();
						int n;

						for (int i = 0; i < 4; i++) {
							switch (i) {
							case 0:
								n = ran.nextInt(4);
								Ans1.setText(random.get(n));
								random.remove(n);
								break;

							case 1:
								n = ran.nextInt(3);
								Ans2.setText(random.get(n));
								random.remove(n);
								break;

							case 2:
								n = ran.nextInt(2);
								Ans3.setText(random.get(n));
								random.remove(n);
								break;

							case 3:
								Ans4.setText(random.get(0));
								random.remove(0);
								break;

							default:
								break;
							}
						}

						// Ans1.setText(arr.get(count).getAns1());
						// Ans2.setText(arr.get(count).getCorrectAns2());
						// Ans3.setText(arr.get(count).getAns3());
						// Ans4.setText(arr.get(count).getAns4());

						AnswerDesc.setText("Correct Answer :\n\n"
								+ arr.get(count).getCorrectAns2());
						radioGroup.clearCheck();
						AnswerDesc.setVisibility(TextView.INVISIBLE);
						btn_Submit.setText("SUBMIT");
						count++;

					}
				} else if (radioID == -1) {
					alertDialog.setTitle("Alert !");
					alertDialog.setMessage("Please Choose Answer");
					alertDialog.show();
				} else if (radioID == Ans1.getId()) {

					if (Ans1.getText().toString()
							.equals(arr.get(count - 1).getCorrectAns2())) {
						Ans1.setTextColor(getResources()
								.getColor(R.color.green));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						resultCorrectAnswer++;
						updateCorrectWrong();
						btn_Submit.setText("NEXT");
					} else {

						Log.e("++++>>>>>", "1 :" + Ans2.getText().toString()
								+ "\n2 : "
								+ arr.get(count - 1).getCorrectAns2());
						if (Ans2.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans2.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans3.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans3.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans4.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans4.setTextColor(getResources().getColor(
									R.color.green));
						}
						Ans1.setTextColor(getResources().getColor(R.color.red));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						resultWrongAnswer++;
						updateCorrectWrong();
						btn_Submit.setText("NEXT");
					}

				} else if (radioID == Ans2.getId()) {

					if (Ans2.getText().toString()
							.equals(arr.get(count - 1).getCorrectAns2())) {
						Ans2.setTextColor(getResources()
								.getColor(R.color.green));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						btn_Submit.setText("NEXT");
						resultCorrectAnswer++;
						updateCorrectWrong();
					} else {
						if (Ans1.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans1.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans3.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans3.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans4.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans4.setTextColor(getResources().getColor(
									R.color.green));
						}
						Ans2.setTextColor(getResources().getColor(R.color.red));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						resultWrongAnswer++;
						updateCorrectWrong();
						btn_Submit.setText("NEXT");

					}

				} else if (radioID == Ans3.getId()) {

					if (Ans3.getText().toString()
							.equals(arr.get(count - 1).getCorrectAns2())) {
						Ans3.setTextColor(getResources()
								.getColor(R.color.green));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						btn_Submit.setText("NEXT");
						resultCorrectAnswer++;
						updateCorrectWrong();
					} else {
						if (Ans1.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans1.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans2.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans2.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans4.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans4.setTextColor(getResources().getColor(
									R.color.green));
						}
						Ans3.setTextColor(getResources().getColor(R.color.red));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						resultWrongAnswer++;
						updateCorrectWrong();
						btn_Submit.setText("NEXT");
					}
				} else if (radioID == Ans4.getId()) {

					if (Ans4.getText().toString()
							.equals(arr.get(count - 1).getCorrectAns2())) {
						Ans4.setTextColor(getResources()
								.getColor(R.color.green));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						btn_Submit.setText("NEXT");
						resultCorrectAnswer++;
						updateCorrectWrong();
					} else {
						if (Ans1.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans1.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans3.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans3.setTextColor(getResources().getColor(
									R.color.green));
						} else if (Ans2.getText().toString()
								.equals(arr.get(count - 1).getCorrectAns2())) {
							Ans2.setTextColor(getResources().getColor(
									R.color.green));
						}
						Ans4.setTextColor(getResources().getColor(R.color.red));
						AnswerDesc.setVisibility(TextView.VISIBLE);
						resultWrongAnswer++;
						updateCorrectWrong();
						btn_Submit.setText("NEXT");
					}
				}
			}
		});

	}

	private void updateCorrectWrong() {
		Showcase_Correct.setText("" + resultCorrectAnswer);
		Showcase_Wrong.setText("" + resultWrongAnswer);
		Showcase_Done.setText("" + count);
		Showcase_Total.setText("" + arr.size());

	}

	private void Question_Fetch() {
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
					.format(urlJsonObj + "subjectName=%s&subTopicName=%s",
							mainSubject, subSubject);
			makeJsonObjectRequest();
		}
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		showpDialog();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				request_url, null, new Response.Listener<JSONObject>() {

					private String ques, ans1, correct_ans2, ans3, ans4,
							mainsubject_ID, subSubject_ID;

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());

						try {
							// Parsing json object response

							JSONObject data = response.getJSONObject("data");
							String Error_Code = data.getString("Error_Code");
							String Error_Msg = data.getString("Error_Msg");

							if ("1".equals(Error_Code)) {

								JSONArray result = data.getJSONArray("result");

								for (int i = 0; i < result.length(); i++) {
									JSONObject ob = result.getJSONObject(i);
									QuestionPaper quest = new QuestionPaper();

									mainsubject_ID = ob.getString("subjectID");
									subSubject_ID = ob.getString("subTopicID");

									sessionObj.setPreference("subjectID", ""
											+ mainsubject_ID);
									sessionObj.setPreference("subTopicID", ""
											+ subSubject_ID);

									ques = ob.getString("Question");
									ans1 = ob.getString("Ans1");
									correct_ans2 = ob.getString("Correct_Ans2");
									ans3 = ob.getString("Ans3");
									ans4 = ob.getString("Ans4");

									quest.setQuestion(ques);
									quest.setAns1(ans1);
									quest.setCorrectAns2(correct_ans2);
									quest.setAns3(ans3);
									quest.setAns4(ans4);

									if (i == 0) {

										ArrayList<String> random = new ArrayList<String>();

										random.add("" + ans1);
										random.add("" + correct_ans2);
										random.add("" + ans3);
										random.add("" + ans4);

										Random ran = new Random();
										int n;

										for (int j = 0; j < 4; j++) {
											switch (j) {
											case 0:
												n = ran.nextInt(4);
												Ans1.setText(random.get(n));
												random.remove(n);
												break;

											case 1:
												n = ran.nextInt(3);
												Ans2.setText(random.get(n));
												random.remove(n);
												break;

											case 2:
												n = ran.nextInt(2);
												Ans3.setText(random.get(n));
												random.remove(n);
												break;

											case 3:
												Ans4.setText(random.get(0));
												random.remove(0);
												break;

											default:
												break;
											}
										}

										Question.setText(ques);
										AnswerDesc
												.setText("Correct Answer :\n\n"
														+ correct_ans2);
									}
									arr.add(quest);

								}
								count = 1;
								hidepDialog();
								Showcase_Total.setText("" + arr.size());
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

		Intent SubjectName = getIntent();
		mainSubject = SubjectName.getStringExtra("subject_name");
		subSubject = SubjectName.getStringExtra("subtopic_name");

		btn_Submit = (TextView) findViewById(R.id.submit);
		Question = (TextView) findViewById(R.id.quetions);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		Ans1 = (RadioButton) findViewById(R.id.ans1);
		Ans2 = (RadioButton) findViewById(R.id.correcr_ans2);
		Ans3 = (RadioButton) findViewById(R.id.ans3);
		Ans4 = (RadioButton) findViewById(R.id.ans4);

		Showcase_Correct = (TextView) findViewById(R.id.show_right);
		Showcase_Wrong = (TextView) findViewById(R.id.show_wrong);
		Showcase_Total = (TextView) findViewById(R.id.show_total_ques);
		Showcase_Done = (TextView) findViewById(R.id.show_done);

		AnswerDesc = (TextView) findViewById(R.id.correct_ans_desc);

		alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();

		arr = new ArrayList<QuestionPaper>();

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});
		sessionObj = new SessionPrefs(this);
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
