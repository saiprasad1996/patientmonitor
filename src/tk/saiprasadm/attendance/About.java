package tk.saiprasadm.attendance;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class About extends Activity {
	TextView about;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		about=(TextView)findViewById(R.id.abouttxt);
		about.setText("App Designed by SAI PRASAD\n\n Project Software Package by SAI & JN");
	}

	

}
