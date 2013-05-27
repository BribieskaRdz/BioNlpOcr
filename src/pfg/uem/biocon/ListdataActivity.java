package pfg.uem.biocon;

import java.util.ArrayList;

import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.lazylist.ImageAdapterView;
import pfg.uem.biocon.webservice.Annotation_CLEiM;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ListdataActivity extends Activity {

	
	private String TAG = getClass().getSimpleName();
	
	private Bundle bundle;
	private String datos;
	private ArrayList<Annotation_CLEiM> Annotations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listdata);
		
		
		
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
        
		
		//Datos Annotation
	    bundle = getIntent().getExtras();
		
		if(bundle !=null){
			Annotations = bundle.getParcelableArrayList("items");
		}
		
		if (Annotations!=null && Annotations.size()>0) {

			ImageAdapterView adapterscan_grid = new ImageAdapterView(getApplicationContext(),ListdataActivity.this,Annotations);
			
			
			GridView grid = (GridView) findViewById(R.id.gridView);
			
			
//		    View viewer = mSectionsPagerAdapter.getItem(1).getView();
		    
		    grid.setAdapter(adapterscan_grid);
//		    adapter.notifyDataSetChanged().
		    
		    grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
						
						if (Annotations.get(arg2).getUrlen()!=null && !Annotations.get(arg2).getUrlen().equals("")) {
								Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://62.212.77.173:8080/CLEiM/"+Annotations.get(arg2).getLocalurl()));
								startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(), "Url incorrecta: http://62.212.77.173:8080/CLEiM/"+Annotations.get(arg2).getLocalurl(), Toast.LENGTH_LONG).show();
						}
				}
		    	
			});
		    
		    
		    
		} else {
		
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_listdata, menu);
		return true;
	}

}
