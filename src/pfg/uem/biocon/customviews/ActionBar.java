
package pfg.uem.biocon.customviews;

import java.util.LinkedList;

import pfg.uem.biocon.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActionBar extends RelativeLayout implements OnClickListener {

    private LayoutInflater mInflater;
    private RelativeLayout mBarView;
    private ImageView mLogoView;
    private View mBackIndicator;
    //private View mHomeView;
    private TextView mTitleView;
    
    private TextView sub_title;
    
    private RelativeLayout sub_menu;
    
    private LinearLayout mActionsView;
    private ImageButton mHomeBtn;
    
    private ImageView cWifiBtn;
    private ImageView cMovilBtn;
    
    private ImageView subimage1;
    private ImageView subimage2;
    private ImageView subimage3;
    private ImageView subimage4;
    
    
    private RelativeLayout mHomeLayout;
    private ProgressBar mProgress;
    private ProgressBar mProgress_sub;

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mBarView = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);
        addView(mBarView);

        mLogoView = (ImageView) mBarView.findViewById(R.id.actionbar_home_logo);
        mHomeLayout = (RelativeLayout) mBarView.findViewById(R.id.actionbar_home_bg);
        mHomeBtn = (ImageButton) mBarView.findViewById(R.id.actionbar_home_btn);
        mBackIndicator = mBarView.findViewById(R.id.actionbar_home_is_back);

        mTitleView = (TextView) mBarView.findViewById(R.id.actionbar_title);
        
        sub_title = (TextView) mBarView.findViewById(R.id.actionbar_subtitle);
        
        sub_menu = (RelativeLayout) mBarView.findViewById(R.id.actionbar_sub);
        
        
        mActionsView = (LinearLayout) mBarView.findViewById(R.id.actionbar_actions);
        
	        subimage1 = (ImageView) findViewById(R.id.actionbar_subimage1);
	        subimage2 = (ImageView) findViewById(R.id.actionbar_subimage2); 
	        setSubimage3((ImageView) findViewById(R.id.actionbar_subimage3));
	        setSubimage4((ImageView) findViewById(R.id.actionbar_subimage4)); 
        
        mProgress = (ProgressBar) mBarView.findViewById(R.id.actionbar_progress);
        mProgress_sub = (ProgressBar) mBarView.findViewById(R.id.actionbar_progress_sub);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ActionBar);
        
        CharSequence title = a.getString(R.styleable.ActionBar_title);
        if (title != null) {
            setTitle(title);
        }
        a.recycle();
    }

    public void setHomeAction(Action action) {
        mHomeBtn.setOnClickListener(this);
        mHomeBtn.setTag(action);
        mHomeBtn.setImageResource(action.getDrawable());
        mHomeLayout.setVisibility(View.VISIBLE);
    }

    public void clearHomeAction() {
        mHomeLayout.setVisibility(View.GONE);
    }

    /**
     * Shows the provided logo to the left in the action bar.
     * 
     * This is ment to be used instead of the setHomeAction and does not draw
     * a divider to the left of the provided logo.
     * 
     * @param resId The drawable resource id
     */
    public void setHomeLogo(int resId) {
        // TODO: Add possibility to add an IntentAction as well.
        mLogoView.setImageResource(resId);
        mLogoView.setVisibility(View.VISIBLE);
        mHomeLayout.setVisibility(View.GONE);
    }

    public ImageView getHomeLogo() {
        // TODO: Add possibility to add an IntentAction as well.
        return mLogoView;
    }
    
    /* Emulating Honeycomb, setdisplayHomeAsUpEnabled takes a boolean
     * and toggles whether the "home" view should have a little triangle
     * indicating "up" */
    public void setDisplayHomeAsUpEnabled(boolean show) {
        mBackIndicator.setVisibility(show? View.VISIBLE : View.GONE);
    }


    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
    }

    public void setTitle(int resid) {
        mTitleView.setText(resid);
    }

    /**
     * Set the enabled state of the progress bar.
     * 
     * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
     *   or {@link View#GONE}.
     */
    public void setProgressBarVisibility(int visibility) {
        mProgress.setVisibility(visibility);
    }

    /**
     * Returns the visibility status for the progress bar.
     * 
     * @param One of {@link View#VISIBLE}, {@link View#INVISIBLE},
     *   or {@link View#GONE}.
     */
    public int getProgressBarVisibility() {
        return mProgress.getVisibility();
    }

    /**
     * Function to set a click listener for Title TextView
     * 
     * @param listener the onClickListener
     */
    public void setOnTitleClickListener(OnClickListener listener) {
        mTitleView.setOnClickListener(listener);
    }

    @Override
    public void onClick(View view) {
        final Object tag = view.getTag();
        if (tag instanceof Action) {
            final Action action = (Action) tag;
            action.performAction(view);
        }
    }

    /**
     * Adds a list of {@link Action}s.
     * @param actionList the actions to add
     */
    public void addActions(ActionList actionList) {
        int actions = actionList.size();
        for (int i = 0; i < actions; i++) {
            addAction(actionList.get(i));
        }
    }

    /**
     * Adds a new {@link Action}.
     * @param action the action to add
     */
    public void addAction(Action action) {
        final int index = mActionsView.getChildCount();
        addAction(action, index);
    }

    /**
     * Adds a new {@link Action} at the specified index.
     * @param action the action to add
     * @param index the position at which to add the action
     */
    public void addAction(Action action, int index) {
        mActionsView.addView(inflateAction(action), index);
    }

    /**
     * Removes all action views from this action bar
     */
    public void removeAllActions() {
        mActionsView.removeAllViews();
    }

    /**
     * Remove a action from the action bar.
     * @param index position of action to remove
     */
    public void removeActionAt(int index) {
        mActionsView.removeViewAt(index);
    }

    /**
     * Remove a action from the action bar.
     * @param action The action to remove
     */
    public void removeAction(Action action) {
        int childCount = mActionsView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mActionsView.getChildAt(i);
            if (view != null) {
                final Object tag = view.getTag();
                if (tag instanceof Action && tag.equals(action)) {
                    mActionsView.removeView(view);
                }
            }
        }
    }

    /**
     * Returns the number of actions currently registered with the action bar.
     * @return action count
     */
    public int getActionCount() {
        return mActionsView.getChildCount();
    }

    /**
     * Inflates a {@link View} with the given {@link Action}.
     * @param action the action to inflate
     * @return a view
     */
    private View inflateAction(Action action) {
        View view = mInflater.inflate(R.layout.actionbar_item, mActionsView, false);

        ImageButton labelView =
            (ImageButton) view.findViewById(R.id.actionbar_item);
        labelView.setImageResource(action.getDrawable());

        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    public ImageView getcWifiBtn() {
		return cWifiBtn;
	}

	public void setcWifiBtn(ImageView cWifiBtn) {
		this.cWifiBtn = cWifiBtn;
	}

	public ImageView getcMovilBtn() {
		return cMovilBtn;
	}

	public void setcMovilBtn(ImageView cMovilBtn) {
		this.cMovilBtn = cMovilBtn;
	}

	/**
     * A {@link LinkedList} that holds a list of {@link Action}s.
     */
    public static class ActionList extends LinkedList<Action> {
    }

    /**
     * Definition of an action that could be performed, along with a icon to
     * show.
     */
    public interface Action {
        public int getDrawable();
        public void performAction(View view);
    }

    public static abstract class AbstractAction implements Action {
        final private int mDrawable;

        public AbstractAction(int drawable) {
            mDrawable = drawable;
        }

        @Override
        public int getDrawable() {
            return mDrawable;
        }
    }

    public static class IntentAction extends AbstractAction {
        private Context mContext;
        private Intent mIntent;

        public IntentAction(Context context, Intent intent, int drawable) {
            super(drawable);
            mContext = context;
            mIntent = intent;
        }

        @Override
        public void performAction(View view) {
            try {
               mContext.startActivity(mIntent); 
            } catch (ActivityNotFoundException e) {
                Toast.makeText(mContext,
                        mContext.getText(R.string.actionbar_activity_not_found),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

	public LayoutInflater getmInflater() {
		return mInflater;
	}

	public void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}

	public RelativeLayout getmBarView() {
		return mBarView;
	}

	public void setmBarView(RelativeLayout mBarView) {
		this.mBarView = mBarView;
	}

	public ImageView getmLogoView() {
		return mLogoView;
	}

	public void setmLogoView(ImageView mLogoView) {
		this.mLogoView = mLogoView;
	}

	public View getmBackIndicator() {
		return mBackIndicator;
	}

	public void setmBackIndicator(View mBackIndicator) {
		this.mBackIndicator = mBackIndicator;
	}

	public TextView getmTitleView() {
		return mTitleView;
	}

	public void setmTitleView(TextView mTitleView) {
		this.mTitleView = mTitleView;
	}

	public TextView getSub_title() {
		return sub_title;
	}

	public void setSub_title(TextView sub_title) {
		this.sub_title = sub_title;
	}
	
	public int getSub_menu_Visibility() {
		return sub_menu.getVisibility();
	}

	public void setSub_menu_Visibility(int v) {
		this.sub_menu.setVisibility(v);
	}
	

	public LinearLayout getmActionsView() {
		return mActionsView;
	}

	public void setmActionsView(LinearLayout mActionsView) {
		this.mActionsView = mActionsView;
	}

	public ImageButton getmHomeBtn() {
		return mHomeBtn;
	}

	public void setmHomeBtn(ImageButton mHomeBtn) {
		this.mHomeBtn = mHomeBtn;
	}

	public ImageView getSubimage1() {
		return subimage1;
	}

	public void setSubimage1(ImageView subimage1) {
		this.subimage1 = subimage1;
	}

	public ImageView getSubimage2() {
		return subimage2;
	}

	public void setSubimage2(ImageView subimage2) {
		this.subimage2 = subimage2;
	}

	public RelativeLayout getmHomeLayout() {
		return mHomeLayout;
	}

	public void setmHomeLayout(RelativeLayout mHomeLayout) {
		this.mHomeLayout = mHomeLayout;
	}

	public ProgressBar getmProgress() {
		return mProgress;
	}

	public void setmProgress(ProgressBar mProgress) {
		this.mProgress = mProgress;
	}

	public ProgressBar getmProgress_sub() {
		return mProgress_sub;
	}

	public void setmProgress_sub(ProgressBar mProgress_sub) {
		this.mProgress_sub = mProgress_sub;
	}

	public ImageView getSubimage3() {
		return subimage3;
	}

	public void setSubimage3(ImageView subimage3) {
		this.subimage3 = subimage3;
	}

	public ImageView getSubimage4() {
		return subimage4;
	}

	public void setSubimage4(ImageView subimage4) {
		this.subimage4 = subimage4;
	}

    
    
    /*
    public static abstract class SearchAction extends AbstractAction {
        public SearchAction() {
            super(R.drawable.actionbar_search);
        }
    }
    */
}
