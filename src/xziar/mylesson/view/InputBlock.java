package xziar.mylesson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import xziar.mylesson.R;
import xziar.mylesson.util.SizeUtil;

public class InputBlock extends LinearLayout
{
	Drawable icon;
	ImageView ico;
	private int pad = 0, setH = 0;
	public static int minH = SizeUtil.dp2px(40);

	public InputBlock(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, new int[] {
				android.R.attr.padding, android.R.attr.layout_height, R.attr.icon });
		pad = ta.getDimensionPixelSize(0, 0);
		setH = ta.getDimensionPixelSize(1, 0);
		Log.v("tester", "IB setH="+setH);
		icon = ta.getDrawable(2);
		ta.recycle();
		init(context);
	}

	public InputBlock(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public InputBlock(Context context)
	{
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		setGravity(Gravity.CENTER_VERTICAL);
		setBackground(context.getResources().getDrawable(R.drawable.inputblock));
		if (pad < 2)
		{
			pad = 2;
			setPadding(pad, pad, pad, pad);
		}
		if (setH < minH)
		{
			setH = minH;
			ViewGroup.LayoutParams parm = getLayoutParams();
			if (parm == null)
				parm = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
			parm.height = setH;
			setLayoutParams(parm);
		}
		if (icon != null)
		{
			ico = new ImageView(context, null);
			ico.setImageDrawable(icon);
			ico.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			int size = SizeUtil.dp2px(18);
			LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(size,
					size);
			parm.leftMargin = parm.rightMargin = SizeUtil.dp2px(10);
			super.addView(ico, 0, parm);
		}
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		Log.v("tester", "AddIB child:" + child.getClass().getName());
		super.addView(child, index, params);
	}

}
