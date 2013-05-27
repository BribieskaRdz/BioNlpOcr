package pfg.uem.biocon.customviews.activitys;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pfg.uem.biocon.ActivityAyuda;
import pfg.uem.biocon.ActivityLeerNFC;
import pfg.uem.biocon.ActivityProcesarURLIntent;
import pfg.uem.biocon.ActivityResultadosTexto;
import pfg.uem.biocon.ActivitySettings;
import pfg.uem.biocon.R;
import pfg.uem.biocon.customviews.ActionBar;
import pfg.uem.biocon.customviews.ActionBar.Action;
import pfg.uem.biocon.customviews.ViewPagerIndicator;
import pfg.uem.biocon.parcelable.objects.FragmentParcelable;
import pfg.uem.biocon.utils.call_async_tasks;
import pfg.uem.biocon.utils.instalacion;
import pfg.uem.biocon.utils.ocr;
import pfg.uem.biocon.utils.vibrate;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

import com.google.zxing.client.android.CaptureActivity;
import com.itwizard.mezzofanti.OCR;
import com.itwizard.mezzofanti.ResultadoActivity;


@SuppressLint("NewApi")
public class ActivityScrollableMenu extends FragmentActivity {
	
    public static  ArrayList<String> ComentariosActualizados;
	
    
	private String TAG = getClass().getSimpleName();
	
	private ActionBar actionBar;
	private Bundle bundle;
	private static final int SELECT_PHOTO = 100;
	private static ImageView Camera_ocr;
	private  ArrayList<FragmentParcelable> departments; 
	private ProgressDialog progress;
	private PagerAdapter mPagerAdapter;
    ViewPager  mViewPager;
    ViewPagerIndicator mIndicator;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departments_selection_gesture);
        
        ComentariosActualizados = new ArrayList<String>();
    	progress = new ProgressDialog(this);
        mIndicator = (ViewPagerIndicator)findViewById(R.id.indicator);
//        mIndicator.setVisibility(View.INVISIBLE);
        
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        actionBar.setSub_menu_Visibility(View.GONE);
        actionBar.setHomeLogo(R.drawable.bionlpocr);
        actionBar.getHomeLogo().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "numero de procesadores = "+Runtime.getRuntime().availableProcessors(), Toast.LENGTH_LONG).show();
			}
		});
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.addAction( new Action(){
			@Override
			public int getDrawable() {
				// TODO Auto-generated method stub
				return R.drawable.action_search;
			}

			@Override
			public void performAction(View view) {
				// TODO Auto-generated method stub
				call_async_tasks.Search_Dialog("texto",ActivityScrollableMenu.this);
			}
        });
        actionBar.addAction( new Action(){
        	@Override
			public int getDrawable() {
				// TODO Auto-generated method stub
				return R.drawable.action_settings;
			}
        	
    	    @Override
    	    public void performAction(View view) {
    	    	Intent intent = new Intent(ActivityScrollableMenu.this, ActivitySettings.class);
				startActivity(intent);
    	    }
        });
        

        //Datos Producto
	    bundle = getIntent().getExtras();
		
		if(bundle !=null)
		{
     	   
			
		}
		
		
		/*
         * Comprueba el estado de la conexion a internet
         */
		Log.i(this.getClass().getSimpleName(),"Compruebo el estado de la conexion a internet");
		
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
        		Log.w(this.getClass().getSimpleName(),"No hay conexion a interntet!");
				AlertDialog alertDialog = new AlertDialog.Builder(ActivityScrollableMenu.this).create();
				
				alertDialog.setTitle("Conexi—n a Internet");
				alertDialog.setMessage("Esta aplicaci—n necesita internet para funcionar correctamente.");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				 
				       //here you can add functions
				 
				    } });
				alertDialog.setIcon(R.drawable.bionlpocr);
				alertDialog.show();
				
				
		}
        
			
			
        
        try {
        	String[] languajes = OCR.getLanguagesNative();
        	if (languajes!=null && languajes.length>0) {
            	for (int i = 0; i < languajes.length; i++) {
    				Log.w(TAG,languajes[i]);
    			}
			}else{
				Toast.makeText(ActivityScrollableMenu.this, "No est‡n instalados los Idiomas", Toast.LENGTH_SHORT).show();
				
				instalacion.instalar(ActivityScrollableMenu.this,actionBar);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
       
		
		
        
        
   	 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
     
     
	 
	 
	 departments = new ArrayList<FragmentParcelable>();
	    FragmentParcelable dep1 = new FragmentParcelable();
	    dep1.setComentario("dep1");
	    dep1.setDescription("dep1");
	    dep1.setComentarioActualizado("dep1ac");
	    dep1.setId("dep1");
	    departments.add(dep1);
	    FragmentParcelable dep2 = new FragmentParcelable();
	    dep2.setComentario("dep2");
	    dep2.setDescription("dep2");
	    dep2.setComentarioActualizado("dep2ac");
	    dep2.setId("dep2");
	    departments.add(dep2);

	    FragmentParcelable dep3 = new FragmentParcelable();
	    dep3.setComentario("dep3");
	    dep3.setDescription("dep3");
	    dep3.setComentarioActualizado("dep3ac");
	    dep3.setId("dep3");
	    departments.add(dep3);

	    FragmentParcelable dep4 = new FragmentParcelable();
	    dep4.setComentario("dep4");
	    dep4.setDescription("dep4");
	    dep4.setComentarioActualizado("dep4ac");
	    dep4.setId("dep4");
	    departments.add(dep4);

	    FragmentParcelable dep5 = new FragmentParcelable();
	    dep5.setComentario("dep5");
	    dep5.setDescription("dep5");
	    dep5.setComentarioActualizado("dep5ac");
	    dep5.setId("dep5");
	    departments.add(dep5);

	    FragmentParcelable dep6 = new FragmentParcelable();
	    dep6.setComentario("dep6");
	    dep6.setDescription("dep6");
	    dep6.setComentarioActualizado("dep6ac");
	    dep6.setId("dep6");
	    departments.add(dep6);


	    
	 
	 // Create our custom adapter to supply pages to the viewpager.
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),departments);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        

        
        // Set the indicator as the pageChangeListener
        mViewPager.setOnPageChangeListener(mIndicator);
        
        
        // Initialize the indicator. We need some information here:
        // * What page do we start on.
        // * How many pages are there in total
        // * A callback to get page titles
        mIndicator.init(1, mPagerAdapter.getCount(), mPagerAdapter);
		Resources res = getResources();
		Drawable prev = res.getDrawable(R.drawable.indicator_prev_arrow);
		Drawable next = res.getDrawable(R.drawable.indicator_next_arrow);
		mIndicator.setFocusedTextColor(new int[]{255, 0, 0});
        
		mViewPager.setCurrentItem(1, false);
		
		mIndicator.setArrows(prev, next);
		
		mIndicator.setOnClickListener(new OnIndicatorClickListener());
		
		
		// Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if  (type.startsWith("image/")) {
	            handleSendImage(intent); // Handle single image being sent
	        }
	    } else {
	        // Handle other intents, such as being started from the home screen
	    }
	
	
	    
		
	}

	
	
	   void handleSendText(Intent intent) {
	        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	        if (sharedText != null) {
	            // Update UI to reflect text being shared
	        }
	    }

	    void handleSendImage(Intent intent) {
	        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	        if (imageUri != null) {
	            // Update UI to reflect image being shared
	        }
	    }

	    void handleSendMultipleImages(Intent intent) {
	        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	        if (imageUris != null) {
	            // Update UI to reflect multiple images being shared
	        }
	    }
	    
	    
    


	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	// TODO Auto-generated method stub
    	super.onConfigurationChanged(newConfig);
    	Log.w(TAG, "onConfigurationChanged : "+newConfig.orientation);
    }


    public ArrayList<FragmentParcelable> getdepartments(){
    	return departments;
    }
    
    
    public void setdepartments(ArrayList<FragmentParcelable> departments){
    	this.departments = departments;
    }


	
	
	
    
	class OnIndicatorClickListener implements ViewPagerIndicator.OnClickListener{
		@Override
		public void onCurrentClicked(View v) {
			Log.w(TAG,"onCurrentClicked");
//			Toast.makeText(ViewPagerIndicatorActivity.this, "Hello", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onNextClicked(View v) {
			Log.w(TAG,"onNextClicked");
			mViewPager.setCurrentItem(Math.min(mPagerAdapter.getCount() - 1, mIndicator.getCurrentPosition() + 1));
		}

		@Override
		public void onPreviousClicked(View v) {
			Log.w(TAG,"onPreviousClicked"); 
			mViewPager.setCurrentItem(Math.max(0, mIndicator.getCurrentPosition() - 1));
		}
    	
    
	
	}
    
	
	class PagerAdapter extends FragmentPagerAdapter implements ViewPagerIndicator.PageInfoProvider{
		
		
		private ArrayList<FragmentParcelable> departments; 
		
		
		
		public PagerAdapter(FragmentManager fm,ArrayList<FragmentParcelable> departments_list) {
					
			super(fm);

			this.departments = departments_list;
			
			
		}

		@Override
		public ItemFragment getItem(int pos) {
				
			ItemFragment respuesta = new ItemFragment();
			
				
				Log.w("PAGER ADAPTER", "getItem  - llama a ItemFragment.newInstance(");
				respuesta = ItemFragment.newInstance( pos,departments.get(pos).getDescription(),departments.get(pos).getComentario(),departments.get(pos).getId());
				if (departments.get(pos).getComentario().equals("")) {
					// TODO: handle exception
					Log.w("PAGER ADAPTER-getItem ", " No hay comentario y lo estas pasando, dara error -- controlado ");
					respuesta = ItemFragment.newInstance( pos,departments.get(pos).getDescription(),departments.get(pos).getComentario(),departments.get(pos).getId());
				}
				
				return respuesta;
				
		}

		@Override
		public int getCount() {
			return departments.size();
		}
		
		public void updatefragment(int pos){
			
		}
		
		public ArrayList<FragmentParcelable> getDepartments(){
			return departments;
		}
		
		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			return super.instantiateItem(container, position);
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public String getTitle(int pos){
			switch (pos) {
			case 0:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.Categorias));
				return getResources().getString(R.string.Categorias);
			case 1:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.Inicio));
				return getResources().getString(R.string.Inicio);
			case 2:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.Texto));
				return getResources().getString(R.string.Texto);
			case 3:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.Camara));
				return getResources().getString(R.string.Camara);
			case 4:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.Imagen));
				return getResources().getString(R.string.Imagen);
			case 5:
				Log.w("PAGE ADAPTER",getResources().getString(R.string.NFC));
				return getResources().getString(R.string.NFC);
			default:
				Log.w("PAGE ADAPTER","getTitle" + (pos+1)+"/"+getCount());
				return (pos+1)+"/"+getCount();
			}
			

			
			
		}

		@Override
		public void finishUpdate(View container) {
			super.finishUpdate(container);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return super.isViewFromObject(view, object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
			super.restoreState(state, loader);
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			Log.w("PAGE ADAPTER","saveState");
			return super.saveState();
		}

		@Override
		public void startUpdate(View container) {
			super.startUpdate(container);
		}
		
	}
	
	
	
	public static class ItemFragment extends ListFragment {
		
			
			 private ProgressDialog progress;
			private static String TAG ;
			 
			
			
			int posicion;
			String departmentid;
			String descripcion;
			String comentario;
			String coordinadorid;
			String user;
			String region;
			String IsRemoved;
			String Year;
			String Week;
			
			
//			private DBAdapterInformeSemanal DBInformeSemanal;
			
			View v,tv;
			
			

			public static final String[] list = new String[]{""};


			View viewcomentario; 
			

			
			static ItemFragment newInstance( int pos, String descripcion,String comentario,String departmentid) {
			
				ItemFragment f = new ItemFragment();

		        // Supply num input as an argument.
		        Bundle args = new Bundle();

		        
		        
		        Log.v("ItemFragment", pos +"    ---   NEWINSTANCE dentro del metodo");
		        
		        
		        
		        Log.v("ItemFragment"," - NEWINSTANCE comentario = "+comentario);
//		        Log.v("ItemFragment"," - NEWINSTANCE comentarioActualizado = "+ComentariosActualizados.get(pos-1));
		        
		        args.putInt("posicion", pos+1);
		        args.putString("descripcion", descripcion);
		        args.putString("comentario", comentario);
//		        args.putString("comentarioActualizado",ComentariosActualizados.get(pos-1));
		        args.putString("departmentid", departmentid);

		        f.setArguments(args);

		        return f;
		    }
			
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				
				TAG = getClass().getSimpleName(); 
			
				progress = new ProgressDialog(getActivity().getApplicationContext());
				
				try {
					this.posicion = (getArguments().getInt("posicion"));
					this.descripcion = (getArguments().getString("descripcion"));
					
					this.departmentid = (getArguments().getString("departmentid"));
					this.coordinadorid = (getArguments().getString("coordinadorid"));
					this.user = (getArguments().getString("user"));
					this.region = (getArguments().getString("region"));
					this.IsRemoved = (getArguments().getString("IsRemoved"));
					
					this.comentario = (getArguments().getString("comentario"));
//					this.comentarioActualizado = (getArguments().getString("comentarioActualizado"));

					this.Year = (getArguments().getString("Year"));
					this.Week = (getArguments().getString("Week"));
						

					
					

					Log.v("ItemFragment - onCreate comentarioActualizado = ",ComentariosActualizados.get(posicion-1));
					
					Log.v("ItemFragment",posicion+"---------------onCreate        "+ Year+" "+Week+" "+posicion+" "+descripcion+" "+comentario+" "+posicion+" "+coordinadorid+" "+user+" "+region+" "+IsRemoved);
					
				} catch (Exception e) {
					Log.v("ItemFragment", "ERROR ItemFragment- onCreate"+e.getMessage());
				}
				
			}
			
			
			
			
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		    	
		    	
		    	
		    	if (this.posicion == 1) {
				
	    		  	v = inflater.inflate(R.layout.fragment_lateral_menu_categories, container, false);
	    		  	
	    		  	
	    		  	String nombreapp ="";
	    		  	String version ="";
	    		  	
	    			try {
	    				nombreapp = getResources().getString(R.string.app_name).toLowerCase();
	    				PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
	    				version = pInfo.versionName;
	    			} catch (NameNotFoundException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			}
	    			
	    			
	    		  	
					TextView tv_version = (TextView) v.findViewById(R.id.textViewVersion);
					tv_version.setText(nombreapp+" "+version);
							

					LinearLayout Search = (LinearLayout) v.findViewById(R.id.linearLayoutSearch);
					Search.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						
							vibrate.toque(getActivity().getApplicationContext());
							
							call_async_tasks.Search_Dialog("texto",getActivity());
							
						}
					});
					
			        
			        LinearLayout URL = (LinearLayout) v.findViewById(R.id.LinearLayoutURL);
			        URL.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
					
							vibrate.toque(getActivity().getApplicationContext());
							
							call_async_tasks.Search_Dialog("url",getActivity());
							
//							Intent intent = new Intent(getActivity().getApplicationContext(), ActivityProcesarURL.class);
////							intent.putExtra("url", url);
//							startActivity(intent);
							
						}
					});
			        
			        
			        LinearLayout Voice = (LinearLayout) v.findViewById(R.id.LinearLayoutVoz);
			        Voice.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
					
							vibrate.toque(getActivity().getApplicationContext());


							final Dialog mDialog = new Dialog(getActivity(), android.R.style.Theme_Panel);
							
							
							final LayoutInflater inflater = getActivity().getLayoutInflater();
//									TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
							
							
							View view_dialog = inflater.inflate(R.layout.custom_dialog_voice1, null);
							TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
//									tv.setText("");
							
							
							final EditText texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
							texto_entrada.setText("Presiona boton para empezar a hablar");
							
							
							ImageView close = (ImageView) view_dialog.findViewById(R.id.imageViewClose); //id defined in camera.xml
							close.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDialog.cancel();
								}
							});
						
							
							ImageView speak = (ImageView) view_dialog.findViewById(R.id.imageViewVoz);
							speak.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									vibrate.toque(getActivity().getApplicationContext());

									texto_entrada.setText("Hable ahora...");


									Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
								    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
								            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
								    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
								            "com.domain.app");

								    SpeechRecognizer recognizer = SpeechRecognizer
								            .createSpeechRecognizer(getActivity());
								    RecognitionListener listener = new RecognitionListener() {
								        @Override
								        public void onResults(Bundle results) {
								            ArrayList<String> voiceResults = results
								                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
								            if (voiceResults == null) {
								                Log.e(TAG, "No voice results");
								            } else {
								                Log.d(TAG, "Printing matches: ");
								                for (String match : voiceResults) {
								                    Log.d(TAG, match);
								                    
								                    
//								                    mDialog.dismiss();
								                    
								                    texto_entrada.setText(voiceResults.get(0));
								                    
								                }
								            }
								        }

								        @Override
								        public void onReadyForSpeech(Bundle params) {
								            Log.d(TAG, "Ready for speech");
								        }

								        @Override
								        public void onError(int error) {
								            Log.d(TAG,
								                    "Error listening for speech: " + error);
								        }

								        @Override
								        public void onBeginningOfSpeech() {
								            Log.d(TAG, "Speech starting");
								        }

								        @Override
								        public void onBufferReceived(byte[] buffer) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onEndOfSpeech() {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onEvent(int eventType, Bundle params) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onPartialResults(Bundle partialResults) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onRmsChanged(float rmsdB) {
								            // TODO Auto-generated method stub

								        }
								    };
								    recognizer.setRecognitionListener(listener);
								    recognizer.startListening(intent);
								    	
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

									Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
							
									vib.vibrate(50);
												
									
									String conceptos = texto_entrada.getText().toString();
									Log.w(this.getClass().getSimpleName(), "conceptos ="+conceptos);
							
										Intent intent = new Intent(getActivity().getApplicationContext(), ActivityResultadosTexto.class);
										intent.putExtra("conceptos",conceptos );
										getActivity().startActivity(intent);
									
								}
							});

							mDialog.addContentView(view_dialog, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							mDialog.show();
							
							

							
						}
					});

					
					
			        
			        LinearLayout Camera = (LinearLayout) v.findViewById(R.id.LinearLayoutCamera);
			        Camera.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());
					
							Intent intent = new Intent(getActivity().getApplicationContext(), com.itwizard.mezzofanti.Camera.class);
							startActivity(intent);
							
						}
					});
			        
			        
			        LinearLayout Imagen = (LinearLayout) v.findViewById(R.id.LinearLayoutImagen);
			        Imagen.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());
							
//							Intent intent = new Intent(getActivity().getApplicationContext(), com.itwizard.mezzofanti.Camera.class);
//							startActivity(intent);
							
//							Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//							startActivityForResult(intent, SELECT_PHOTO);
							
							
							Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
							photoPickerIntent.setType("image/*");
							startActivityForResult(photoPickerIntent, SELECT_PHOTO);   
							
						}
					});
			        
			        LinearLayout Scanner = (LinearLayout) v.findViewById(R.id.LinearLayoutScanner);
			        Scanner.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());

							Intent intent = new Intent(getActivity(),CaptureActivity.class);
							startActivity(intent);
							
//							integrator.initiateScan();
							
							
						}
					});
			        
			        
			        LinearLayout NFC = (LinearLayout) v.findViewById(R.id.LinearLayoutNFC);
			        NFC.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());

							Intent intent = new Intent(getActivity(),ActivityLeerNFC.class);
							startActivity(intent);
							
//							integrator.initiateScan();
							
							
						}
					});			        
			        
			        
			        LinearLayout Ayuda = (LinearLayout) v.findViewById(R.id.LinearLayoutAyuda);
					Ayuda.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						
							vibrate.toque(getActivity().getApplicationContext());
							
							Intent intent = new Intent(getActivity(), ActivityAyuda.class);
							startActivity(intent);
							
						}
					});
			        
			        	

		    	}else if (this.posicion == 2 ) {
				
	    		  	v = inflater.inflate(R.layout.fragment_inicio, container, false);
			        
		    	}else if (this.posicion == 3 ) {
				
	    		  	v = inflater.inflate(R.layout.fragment_texto, container, false);
	    		  	

	    		  	
	    		  	LinearLayout Search = (LinearLayout) v.findViewById(R.id.linearLayoutSearch);
					Search.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						
							vibrate.toque(getActivity().getApplicationContext());
							
							call_async_tasks.Search_Dialog("texto",getActivity());
							
						}
					});
					
			        
			        LinearLayout URL = (LinearLayout) v.findViewById(R.id.LinearLayoutURL);
			        URL.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
					
							vibrate.toque(getActivity().getApplicationContext());
							
							call_async_tasks.Search_Dialog("url",getActivity());
							
//							Intent intent = new Intent(getActivity().getApplicationContext(), ActivityProcesarURL.class);
////							intent.putExtra("url", url);
//							startActivity(intent);
							
						}
					});
			        
			        
			        
			        
			        LinearLayout Voice = (LinearLayout) v.findViewById(R.id.LinearLayoutVoz);
			        Voice.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
					
							vibrate.toque(getActivity().getApplicationContext());


							final Dialog mDialog = new Dialog(getActivity(), android.R.style.Theme_Panel);
							
							
							final LayoutInflater inflater = getActivity().getLayoutInflater();
//									TextView tv_texto = (TextView) mDialog.findViewById(R.id.tv_texto);
							
							
							View view_dialog = inflater.inflate(R.layout.custom_dialog_voice1, null);
							TextView tv = (TextView) view_dialog.findViewById(R.id.tv_texto); //id defined in camera.xml
//									tv.setText("");
							
							
							final EditText texto_entrada = (EditText) view_dialog.findViewById(R.id.editText_texto_Entrada); 
							texto_entrada.setText("Presiona boton para empezar a hablar");
							
							
							ImageView close = (ImageView) view_dialog.findViewById(R.id.imageViewClose); //id defined in camera.xml
							close.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mDialog.cancel();
								}
							});
						
							
							ImageView speak = (ImageView) view_dialog.findViewById(R.id.imageViewVoz);
							speak.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									
									
									
									
									texto_entrada.setText("Hable ahora...");


									Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
								    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
								            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
								    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
								            "com.domain.app");

								    SpeechRecognizer recognizer = SpeechRecognizer
								            .createSpeechRecognizer(getActivity());
								    RecognitionListener listener = new RecognitionListener() {
								        @Override
								        public void onResults(Bundle results) {
								            ArrayList<String> voiceResults = results
								                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
								            if (voiceResults == null) {
								                Log.e(TAG, "No voice results");
								            } else {
								                Log.d(TAG, "Printing matches: ");
								                for (String match : voiceResults) {
								                    Log.d(TAG, match);
								                    
								                    
//								                    mDialog.dismiss();
								                    
								                    texto_entrada.setText(voiceResults.get(0));
								                    
								                }
								            }
								        }

								        @Override
								        public void onReadyForSpeech(Bundle params) {
								            Log.d(TAG, "Ready for speech");
								        }

								        @Override
								        public void onError(int error) {
								            Log.d(TAG,
								                    "Error listening for speech: " + error);
								        }

								        @Override
								        public void onBeginningOfSpeech() {
								            Log.d(TAG, "Speech starting");
								        }

								        @Override
								        public void onBufferReceived(byte[] buffer) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onEndOfSpeech() {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onEvent(int eventType, Bundle params) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onPartialResults(Bundle partialResults) {
								            // TODO Auto-generated method stub

								        }

								        @Override
								        public void onRmsChanged(float rmsdB) {
								            // TODO Auto-generated method stub

								        }
								    };
								    recognizer.setRecognitionListener(listener);
								    recognizer.startListening(intent);
								    	
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

									Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
							
									vib.vibrate(50);
												
									
									String conceptos = texto_entrada.getText().toString();
									Log.w(this.getClass().getSimpleName(), "conceptos ="+conceptos);
							
										Intent intent = new Intent(getActivity().getApplicationContext(), ActivityResultadosTexto.class);
										intent.putExtra("conceptos",conceptos );
										getActivity().startActivity(intent);

									
								}
							});

							mDialog.addContentView(view_dialog, new  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							mDialog.show();
							
							

							
						    
						    
						    
						    
						    

							
						}
					});
			        
			       
	    		  	
	    		  	
		    	
		    	}else if (this.posicion == 4 ) {
					
	    		  	v = inflater.inflate(R.layout.fragment_camara, container, false);
	    		  	
	    		  	
	    		  	 LinearLayout Camera = (LinearLayout) v.findViewById(R.id.LinearLayoutCamera);
				        Camera.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								vibrate.toque(getActivity().getApplicationContext());
						
								Intent intent = new Intent(getActivity().getApplicationContext(), com.itwizard.mezzofanti.Camera.class);
								startActivity(intent);
								
							}
						});
				        

				        
				        LinearLayout Scanner = (LinearLayout) v.findViewById(R.id.LinearLayoutScanner);
				        Scanner.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								vibrate.toque(getActivity().getApplicationContext());

								Intent intent = new Intent(getActivity(),CaptureActivity.class);
								startActivity(intent);
								
//								integrator.initiateScan();
								
								
							}
						});
				        
				        
				        
				        
		    	
		    	}else if (this.posicion == 5 ) {
					
	    		  	v = inflater.inflate(R.layout.fragment_imagen, container, false);
	    		  	
	    			 
			        LinearLayout Imagen = (LinearLayout) v.findViewById(R.id.LinearLayoutImagen);
			        Imagen.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());
							
//							Intent intent = new Intent(getActivity().getApplicationContext(), com.itwizard.mezzofanti.Camera.class);
//							startActivity(intent);
							
//							Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//							startActivityForResult(intent, SELECT_PHOTO);
							
							
							Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
							photoPickerIntent.setType("image/*");
							startActivityForResult(photoPickerIntent, SELECT_PHOTO);   
							
						}
					});
		    	
		    	}else if (this.posicion == 6 ) {
					
	    		  	v = inflater.inflate(R.layout.fragment_leer_nfc, container, false);
	    		  
			        
			        LinearLayout NFC = (LinearLayout) v.findViewById(R.id.LinearLayoutNFC);
			        NFC.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							vibrate.toque(getActivity().getApplicationContext());

							Intent intent = new Intent(getActivity(),ActivityLeerNFC.class);
							startActivity(intent);
							
//							integrator.initiateScan();
							
							
						}
					});	
		    	
				
				}else{
					
					
					
					  	v = inflater.inflate(R.layout.fragment_main_menu, container, false);
				        tv = v.findViewById(R.id.text);
				}
		    	
		        
		        return v;
		        
		    }
		    
		    

		    @Override
		    public void onActivityCreated(Bundle savedInstanceState) {
		        super.onActivityCreated(savedInstanceState);
//		        Log.v("ItemFragment", posicion+" onActivityCreated" );
		        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, list));
		        
		    }
		    
		    

		    @Override
		    public void onListItemClick(ListView l, View v, int position, long id) {
		        
//		    	Log.v("FragmentList", posicion+" Item clicked: " + id);
		    }

			@Override
			public ListAdapter getListAdapter() {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" getListAdapter");
				return super.getListAdapter();
			}

			@Override
			public ListView getListView() {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+ " getListView");
				return super.getListView();
			}

			@Override
			public long getSelectedItemId() {
				// TODO Auto-generated method stub
				Log.v("FragmentList", posicion+" getSelectedItemId");
				return super.getSelectedItemId();
			}

			@Override
			public int getSelectedItemPosition() {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" getSelectedItemPosition");
				return super.getSelectedItemPosition();
			}

			@Override
			public void onDestroyView() {
				// TODO Auto-generated method stub
				super.onDestroyView();
			}
			
			
			

			@Override
			public void onViewCreated(View view, Bundle savedInstanceState) {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" -------------------------- onViewCreated");
				super.onViewCreated(view, savedInstanceState);
			}

			@Override
			public void setListShown(boolean shown) {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" setListShown");
				super.setListShown(shown);
			}

			@Override
			public void setSelection(int position) {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" ---------------------------setSelection");
				super.setSelection(position);
			}

			@Override
			public View getView() {
				// TODO Auto-generated method stub
				if (this.posicion ==1 ) {
					
					
				}else if (this.posicion ==2 ) {
				
				
				}else{
					try {
						ComentariosActualizados.set(posicion-1,  (((EditText)viewcomentario).getText().toString().replaceAll("\n", "<br />").replace("<br /><br />", "<br />").replaceAll("<br /><br />", "<br />")));
						
						Log.v("ItemFragment","GETVIEW - UPDATE strcometario- Actualizo "+(posicion-1)+" "+comentario + " a " +(((EditText)viewcomentario).getText().toString().replaceAll("\n", "<br />").replace("<br /><br />", "<br />").replaceAll("<br /><br />", "<br />")));	
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
				}
				return super.getView();
			}

			@Override
			public void onActivityResult(int requestCode, int resultCode,
					Intent data) {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" onActivityResult -- ");
				super.onActivityResult(requestCode, resultCode, data);
				

				    switch(requestCode) { 
//				    case SCANNER:
//				    	IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//				    	if (scanResult != null) {
//				    	    // handle scan result
//				    		Log.w(TAG, "SCANNER = " + scanResult);
//				    	  }
//				    	  // el
//				    	break;
				    case SELECT_PHOTO:
				        if(resultCode == RESULT_OK){  
				            Uri selectedImage = data.getData();
				            InputStream imageStream;
				            final Bitmap yourSelectedImage;
							try {
								imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
								
								
								BitmapFactory.Options options=new BitmapFactory.Options();
								options.inSampleSize = 8;
								yourSelectedImage=BitmapFactory.decodeStream(imageStream,null,options);
								
//								Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
								
								
//								Camera_ocr.setImageBitmap(yourSelectedImage);
								
								Log.v(TAG, "handleMessage() R.id.decode");
								ocr.CompareTime(TAG + "in handler, just received the picture");

								// do ocr
								try
								{
//									byte[] intriga = new byte[1024];
//									// save the file on disk
//									FileOutputStream fs = new FileOutputStream(RESULTS_PATH + "img.jpg");
//									fs.write(intriga, 0, intriga.length);
//									fs.close();
//
//									yourSelectedImage = BitmapFactory.decodeByteArray(intriga, 0, intriga.length);
//									intriga = null;
									System.gc();
									

									
									Log.v(TAG, "w="+yourSelectedImage.getWidth() + " h="+yourSelectedImage.getHeight());
//									if (m_bLineMode) // we crop just the image of interest
//										mBitmap = Bitmap.createBitmap(mBitmap, 256, 768/2-30, 512, 60, null, false);
									// otherwise, we use all image
								}
								catch (Throwable th) 
								{
									Log.v(TAG, "exception: handler-cmrequestpic: "+ th.toString());
//									m_bOCRInProgress = false;
//									if (m_bLineMode)
//										m_clCapture.ShowWaiting("");
//									else
//										m_MezzofantiMessageHandler.sendEmptyMessage(R.id.mezzofanti_restartCaptureMode);
									break;
								}


								ocr.CompareTime(TAG + "starting the thread");
												
								OCR.Initialize();				
								OCR.get().SetLanguage(OCR.mConfig.GetLanguage());
								
//								DoOCRJob(yourSelectedImage);

								
								Thread theOCRthread = new Thread(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										
										//Seleccionar idioma primero
										
//										OCR.get().SetLanguage("spa");
										
										String m_sOCRResultLineMode = ocr.do_ocr(yourSelectedImage);
										
										Intent intent = new Intent(getActivity(), ResultadoActivity.class);
										intent.putExtra("m_sOCRResultLineMode", m_sOCRResultLineMode);
										startActivity(intent);
										
									}
								});
								theOCRthread.start();
								
								
								
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
							}
				            
				        }
				    }
				
				
			}

			@Override
			public void onDestroy() {
				// TODO Auto-generated method stub
				Log.v("FragmentList", posicion+" onDestroy-------------------------------------------");
//				
				super.onDestroy();
			}

			@Override
			public void onHiddenChanged(boolean hidden) {
				// TODO Auto-generated method stub
				
				super.onHiddenChanged(hidden);
			}

			@Override
			public void onPause() {
				// TODO Auto-generated method stub
				super.onPause();
			}
			
			

			@Override
			public void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" onStart");
				super.onStart();
			}

			@Override
			public void onStop() {
				// TODO Auto-generated method stub
//				Log.v("FragmentList", posicion+" onStop");
				super.onStop();
			}
		    
		    
			
			

			
		}
	

	
			
			
	
	
	
	
    
}