package pfg.uem.biocon;

import pfg.uem.biocon.customviews.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ActivityAyuda extends Activity {
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        
        ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setSub_menu_Visibility(View.GONE);
        actionBar.setHomeLogo(R.drawable.bionlpocr);
        actionBar.getHomeLogo().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
				
			}
		});
        actionBar.setDisplayHomeAsUpEnabled(true);
	    
    }
	
}
