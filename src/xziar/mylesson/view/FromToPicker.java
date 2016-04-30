package xziar.mylesson.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class FromToPicker extends LinearLayout
{
	private int bakSDD;
	private Field fdSDD;
	private NumberPicker npFrom, npTo;

	public FromToPicker(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public FromToPicker(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public FromToPicker(Context context)
	{
		this(context, null);
	}

	private void init(Context context)
	{
		npFrom = new NumberPicker(context);
		npTo = new NumberPicker(context);
		npFrom.setMaxValue(6);
		npTo.setMaxValue(12);
		try
		{
			fdSDD = NumberPicker.class
					.getDeclaredField("mSelectionDividersDistance");
			fdSDD.setAccessible(true);
			bakSDD = (int) fdSDD.get(npFrom);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		addView(npFrom, -1, lp);
		addView(npTo, -1, lp);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		try
		{
			int newSDD = (int) (getHeight() * bakSDD / 270.0f);
			fdSDD.set(npFrom, newSDD);
			fdSDD.set(npTo, newSDD);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

}
