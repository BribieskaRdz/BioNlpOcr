package pfg.uem.biocon.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import pfg.uem.biocon.R;
import pfg.uem.biocon.customviews.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class instalacion {


	  
	  public static void instalar(final Activity activity, final ActionBar actionbar) {
		
		  /**
		   * Mantiene la pantalla activa durante la instalaci—n
		   */
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
				
				 /**
		         * Exite carpeta tessdata
		         */
		        File tess_directory = new File(Environment.getExternalStorageDirectory() + "/tessdata");
		        if(tess_directory.isDirectory()) {
		        	/**
		        	 * El directorio existe comprobamos si existen los archivos
		        	 */
		        	
		        }else{
		            /**
		             * El directorio no existe comprobamos si est‡ descargado el archivo de datos comrprimido 
		             */
		            File dir = Environment.getExternalStorageDirectory();
		            File yourFile = new File(dir, "tessdata.zip");
		            
		            if (yourFile.isFile() && yourFile.canRead()) {
		    			Toast.makeText(activity, "isFile", Toast.LENGTH_SHORT).show();
		            	/**
		            	 * El archivo existe. Hay que descomprimirlo.
		            	 */
		    			
		    			

		    			final AlertDialog.Builder peticion_descarga = new AlertDialog.Builder(activity);
		    			peticion_descarga.setMessage("No est‡n instalados los idiomas. Deseas Descargarlos e instalarlos ahora?");
		    			peticion_descarga.setPositiveButton("SI", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							
								  ProgressBar downloading_peticion_descarga;
								  Dialog mDialog_peticion_descarga;
								  TextView accion_peticion_descarga;
								  
								  
								
									 LayoutInflater inflater = activity.getLayoutInflater();
									  View dialoglayout = inflater.inflate(R.layout.alert_loading_simple, (ViewGroup) activity.getCurrentFocus());
									  mDialog_peticion_descarga = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
									  
									  downloading_peticion_descarga = (ProgressBar) dialoglayout.findViewById(R.id.progressBarDownlaod);
									  downloading_peticion_descarga.setMax(100);
									  
									  accion_peticion_descarga = (TextView) dialoglayout.findViewById(R.id.textViewAccion);
									  accion_peticion_descarga.setText("Descargando lenguajes");
									  
									  LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
									  mDialog_peticion_descarga.addContentView(dialoglayout, params);
									  mDialog_peticion_descarga.show();
								
									  
									  new Async_UnZip(activity, mDialog_peticion_descarga, accion_peticion_descarga, downloading_peticion_descarga).execute();
									  
								
							}
						});
		    			peticion_descarga.setNegativeButton("NO", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Toast.makeText(activity, "Sin idiomas la aplicaci—n no funcionar‡ correctamente", Toast.LENGTH_LONG).show();
//								activity.finish();
							}
						});
		    			peticion_descarga.show();
		    				
		    				
		            	
		    		}else{
		    			/**
		    			 * Ni el directorio ni el archivo comprimido existen. Se descarga
		    			 */
		    	        
		    			
		    			AlertDialog.Builder peticion_descarga = new AlertDialog.Builder(activity);
		    			peticion_descarga.setMessage("No est‡n instalados los idiomas. Deseas Descargarlos e instalarlos ahora?");
		    			peticion_descarga.setPositiveButton("SI", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							
				    			
//			    				ProgressDialog progressComprobarVersion = new ProgressDialog(activity);
//			    	        	progressComprobarVersion.setMessage("Descargando datos. Puede tardar unos minutos con conexiones lentas.");
//			    	    		progressComprobarVersion.setTitle(" ");
//			    	    		progressComprobarVersion.setIcon(R.drawable.ic_launcher);
			//    	    		progressComprobarVersion.show();
			    	    		new taskDoftp(activity,actionbar).execute();
			    	    		
								
							}
						});
		    			peticion_descarga.setNegativeButton("NO", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Toast.makeText(activity, "Sin idiomas la aplicaci—n no funcionar‡ correctamente", Toast.LENGTH_LONG).show();
								activity.finish();
							}
						});
		    			peticion_descarga.show();
		    				
		    	    		
		    		}
		            
		            
		        }

		        
		        
	}


    private static class taskDoftp extends AsyncTask<Long, Integer, Integer>{

    	
		  private FTPClient con;
		  private BufferedOutputStream fos;
		  private String filename;
		  private long size;
		  private Activity activity;
		  private ActionBar actionBar;
		  private ProgressBar downloading;
		  private Dialog mDialog;
		  private TextView accion;
		
		  private Status status ;
		  
		  public taskDoftp(Activity actividad,ActionBar action_bar) {
			// TODO Auto-generated constructor stub
			  this.activity = actividad;
			  this.actionBar = action_bar;
		  }
		  
		  
		    @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
		//	    	this.progressComprobarVersion.show();
			    	actionBar.setProgressBarVisibility(ProgressBar.VISIBLE);
			    	actionBar.setTitle(" Comprobando lenguajes");
			    	
			    	
			    	  LayoutInflater inflater = activity.getLayoutInflater();
					  View dialoglayout = inflater.inflate(R.layout.alert_loading_simple, (ViewGroup) activity.getCurrentFocus());
					  mDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar); 
					  
					  downloading = (ProgressBar) dialoglayout.findViewById(R.id.progressBarDownlaod);
					  downloading.setMax(100);
					  
					  accion = (TextView) dialoglayout.findViewById(R.id.textViewAccion);
					  accion.setText("Descargando lenguajes");
					  
					  LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					  mDialog.addContentView(dialoglayout, params);
					  mDialog.show();
					  
					  
					  
					  
					  super.onPreExecute();
			}

		    
			  /* (non-Javadoc)
			 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
			 */
			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values[0]);
//				Log.i( "AsyncTest", "onProgressUpdate(): " +  String.valueOf( values[0]/100000 ) + " MB ----- "+( values[0] ) + "B    ----  de : " + values.length);
//                _percentField.setText( ( values[0] * 2 ) + "%");
//                _percentField.setTextSize( values[0] );
//                Log.i("instalacion", String.valueOf( (values[0]*100)/1294782 ));
                
//                downloading.setProgress((values[0]*100)/1294782);
                
                downloading.setProgress(values[0]);                
//                Log.i("instalacion", String.valueOf(values[0]));
                
                
			}
			
			

			@Override
	    protected Integer doInBackground(Long... params) {
	        //Log.w("LOGGER", "Starting...doInBackground");
	        
	        try {
	        	 
	        	 actionBar.setProgressBarVisibility(ProgressBar.VISIBLE);
				   con = new FTPClient();
		  				try
		  				{
		  					   String version_actualizar = "";
		  					
		  					   //set time out
		  					   con.setConnectTimeout(9000);

		  					   	//Log.v("instalacion", "conectando");
			  					con.connect("62.212.77.173");
			  					
			  				    
			  				    //Log.v("instalacion", "conectado");
			  				    if (con.login("soda", "pepino")){
			  				    	//Log.v("instalacion", "login");
			  				        con.enterLocalPassiveMode(); // important!
			  				        
			  				        
			  				        Log.v("instalacion", "changeWorkingDirectory -     "+con.printWorkingDirectory());
			  				        
//			  				        con.changeWorkingDirectory("" + "tessdata" + "");
			  			           
			  				        
			  				        Log.v("instalacion", "changeWorkingDirectory -     "+con.printWorkingDirectory());
			  				        
			  				        String[] files = con.listNames();
			  				        FTPFile[] directories = con.listDirectories();
			  				        FTPFile[] archivos = con.listFiles();
			  				        
				  				    for (int i = 0; i < directories.length; i++) {
			  				        	Log.v("instalacion",directories[i].getName().toString());
				  				    }
				  				    for (int i = 0; i < archivos.length; i++) {
			  				        	Log.v("instalacion",archivos[i].getName().toString());
				  				    }
		  				        	
		  				        	
			  				        
			  				        //DESCARGA
			  				    	System.out.println("  ---   Remote system is " + con.getSystemName());
			  				    		
			  				    	con.setFileType(FTP.BINARY_FILE_TYPE);
			  				    	con.enterLocalPassiveMode();
			  				    	
			  				    		filename = "tessdata.zip";
			  				    		
			  				    		
			  				    		for (int i = 0; i < archivos.length; i++) {
				  				        	Log.v("----- instalacion",archivos[i].getName().toString());
				  				        	
				  				        	if (archivos[i].getName().toString().equals(filename)) {
				  				        		size = archivos[i].getSize();
											}
				  				        	
					  				    }
			  				    		
			  				    		try {
			  				    			
			  				    			
											fos = new BufferedOutputStream( new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/"+filename));
											//Log.i("instalacion"," FileOutputStream ");
				  				    		
				  		
				  				    		
				  				    		InputStream input = con.retrieveFileStream("tessdata.zip");
				  				    		
				  				    		
				  				    		byte data[] = new byte[1024];
				  				            long total = 0;
				  				            int count;
				  				            while ((count = input.read(data)) != -1) {
				  				                total += count;
				  				                // publishing the progress....
//				  				                publishProgress((int) (total * 100 / data.length));
				  				                
				  				                publishProgress(Integer.valueOf((int) (((total * 100 / data.length)*1000)/size) ));
//				  				                Log.i("instalacion", String.valueOf( (((int) (total * 100 / data.length))*100)/size ));
				  				                
				  				                
//				  				                publishProgress(count);
				  				                fos.write(data, 0, count);
				  				            }
				  				    		
				  				    		
				  				    		fos.flush();
				  				    		fos.close();
				  				    		

			  				                con.logout();
			  				                con.disconnect();
				  				  		} catch (Exception e) {
			  	    						// TODO: handle exception
				  				  			Log.e("instalacion", "ERROR al obtener la actualizaci—n de Elara  : "+e.getMessage() +"    ");
				  				  			return -4;
			  	    					}
										
			  				    }else{
			  				    	//Log.e("instalacion","Nombre usuario o contrase–a ftp no valido.");
			  				    	Log.e("instalacion", "Nombre usuario o contrase–a ftp no valido. ");
		  				  			return -5;
			  				    }
		  				}
		  				catch (Exception e)
		  				{

		  					Log.e("instalacion", "ERROR de conexion  : "+e.getMessage() +"    ");
		  					return -2;
		  				}
		
		
		  				try
		  				{
		  				    con.logout();
		  				    return 0;	
		  				}
		  				catch (IOException e)
		  				{
		  					//Log.e("instalacion",e.getMessage());
		  					Log.e("instalacion", "ERROR al desconectar del FTP : "+e.getMessage() +"    "); 
		  					return 0;
		  				}
		  				
		  			  
		  				 
	        } catch (Exception e) {
	            //Log.e("instalacion",e.getMessage());
	        	Log.e("instalacion", "ERROR al buscar actualizaciones de Elara: "+e.getMessage() +"    ");
	        	return -1;
	        }
	       
	    }

	    @Override
	    protected void onPostExecute(Integer result) {
	        //Log.w("LOGGER", "...Done");

	    	
	    	if (result==0) {
			    		 try {
			    			 //DESCOMPRIMIR
			    			 
			    			 accion.setText("Descomprimiendo archivos");
			    			 downloading.setProgress(0);
			    			 
			    			 new Async_UnZip(activity, mDialog, accion, downloading).execute();
			    			 
			    			 
			    			 
			    			 
//			    			 File dir = Environment.getExternalStorageDirectory();
//			    			 dir = Environment.getExternalStorageDirectory();
//			    			 File zipfile = new File(dir, "/tessdata.zip");
//			    			 
//			    			 unzipArchive(activity, zipfile, dir.getAbsolutePath(),mDialog, accion,downloading);
			    			 
//			    				File zipFile = zipfile;
//			    				String directory = null;
//			    				directory = zipFile.getParent();
//			    				directory = directory + "/";
//
//			    				new Async_UnZip(zipFile, directory).execute();
			    				
			    			 
			    		} catch (Exception e) {
			    			// TODO: handle exception
			    			//Log.e("instalacion","Error con el ftp :"+e.getMessage());
			    		}
			}else if (result==1){
				
				
			}else if (result==-2){
				  
				Toast.makeText(activity, "ERROR DE CONEXION CON EL SERVIDOR", Toast.LENGTH_LONG).show();
				  accion.setText("ERROR DE CONEXION CON EL SERVIDOR");
				  mDialog.dismiss();
				  activity.finish();
				
			}else {
				
			}
	    	
//	    	mDialog.dismiss();
	       
	        actionBar.setProgressBarVisibility(ProgressBar.GONE);
	        actionBar.setTitle("");
	    }

	}
    
	@SuppressWarnings("unchecked")
	public static void unzipArchive(Activity activity, File archive, String outputDir,Dialog mDialog, final TextView accion, final ProgressBar descomprimidos) {
		
		try {
			
			  ZipFile zipfile = new ZipFile(archive);
					int total_elementos = 0;
					
				for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
					total_elementos = total_elementos+1;
					ZipEntry entry = (ZipEntry) e.nextElement();
				}
	
			  
			  descomprimidos.setMax(total_elementos);

			int numero_elemento = 0;
			for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
				
				
				
				class OneShotTask implements Runnable {
			        int finalnum;
			        OneShotTask(int f) { finalnum = f; }
			        public void run() {
			        	descomprimidos.setProgress(Integer.valueOf(finalnum));
			        }
			    }
				
				
				new Thread(new OneShotTask(numero_elemento)).start();
				
				
				ZipEntry entry = (ZipEntry) e.nextElement();
				numero_elemento = numero_elemento+1;
				unzipEntry(zipfile, entry, outputDir);
			}
			
			
			mDialog.dismiss();
			

			
		} catch (Exception e) {
			Log.e("unzipper","Error while extracting file " + archive);
			Toast.makeText(activity, "Error al extraer los datos. Reinicie la aplicaci—n.", Toast.LENGTH_LONG).show();
			archive.delete();
			mDialog.dismiss();
		}
	}

	private static void unzipEntry(ZipFile zipfile, ZipEntry entry,
			String outputDir) throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}

		Log.e("unzipper","Extracting: " + entry);
		BufferedInputStream inputStream = new BufferedInputStream(
				zipfile.getInputStream(entry));
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(outputFile));

		try {
			IOUtils.copy(inputStream, outputStream);
		} finally {
			outputStream.close();
			inputStream.close();
		}
	}

	private static void createDir(File dir) {
		Log.i("unzipper","Creating dir " + dir.getName());
		if (!dir.mkdirs())
			throw new RuntimeException("Can not create dir " + dir);
	}
	
	
	private static class Async_UnZip extends AsyncTask<Long, Integer, Integer> {

		private Activity activity;
		private Dialog mDialog;
		private TextView accion;
		private ProgressBar downloading;
		
		
		public Async_UnZip(Activity actividad, Dialog MDialog, TextView Accion, ProgressBar Downloading){
			// TODO Auto-generated constructor stub
			this.activity = actividad;
			this.mDialog = MDialog;
			this.accion = Accion;
			this.downloading = Downloading;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Long... params) {

			try {
				
					  
	    			 File zipfile = new File(Environment.getExternalStorageDirectory(), "/tessdata.zip");
//	    			 
	    			 unzipArchive(activity, zipfile, Environment.getExternalStorageDirectory().getAbsolutePath(),mDialog,accion,downloading);
	    			 
	    			 
				
				return 0;
			} catch (Exception e) {

				return -1;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {
			// Log.w("LOGGER", "...Done");

			if (result == 0) {

				try {

				} catch (Exception e) {
					// TODO: handle exception

				}

			} else if (result == -1) {

			} else {

			}

		}

	}
	
	
}
