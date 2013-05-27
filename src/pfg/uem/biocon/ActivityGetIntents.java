package pfg.uem.biocon;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.utils.Async_Tasks;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.itwizard.mezzofanti.OCR;

public class ActivityGetIntents extends Activity implements Runnable {
	

	private String TAG = getClass().getSimpleName();
	
	private Bundle bundle;
	private AlertDialog.Builder builder;
//	private EditText texto_entrada ;
	private ProgressDialog progress;
	private TextView tv_url ;
	private Bitmap m_bmOCRBitmap;
	private ProgressDialog m_pdOCRInProgress;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getintent);
		
		tv_url = (TextView) findViewById(R.id.TextViewTexto); 
	
		
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
	

	
	
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		// Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    /**
	     * TEXTO / IMAGENES
	     */
	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	            handleSendText(intent); // Handle text being sent
	        } else if (type.equals("")){
//	        	?? URL
	        }else if (type.startsWith("image/")) {
	            handleSendImage(intent); // Handle single image being sent
	        }
	    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendMultipleImages(intent); // Handle multiple images being sent
	        }
	    } else {
	        // Handle other intents, such as being started from the home screen

	    }
	    /**
	     * URL - ??  no funciona 
	     */
	    if (action.equalsIgnoreCase(Intent.ACTION_SEND) && intent.hasExtra(Intent.EXTRA_TEXT)) { 
	        String s = intent.getStringExtra(Intent.EXTRA_TEXT); 
	        handleURL(intent); // Handle text being sent
	    } 
	    

	    /**
	     * NFC
	     */
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {

	    	Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

	    	NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
 			String nachricht = "";	    	
            for (int k = 0; k < rawMsgs.length; k++) {
                msgs[k] = (NdefMessage) rawMsgs[k];
                nachricht = nachricht + " " + msgs;
            }
            
//	 		for (int i = 0; i < rawMsgs.length; i++) {
//	 			msgs[i] = (NdefMessage) rawMsgs[i];
//
//                StringBuilder m_sOCRResultLineMode = new StringBuilder();
//
//            	for (int j = 0; j < msgs[i].getRecords().length; j++) {
//            		try {
//						Log.w(TAG, "NFC = "+new String(msgs[i].getRecords()[j].getPayload(), "UTF-8"));
//						Log.w(TAG, "NFC = "+msgs[i].getRecords()[j].getPayload().toString());
//						Log.w(TAG, "NFC = "+new String(msgs[i].getRecords()[j].getId(), "UTF-8"));
////						Log.w(TAG, "NFC = "+msgs[i].getRecords()[j].toMimeType().toString());
//						Log.w(TAG, "NFC = "+msgs[i].getRecords()[j].toString());
//						Log.w(TAG, "NFC = "+new String(msgs[i].getRecords()[j].getPayload(), "UTF-8"));
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////            		m_sOCRResultLineMode.append(new String(msgs[i].getRecords()[j].getPayload(), "UTF-8"));
//            		
//				}
//            }
	    	
	        
	    	if ("text/plain".equals(type)) {
	    		handleNFCSendText(rawMsgs);
	    	} else{
	    		Intent new_intent = new Intent(ActivityGetIntents.this, ActivityProcesarURLIntent.class);
	        	new_intent.putExtra("url", getIntent().getDataString());
	        	startActivity(new_intent);
	        	finish();
	    	}
	    }
	    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {

	    	Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	    	if ("text/plain".equals(type)) {
	    		handleNFCSendText(rawMsgs);
	    	} else if (type.startsWith("image/")) {
		        	
		    }
	    	
	    	
	    }
	    if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {

	    	Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	    	if ("text/plain".equals(type)) {
	    		handleNFCSendText(rawMsgs);
	    	} else if (type.startsWith("image/")) {
		        	
		    }
	    	
	    }
	    
	    
	    
	    
		
	}
	
	
	




	void handleNFCSendText(Parcelable[] rawMsgs) {

			  if (rawMsgs != null) {
		            NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
		            
		            for (int i = 0; i < rawMsgs.length; i++) {
		                msgs[i] = (NdefMessage) rawMsgs[i];
		                try {
		                	StringBuilder m_sOCRResultLineMode = new StringBuilder();
		                	
		                	for (int j = 0; j < msgs[i].getRecords().length; j++) {
		                		Log.w(TAG, "NFC = "+new String(msgs[i].getRecords()[j].getPayload(), "UTF-8"));
		                		
		                		

		                		m_sOCRResultLineMode.append(new String(msgs[i].getRecords()[j].getPayload(), "UTF-8"));
		                		
		                		
//		                		tv_url.setText(tv_url.getText()+" "+m_sOCRResultLineMode);
		                		
							}

		                	Log.w(TAG, m_sOCRResultLineMode.toString());
	                		Intent intent = new Intent(ActivityGetIntents.this, ActivityResultadosTexto.class);
	                		intent.putExtra("conceptos", m_sOCRResultLineMode.toString());
	                		startActivity(intent);
	                		finish();

	                		
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		       }
  	 	
	  }
	  
	  
	  void handleURL(Intent intent) {
	        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	        if (sharedText != null) {
	            // Update UI to reflect text being shared
		        
		        	tv_url.setText(sharedText);
		        	Log.w(TAG, "TEXTO : "+sharedText);
	        	
		        	Intent new_intent = new Intent(ActivityGetIntents.this, ActivityProcesarURLIntent.class);
		        	new_intent.putExtra("url", sharedText);
		        	startActivity(new_intent);
		        	finish();
	        }
	   }
	
	
	   void handleSendText(Intent intent) {
	        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	        if (sharedText != null) {
	            // Update UI to reflect text being shared
		        
	        	tv_url.setText(sharedText);
	        	Log.w(TAG, "TEXTO : "+sharedText);
	        	
	        	if (sharedText.contains("www.") || sharedText.contains("http://")) {
		        	Intent new_intent = new Intent(ActivityGetIntents.this, ActivityProcesarURLIntent.class);
		        	new_intent.putExtra("url", sharedText);
		        	startActivity(new_intent);
		        	finish();
		        	
		        	
				}else{

					/**
					 * procesar texto y mostrar resultados
					 */

					Log.d(this.getClass().getSimpleName(),"Ckick Boton Scanner");
					
					LayoutInflater inflater = getLayoutInflater();
					
					View view_dialog = inflater.inflate(R.layout.custom_dialog_message1, null);
					TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
			//				tv.setText("");
					
					EditText texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
					
			
					//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
					// Get instance of Vibrator from current Context
					Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			
					// Vibrate for 5 milliseconds
					vib.vibrate(50);
					//BotonCatalogo.setBackgroundResource(R.drawable.catalogo);
			
					
			//		new AsyncTask_Search_Concept_from_Bitmap_webservice(ActivityScrollableMenu.this).execute("");
					new Async_Tasks.AsyncTask_Search_Concept_webservice(texto_entrada.getText().toString(),ActivityGetIntents.this).execute("");
					
					
					
				}

	        	
	        }
	    }

	    void handleSendImage(Intent intent) {
	        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	        if (imageUri != null) {
	            // Update UI to reflect image being shared
	        	Log.w(TAG, "IMAGE = "+imageUri.getPath());
	        	
//	        	Intent new_intent = new Intent(ActivityGetIntents.this, asd.class);
//	        	new_intent.putExtra("imageUri", imageUri);
//	        	startActivity(new_intent);
	        	
	        	    
	        	    
	        	    
	        	
	        	Uri selectedImage = intent.getData();
	        	InputStream is;
					
					Bitmap yourSelectedImage = getBitmap(imageUri);

					
					
//        	        Bitmap yourSelectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        	        
//        	        yourSelectedImage = Bitmap.createBitmap(800, 600, Bitmap.Config.ALPHA_8);;
        	        
        	        
//        	        BitmapFactory.Options options=new BitmapFactory.Options();
//        	        options.inSampleSize = 8;
//        	        
//					Bitmap preview_bitmap=BitmapFactory.decodeStream(is,null,options);
        	        
        	        
        	        
//					BitmapFactory.Options options=new BitmapFactory.Options();
//					options.inSampleSize = 8;
//					Bitmap yourSelectedImage=BitmapFactory.decodeStream(imageStream,null,options);
					
//					Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
					
					
//					Log.v(TAG, "handleMessage() R.id.decode");
//					CompareTime(TAG + "in handler, just received the picture");

					// do ocr
//						byte[] intriga = new byte[1024];
//						// save the file on disk
//						FileOutputStream fs = new FileOutputStream(RESULTS_PATH + "img.jpg");
//						fs.write(intriga, 0, intriga.length);
//						fs.close();
//
//						yourSelectedImage = BitmapFactory.decodeByteArray(intriga, 0, intriga.length);
//						intriga = null;
						System.gc();
						

						
						Log.v(TAG, "w="+yourSelectedImage.getWidth() + " h="+yourSelectedImage.getHeight());
//						if (m_bLineMode) // we crop just the image of interest
//							mBitmap = Bitmap.createBitmap(mBitmap, 256, 768/2-30, 512, 60, null, false);
						// otherwise, we use all image

//					CompareTime(TAG + "starting the thread");
									
					OCR.Initialize();
//					OCR.get().SetLanguage(OCR.mConfig.GetLanguage());
					OCR.get().SetLanguage("spa");
					
					m_bmOCRBitmap = yourSelectedImage;
					DoOCRJob(yourSelectedImage);
					

					
					
				
	        	
	        }
	    }

	    void handleSendMultipleImages(Intent intent) {
	        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	        if (imageUris != null) {
	            // Update UI to reflect multiple images being shared
	        	for (int i = 0; i < imageUris.size(); i++) {
	        		Log.w(TAG, "IMAGE = "+imageUris.get(i).getPath());	
	        		
				}
	        	
	        	Intent new_intent = new Intent(ActivityGetIntents.this, ActivityProcesarURLIntent.class);
	        	new_intent.putExtra("imageUris", imageUris);
	        	startActivity(new_intent);
	        	finish();
	        	
	        }
	    }
	    
	    
	    
	    /**
		 * Show a progress bar and start the OCR thread.
		 * @param bm the bitmap to be OCR-ized.
		 */
		public void DoOCRJob(Bitmap bm)
		{    	
			m_bmOCRBitmap = bm;
			if (bm == null)
				return;

//			if (!m_bLineMode)
//			{
				m_pdOCRInProgress = ProgressDialog.show(this, this.getString(R.string.mezzofanti_ocr_processing_title), 
						this.getString(R.string.mezzofanti_ocr_processing_body_begin) +" "+ OCR.mConfig.GetLanguageMore() + " " + this.getString(R.string.mezzofanti_ocr_processing_body_end), 
						true, true);
				m_pdOCRInProgress.setOnCancelListener( new OnCancelListener() {
					public void onCancel(DialogInterface dialog) 
					{
						android.os.Process.killProcess(android.os.Process.myPid());
					}    		    		
				});
//			}
//			else
//			{
//				m_clCapture.ShowWaiting(getString(R.string.mezzofanti_capturelayout_processing) + " " +
//						OCR.mConfig.GetLanguageMore() + " " + 
//						getString(R.string.mezzofanti_capturelayout_processing2));    		
//			}

			Thread theOCRthread = new Thread(this);
			theOCRthread.start();
		}


		/**
		 * start the OCR thread
		 */
		public void run() 
		{

			// called by the OCR thread
			int iPicWidth  = m_bmOCRBitmap.getWidth();
			int iPicHeight = m_bmOCRBitmap.getHeight();
			int[] iImage = null;
			try
			{
				iImage = new int[iPicWidth * iPicHeight];
				Log.v(TAG, "allocated img buffer: " +iPicWidth + ", "+iPicHeight);
				m_bmOCRBitmap.getPixels(iImage, 0, iPicWidth, 0, 0, iPicWidth, iPicHeight);
				Log.v(TAG, "pix1="+Integer.toHexString(iImage[0]));
			}
			catch (Exception ex)
			{
				Log.v(TAG, "exception: run():" + ex.toString());
				m_bmOCRBitmap = null;
				System.gc();			
			}


			if (iImage != null)
			{
//				String m_sOCRResult = OCR.get().ImgOCRAndFilter(iImage, iPicWidth, iPicHeight, m_bHorizDispAtPicTaken, m_bLineMode);	
				final String m_sOCRResult = OCR.get().ImgOCRAndFilter(iImage, iPicWidth, iPicHeight, true, false);	
				Log.v(TAG, "ocr done text= [" + m_sOCRResult +"]");
				
//				
				
		
				
				
				
				// force free the mem
				iImage = null;
				m_bmOCRBitmap = null;
				System.gc();			

				// bad results, get the (internal) image
				OCR.get().SaveMeanConfidence();

				Log.v(TAG, "----------------------------------------------------------------------------------------------------");
				Log.v(TAG, "starting results handler");
				
//				Intent intent = new Intent(ActivityGetIntents.this, ActivityResultadosTexto.class);
//				intent.putExtra("conceptos", m_sOCRResult);
//				startactivity(intent);
				
				m_bmOCRBitmap = null;
				System.gc();

//				if (m_bLineMode)
//					m_clCapture.ShowWaiting("");    		
//				else

				
				
				runOnUiThread(new Runnable() {
				     public void run() {

				//stuff that updates ui

				    		tv_url.setText(m_sOCRResult);
							m_pdOCRInProgress.dismiss();
							
				    }
				});
				
					
								
			}
			else
			{
				System.gc();
//				m_bOCRInProgress = false;
//				m_MezzofantiMessageHandler.sendEmptyMessage(R.id.mezzofanti_restartCaptureMode);
				return;
			}

//			m_MezzofantiMessageHandler.sendEmptyMessage(R.id.mezzofanti_ocrFinished);
			Log.i(TAG, "pcjpg - finish startPreview()");

//			CompareTime(TAG + "finished the ocr processing");
		} 

		
		private Bitmap getBitmap(Uri uri) {

			InputStream in = null;
			try {
				ContentResolver mContentResolver = getApplicationContext().getContentResolver();
				
			    final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
				in = mContentResolver.openInputStream(uri);

			    // Decode image size
			    BitmapFactory.Options o = new BitmapFactory.Options();
			    o.inJustDecodeBounds = true;
			    BitmapFactory.decodeStream(in, null, o);
			    in.close();



			    int scale = 1;
			    while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > 
			          IMAGE_MAX_SIZE) {
			       scale++;
			    }
//			    Log.d(TAG, "scale = " + scale + ", orig-width: " + o.outWidth + ", 
//			       orig-height: " + o.outHeight);

			    Bitmap b = null;
			    in = mContentResolver.openInputStream(uri);
			    if (scale > 1) {
			        scale--;
			        // scale to max possible inSampleSize that still yields an image
			        // larger than target
			        o = new BitmapFactory.Options();
			        o.inSampleSize = scale;
			        b = BitmapFactory.decodeStream(in, null, o);

			        // resize to desired dimensions
			        int height = b.getHeight();
			        int width = b.getWidth();
			        Log.d(TAG, "1th scale operation dimenions - width: " + width + ",height: " + height);

			        double y = Math.sqrt(IMAGE_MAX_SIZE
			                / (((double) width) / height));
			        double x = (y / height) * width;

			        Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x, 
			           (int) y, true);
			        b.recycle();
			        b = scaledBitmap;

			        System.gc();
			    } else {
			        b = BitmapFactory.decodeStream(in);
			    }
			    in.close();

			    Log.d(TAG, "bitmap size - width: " +b.getWidth() + ", height: " + 
			       b.getHeight());
			    return b;
			} catch (IOException e) {
			    Log.e(TAG, e.getMessage(),e);
			    return null;
			}
	    
		}
	    
	    
}
