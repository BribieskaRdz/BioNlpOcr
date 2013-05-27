package pfg.uem.biocon.webservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Annotation_CLEiM implements Parcelable {

	// <annotation language="en" source="MedlinePlus">
	// <concept neg="0">CANCER</concept>
	// <from>0</from>
	// <to>6</to>
	// <preferred direct="true">CANCER</preferred>
	// <localurl>medlineplus.jsp?term=CANCER</localurl>
	// <urlen>http://www.nlm.nih.gov/medlineplus/cancer.html</urlen>
	// <urlsp>
	// http://www.nlm.nih.gov/medlineplus/spanish/cancer.html
	// </urlsp>
	// <groups>Cancers</groups>
	// </annotation>

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preferred) {
		this.preferred = preferred;
	}

	public String getLocalurl() {
		return localurl;
	}

	public void setLocalurl(String localurl) {
		this.localurl = localurl;
	}

	public String getUrlen() {
		return urlen;
	}

	public void setUrlen(String urlen) {
		this.urlen = urlen;
	}

	public String getUrlsp() {
		return urlsp;
	}

	public void setUrlsp(String urlsp) {
		this.urlsp = urlsp;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	private String concept;
	private String from;
	private String to;
	private String preferred;
	private String localurl;

	private String urlen;
	private String urlsp;
	private String groups;
	
	private String source;

	
	
	public static Parcelable.Creator getCreator() {
		return CREATOR;
	}
	
	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use use the default
    * constructor to create the object and use another
    * method to hyrdate it as necessary.
    *
    * I just find it easier to use the constructor.
    * It makes sense for the way my brain thinks ;-)
    *
    */
   public static final Parcelable.Creator CREATOR =
   	new Parcelable.Creator() {
           public Annotation_CLEiM createFromParcel(Parcel in) {
               return new Annotation_CLEiM(in);
           }

           public Annotation_CLEiM[] newArray(int size) {
               return new Annotation_CLEiM[size];
           }
       };
       
       
	/**
	 * Standard basic constructor for non-parcel object creation
	 */
	public Annotation_CLEiM() {
		;
	};

	/**
	 * 
	 * Constructor to use when re-constructing object from a parcel
	 * 
	 * @param in a parcel from which to read this object
	 */
	public Annotation_CLEiM(Parcel in) {
		readFromParcel(in);
	}

	public Annotation_CLEiM(String concept, String from, String to, String preferred,
			String localurl, String urlen, String urlsp, String groups) {

		this.concept = concept;
		this.from = from;
		this.to = to;
		this.preferred = preferred;

		this.localurl = localurl;
		this.urlen = urlen;
		this.urlsp = urlsp;
		this.groups = groups;
		
		this.source = groups;

	}
	
	
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 * Called from the constructor to create this object from a parcel.
	 * 
	 * @param in
	 *            parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {

		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		concept = in.readString();
		from = in.readString();
		to = in.readString();
		preferred = in.readString();
		localurl = in.readString();
		urlen = in.readString();
		urlsp = in.readString();
		groups = in.readString();
		source = in.readString();

	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		dest.writeString(concept);
		dest.writeString(from);
		dest.writeString(to);
		dest.writeString(preferred);
		dest.writeString(localurl);
		dest.writeString(urlen);
		dest.writeString(urlsp);
		dest.writeString(groups);
		dest.writeString(source);

	}

}
