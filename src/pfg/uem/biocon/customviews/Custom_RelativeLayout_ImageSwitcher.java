package pfg.uem.biocon.customviews;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import pfg.uem.biocon.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * An small Custom Imageswitcher with buttons and gestures for previous,
 * current and next picture to be shown in a ViewPager.
 * 
 * @author Alejandro Cord—n Ure–a
 */
public class Custom_RelativeLayout_ImageSwitcher extends RelativeLayout implements ViewFactory { 
	

	TextView numero_foto;
	ImageSwitcher image_switcher;
	ImageButton mNext;
	ImageButton mPrevious;
	
	private static int index = 0;
	
	private ProgressDialog progress;
    private int downX = 0;
    private int downY = 0;
    private int upX = 0;
    private int upY = 0;
    
	private String TAG = this.getClass().getSimpleName();
	
	
	
	private ArrayList<String> mStrings;
	
	
	/**
	 * @return the mStrings
	 */
	public ArrayList<String> getmStrings() {
		return mStrings;
	}

	/**
	 * @param mStrings the mStrings to set
	 */
	public void setmStrings(ArrayList<String> pictures) {
		this.mStrings = pictures;
		index = 0;
		
		
		if (this.mStrings!=null && this.mStrings.size()>0) {
//			if (this.mStrings.size()>1) {
//				mNext.setVisibility(View.VISIBLE);
//				mPrevious.setVisibility(View.VISIBLE);
//			}
			try {
				progress = new ProgressDialog(getContext());
				progress.setMessage("Cargando...");
				progress.setTitle(" ");
				progress.setIcon(R.drawable.bionlpocr);
				progress.show();
//				new DialogoCargando(progress,mStrings[index]).execute();
				new DialogoCargando(progress,mStrings.get(index)).execute();
				
				numero_foto.setVisibility(View.VISIBLE);
		        numero_foto.setText((index+1)+"/"+mStrings.size()); 
		        
				if ((index+1)==1) {
					mNext.setVisibility(View.VISIBLE);
					mPrevious.setVisibility(View.INVISIBLE);
					if ((index+1)==mStrings.size() && (index+1)==1) {
						mNext.setVisibility(View.INVISIBLE);
						mPrevious.setVisibility(View.INVISIBLE);
						numero_foto.setVisibility(View.INVISIBLE);
					}
				}else if ((index+1)==mStrings.size()) {
					mNext.setVisibility(View.INVISIBLE);
					mPrevious.setVisibility(View.VISIBLE);
				}else{
					mNext.setVisibility(View.VISIBLE);
					mPrevious.setVisibility(View.VISIBLE);
				}
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
				 Toast.makeText(getContext(), "Error al cargar imagen..", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getContext(), "No hay fotos..", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	
	   
	public Custom_RelativeLayout_ImageSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);
		setVisibility(View.INVISIBLE);
		addContent();
	}
	
	public Custom_RelativeLayout_ImageSwitcher(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		setVisibility(View.INVISIBLE);
		addContent();
	}
	
	public Custom_RelativeLayout_ImageSwitcher(Context context) {
		super(context);
		setVisibility(View.INVISIBLE);
		addContent();
	}
	
	
	/**
	 * Create all views, build the layout
	 */
	private void addContent(){
		
		// ImageButton views
		mPrevious = new ImageButton(getContext());
		mNext = new ImageButton(getContext());
		// ImageSwitcher view
		image_switcher = new ImageSwitcher(getContext());
		// TextView view
		numero_foto = new TextView(getContext());
		
		LayoutParams params_mPrevious = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params_mPrevious.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params_mPrevious.addRule(RelativeLayout.CENTER_VERTICAL);
		params_mPrevious.setMargins(5, 5, 40, 5);

		mPrevious.setLayoutParams(params_mPrevious); //causes layout update
		mPrevious.setImageDrawable(getResources().getDrawable(R.drawable.navigation_back));
		mPrevious.setBackgroundColor(getResources().getColor(R.color.transparent));
		mPrevious.setVisibility(View.INVISIBLE);

		
		LayoutParams params_mNext = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params_mNext.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params_mNext.addRule(RelativeLayout.CENTER_VERTICAL);
		params_mNext.setMargins(40, 5, 5, 5);

		mNext.setLayoutParams(params_mNext); //causes layout update	
		mNext.setImageDrawable(getResources().getDrawable(R.drawable.navigation_next));
		mNext.setBackgroundColor(getResources().getColor(R.color.transparent));
		mNext.setVisibility(View.INVISIBLE);
		
		LayoutParams params_image_switcher = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		params_image_switcher.addRule(RelativeLayout.CENTER_VERTICAL);

		image_switcher.setLayoutParams(params_image_switcher); //causes layout update	
		
		image_switcher.setFactory(this);
		
		// so that its SWITCHER_ID is useful
//		image_switcher.setOnClickListener(this);

		image_switcher.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

            	
            	/**
            	 * Gestures
            	 */
				// TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = (int) event.getX(); 
                    downY = (int) event.getY(); 
                    Log.i(TAG,"Down - X " + downX + "Y " + downY);
                    return true;
                }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                
                	 Log.i(TAG,"ACTION_MOVE");
                	 
                	
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = (int) event.getX();
                    upY = (int) event.getY(); 
                    Log.i(TAG,"Up - X " + upX + "Y " + upY);
                    
//                    int curIndex;
					if ((upX - downX) > 100) {
//                        image_switcher.setInAnimation(AnimationUtils
//                        .loadAnimation(ActivityLazyImageSwitcher.this,
//                         android.R.anim.slide_in_left));
//                        image_switcher.setOutAnimation(AnimationUtils
//                         .loadAnimation(ActivityLazyImageSwitcher.this,
//                         android.R.anim.slide_out_right));
//                         //curIndex  current image index in array viewed by user
//                        curIndex--;
//                        if (curIndex < 0) {
//                            curIndex = 5;
//                        }
//                        //IMAGE_LIST :-image list array
//                        image_switcher.setImageResource(IMAGE_LIST[curIndex]);
//                        ActivityLazyImageSwitcher.this.switchTitle(curIndex);
						Log.w(TAG,"upX - downX > 100 ---> Anterior Imagen");
						AnteriorImagen();
						
                    } 
					if ((downX - upX) > 100) { 
//                        image_switcher.setInAnimation(AnimationUtils
//                        .loadAnimation(ActivityLazyImageSwitcher.this,
//                        R.anim.slide_out_left));
//                        image_switcher.setOutAnimation(AnimationUtils
//                        .loadAnimation(ActivityLazyImageSwitcher.this,
//                        R.anim.slide_in_right));
//                        curIndex++;
//                        if (curIndex > 5) {
//                            curIndex = 0;
//                        }
//                        image_switcher.setImageResource(IMAGE_LIST[curIndex]);
//                        ActivityLazyImageSwitcher.this.switchTitle(curIndex);
                    	Log.w(TAG,"downX - upX > 100 ---> Siguiente Imagen");
                    	SiguienteImagen();
                    } 
					if ((downY - upY) > 100) { 
//                        image_switcher.setInAnimation(AnimationUtils
//                        .loadAnimation(ActivityLazyImageSwitcher.this,
//                        R.anim.slide_out_left));
//                        image_switcher.setOutAnimation(AnimationUtils
//                        .loadAnimation(ActivityLazyImageSwitcher.this,
//                        R.anim.slide_in_right));
//                        curIndex++;
//                        if (curIndex > 5) {
//                            curIndex = 0;
//                        }
//                        image_switcher.setImageResource(IMAGE_LIST[curIndex]);
//                        ActivityLazyImageSwitcher.this.switchTitle(curIndex);
                    	
                    	Log.w(TAG,"downY - upY > 100  ---> Abajo Imagen");
//                    	SiguienteImagen();
                    		
                    	
                    }
					if ((upY - downY) > 100) { 
//                      image_switcher.setInAnimation(AnimationUtils
//                      .loadAnimation(ActivityLazyImageSwitcher.this,
//                      R.anim.slide_out_left));
//                      image_switcher.setOutAnimation(AnimationUtils
//                      .loadAnimation(ActivityLazyImageSwitcher.this,
//                      R.anim.slide_in_right));
//                      curIndex++;
//                      if (curIndex > 5) {
//                          curIndex = 0;
//                      }
//                      image_switcher.setImageResource(IMAGE_LIST[curIndex]);
//                      ActivityLazyImageSwitcher.this.switchTitle(curIndex);
                  	
                    	Log.w(TAG,"upY - downY > 100  ---> Arriba Imagen");
//                  		AnteriorImagen();
                  		
                    }
					
                    return true;
                }
                return false;
            }
        });
		
		

		image_switcher.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
		image_switcher.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        
 		
		LayoutParams params_numero_foto = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params_numero_foto.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params_numero_foto.addRule(RelativeLayout.ALIGN_TOP);
		params_numero_foto.setMargins(0, 4, 0, 0);

		numero_foto.setLayoutParams(params_numero_foto); //causes layout update	
		numero_foto.setTextColor(getResources().getColor(R.color.Blanco));
		numero_foto.setTypeface(null, Typeface.BOLD);
		numero_foto.setBackgroundColor(getResources().getColor(R.color.Negro));
		numero_foto.getBackground().setAlpha(125);
		numero_foto.setPadding(6, 0, 6, 0);
		numero_foto.setVisibility(View.VISIBLE);
		
		
		
		addView(image_switcher);
		addView(mPrevious);
		addView(mNext);
		addView(numero_foto);

		mPrevious.setOnClickListener(new OnPreviousClickedListener());
		mNext.setOnClickListener(new OnNextClickedListener());
		
	}

	
	

	
	OnClickListener mOnClickHandler;
	
	public interface PageInfoProvider{
		String getTitle(int pos);
	}
	
	public interface OnClickListener{
		void onNextClicked(View v);
		void onPreviousClicked(View v);
		void onCurrentClicked(View v);
	}
	

	
	class OnPreviousClickedListener implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			Log.i(TAG, "OnPreviousClickedListener");
			
			AnteriorImagen();
			
		}
	}
	class OnNextClickedListener implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			Log.i(TAG, "OnNextClickedListener");
			
			
			SiguienteImagen();
			
		}
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		
		 ImageView imageView = new ImageView(getContext());
		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
		            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		    return imageView;
		    
	}
	
	
	 public class DialogoCargando extends AsyncTask<Void, Void, Void> {
			
			private ProgressDialog progress;
			private String url;
			private Drawable drawable;
			
			  public DialogoCargando(ProgressDialog progress, String URL) {
				  this.progress = progress;
				  this.url = URL;
			  }
			
			
			protected void onPreExecute(Void result) {
				// TODO Auto-generated method stub
				
				
				progress.show();
				
				
				super.onPostExecute(result);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
					
				
				
				 drawable = new BitmapDrawable(downloadimage(url.trim()));

				
				
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				

					try {
						
						image_switcher.setImageDrawable(drawable);
						setVisibility(View.VISIBLE);
						
					} catch (Exception e) {
						// TODO: handle exception
//						Toast.makeText(context?, "Error al cargar imagen..", Toast.LENGTH_SHORT).show();
					}
					
				
				progress.dismiss();
				 
				super.onPostExecute(result);
			}

			  
		}
	    


	private void SiguienteImagen() {

		if (mStrings.size()>1) {
			index++;
			if (index >= mStrings.size()) {
//				index = 0;
				index--;
				Toast.makeText(getContext(), "No hay m‡s im‡genes..", Toast.LENGTH_SHORT).show();
			}else{
				//m_Switcher.setImageResource(imagelist[index]);
				try {
					
					progress = new ProgressDialog(getContext());
					progress.setMessage("Cargando...");
					progress.setTitle(" ");
					progress.setIcon(R.drawable.bionlpocr);
					progress.show();
					new DialogoCargando(progress,mStrings.get(index)).execute();
					
					numero_foto.setVisibility(View.VISIBLE);
					numero_foto.setText((index+1)+"/"+mStrings.size());
					
					if ((index+1)==1) {
						mNext.setVisibility(View.VISIBLE);
						mPrevious.setVisibility(View.INVISIBLE);
						if ((index+1)==mStrings.size() && (index+1)==1) {
							mNext.setVisibility(View.INVISIBLE);
							mPrevious.setVisibility(View.INVISIBLE);
							numero_foto.setVisibility(View.INVISIBLE);
						}
					}else if ((index+1)==mStrings.size()) {
						mNext.setVisibility(View.INVISIBLE);
						mPrevious.setVisibility(View.VISIBLE);
					}else{
						mNext.setVisibility(View.VISIBLE);
						mPrevious.setVisibility(View.VISIBLE);
					}
					
					//			actionBar.getSub_title().setText((index+1)+"/"+mStrings.length);
					
				} catch (Exception e) {
					// TODO: handle exception
					 Toast.makeText(getContext(), "Error al cargar imagen..", Toast.LENGTH_SHORT).show();
				}
			}
		}else{
			numero_foto.setVisibility(View.INVISIBLE);
		}
		
		
		
	}


	private void AnteriorImagen() {

		if (mStrings.size()>1) {
			index--;
			if (index < 0) {
//				index = mStrings.size() - 1;
				index++;
				Toast.makeText(getContext(), "No hay im‡gen previa..", Toast.LENGTH_SHORT).show();
			}else{
				//m_Switcher.setImageResource(imagelist[index]);
				//m_Switcher.setImageURI(Uri.parse(mStrings[index]));
				try {
					
					
					progress = new ProgressDialog(getContext());
					progress.setMessage("Cargando...");
					progress.setTitle(" ");
					progress.setIcon(R.drawable.bionlpocr);
					progress.show();
					new DialogoCargando(progress,mStrings.get(index)).execute();
					
					numero_foto.setVisibility(View.VISIBLE);
					numero_foto.setText((index+1)+"/"+mStrings.size());
					
					if ((index+1)==1) {
						mNext.setVisibility(View.VISIBLE);
						mPrevious.setVisibility(View.INVISIBLE);
						if ((index+1)==mStrings.size() && (index+1)==1) {
							mNext.setVisibility(View.INVISIBLE);
							mPrevious.setVisibility(View.INVISIBLE);
							numero_foto.setVisibility(View.INVISIBLE);
						}
					}else if ((index+1)==mStrings.size()) {
						mNext.setVisibility(View.INVISIBLE);
						mPrevious.setVisibility(View.VISIBLE);
					}else{
						mNext.setVisibility(View.VISIBLE);
						mPrevious.setVisibility(View.VISIBLE);
					}
					
//					actionBar.getSub_title().setText((index+1)+"/"+mStrings.length);
					
				} catch (Exception e) {
					// TODO: handle exception
					 Toast.makeText(getContext(), "Error al cargar imagen..", Toast.LENGTH_SHORT).show();
				}
			}

		}else{
			numero_foto.setVisibility(View.INVISIBLE);
		}
		
	}
	
	private Bitmap downloadimage(String url) 
    {

    	
    	
//        File f=fileCache.getFile(url.trim());
        
        //from SD cache
//        Bitmap b = decodeFile(f);
//        if(b!=null)
//            return b;
        
        //from web
        try {
			

        	
        	DefaultHttpClient httpclient = new DefaultHttpClient(); 
    		// Set the timeout in milliseconds until a connection is established.
    		// The default value is zero, that means the timeout is not used. 
    		int timeoutConnection = 60000;
    		HttpGet httpRequest = new HttpGet(url.trim());
    		
    		        HttpResponse response;
    					response = (HttpResponse) httpclient.execute(httpRequest);
    					 HttpEntity entity = response.getEntity();
    			            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity); 
    			            InputStream instream = bufHttpEntity.getContent();
    			            Bitmap bitmap= BitmapFactory.decodeStream(instream);
    			            
    			            return bitmap ;
    			            
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ImageLoader", "ERROR al bajar imagen : "+e.getMessage());
//			 memoryCache.clear();
			return null;
		} 
        	
    	         
    	         
    }
    

}
