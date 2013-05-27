package pfg.uem.biocon;

import java.util.ArrayList;

import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.customviews.activitys.ActivityScrollableMenu;
import pfg.uem.biocon.utils.procesamientoCadenas;
import pfg.uem.biocon.webservice.Annotation_CLEiM;
import pfg.uem.biocon.webservice.callWebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityResultadosTexto extends Activity {
	
	private String TAG = getClass().getSimpleName();
	
	private Bundle bundle;
	private AlertDialog.Builder builder;
	private EditText et_resulado;
	private ProgressDialog progress;
	private TextView tv_conceptos ;
	private String conceptos;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultados_texto);
		
		
		
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
			
			conceptos = bundle.getString("conceptos");
			
		}else{
			
			
			SharedPreferences prefs = getSharedPreferences("BioNlpOcr_html_process", Context.MODE_PRIVATE);

			conceptos = prefs.getString("conceptos", conceptos); 
		}
		
		
		/*
         * Comprueba el estado de la conexion a internet
         */
		Log.i(this.getClass().getSimpleName(),"Compruebo el estado de la conexion a internet");
		
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
        		Log.w(this.getClass().getSimpleName(),"No hay conexion a interntet!");
				AlertDialog alertDialog = new AlertDialog.Builder(ActivityResultadosTexto.this).create();
				
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
        
		
		tv_conceptos = (TextView) findViewById(R.id.TextViewTexto); 
		
		
		
		if (conceptos!=null && conceptos!="") {
			tv_conceptos.setText(conceptos);
			

			
//				new RetrieveSiteData(progress,procesar).execute(conceptos);
			
		}else{
			tv_conceptos.setText(conceptos);
		}
		
		
		
		Button procesar = (Button) findViewById(R.id.buttonProcesar);
		procesar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask_Search_Concept_webservice(ActivityResultadosTexto.this, conceptos).execute();
			}
		});
		
		
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
	    Intent myintent=new Intent(ActivityResultadosTexto.this,ActivityScrollableMenu.class);
	    startActivity(myintent);

	    finish();
	    super.onBackPressed();
	}

	
	
	
	
	
	public class AsyncTask_Search_Concept_webservice extends AsyncTask<String,String,String> {
		
			
			 private String TAG = getClass().getSimpleName();
			
			 private Dialog mDialog_Loading ;
			 private Activity activity;
			 private String concepto;
			 private ArrayList<Annotation_CLEiM> items ;
			 
			 /**
			  * Constructor
			  */
			 public AsyncTask_Search_Concept_webservice(Activity Actividad, String concept) {
				super();
				// TODO Auto-generated constructor stub
				this.concepto = concept;
				this.activity = Actividad;
				this.items = new ArrayList<Annotation_CLEiM>();
			}


			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				Log.w("AsyncTask_Search_Concept_webservice", "onPreExecute : "+concepto);
				
				Log.w("AsyncTask_Search_Concept_webservice", "AsyncTask_Searching : "+concepto);
				
				mDialog_Loading = new Dialog(ActivityResultadosTexto.this, android.R.style.Theme_Panel);
				
				final LayoutInflater inflater = activity.getLayoutInflater();
//						TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
				
				
				View view = inflater.inflate(R.layout.custom_dialog_loading1, null);
//					tv.setText("");
				
//					final EditText texto_Entrada = (EditText) view.findViewById(R.id.editText_texto_Entrada); //id defined in camera.xml
				
				
				ImageView close = (ImageView) view.findViewById(R.id.imageViewClose); //id defined in camera.xml
				close.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog_Loading.cancel();
					}
				});
				
				
				mDialog_Loading.addContentView(view, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				mDialog_Loading.show();
				
			}


			@Override
		    protected String doInBackground(String... background) {
				 Log.w("AsyncTask_Search_Concept_webservice", "doInBackground : "+concepto);
				 
				 concepto = procesamientoCadenas.procesaTexto(concepto);
				 
				 StringBuilder url = new StringBuilder();
			        url.append("http://62.212.77.173:8080/CLEiM/XMLServ?text=");
			        url.append(concepto);
			        
//			        "http://orion.esp.uem.es:8080/CLEiM/XMLServ?text=c%C3%A1ncer%20de%20pr%C3%B3stata%20prostate%20cancer"
				 

				 items  = callWebService.WS_getCLEiM_Search_Concept(concepto);
				 
				 if (items!=null) {
					 return "completado";
				 }else{
				 	return "-1";
				 }
		        
		    }
			 
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				
				if (result!=null && result.equals("completado") && items!=null) {
					
					
					Intent intent = new Intent(activity.getApplicationContext(), ListdataActivity.class);
					intent.putExtra("items", items);
					activity.startActivity(intent);
					
					
				} else if (result!=null && result.equals("-1")) {
					
				} else {

				}
				
				mDialog_Loading.cancel();
				
				
			}
			
			@Override
			protected void onProgressUpdate(String... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}
			 
			 @Override
			protected void onCancelled() {
				// TODO Auto-generated method stub
				super.onCancelled();
			}

//				@Override
//				protected void onCancelled(String result) {
//					// TODO Auto-generated method stub
//					super.onCancelled(result);
//				}

			
			 
		 
	}
	
	
	
	
//	
//	public class RetrieveSiteData extends AsyncTask<String, Void, String> {
//		
//		private ProgressBar progress;
//		private Button Go;
//		
//		public RetrieveSiteData(ProgressBar prog,  Button go) {
//			super();
//			// TODO Auto-generated constructor stub
//			this.progress = prog;
//			this.Go = go;
//			
//			
//			
//		}
//		
//		
//
//		/* (non-Javadoc)
//		 * @see android.os.AsyncTask#onPreExecute()
//		 */
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			progress.setVisibility(View.VISIBLE);
//			super.onPreExecute();
//		}
//
//
//
//		@Override
//		protected String doInBackground(String... urls) {
//		    StringBuilder builder = new StringBuilder(100000);
//
//		    for (String url : urls) {
//		        DefaultHttpClient client = new DefaultHttpClient();
//		        HttpGet httpGet = new HttpGet(url);
//		        try {
//		            HttpResponse execute = client.execute(httpGet);
//		            InputStream content = execute.getEntity().getContent();
//
//		            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
//		            String s = "";
//		            while ((s = buffer.readLine()) != null) {
//		                builder.append(s);
//		            }
//
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		        }
//		    }
//
//		    return builder.toString();
//		}
//
//		@Override
//		protected void onPostExecute(final String result) {
//			et_resulado.setText(result);
//			progress.setVisibility(View.GONE);
//			
//			Go.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					vibrate.toque(getApplicationContext());
//					
//					
//					ArrayList<Annotation_CLEiM> items = new ArrayList<Annotation_CLEiM>();
//					
//					
//					
//					items = callWebService.WS_getCLEiM_Search_Concept(result);
//					
//					if (items!=null){
//							
//							if( items.get(0).getConcept().equals("ERROR")) {
////								Toast.makeText(ActivityProcesarURLIntent.this, "ESTA WEB TIENE ELEMENOS NO COMPATIBLES CON EL PROCESAMIENTO DE TEXTO: "+ items.get(0).getSource().substring(0, 30), Toast.LENGTH_LONG).show();
//								
//								try {
////										String cadena = items.get(0).getSource();
//										
////										try {
////											items.get(0).setSource(items.get(0).getSource().replace("sequence: ", "sequance"));
//////											cadena = cadena.replace("sequence:", "sequence");	
////										} catch (Exception e) {
////											// TODO: handle exception
////											Log.e(TAG, e.getMessage());
////										}
//										
//										String[] a =items.get(0).getSource().split(":");
//										String[] b = a[0].split(" ");
//										String caracter = b[b.length-1].trim().toString();
//										Log.e(TAG,"Para futuras veriosnes a–adir este elemento a los filtros: "+caracter);
//										int numero_caracter = 0;
//										try {
//											 numero_caracter = Integer.valueOf(caracter);
//										} catch (Exception e) {
//											// TODO: handle exception
//											Log.e(TAG, "error : "+e.getMessage());
//											Log.e(TAG, "error : "+e.getLocalizedMessage());
//										}
//										Log.e(TAG,"Para futuras veriosnes a–adir este elemento a los filtros: "+caracter);
//										Log.e(TAG,"Para futuras veriosnes a–adir este elemento a los filtros: "+items.get(0).getSource().substring(numero_caracter-2, numero_caracter+2));
//										Toast.makeText(ActivityResultadosTexto.this,"Para futuras veriosnes a–adir este elemento a los filtros: "+items.get(0).getSource().substring(numero_caracter-2, numero_caracter+2) , Toast.LENGTH_LONG).show();
//										Log.e(TAG,"Para futuras veriosnes a–adir este elemento a los filtros: "+items.get(0).getSource().substring(numero_caracter-100, numero_caracter+100));
//										Toast.makeText(ActivityResultadosTexto.this,"Para futuras veriosnes a–adir este elemento a los filtros: "+items.get(0).getSource().substring(numero_caracter-10, numero_caracter+10) , Toast.LENGTH_LONG).show();
//											
//							
//										
//										
//								} catch (Exception e) {
//									// TODO: handle exception
//								} 
//								
//								 
//								
//								finish();
//							} else{
//								
//								Intent intent = new Intent(ActivityResultadosTexto.this, ListdataActivity.class);
//								intent.putExtra("items", items);
//								startActivity(intent);
//								finish();
//							}	
//							
//							
//					}else{
//						Toast.makeText(ActivityResultadosTexto.this, "No se han encontrado datos.", Toast.LENGTH_LONG).show();
//					}
//						
//									
//				}
//			});
//			
//			Go.setClickable(true);
//		}
//	}
//	
//	
	
	
}
