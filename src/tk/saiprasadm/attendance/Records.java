package tk.saiprasadm.attendance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Records extends Activity {

	private TextView outputview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);

		initComponents();
	}

	private void initComponents() {
		outputview = (TextView) findViewById(R.id.outputtext);
		outputview.setMovementMethod(new ScrollingMovementMethod());
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {

			//new DoProcessinbackground().execute(new String[] {"http://saiprasadm.ml/homeautomation/jsondata.php"});
			new DoProcessinbackground().execute(new String[] {"http://saiprasadm.ml/homeautomation/patientmonitor.php"});

		} else {
			outputview.setText("No network connection available.");
		}

	}

	class DoProcessinbackground extends AsyncTask<String, String, String> {
		String jsondata_;

		@Override
		protected String doInBackground(String... url) {
			
			String resultdata = "";
			InputStream isr = null;

			try {
				// Getting the data from the web
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url[0]);
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
				// converting the data into readable string
				BufferedReader br = new BufferedReader(new InputStreamReader(
						isr));
				StringBuilder sb = new StringBuilder();
				String str = "";
				while ((str = br.readLine()) != null) {
					sb.append(str + "\n");
				}
				isr.close();
				resultdata = sb.toString();
			} catch (Exception e) {
				// Toast.makeText(getApplicationContext(),
				// "Error fetching the data", Toast.LENGTH_SHORT).show();
			}

			// parsing Json
			//VALUES(time,bed,bp,pulse,temp,spo2)
			String jsondata = " ";
			JSONArray jarray;
			try {
				jarray = new JSONArray(resultdata);
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject json = jarray.getJSONObject(i);
					jsondata = jsondata +"\n"+ 
							"Time : "+json.getString("time") + "\n"+
							"Bed No. : "+ json.getString("bed")+"\n"+
							"B.P. : "+ json.getString("bp")+"\n"+
							"Pulse : "+ json.getString("pulse")+"\n"+
							"Temperature : "+ json.getString("temp")+"\n"+
							"SPO2 : "+ json.getString("spo2")+
							
							
							"\n\n";
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsondata_ = jsondata;
			Log.i("debug", "Data " + "\n" + jsondata);



			return jsondata;
		}

		@Override
		protected void onPostExecute(String str) {
			outputview.setText(str);
		}

	/*	String getData() {
			String url = "http://saiprasadm.ml/homeautomation/jsondata.php";
			String resultdata = "";
			InputStream isr = null;

			try {
				// Getting the data from the web
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				HttpResponse response = client.execute(post);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
				// converting the data into readable string
				BufferedReader br = new BufferedReader(new InputStreamReader(
						isr));
				StringBuilder sb = new StringBuilder();
				String str = "";
				while ((str = br.readLine()) != null) {
					sb.append(str + "\n");
				}
				isr.close();
				resultdata = sb.toString();
			} catch (Exception e) {
				// Toast.makeText(getApplicationContext(),
				// "Error fetching the data", Toast.LENGTH_SHORT).show();
			}

			// parsing Json
			String jsondata = "";
			JSONArray jarray;
			try {
				jarray = new JSONArray(resultdata);
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject json = jarray.getJSONObject(i);
					jsondata = jsondata + json.getString("data_sent") + "\n"
							+ json.getString("data_receive");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsondata_ = jsondata;
			Log.i("debug", "Data " + "\n" + jsondata);
			return jsondata;
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.records, menu);
		return true;
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.action_settings:
    		Intent aboutActivity=new Intent(getApplicationContext(),About.class);
    		startActivity(aboutActivity);
    		break;
    	
    	default :
    		super.onOptionsItemSelected(item);
    	}
    	return true;
    
}
}
