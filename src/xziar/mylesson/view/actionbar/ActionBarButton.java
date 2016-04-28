package xziar.mylesson.view.actionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ActionBarButton extends View
{

	public ActionBarButton(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
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
		
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
	}
	
	
}
