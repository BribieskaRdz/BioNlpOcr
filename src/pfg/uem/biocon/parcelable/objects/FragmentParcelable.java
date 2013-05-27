package pfg.uem.biocon.parcelable.objects;


import android.os.Parcel;
import android.os.Parcelable;
 
public class FragmentParcelable implements Parcelable {
 
	
	private String id="";
	private String description="";
	private String comentario = "";
	private String comentarioActualizado = "";
	
	private String year="";
	private String week = "";

	
	
	/**
	 * Standard basic constructor for non-parcel
	 * object creation
	 */
	public FragmentParcelable() { ; };
 
	/**
	 *
	 * Constructor to use when re-constructing object
	 * from a parcel
	 *
	 * @param in a parcel from which to read this object
	 */
	public FragmentParcelable(Parcel in) {
		readFromParcel(in);
	}
 
	
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public static Parcelable.Creator getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		return 0;
	}
 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
 
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		// will come back in the same order
		
		dest.writeString(id);
		dest.writeString(description);
		dest.writeString(comentario);
		dest.writeString("comentarioActualizado");
		dest.writeString(year);
		dest.writeString(week);
	}
 
	/**
	 *
	 * Called from the constructor to create this
	 * object from a parcel.
	 *
	 * @param in parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		id = in.readString();
		description = in.readString();
		comentario = in.readString();
		comentarioActualizado = in.readString();
		year = in.readString();
		week = in.readString();
		
	}
 
    public String getComentarioActualizado() {
		return comentarioActualizado;
	}

	public void setComentarioActualizado(String comentarioActualizado) {
		this.comentarioActualizado = comentarioActualizado;
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
            public FragmentParcelable createFromParcel(Parcel in) {
                return new FragmentParcelable(in);
            }
 
            public FragmentParcelable[] newArray(int size) {
                return new FragmentParcelable[size];
            }
        };
 
}