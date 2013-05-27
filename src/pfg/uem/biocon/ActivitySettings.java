package pfg.uem.biocon;

import pfg.uem.biocon.customviews.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ActivitySettings extends Activity {
	
	private Bundle bundle;
	private AlertDialog.Builder builder;
	private EditText texto_entrada ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		
		
	    ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
        // You can also assign the title programmatically by passing a
        // CharSequence or resource id.
//        actionBar.setTitle("Black List");
        actionBar.setSub_menu_Visibility(View.GONE);
        actionBar.setHomeLogo(R.drawable.ic_launcher);
        actionBar.getHomeLogo().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(ActivityInformeSemanal.this, ActivityPerfil.class);
//
//				startActivity(intent);
//				
//				finish();
				
				
				
				builder = new AlertDialog.Builder(ActivitySettings.this);
				builder.setMessage("Si cancelas, se perder‡n los datos. ÀQuieres cancelar?")
					.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							finish();
							
						}
					})
				    .setNegativeButton("No", new DialogInterface.OnClickListener() {
						

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
				});
				builder.setTitle(" ");
				builder.setIcon(R.drawable.ic_launcher);
				builder.show();
				
				
				
			}
		});
//        actionBar.setHomeAction(new IntentAction(this, ActivityPerfil.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.addAction( new UpdateAllAction());
//        actionBar.addAction( new Action(){
//
//			@Override
//			public int getDrawable() {
//				// TODO Auto-generated method stub
////				return 0;
//				return R.drawable.action_search;
//			}
//
//			@Override
//			public void performAction(View view) {
//				// TODO Auto-generated method stub
//						final Dialog mDialog = new Dialog(ActivitySettings.this, android.R.style.Theme_Panel);
//						
//						
//						LayoutInflater inflater = getLayoutInflater();
//		//				TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
//						
//						
//						View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
//						TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
//		//				tv.setText("");
//						
//						
//						texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
//						
//						
//						ImageView close = (ImageView) view_dialog.findViewById(R.id.imageViewClose); //id defined in camera.xml
//						close.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								mDialog.cancel();
//							}
//						});
//						
//						LinearLayout codBarras = (LinearLayout) view_dialog.findViewById(R.id.LinearLayoutCodBarras); //id defined in camera.xml
//						codBarras.setOnClickListener(new OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								
//								
//									Log.d(this.getClass().getSimpleName(),"Ckick Boton Scanner");
//									//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
//									// Get instance of Vibrator from current Context
//									Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//			
//									// Vibrate for 5 milliseconds
//									vib.vibrate(50);
//									//BotonCatalogo.setBackgroundResource(R.drawable.catalogo);
//							
//
//									
////									new AsyncTask_Search_Concept_from_Bitmap_webservice(ActivityScrollableMenu.this).execute("");
//									new AsyncTask_Search_Concept_webservice(texto_entrada.getText().toString()).execute("");
//								
//							}
//						});
//						
//						
//		//				View tv_texto = inflater.getinflate(R.id.tv_texto, arg0);
//		//				((TextView) tv_texto).setText(sb.toString());
//						
//		//				mDialog.addContentView(inflater.inflate(R.layout.custom_dialog, null), new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//						mDialog.addContentView(view_dialog, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//						mDialog.show();
//			}
//			
//			
//        });
//        actionBar.addAction( new Action(){
//
//        	@Override
//			public int getDrawable() {
//				// TODO Auto-generated method stub
////				return 0;
//				return R.drawable.action_settings;
//			}
//        	
//    	    @Override
//    	    public void performAction(View view) {
//
//    	    	Intent intent = new Intent(ActivitySettings.this, ActivitySettings.class);
//				startActivity(intent);
//
//    	    }
//			
//        });
        

    	
    	
        
        //Datos Producto
	    bundle = getIntent().getExtras();
		
		if(bundle !=null)
		{
			
//			getcoordinador = bundle.getParcelable("coordinador");
//			Log.v(TAG,"coordinador = "+getcoordinador.getNombre());
			
			
			
		}
		
		
		/*
         * Comprueba el estado de la conexion a internet
         */
		Log.i(this.getClass().getSimpleName(),"Compruebo el estado de la conexion a internet");
		
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
        		Log.w(this.getClass().getSimpleName(),"No hay conexion a interntet!");
				AlertDialog alertDialog = new AlertDialog.Builder(ActivitySettings.this).create();
				
				alertDialog.setTitle("Conexi—n a Internet");
				alertDialog.setMessage("Esta aplicaci—n necesita internet para funcionar correctamente.");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				 
				       //here you can add functions
				 
				    } });
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.show();
				
				
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_activit_main_menu, menu);
		return true;
	}

}
