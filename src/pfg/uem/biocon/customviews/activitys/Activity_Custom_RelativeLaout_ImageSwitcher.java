package pfg.uem.biocon.customviews.activitys;

import java.util.ArrayList;

import pfg.uem.biocon.R;
import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.customviews.ActionBar.AbstractAction;
import pfg.uem.biocon.customviews.Custom_RelativeLayout_ImageSwitcher;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Activity_Custom_RelativeLaout_ImageSwitcher extends Activity  {

	private  ActionBar actionBar;
	public static String PREFS_NAME = "preferencias";
	private String TAG = this.getClass().getSimpleName();
	private Bundle bundle;
	private ArrayList<String> pictures;


    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image_switcher);

		
		
	     actionBar = (ActionBar) findViewById(R.id.actionbar);
	        // You can also assign the title programmatically by passing a
	        // CharSequence or resource id.
//	        actionBar.setTitle("Black List");
	        actionBar.setHomeLogo(R.drawable.bionlpocr);
	        actionBar.getSub_title().setText("Im�genes");
	        actionBar.getHomeLogo().setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Activity_Custom_RelativeLaout_ImageSwitcher.this, pfg.uem.biocon.customviews.activitys.ActivityScrollableMenu.class);

					startActivity(intent);
					
					finish();
					
					
//					int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
//					SlideoutActivity.prepare(ActivityEvaluacionInforme.this, R.id.inner_content, width);
//					
//					Intent intent = new Intent(ActivityEvaluacionInforme.this,MenuActivity.class);
//					intent.putExtra("coordinador", getcoordinador);
//	   				
//	   				//TIENDAS DE ESE COORDINADOR
//	   				intent.putParcelableArrayListExtra("tiendas", getcoordinador.getTiendas());
//	   				
//					
//					startActivity(intent);
//					overridePendingTransition(0, 0);
				}
			});
//	        actionBar.setHomeAction(new IntentAction(this, ActivityPerfil.createIntent(this), R.drawable.ic_title_home_default));
	        actionBar.setDisplayHomeAsUpEnabled(true);
//	        actionBar.addAction( new UpdateAllAction());
//	        actionBar.addAction( new AddAction());
	        actionBar.addAction( new FinishAction());
	        
	        pictures = new ArrayList<String>();
	        //Datos Producto
		    bundle = getIntent().getExtras();
			
			if(bundle !=null)
			{
				
				pictures = bundle.getStringArrayList("files");
	        	   
			}
			
			

			/*
	         * Comprueba el estado de la conexion a internet
	         */
			Log.i(this.getClass().getSimpleName(),"Compruebo el estado de la conexion a internet");
			
	        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getActiveNetworkInfo();
	        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
	        		Log.w(this.getClass().getSimpleName(),"No hay conexion a interntet!");
					AlertDialog alertDialog = new AlertDialog.Builder(Activity_Custom_RelativeLaout_ImageSwitcher.this).create();
					
					alertDialog.setTitle("Conexi�n a Internet");
					alertDialog.setMessage("Esta aplicaci�n necesita internet para funcionar correctamente.");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					 
					       //here you can add functions
					 
					    	  
					    	  
					    } });
					alertDialog.setIcon(R.drawable.bionlpocr);
					alertDialog.show();
					
			}
	        

	        
	        
	        
	        Custom_RelativeLayout_ImageSwitcher custom_imageSwitcher = (Custom_RelativeLayout_ImageSwitcher) findViewById(R.id.customRelativeLayoutImageSwitcher);
	        custom_imageSwitcher.setmStrings(pictures);
		
		

	}

    
	
	private class FinishAction extends AbstractAction {
	
	    public FinishAction() {
	        super(R.drawable.action_help);
	    }
	
	    @Override
	    public void performAction(View view) {
	//        Toast.makeText(ActivityPerfil.this,
	//                "Example action", Toast.LENGTH_SHORT).show();
	    	
	    
	//    	Intent intent = new Intent(ActivityLazyImageSwitcher.this, .class);
	//
	//		startActivity(intent);
			
			finish();
	
	
	    	
	
	    }
	}




}
