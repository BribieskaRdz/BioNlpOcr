package pfg.uem.biocon.utils;

import pfg.uem.biocon.ActivityProcesarURLIntent;
import pfg.uem.biocon.ActivityResultadosTexto;
import pfg.uem.biocon.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;


public class call_async_tasks {

	

	public static void Search_Dialog (final String type, final Activity activity){
			
	
		final Dialog mDialog = new Dialog(activity, android.R.style.Theme_Panel);
		
		
		final LayoutInflater inflater = activity.getLayoutInflater();
//				TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
		
		
		View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
		TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
//				tv.setText("");
		
		
		final EditText texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
		
		if (type.equals("texto")) {
		}else if (type.equals("url")) {
			texto_entrada.setText("http://www.rae.es/drae/srv/search?id=0ttRL0bbjDXX2XFT19Tl");
		}
		
		
		
		ImageView close = (ImageView) view_dialog.findViewById(R.id.imageViewClose); //id defined in camera.xml
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.cancel();
			}
		});
		
		Button search = (Button) view_dialog.findViewById(R.id.buttonSearch);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				Log.d(this.getClass().getSimpleName(),"Ckick Boton Scanner");
				
				
				
				View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
				TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
		//				tv.setText("");

				
				
				
				//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
				// Get instance of Vibrator from current Context
				Vibrator vib = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		
				// Vibrate for 5 milliseconds
				vib.vibrate(50);
				//BotonCatalogo.setBackgroundResource(R.drawable.catalogo);
		
				
		//		new AsyncTask_Search_Concept_from_Bitmap_webservice(ActivityScrollableMenu.this).execute("");
//				new Async_Tasks.AsyncTask_Search_Concept_webservice(texto_entrada.getText().toString(),activity).execute("");
							
				
				String conceptos = texto_entrada.getText().toString();
				Log.w(this.getClass().getSimpleName(), "conceptos ="+conceptos);
		
				if (type.equals("texto")) {
					Intent intent = new Intent(activity.getApplicationContext(), ActivityResultadosTexto.class);
					intent.putExtra("conceptos",conceptos );
					activity.startActivity(intent);
				}else if (type.equals("url")) {
					Intent intent = new Intent(activity.getApplicationContext(), ActivityProcesarURLIntent.class);
					intent.putExtra("url",conceptos );
					activity.startActivity(intent);
				}

				
			}
		});
		
		
//				View tv_texto = inflater.getinflate(R.id.tv_texto, arg0);
//				((TextView) tv_texto).setText(sb.toString());
		
//				mDialog.addContentView(inflater.inflate(R.layout.custom_dialog, null), new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mDialog.addContentView(view_dialog, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mDialog.show();
		
		
		
		
	}

	
	

	public static void Search_Voice_Dialog (final String type, final Activity activity){
			
	
		final Dialog mDialog = new Dialog(activity, android.R.style.Theme_Panel);
		
		
		final LayoutInflater inflater = activity.getLayoutInflater();
//				TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
		
		
		View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
		TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
//				tv.setText("");
		
		
		final EditText texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
		
		if (type.equals("texto")) {
		}else if (type.equals("url")) {
			texto_entrada.setText("https://en.wikipedia.org/wiki/Cancer");
		}
		
		
		
		ImageView close = (ImageView) view_dialog.findViewById(R.id.imageViewClose); //id defined in camera.xml
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.cancel();
			}
		});
		
		Button search = (Button) view_dialog.findViewById(R.id.buttonSearch);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				Log.d(this.getClass().getSimpleName(),"Ckick Boton Scanner");
				
				
				
				View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
				TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
		//				tv.setText("");

				
				
				
				//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
				// Get instance of Vibrator from current Context
				Vibrator vib = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		
				// Vibrate for 5 milliseconds
				vib.vibrate(50);
				//BotonCatalogo.setBackgroundResource(R.drawable.catalogo);
		
				
		//		new AsyncTask_Search_Concept_from_Bitmap_webservice(ActivityScrollableMenu.this).execute("");
//				new Async_Tasks.AsyncTask_Search_Concept_webservice(texto_entrada.getText().toString(),activity).execute("");
							
				
				String conceptos = texto_entrada.getText().toString();
				Log.w(this.getClass().getSimpleName(), "conceptos ="+conceptos);
		
				if (type.equals("texto")) {
					Intent intent = new Intent(activity.getApplicationContext(), ActivityResultadosTexto.class);
					intent.putExtra("conceptos",conceptos );
					activity.startActivity(intent);
				}else if (type.equals("url")) {
					Intent intent = new Intent(activity.getApplicationContext(), ActivityProcesarURLIntent.class);
					intent.putExtra("url",conceptos );
					activity.startActivity(intent);
				}

				
			}
		});
		
		
//				View tv_texto = inflater.getinflate(R.id.tv_texto, arg0);
//				((TextView) tv_texto).setText(sb.toString());
		
//				mDialog.addContentView(inflater.inflate(R.layout.custom_dialog, null), new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mDialog.addContentView(view_dialog, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mDialog.show();
		
		
		
		
	}

	
	
	
}
