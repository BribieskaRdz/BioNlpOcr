/**
 * @Auth Alejandro Cord—n Ure–a y Jorge SanJose 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
   This project has used third-party libraries which keep their own licenses although they were modified   
   
 */
package pfg.uem.biocon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class ActivitySplash extends Activity {

	
	private static final int ACTIVITY_RESULT = 1;
	private String TAG = this.getClass().getSimpleName();
    final String INT_ENTERPRISEFIELD_NAME = "android.net.wifi.WifiConfiguration$EnterpriseField";
	private int sleepTime = 1900;
	protected Intent startIntent = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_splash);
	        
	      		/*
	               * Comprueba el estado de la conexion a internet
	               */
	      		Log.i(TAG,"Compruebo el estado de la conexion a internet");
	      		
	              ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	              NetworkInfo netInfo = cm.getActiveNetworkInfo();
	              if (!(netInfo != null && netInfo.isConnectedOrConnecting())) {
	              		Log.w(TAG,"No hay conexion a interntet!");
	      				AlertDialog alertDialog = new AlertDialog.Builder(ActivitySplash.this).create();
	      				
	      				alertDialog.setTitle(" ");
	      				alertDialog.setMessage("Esta aplicaci—n necesita internet para funcionar correctamente.");
	      				alertDialog.setIcon(R.drawable.bionlpocr);
	      				alertDialog.show();
	      				
	      		} else{
	      			
	      		}
	              
	            
	            new DialogoCargado().execute();

		}

	
	
	
    
	public class DialogoCargado extends AsyncTask<Void, Void, Void> {

		
		private Animation anim;
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.zoom_exit);
			
		}
	   
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			SystemClock.sleep(sleepTime);
			
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Intent intent = new Intent(ActivitySplash.this, pfg.uem.biocon.customviews.activitys.ActivityScrollableMenu.class);
			startActivity(intent);
			overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit); 
			
			finish();
			
		}

			  
	  }
	
	
	
	}




