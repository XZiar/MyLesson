package xziar.mylesson.view.lessonview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import xziar.mylesson.R;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.util.SizeUtil;
import xziar.mylesson.view.lessonview.TimeTableView.OnChooseListener;

public class LessonView extends ViewGroup
{
	private RowHeaders rowH = null;
	private ColumnHeaders colH = null;
	private TimeTableView ttv = null;
	protected Drawable titleBG = null;
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	private OnChooseItemListener OnChooseItem = null;

	private Rect rectRH = new Rect(), rectCH = new Rect(), rectTTV = new Rect();
	private Rect loRH = new Rect(), loCH = new Rect(), loTTV = new Rect();
	private int offsetX = 0, offsetY = 0, moveX = -1, moveY = -1, lastX, lastY;
	private int locTX, locTY, maxDX, maxDY, dDis;
	private boolean isTTV = false, isMoved = false;
	private View objTouch;

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
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.LessonView);
		titleBG = ta.getDrawable(R.styleable.LessonView_titleBG);
		ta.recycle();
		init(context);
	}

	private void init(Context context)
	{
		final int blkSize = 56;
		dDis = SizeUtil.dp2px(blkSize) / 10;
		rowH = new RowHeaders(context, 38, blkSize);
		colH = new ColumnHeaders(context, blkSize, 44);
		ttv = new TimeTableView(context, blkSize);

		paintLine.setColor(Color.GRAY);
		paintLine.setStrokeWidth(2.0f);
		paintLine.setStyle(Style.STROKE);

		if (titleBG == null)
			titleBG = new ColorDrawable(0xfff7f7f7);
		colH.setBackground(titleBG);

		if (getBackground() == null)
			setBackground(new ColorDrawable(0xffdde2e7));
		else
			setBackground(getBackground());

		ttv.setChooseListener(new OnChooseListener()
		{
			@Override
			public void onChoose(LessonBlock lb)
			{
				OnChooseItem.onChoose(lb);
			}
		});
	}

	public void setData(LessonBean[] lbs)
	{
		List<LessonBlock> ls = new ArrayList<>();
		if (lbs != null)
		{
			for (LessonBean lb : lbs)
				ls.add(lb);
		}
		Log.v("tester", "setData:" + ls.size());
		ttv.setLessons(ls);
		postInvalidate();
	}

	private boolean scrollElement(int dx, int dy)
	{
		lastX = moveX;
		lastY = moveY;
		moveX = Math.max(maxDX, Math.min(0, offsetX + dx));
		moveY = Math.max(maxDY, Math.min(0, offsetY + dy));
		if (lastX == moveX && lastY == moveY)
			return false;// no change

		loTTV.set(rectTTV);
		loTTV.offset(moveX, moveY);
		loRH.set(rectRH);
		loRH.offset(0, moveY);
		loCH.set(rectCH);
		loCH.offset(moveX, 0);

		ttv.layout(loTTV.left, loTTV.top, loTTV.right, loTTV.bottom);
		rowH.layout(loRH.left, loRH.top, loRH.right, loRH.bottom);
		colH.layout(loCH.left, loCH.top, loCH.right, loCH.bottom);
		return true;
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
		Log.v("tester", "LessonView : " + getWidth() + "," + getHeight());
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		Log.v("tester", "LessonView onLayout");
		scrollElement(0, 0);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		// Log.v("tester", "LessonView dispatchDraw");

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
		titleBG.setBounds(0, 0, rectRH.right, rectCH.bottom);
		titleBG.draw(canvas);

		canvas.drawLine(0, loCH.bottom, canvas.getWidth(), loCH.bottom,
				paintLine);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent e)
	{
		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			isMoved = isTTV = false;
			locTX = (int) e.getX();
			locTY = (int) e.getY();
			if (rectTTV.contains(locTX, locTY))
			{
				objTouch = ttv;
				isTTV = true;
			}
			else if (rectRH.contains(locTX, locTY))
			{
				objTouch = rowH;
			}
			else if (rectCH.contains(locTX, locTY))
			{
				objTouch = colH;
			}
			objTouch.dispatchTouchEvent(e);
			Log.v("tester", "Touch_Down at " + locTX + "," + locTY);
			break;
		case MotionEvent.ACTION_MOVE:
			if (isTTV)
				onTouchEvent(e);
			break;
		case MotionEvent.ACTION_UP:
			if (!isMoved)
			{
				objTouch.dispatchTouchEvent(e);
			}
			else if (isTTV)
				onTouchEvent(e);
			break;
		}

		return true;

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_MOVE:
			int dx = (int) e.getX() - locTX, dy = (int) e.getY() - locTY;
			if (dx == 0 && dy == 0)
				break;
			else if (Math.abs(dx) + Math.abs(dy) > dDis)
				isMoved = true;
			// Log.v("tester", "Touch_Move " + dx + "," + dy);

			if (scrollElement(dx, dy))
				invalidate();
			break;
		case MotionEvent.ACTION_UP:
			offsetX = moveX;
			offsetY = moveY;
			invalidate();
			break;
		}
		return true;
	}

	public interface OnChooseItemListener
	{
		public void onChoose(LessonBlock lb);
	}

	public void SetOnChooseItemListener(OnChooseItemListener chooseListener)
	{
		this.OnChooseItem = chooseListener;
	}

	public void setTitleBG(Drawable tbg)
	{
		titleBG = tbg;
		colH.setBackground(titleBG);
		postInvalidate();
	}

	@Override
	public void setBackground(Drawable bg)
	{
		super.setBackground(bg);
		if (ttv != null)
			ttv.setBackground(bg);
		if (rowH != null)
			rowH.setBackground(bg);
		postInvalidate();
	}
}
