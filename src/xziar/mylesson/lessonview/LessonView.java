package xziar.mylesson.lessonview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import xziar.mylesson.data.LessonBean;

public class LessonView extends ViewGroup
{
	private RowHeaders rowH = null;
	private ColumnHeaders colH = null;
	private TimeTableView ttv = null;

	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);

	private Rect rectRH = new Rect(), rectCH = new Rect(), rectTTV = new Rect();
	private Rect loRH = new Rect(), loCH = new Rect(), loTTV = new Rect();
	private int moveX = 0, moveY = 0, lastX, lastY, maxDX, maxDY;
	private boolean isMove = false;

	public LessonView(Context context)
	{
		super(context);
		init(context);
	}

	public LessonView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public LessonView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context)
	{
		final int blkSize = 64;
		rowH = new RowHeaders(context, 38, blkSize);
		colH = new ColumnHeaders(context, blkSize, 44);
		ttv = new TimeTableView(context, blkSize);

		paintLine.setColor(Color.GRAY);
		paintLine.setStrokeWidth(2.0f);
		paintLine.setStyle(Style.STROKE);
		
/*		LessonBean lb = new LessonBean();
		lb.timeWeek = lb.timeFrom = 0;
		lb.timeTo = 3;
		lb.lessonName= "手机软件开发";
		lb.color = 0xff40b060;
		ttv.lessons.add(lb);
		LessonBean lb2 = new LessonBean();
		lb2.timeWeek = lb2.timeFrom = 5;
		lb2.timeTo = 7;
		lb2.lessonName= "人机交互";
		lb2.color = 0xffb040b0;
		ttv.lessons.add(lb2);*/
		
		for(int a=0;a<7;a++)
		{
			for(int b=0;b<12;b+=2)
			{
				LessonBean lb = new LessonBean();
				lb.timeWeek = a;
				lb.timeFrom = b;
				lb.timeTo = b+2;
				lb.lessonName= "手机软件开发";
				lb.color = 0xff40b060;
				ttv.lessons.add(lb);
			}
		}
	}

	private void scrollElement(int dx, int dy)
	{
		moveX = Math.max(maxDX, Math.min(0, moveX + dx));
		moveY = Math.max(maxDY, Math.min(0, moveY + dy));

		loTTV.set(rectTTV);
		loTTV.offset(moveX, moveY);
		loRH.set(rectRH);
		loRH.offset(0, moveY);
		loCH.set(rectCH);
		loCH.offset(moveX, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.v("tester", "LessonView onMeasure");
		int w, h;

		colH.measure(widthMeasureSpec, heightMeasureSpec);
		w = colH.getMeasuredWidth();
		h = colH.getMeasuredHeight();
		rectCH.set(0, 0, w, h);
		Log.v("tester", "colH : " + w + "," + h);

		rowH.measure(widthMeasureSpec, heightMeasureSpec);
		w = rowH.getMeasuredWidth();
		h = rowH.getMeasuredHeight();
		rectRH.set(0, 0, w, h);
		Log.v("tester", "rowH : " + w + "," + h);

		ttv.measure(widthMeasureSpec, heightMeasureSpec);
		w = ttv.getMeasuredWidth();
		h = ttv.getMeasuredHeight();
		rectTTV.set(0, 0, w, h);
		Log.v("tester", "ttv : " + w + "," + h);

		int dx = rectRH.right, dy = rectCH.bottom;
		rectTTV.offset(dx, dy);
		rectRH.offset(0, dy);
		rectCH.offset(dx, 0);

		maxDX = getWidth() - rectTTV.right;
		maxDY = getHeight() - rectTTV.bottom;

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		Log.v("tester", "LessonView onLayout");
		scrollElement(0, 0);

		Log.v("tester", "loTTV:" + loTTV);
		ttv.layout(loTTV.left, loTTV.top, loTTV.right, loTTV.bottom);

		Log.v("tester", "loRH:" + loRH);
		rowH.layout(loRH.left, loRH.top, loRH.right, loRH.bottom);

		Log.v("tester", "loCH:" + loCH);
		colH.layout(loCH.left, loCH.top, loCH.right, loCH.bottom);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		Log.v("tester", "LessonView dispatchDraw");

		canvas.save();
		canvas.clipRect(rectTTV);
		canvas.translate(loTTV.left, loTTV.top);
		ttv.draw(canvas);
		canvas.restore();

		canvas.save();
		canvas.clipRect(rectRH);
		canvas.translate(loRH.left, loRH.top);
		rowH.draw(canvas);
		canvas.restore();

		canvas.save();
		canvas.clipRect(rectCH);
		canvas.translate(loCH.left, loCH.top);
		colH.draw(canvas);
		canvas.restore();

		// draw self
		canvas.save();
		canvas.clipRect(0, 0, rectRH.right, rectCH.bottom);
		canvas.drawColor(0xfff7f7f7);
		canvas.restore();
		canvas.drawLine(0, loCH.bottom, canvas.getWidth(), loCH.bottom,
				paintLine);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent e)
	{
		Log.v("tester", "dispatchTouchEvent");
		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			lastX = (int) e.getRawX();
			lastY = (int) e.getRawY();
			if (rectTTV.contains(lastX, lastY))
			{
				ttv.onTouchEvent(e);
				isMove = true;
			}
			else if (rectRH.contains(lastX, lastY))
			{
				rowH.onTouchEvent(e);
			}
			else if (rectCH.contains(lastX, lastY))
			{
				colH.onTouchEvent(e);
			}
			Log.v("tester", "Touch_Down at " + lastX + "," + lastY);
			break;
		case MotionEvent.ACTION_MOVE:
			if (isMove)
				onTouchEvent(e);
			break;
		case MotionEvent.ACTION_UP:
			isMove = false;
			break;
		}

		return true;

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		Log.v("tester", "onTouchEvent");

		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_MOVE:
			int newX = (int) e.getRawX(), newY = (int) e.getRawY();
			int dx = newX - lastX, dy = newY - lastY;
			lastX = newX;
			lastY = newY;
			Log.v("tester", "Touch_Move " + dx + "," + dy);

			scrollElement(dx, dy);
			invalidate();
		}
		return true;
	}

}
