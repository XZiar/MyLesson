/**
 * @author XZiar
 */
package xziar.mylesson.lessonview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import xziar.mylesson.util.SizeUtil;

@SuppressLint("ClickableViewAccessibility")
public class TimeTableView extends View implements OnTouchListener
{
	protected Bitmap bufBM = null;
	protected Canvas bufCV = null;
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintBlk = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected TextPaint paintTxt = new TextPaint(Paint.ANTI_ALIAS_FLAG);

	int bgColor = 0xffdde2e7;
	private int viewWidth, viewHeight, width, height;
	private float blkPadX, blkPadY;
	private boolean isReBuf = true;
	private LessonBlock[][] lessonMap = null;
	private ArrayList<LessonBlock> lessons = new ArrayList<LessonBlock>();

	private OnChooseListener chooseListener = null;

	/**
	 * constructor of TimeTableView
	 * 
	 * @param context
	 *            context
	 * @param blkSize
	 *            size of one block
	 */
	public TimeTableView(Context context, int blkSize)
	{
		super(context);
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		setOnTouchListener(this);
		setClickable(true);

		Log.v("tester", "TimeTable initialize");
		width = height = SizeUtil.dp2px(blkSize) + 1;
		viewHeight = height * 12;
		viewWidth = width * 7;
		blkPadX = width / 8f;
		blkPadY = height / 8f;

		paintLine.setColor(Color.LTGRAY);
		paintLine.setStrokeWidth(1.0f);
		paintLine.setStyle(Style.STROKE);
		paintTxt.setTextSize(width * 0.24f);
		paintTxt.setColor(Color.WHITE);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		setMeasuredDimension(viewWidth, viewHeight);
		Log.v("tester", "TTV onMeasure " + viewWidth + "," + viewHeight);
	}

	private void drawBlock(Canvas canvas, LessonBlock lb)
	{
		int left = lb.getWeekDay() * width;
		int top = lb.getTime() * height;
		int bottom = top + lb.getLast() * height;
		for (int t = lb.getTime(), w = lb.getWeekDay(), b = lb.getLast(); b-- > 0;)
		{
			lessonMap[w][t++] = lb;
		}

		paintBlk.setColor(lb.getBlkColor());
		canvas.drawRect(left, top, left + width - 1, bottom - 1, paintBlk);

		StaticLayout sl = new StaticLayout(lb.getName(), paintTxt,
				(int) (width - blkPadX * 2), Alignment.ALIGN_NORMAL, 1.0f, 0.0f,
				true);
		canvas.save();
		canvas.translate(left + blkPadX, top + blkPadY);
		sl.draw(canvas);
		canvas.restore();
	}

	protected void bufferDraw()
	{
		if (bufCV == null || bufBM == null || bufBM.getWidth() != viewWidth
				|| bufBM.getHeight() != viewHeight)
		{
			bufBM = Bitmap.createBitmap(viewWidth, viewHeight,
					Bitmap.Config.ARGB_8888);
			bufCV = new Canvas(bufBM);
		}

		Log.v("tester", "TTV bufDraw HW:" + bufCV.isHardwareAccelerated());
		bufCV.clipRect(0, 0, viewWidth, viewHeight);
		bufCV.drawColor(bgColor);

		float baseY = 0;
		for (int a = 0; a < 12; a++)
		{
			baseY += height;
			bufCV.drawLine(0, baseY, viewWidth, baseY, paintLine);
		}
		lessonMap = new LessonBlock[7][12];
		for (LessonBlock lb : lessons)
		{
			drawBlock(bufCV, lb);
		}

		isReBuf = false;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Log.v("tester", "TTV draw HW:" + canvas.isHardwareAccelerated());
		if (isReBuf)
			bufferDraw();
		canvas.drawBitmap(bufBM, 0, 0, null);

	}

	@Override
	public boolean onTouch(View v, MotionEvent e)
	{
		switch (e.getActionMasked())
		{
		case MotionEvent.ACTION_UP:
			// click
			int dx = (int) e.getX() - getLeft(), dy = (int) e.getY() - getTop();
			int w = dx / width, t = dy / height;
			String txt = "click week " + w + " time " + t;
			Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
			if(chooseListener != null)
				chooseListener.onChoose(lessonMap[w][t]);
			break;
		}
		return false;
	}

	public interface OnChooseListener
	{
		public void onChoose(LessonBlock lb);
	}

	public void setChooseListener(OnChooseListener chooseListener)
	{
		this.chooseListener = chooseListener;
	}
	
	public void setLessons(ArrayList<LessonBlock> lessons)
	{
		this.lessons = lessons;
		bufferDraw();
	}
}
