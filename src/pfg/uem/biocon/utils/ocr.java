package pfg.uem.biocon.utils;

import java.util.Calendar;

import android.graphics.Bitmap;
import android.util.Log;

import com.itwizard.mezzofanti.OCR;


public class ocr {

//	private static Bitmap m_bmOCRBitmap;
	private static String TAG = "ocr";

	/*
	 * ----------------------------------------------------------------------------------------------------
	 * OCR Thread 
	 * ---------------------------------------------------------------------------------------------------- 
	 */

	/**
	 * Show a progress bar and start the OCR thread.
	 * @param bm the bitmap to be OCR-ized.
	 */
	public void DoOCRJob(Bitmap bm)
	{    	
//		m_bmOCRBitmap = bm;
//		if (bm == null)
//			return;
//
////		if (!m_bLineMode)
////		{
////			m_pdOCRInProgress = ProgressDialog.show(getActivity(), this.getString(R.string.mezzofanti_ocr_processing_title), 
////					this.getString(R.string.mezzofanti_ocr_processing_body_begin) +" "+ OCR.mConfig.GetLanguageMore() + " " + this.getString(R.string.mezzofanti_ocr_processing_body_end), 
////					true, true);
////			m_pdOCRInProgress.setOnCancelListener( new OnCancelListener() {
////				public void onCancel(DialogInterface dialog) 
////				{
////					android.os.Process.killProcess(android.os.Process.myPid());
////				}    		    		
////			});
////		}
////		else
////		{
////			m_clCapture.ShowWaiting(getString(R.string.mezzofanti_capturelayout_processing) + " " +
////					OCR.mConfig.GetLanguageMore() + " " + 
////					getString(R.string.mezzofanti_capturelayout_processing2));    		
////		}
//
//		Thread theOCRthread = new Thread(run());
//		theOCRthread.start();
	}


	/**
	 * start the OCR thread
	 */
	public static String do_ocr(Bitmap m_bmOCRBitmap) 
	{
//		CompareTime(TAG + "STARTING ocr processing");

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
//			String m_sOCRResult = OCR.get().ImgOCRAndFilter(iImage, iPicWidth, iPicHeight, m_bHorizDispAtPicTaken, m_bLineMode);	
			String m_sOCRResult = OCR.get().ImgOCRAndFilter(iImage, iPicWidth, iPicHeight, true, false);	
			Log.v(TAG, "ocr done text= [" + m_sOCRResult +"]");
			
			// force free the mem
			iImage = null;
			m_bmOCRBitmap = null;
			System.gc();			

			// bad results, get the (internal) image
			OCR.get().SaveMeanConfidence();

			Log.v(TAG, "starting results handler");
			Log.w(TAG, "IMAGE PROCESADA = "+m_sOCRResult);
			
			Log.i(TAG, "pcjpg - finish startPreview()");

			CompareTime(TAG + "finished the ocr processing");
			
			return m_sOCRResult;
		}
		else
		{
			System.gc();
//			m_bOCRInProgress = false;
//			m_MezzofantiMessageHandler.sendEmptyMessage(R.id.mezzofanti_restartCaptureMode);
//			Log.i(TAG, getResources().getString(R.id.mezzofanti_restartCaptureMode));
			Log.w(TAG, "IMAGE NULL");
			Log.i(TAG, "pcjpg - finish startPreview()");

			CompareTime(TAG + "finished the ocr processing");
			return "";
		}

//		m_MezzofantiMessageHandler.sendEmptyMessage(R.id.mezzofanti_ocrFinished);

	} 


	
	private static long m_lTime = 0;
	public static void CompareTime(String status)
	{
		long prevtime = m_lTime;
		Calendar now = Calendar.getInstance();
		long currenttime=now.getTimeInMillis();

		Log.v(TAG, "---------------------");
		Log.v(TAG, "Status: "+status);
		Log.v(TAG, "Compare prev="+m_lTime + " to current=" + currenttime + " diff=" + (currenttime-prevtime));
		Log.v(TAG, "---------------------");
		m_lTime = currenttime;
	}

	
	
	
}
