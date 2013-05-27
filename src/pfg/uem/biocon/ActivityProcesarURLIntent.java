package pfg.uem.biocon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.customviews.activitys.ActivityScrollableMenu;
import pfg.uem.biocon.utils.procesamientoCadenas;
import pfg.uem.biocon.webservice.Annotation_CLEiM;
import pfg.uem.biocon.webservice.callWebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityProcesarURLIntent extends Activity {
	
	private String TAG = getClass().getSimpleName();
	
	private Bundle bundle;
	private AlertDialog.Builder builder;
	private EditText et_resulado;
	private ProgressDialog progress;
	private TextView tv_url ;
	private String url;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_procesar_url_intent);
		
		
		
	    ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        // You can also assign the title programmatically by passing a
        // CharSequence or resource id.
//        actionBar.setTitle("Black List");
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
    	
    	
        
        //Datos Producto
	    bundle = getIntent().getExtras();
		
		if(bundle !=null)
		{
			
			url = bundle.getString("url");
			
		}
		
		
		/*
         * Comprueba el estado de la conexion a internet
         */
		Log.i(this.getClass().getSimpleName(),"Compruebo el estado de la conexion a internet");
		
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
        		Log.w(this.getClass().getSimpleName(),"No hay conexion a interntet!");
				AlertDialog alertDialog = new AlertDialog.Builder(ActivityProcesarURLIntent.this).create();
				
				alertDialog.setTitle("Conexi—n a Internet");
				alertDialog.setMessage("Esta aplicaci—n necesita internet para funcionar correctamente.");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				 
				       //here you can add functions
				 
				    } });
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.show();
				
				
		}
        

        et_resulado = (EditText) findViewById(R.id.editTextResultado);
        ProgressBar progress = (ProgressBar) findViewById(R.id.progressBarGetHTML);
        
		
		tv_url = (TextView) findViewById(R.id.TextViewTexto); 
		
		Button procesar = (Button) findViewById(R.id.buttonProcesar);
		
		
		if (url!=null && url!="") {
			tv_url.setText(url);
			
				new RetrieveSiteData(progress,procesar).execute(url);
			
		}
		
		
	}
	    
	    
	    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_activit_main_menu, menu);
		return true;
	}

	
	
	
	@Override
	public void onBackPressed() 
	{
	    Intent myintent=new Intent(ActivityProcesarURLIntent.this,ActivityScrollableMenu.class);
	    startActivity(myintent);

	    finish();
	    super.onBackPressed();
	}

	
	
	public class RetrieveSiteData extends AsyncTask<String, Void, String> {
		
		private ProgressBar progress;
		private Button Go;
		private ArrayList<Annotation_CLEiM> items;
		
		public RetrieveSiteData(ProgressBar prog,  Button go) {
			super();
			// TODO Auto-generated constructor stub
			this.progress = prog;
			this.Go = go;
		}
		
		

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}



		@Override
		protected String doInBackground(String... urls) {
		    StringBuilder builder = new StringBuilder();
		    String conceptos="";
		    for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		            HttpResponse execute = client.execute(httpGet);
		            InputStream content = execute.getEntity().getContent();

		            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		            String s = "";
		            while ((s = buffer.readLine()) != null) {
		                builder.append(s);
		            }

//		            conceptos = builder.toString();
		            Log.v(TAG, conceptos);
		            
		            
		            conceptos = procesamientoCadenas.procesaTexto(builder.toString());
		             items = callWebService.WS_getCLEiM_Search_Concept(conceptos);
		            Log.w(TAG, conceptos);
		            
		            Log.w(TAG, "------------------------------------------");
		            Log.w(TAG, "------------------------------------------");
		            Log.e(TAG, " "+items.size());
		            Log.w(TAG, "------------------------------------------");
		            Log.w(TAG, "------------------------------------------");
		            
		            
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		    
		    return conceptos;
		    
		}

		@Override
		protected void onPostExecute(final String result) {
			et_resulado.setText(result);
			progress.setVisibility(View.GONE);
			Go.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ActivityProcesarURLIntent.this, ListdataActivity.class);
					intent.putExtra("items", items);
					startActivity(intent);
				}
			});
			
			Go.setClickable(true);
				
		}
	}
	
	
	
	
}
