package pfg.uem.biocon.webservice;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

//Noninstantiable utility class
public final class callWebService {

	private static String TAG = "WebServices";


	private callWebService() {
		throw new AssertionError();
	}
	
//	http://orion.esp.uem.es:8080/CLEiM/XMLServ?text=c%C3%A1ncer%20de%20pr%C3%B3stata%20prostate%20cancer
//	http://62.212.77.173:8080/CLEiM/XMLServ?text=c%C3%A1ncer%20de%20pr%C3%B3stata%20prostate%20cancer

		
//		<cleim>
	//		<input>
		//		<text>cÃ¡ncer de prÃ³stata prostate cancer</text>
		//		<remoteonts>40397,46116</remoteonts>
		//		<lev>0</lev>
		//		<localSrc>[Freebase, MedlinePlus, SnomedCore, Snomed]</localSrc>
		//		<localLan>[en, sp]</localLan>
	//		</input>
	//		<annotation language="en" source="Freebase">
		//		<concept neg="0">PROSTATE CANCER</concept>
		//		<from>21</from>
		//		<to>36</to>
		//		<preferred direct="true">PROSTATE CANCER</preferred>
		//		<localurl>disease.jsp?term=PROSTATE CANCER</localurl>
		//		<urlen>http://www.freebase.com/view/en/prostate_cancer</urlen>
		//		<urlsp/>
		//		<groups>Disease</groups>
	//		</annotation>
//		</cleim>
//		
	
		
	public static ArrayList<Annotation_CLEiM> WS_getCLEiM_Search_Concept(String concepto) {
//		public static String WS_getCLEiM_Search_Concept(String concepto) {
				
				ArrayList<Annotation_CLEiM> items = new ArrayList<Annotation_CLEiM>();
				
				String resultado = "";
				
				Log.w(TAG, "webservice");
				

				try {
					
					 StringBuilder url = new StringBuilder();
				        url.append("http://62.212.77.173:8080/CLEiM/XMLServ?text=");
				        url.append(concepto);
					            
//					HttpClient client = new DefaultHttpClient();  
					
					DefaultHttpClient client = new DefaultHttpClient();
					HttpParams httpParameters = new BasicHttpParams();
		
					HttpConnectionParams.setConnectionTimeout(httpParameters, 25000);
					client.setParams(httpParameters);
					
//			        String URL = "http://62.212.77.173:8080/CLEiM/XMLServ?text="`;
			        HttpGet get = new HttpGet(url.toString());
			        get.addHeader("Accept", "application/xml");
			        get.addHeader("Content-Type", "application/xml");
			        HttpResponse responsePost;
				
					responsePost = client.execute(get);
					
			        HttpEntity resEntity = responsePost.getEntity(); 
					
					        if (resEntity != null) 
					
					        {  
					                    System.out.println("Not null!");
					
					                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					
					                    DocumentBuilder db = dbf.newDocumentBuilder();
					
					                    String responseXml = EntityUtils.toString(responsePost.getEntity());
					                    
					                    
					                    Log.w(TAG, responseXml.toString());
					                    
					                    resultado = responseXml.toString();
					                    
//					                    resultado = (String) resultado.subSequence(resultado.indexOf(">")+1, resultado.length());
					                    resultado = resultado.replace("standalone=\"no\"", "");
					                    Log.i(TAG, resultado.toString().trim());
					                    
					                    
					                    		
//					                    Document doc = db.parse(responseXml);
					                    Document doc = db.parse(new InputSource(new ByteArrayInputStream(resultado.getBytes("utf-8"))));
					                    doc.getDocumentElement().normalize();
					                    
					                    
					                    
					
					                    NodeList nodeList = doc.getElementsByTagName("annotation");
					                    
					                    Log.w(TAG, nodeList.toString());
					                    resultado = nodeList.toString();
					                    
					                    for (int i = 0; i < nodeList.getLength(); i++) {
					    					
					                    	
					                    	Annotation_CLEiM annotation = new Annotation_CLEiM();
					                    	
					    					Node node = nodeList.item(i);
					    					Element fstElmnt = (Element) node;
					    					
					    					
					    					NodeList concept = fstElmnt.getElementsByTagName("concept");
					    					Log.i(TAG, "CONCEPT id"+ i + " - " + concept.item(0).getTextContent());
					    					annotation.setConcept(concept.item(0).getTextContent());
					    					annotation.setSource(fstElmnt.getAttribute("source"));
					    					
					    					
					    					
					    					
					    					NodeList from = fstElmnt.getElementsByTagName("from");
					    					Log.i(TAG, "from id"+ i + " - " + from.item(0).getTextContent());
					    					annotation.setFrom(from.item(0).getTextContent());
					    					
					    					NodeList to = fstElmnt.getElementsByTagName("to");
					    					Log.i(TAG, "to id"+ i + " - " + to.item(0).getTextContent());
					    					annotation.setTo(to.item(0).getTextContent());
					    					
					    					NodeList preferred = fstElmnt.getElementsByTagName("preferred");
					    					Log.i(TAG, "preferred id"+ i + " - " + preferred.item(0).getTextContent());
					    					annotation.setPreferred(preferred.item(0).getTextContent());
					    					
					    					NodeList localurl = fstElmnt.getElementsByTagName("localurl");
					    					Log.i(TAG, "localurl id"+ i + " - " + localurl.item(0).getTextContent());
					    					annotation.setLocalurl(localurl.item(0).getTextContent());
					    					
					    					NodeList urlen = fstElmnt.getElementsByTagName("urlen");
					    					Log.i(TAG, "urlen id"+ i + " - " + urlen.item(0).getTextContent());
					    					annotation.setUrlen(urlen.item(0).getTextContent());
					    					
					    					NodeList urlsp = fstElmnt.getElementsByTagName("urlsp");
					    					Log.i(TAG, "urlsp id"+ i + " - " + urlsp.item(0).getTextContent());
					    					annotation.setUrlsp(urlsp.item(0).getTextContent());
					    					
					    					NodeList groups = fstElmnt.getElementsByTagName("groups");
					    					Log.i(TAG, "groups id"+ i + " - " + groups.item(0).getTextContent());
					    					annotation.setGroups(groups.item(0).getTextContent());
					    					
					    					
					    					items.add(annotation);
					    					
					    				}
					                    	
					                    	

					        }
					        
					        
					        return items;
//		        			return resultado;
		        			
		        			
					
					    } catch (Exception e) {
					
					                    Log.e(TAG,"XML Passing Exception = " + e.getMessage());
					                    return null;
					    }
				
				

				
		}
	
	
	
}
	
