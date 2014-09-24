package info.prontopass.main;

import info.prontopass.main.adapter.NavDrawerListAdapter;

import info.prontopass.main.model.NavDrawerItem;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProntoPassMainActivity extends Activity {

	// json object response url
	private String urlJsonObj = "http://phbjharkhand.in/Prontopass-Webservices/Get_Subject_Details.php?";
	private static String TAG = LoginActivity.class.getSimpleName();

	private AlertDialog alertDialog;
	private ProgressDialog pDialog;

	public static ArrayList<ArrayList<String>> arr_of_subList;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private RelativeLayout mRelativeDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private SessionPrefs sessionObj;
	// nav drawer title
	private CharSequence mDrawerTitle;
	private ConnectionDetector cd;
	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private ArrayList<String> arr_of_subject;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private String StudentName, StudentEmail;
	private TextView stud_name, stud_email;
	private Animation animFadein;
	private LinearLayout logout;

	private String JSONsubjectName, subjectID, sub_subjectName;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_main);

		mTitle = mDrawerTitle = getTitle();
		sessionObj = new SessionPrefs(this);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Importing Subjects...");
		pDialog.setCancelable(false);

		StudentName = sessionObj.getPreference("student_NAME");
		StudentEmail = sessionObj.getPreference("student_EMAIL");

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		arr_of_subList = new ArrayList<ArrayList<String>>();

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		arr_of_subject = new ArrayList<String>();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.layoutDrawer);
		mDrawerList = (ListView) findViewById(R.id.listDrawer);
		mRelativeDrawer = (RelativeLayout) findViewById(R.id.relativeDrawer);
		logout = (LinearLayout) findViewById(R.id.logout);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		stud_name = (TextView) findViewById(R.id.txt_user_name_drawer);
		stud_email = (TextView) findViewById(R.id.txt_user_email_drawer);
		stud_name.setText("" + StudentName);
		stud_email.setText("" + StudentEmail);

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				sessionObj.clearAllPreferences();
				Intent logout = new Intent(ProntoPassMainActivity.this,
						AskViewActivity.class);
				startActivity(logout);
				ProntoPassMainActivity.this.finish();
			}
		});
		login_click();
		// adding nav drawer items to array
		// Home
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
		// .getResourceId(0, -1), true, "12"));
		// // Find People
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
		// .getResourceId(1, -1), true, "10"));
		// // Photos
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
		// .getResourceId(2, -1), true, "17"));
		// // Communities, Will add a counter here
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
		// .getResourceId(3, -1), true, "25"));
		// // Pages
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
		// .getResourceId(4, -1), true, "5"));
		// // What's hot, We will add a counter here
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
		// .getResourceId(5, -1), true, "50+"));
		// navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
		// .getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private void login_click() {

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present

			alertDialog.setTitle("Internet Connection Error");
			alertDialog
					.setMessage("Please connect to working Internet connection");
			alertDialog.show();
		} else {
			// request_url = String.format(urlJsonObj
			// + "emailID=%s&Password=%s", emailID, Password);
			makeJsonObjectRequest();
		}
	}

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		showpDialog();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				urlJsonObj, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						// Toast.makeText(getApplicationContext(),
						// "" + response.toString(), 10000).show();

						try {
							// Parsing json object response
							// response will be a json object
							JSONObject data = response.getJSONObject("data");
							String Error_Code = data.getString("Error_Code");
							String Error_Msg = data.getString("Error_Msg");
							ArrayList<String> mainSubject_arr = new ArrayList<String>();
							if ("1".equals(Error_Code)) {

								JSONArray subList = data
										.getJSONArray("subList");
								JSONArray sub_subList = data
										.getJSONArray("result");
								JSONObject obj = sub_subList.getJSONObject(0);

								for (int i = 0; i < subList.length(); i++) {
									JSONObject ob = subList.getJSONObject(i);

									subjectID = ob.getString("subID");
									JSONsubjectName = ob
											.getString("subjectName");
									mainSubject_arr.add(JSONsubjectName);
									NavDrawerItem item = new NavDrawerItem(
											JSONsubjectName);
									navDrawerItems.add(item);

								}

								for (int i = 0; i < mainSubject_arr.size(); i++) {
									JSONArray arr = obj
											.getJSONArray(mainSubject_arr
													.get(i));
									ArrayList<String> arr_sub_subject = new ArrayList<String>();

									for (int j = 0; j < arr.length(); j++) {
										JSONObject ob_of_sub_list = arr
												.getJSONObject(j);
										arr_sub_subject.add(ob_of_sub_list
												.getString("SubName"));
										Log.d("Sub Subject List = ",
												""
														+ ob_of_sub_list
																.getString("SubName"));

									}
									arr_of_subList.add(arr_sub_subject);
								}

								adapter.notifyDataSetChanged();
								hidepDialog();
								Toast.makeText(getApplicationContext(),
										"Subjects Imported.", 3000).show();

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

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mRelativeDrawer);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		try {
			fragment = new HomeFragment(position, navDrawerItems.get(position)
					.getTitle());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mRelativeDrawer);

		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
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
