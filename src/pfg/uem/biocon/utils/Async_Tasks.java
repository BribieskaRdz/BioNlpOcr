package pfg.uem.biocon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import pfg.uem.biocon.ListdataActivity;
import pfg.uem.biocon.R;
import pfg.uem.biocon.webservice.Annotation_CLEiM;
import pfg.uem.biocon.webservice.callWebService;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout.LayoutParams;

public class Async_Tasks {

	
	
	public static class AsyncTask_Search_Concept_webservice extends AsyncTask<String,String,String> {
		
			
			 private String TAG = getClass().getSimpleName();
			
			 private Dialog mDialog_Loading ;
			 private Activity activity;
			 private String concepto;
			 private ArrayList<Annotation_CLEiM> items ;
			 
			 /**
			  * Constructor
			  */
			public AsyncTask_Search_Concept_webservice(String concept,Activity Actividad) {
				super();
				// TODO Auto-generated constructor stub
				this.concepto = concept;
				this.activity = Actividad;
				this.items = new ArrayList<Annotation_CLEiM>();
				Log.w("AsyncTask_Search_Concept_webservice", "AsyncTask_Searching : "+concepto);
				
				mDialog_Loading = new Dialog(Actividad, android.R.style.Theme_Panel);
				
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
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				Log.w("AsyncTask_Search_Concept_webservice", "onPreExecute : "+concepto);
			}
	
	
			@Override
		    protected String doInBackground(String... background) {
	//					 callWebService.WS_getProductsFromRequest("http://orion.esp.uem.es:8080/CLEiM/XMLServ?text=c%C3%A1ncer%20de%20pr%C3%B3stata%20prostate%20cancer");
				 Log.w("AsyncTask_Search_Concept_webservice", "doInBackground : "+concepto);
				 
				 concepto = procesamientoCadenas.procesaTexto(concepto);
				 concepto = concepto.trim();
	
//				 new AsyncTask_getHTMLfromUrl(concepto, activity);
				
				 
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
				Log.w(TAG, "progreso = "+values);
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

	
	
	
	
	public static class AsyncTask_getConceptsFromURL extends AsyncTask<String,String,String> {
		
		
		 private String TAG = getClass().getSimpleName();
		
		 private Dialog mDialog_Loading ;
		 private Activity activity;
		 private String concepto;
		 private ArrayList<Annotation_CLEiM> items ;
		 
		 /**
		  * Constructor
		  */
		public AsyncTask_getConceptsFromURL(String concept,Activity Actividad) {
			super();
			// TODO Auto-generated constructor stub
			this.concepto = concept;
			this.activity = Actividad;
			this.items = new ArrayList<Annotation_CLEiM>();
			Log.w("AsyncTask_getHTMLfromUrl", "AsyncTask_getHTMLfromUrl : "+concepto);
			
			mDialog_Loading = new Dialog(Actividad, android.R.style.Theme_Panel);
			
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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Log.w("AsyncTask_Search_Concept_webservice", "onPreExecute : "+concepto);
		}


		@Override
	    protected String doInBackground(String... background) {
//					 callWebService.WS_getProductsFromRequest("http://orion.esp.uem.es:8080/CLEiM/XMLServ?text=c%C3%A1ncer%20de%20pr%C3%B3stata%20prostate%20cancer");
			 Log.w("AsyncTask_Search_Concept_webservice", "doInBackground : "+concepto);
			 
			 
			 
			 			 
			 
			 // Making HTTP request
	        try {
	 
	        	String query = URLEncoder.encode(concepto, "utf-8");
				 String url = "http://62.212.77.173:8080/CLEiM/XMLServ?text=" + query;

	        	
	        	
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpPost httpPost = new HttpPost(url);
	 
	                HttpResponse httpResponse = httpClient.execute(httpPost);
	                HttpEntity httpEntity = httpResponse.getEntity();
	                InputStream is = httpEntity.getContent();
	 
	                
	                BufferedReader reader = new BufferedReader(new InputStreamReader(
	                        is, "iso-8859-1"), 8);
	                StringBuilder sb = new StringBuilder();
	                String line = null;
	                while ((line = reader.readLine()) != null) {
	                	Log.w(TAG, line);
	                    sb.append(line + "\n");
	                }
	                is.close();
			 
	                
	                
	                items = callWebService.WS_getCLEiM_Search_Concept(concepto);
	                
	                
	                
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return "";			 
	        
	    }
		

		 
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			
//			if (result!=null && result.equals("completado") && items!=null) {
//				
//				
//				Intent intent = new Intent(activity.getApplicationContext(), ListdataActivity.class);
//				intent.putExtra("items", items);
//				activity.startActivity(intent);
//				
//			} else if (result!=null && result.equals("-1")) {
//				
//			} else {
//
//			}
			
			mDialog_Loading.cancel();
			
			
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			Log.w(TAG, "progreso = "+values);
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


	
	
	
	
	
}
