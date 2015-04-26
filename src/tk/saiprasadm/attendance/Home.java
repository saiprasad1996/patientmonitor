package tk.saiprasadm.attendance;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {

	private Button checkbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		checkbtn = (Button) findViewById(R.id.checkbtn);
		setupListeners();
	}

	void setupListeners() {
		checkbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent toRecords = new Intent(getApplicationContext(),
						Records.class);
				startActivity(toRecords);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent aboutActivity = new Intent(getApplicationContext(),
					About.class);
			startActivity(aboutActivity);
			break;

		default:
			super.onOptionsItemSelected(item);
		}
		return true;

	}
}
