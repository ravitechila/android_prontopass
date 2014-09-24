package info.prontopass.main;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {

	ArrayList<String> str;
	ArrayList<String> sub;

	private String request_url;
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/User_Login.php?";
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;
	private static String TAG = LoginActivity.class.getSimpleName();
	private SessionPrefs sessionObj;
	String subName;
	int position;

	public HomeFragment(int position2, String subject) {
		// TODO Auto-generated constructor stub
		this.position = position2;
		this.subName = subject;
	}

	/*
	 * public HomeFragment(int position) {
	 * 
	 * this.position = position; }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sessionObj = new SessionPrefs(getActivity());

		View rootView = inflater.inflate(
				R.layout.activity_custom_list_view_android_example, container,
				false);
		ListView lv = (ListView) rootView.findViewById(R.id.list);

		try {

			str = ProntoPassMainActivity.arr_of_subList.get(position);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.select_dialog_item, str);

			lv.setAdapter(adapter);
		} catch (Exception e) {

		}

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Intent nextActivity = new Intent(getActivity(),
						QuestionActivity.class);

				getActivity().overridePendingTransition(R.anim.lefttoright,
						R.anim.righttoleft);

				nextActivity.putExtra("subject_name", "" + subName);
				nextActivity.putExtra("subtopic_name", "" + str.get(pos));
				Toast.makeText(getActivity(),
						"1 :" + subName + "\n2 :" + str.get(pos),
						Toast.LENGTH_LONG).show();
				startActivity(nextActivity);

			}
		});
		return rootView;

	}

}
