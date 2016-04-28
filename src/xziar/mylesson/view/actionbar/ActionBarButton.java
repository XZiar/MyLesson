package xziar.mylesson.view.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import xziar.mylesson.R;
import xziar.mylesson.util.SizeUtil;

public class ActionBarButton extends View implements ActionBarElement
{
	private Drawable btnImg = null;
	private int blkSize;
	int alignType = 0;

	public ActionBarButton(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ACtionBarButton);
		btnImg = ta.getDrawable(R.styleable.ACtionBarButton_img);
		ta.recycle();
		ta = context.obtainStyledAttributes(attrs, R.styleable.ActionBarElement);
		alignType = ta.getInt(R.styleable.ActionBarElement_Align, 0);
		ta.recycle();
		init();
	}

	public ActionBarButton(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ActionBarButton(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		blkSize = SizeUtil.dp2px(32);
		if (btnImg == null)
		{
			btnImg = new ColorDrawable(0xffff7777);
			Log.v("tester", "color drawable:" + btnImg.getIntrinsicWidth() + ","
					+ btnImg.getIntrinsicHeight());
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(blkSize, blkSize);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		btnImg.setBounds(0, 0, blkSize, blkSize);
		btnImg.draw(canvas);
	}

	@Override
	public AlignType getAlign()
	{
		return AlignType.values()[alignType];
	}

}
