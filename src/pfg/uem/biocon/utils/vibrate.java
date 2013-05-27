package pfg.uem.biocon.utils;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

public class vibrate {

	
	public static void sos(Context context){
		
		
		Log.d(context.getClass().getSimpleName(),"Ckick Boton Scanner");
		//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
		// Get instance of Vibrator from current Context
		Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		// Vibrate for 5 milliseconds
//		vib.vibrate(50);
		
	    
	    // This example will cause the phone to vibrate "SOS" in Morse Code
	    // In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
	    // There are pauses to separate dots/dashes, letters, and words
	    // The following numbers represent millisecond lengths
//	    int dot = 100;      // Length of a Morse Code "dot" in milliseconds
//	    int dash = 250;     // Length of a Morse Code "dash" in milliseconds
//	    int short_gap = 100;    // Length of Gap Between dots/dashes
//	    int medium_gap = 250;   // Length of Gap Between Letters
//	    int long_gap = 500;    // Length of Gap Between Words
	    
	    
	    int dot = 50;      // Length of a Morse Code "dot" in milliseconds
	    int dash = 175;     // Length of a Morse Code "dash" in milliseconds
	    int short_gap = 50;    // Length of Gap Between dots/dashes
	    int medium_gap = 175;   // Length of Gap Between Letters
	    int long_gap = 250;    // Length of Gap Between Words
	    
	    
	    long[] pattern = {
	        0,  // Start immediately
	        dot, short_gap, dot, short_gap, dot,    // s
	        medium_gap,
	        dash, short_gap, dash, short_gap, dash, // o
	        medium_gap,
	        dot, short_gap, dot, short_gap, dot,    // s
	        long_gap
	    };

	    // The "0" means to repeat the pattern starting at the beginning
	    // The "-1" means not to repeat the pattern 
	    // CUIDADO: If you start at the wrong index (e.g., 1) then your pattern will be off --
	    // You will vibrate for your pause times and pause for your vibrate times !
//	    vib.vibrate(pattern,0);
	    vib.vibrate(pattern,-1);
	    
	    
	}
	
	
	
	public static void toque(Context context){
		
		
		Log.d(context.getClass().getSimpleName(),"Ckick Boton Scanner");
		//Tiendas.setBackgroundResource(R.drawable.catalogoclick);
		// Get instance of Vibrator from current Context
		Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		// Vibrate for 5 milliseconds
//		vib.vibrate(50);
		
	    
	    // This example will cause the phone to vibrate "SOS" in Morse Code
	    // In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
	    // There are pauses to separate dots/dashes, letters, and words
	    // The following numbers represent millisecond lengths
//	    int dot = 100;      // Length of a Morse Code "dot" in milliseconds
//	    int dash = 250;     // Length of a Morse Code "dash" in milliseconds
//	    int short_gap = 100;    // Length of Gap Between dots/dashes
//	    int medium_gap = 250;   // Length of Gap Between Letters
//	    int long_gap = 500;    // Length of Gap Between Words
	    
	    
	    int dot = 50;      // Length of a Morse Code "dot" in milliseconds
	    int dash = 175;     // Length of a Morse Code "dash" in milliseconds
	    int short_gap = 50;    // Length of Gap Between dots/dashes
	    int medium_gap = 175;   // Length of Gap Between Letters
	    int long_gap = 250;    // Length of Gap Between Words
	    
	    
	    long[] pattern = {
	        0,  // Start immediately
	        dot, short_gap
	    };

	    // The "0" means to repeat the pattern starting at the beginning
	    // The "-1" means not to repeat the pattern 
	    // CUIDADO: If you start at the wrong index (e.g., 1) then your pattern will be off --
	    // You will vibrate for your pause times and pause for your vibrate times !
//	    vib.vibrate(pattern,0);
	    vib.vibrate(pattern,-1);
	    
	    
	}
	
	
	
}
