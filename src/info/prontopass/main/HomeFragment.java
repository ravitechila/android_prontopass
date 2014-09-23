package info.prontopass.main;

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
	String main_Subject;
	String str[];
	private String request_url;
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/User_Login.php?";
	private AlertDialog alertDialog;
	// Progress dialog
	private ProgressDialog pDialog;
	private static String TAG = LoginActivity.class.getSimpleName();
	private SessionPrefs sessionObj;

	int position;

	public HomeFragment(String string, int position) {
		this.main_Subject = string;
		this.position = position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sessionObj = new SessionPrefs(getActivity());

		View rootView = inflater.inflate(
				R.layout.activity_custom_list_view_android_example, container,
				false);
		ListView lv = (ListView) rootView.findViewById(R.id.list);

		switch (position) {
		case 0:
			str = getResources().getStringArray(R.array.maths_subtopics);
			break;
		case 1:
			str = getResources().getStringArray(R.array.science_subtopics);
			break;
		case 2:
			str = getResources().getStringArray(R.array.english_subtopics);
			break;
		case 3:
			str = getResources().getStringArray(R.array.aptitude_subtopics);
			break;
		case 4:
			str = getResources().getStringArray(R.array.logical_subtopics);
			break;
		case 5:
			str = getResources().getStringArray(R.array.biology_subtopics);
			break;

		default:
			break;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.select_dialog_item, str);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String tempmain = main_Subject;
				int tempsub_pos = pos;
				Intent nextActivity = new Intent(getActivity(),
						QuestionActivity.class);

				switch (pos) {

				case 0:
					getActivity().overridePendingTransition(R.anim.lefttoright,
							R.anim.righttoleft);
					nextActivity.putExtra("subject_name", "" + tempmain);
					nextActivity.putExtra("subtopic_name", "" + str[pos]);
					startActivity(nextActivity);
					break;

				case 1:
					getActivity().overridePendingTransition(R.anim.lefttoright,
							R.anim.righttoleft);
					nextActivity.putExtra("subject_name", "" + tempmain);
					nextActivity.putExtra("subtopic_name", "" + str[pos]);
					startActivity(nextActivity);
					break;
				case 2:
					getActivity().overridePendingTransition(R.anim.lefttoright,
							R.anim.righttoleft);
					nextActivity.putExtra("subject_name", "" + tempmain);
					nextActivity.putExtra("subtopic_name", "" + str[pos]);
					startActivity(nextActivity);
					break;

				default:
					break;
				}

			}
		});
		return rootView;

	}

}
