package pfg.uem.biocon.customviews;

import java.util.ArrayList;

import pfg.uem.biocon.R;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomSpinnerAdapter extends ArrayAdapter<String>{
	 
	private ArrayList<String> Objects;
	
	private LayoutInflater inflater;
	private TextView label;
	
//	private TextView sub;
	private ImageView icon;
	
	private Drawable drawableHecho;    
	private Drawable drawableSinHacer;
	
	private Activity activity;
	
    public MyCustomSpinnerAdapter(Activity activity, int textViewResourceId, ArrayList<String> objects) {
        super(activity.getApplicationContext(), textViewResourceId, objects);
        this.Objects = objects;
        this.activity = activity;
        
        drawableHecho = activity.getApplicationContext().getResources().getDrawable(R.drawable.valid);
    	drawableSinHacer = activity.getApplicationContext().getResources().getDrawable(R.drawable.error);
    	
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
      
    	
    	 if (convertView == null) { 
    		 inflater = activity.getLayoutInflater();
             convertView = inflater.inflate(R.layout.row, parent, false);
//             row=inflater.inflate(R.layout.row, parent, false);
         } 
    	 
    	 
    	 label = (TextView)convertView.findViewById(R.id.texto);
         label.setText(Objects.get(position));

//         sub = (TextView)convertView.findViewById(R.id.sub);
         icon = (ImageView)convertView.findViewById(R.id.icon);
         
         if (Objects.get(position).contains("*")) {
//             sub.setText("BOOK HECHO");
             icon.setImageDrawable(drawableHecho);
 		 }else{
//             sub.setText("BOOK SIN HACER");
               icon.setImageDrawable(drawableSinHacer);
 		 }
         
         
         return convertView; 
         
         
        }
    }
