package pfg.uem.biocon.utils;

import java.net.URLEncoder;

import android.net.Uri;
import android.util.Log;

public class procesamientoCadenas {

	
	public static String procesaTexto(String concepto){
		try {
			String concepto_retocado = concepto;
			
//			 Spanned sp = Html.fromHtml(concepto);
//			 concepto_retocado = sp.toString();
			
//			 concepto_retocado = concepto_retocado.trim();
//			 concepto_retocado = concepto_retocado.replace(".", "");
//			 concepto_retocado = concepto_retocado.replace(" href=", "");
//			 concepto_retocado = concepto_retocado.replace("href=", "");
//			 concepto_retocado = concepto_retocado.replace("hre", "");
//			 concepto_retocado = concepto_retocado.replace("=", "");
//			 concepto_retocado = concepto_retocado.replace("#", "");
//			 concepto_retocado = concepto_retocado.replace("/", "");
//			 concepto_retocado = concepto_retocado.replace("\n", "");
//			 concepto_retocado = concepto_retocado.replace("\r", "");
//			 concepto_retocado = concepto_retocado.replace("\t", "");
//			 concepto_retocado = concepto_retocado.replace("  ", "");
//			 concepto_retocado = concepto_retocado.replace("...", "");
//			 concepto_retocado = concepto_retocado.replace("*", "");
//			 concepto_retocado = concepto_retocado.replace("{", "");
//			 concepto_retocado = concepto_retocado.replace("}", "");
//			 concepto_retocado = concepto_retocado.replace(";", "");
//			 concepto_retocado = concepto_retocado.replace("<", "");
//			 concepto_retocado = concepto_retocado.replace(">", "");
//			 concepto_retocado = concepto_retocado.replace("@", "");
//			 concepto_retocado = concepto_retocado.replace("-", "");
//			 concepto_retocado = concepto_retocado.replace("!", "");
//			 concepto_retocado = concepto_retocado.replace("Á", "");
//			 concepto_retocado = concepto_retocado.replace(";", "");
//			 concepto_retocado = concepto_retocado.replace("_", "");
//			 concepto_retocado = concepto_retocado.replace(".", "");
//			 concepto_retocado = concepto_retocado.replace("'", "");
//			 concepto_retocado = concepto_retocado.replace(",", "");
//			 concepto_retocado = concepto_retocado.replace("\"", "");
//			 concepto_retocado = concepto_retocado.replace("\\", "");
//			 concepto_retocado = concepto_retocado.replace("^", "");
//			 concepto_retocado = concepto_retocado.replace("/", "");
//			 concepto_retocado = concepto_retocado.replace(":", "");
//			 concepto_retocado = concepto_retocado.replace("]", "");
//			 concepto_retocado = concepto_retocado.replace("[", "");
//			 concepto_retocado = concepto_retocado.replace(")", "");
//			 concepto_retocado = concepto_retocado.replace("(", "");
//			 concepto_retocado = concepto_retocado.replace("?", "");
//			 concepto_retocado = concepto_retocado.replace("|", "");
//			 concepto_retocado = concepto_retocado.replace("%", "");
//			 concepto_retocado = concepto_retocado.replace("À", "");
//			 concepto_retocado = concepto_retocado.replace("=", "");
////			 concepto_retocado = concepto_retocado.replace("wgPageContentLanguage", "");
////			 concepto_retocado = concepto_retocado.replace("wgBreakFrames", "");
////			 concepto_retocado = concepto_retocado.replace("wgSeparatorTransformTable", "");
////			 concepto_retocado = concepto_retocado.replace("wgUserGroups", "");
////			 concepto_retocado = concepto_retocado.replace("wg", "");
//
//
//			 concepto_retocado = concepto_retocado.replace(" ", "%20");
//			 concepto_retocado = concepto_retocado.replace("++", "+");
//			 concepto_retocado = concepto_retocado.replace("++", "+");
//			 concepto_retocado = concepto_retocado.replace("+++", "+");
//			 concepto_retocado = concepto_retocado.replace(" ", "");
			 
//			 concepto_retocado= concepto.replace(/ +(?= )/g,'');   // <-- Replace all consecutive spaces, 2+"
//	         concepto_retocado= concepto.replace(/%/g, '%25')     // <-- Escape %
//	         concepto_retocado= concepto.replace(/&/g, '%26')     // <-- Escape &
//	         concepto_retocado= concepto.replace(/#/g, '%23')     // <-- Escape #
//	         concepto_retocado= concepto.replace(/"/g, '%22')     // <-- Escape "
//	         concepto_retocado= concepto.replace(/'/g, '%27');    // <-- Escape ' (to be 100% safe)
	        		 
//			 if(concepto_retocado.startsWith("+")){
//				 concepto_retocado.replaceFirst("+", "");
//			 }
			 
//			 String requestURL = String.format(concepto_retocado, Uri.encode("foo bar"), Uri.encode("100% fubar'd"));
//			 URIUtil.encodeQuery(concepto_retocado);
//			 concepto_retocado = URLEncoder.encode(requestURL, "UTF-8");
			 
	        		 
			
			concepto_retocado = URLEncoder.encode(concepto, "UTF-8");
			
			 Log.w("procesamientoCadenas", "--- --- --- "+concepto_retocado+" --- --- ---");
			 
			 return concepto_retocado;
			 
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("procesamiento cadenas", "Error procesamiento cadenas: "+e.getMessage());
			return "error";
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
}











