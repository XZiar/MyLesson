/**
 * @author XZiar
 */
package mylesson.lessonview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import mylesson.util.SizeUtil;

@SuppressLint("ClickableViewAccessibility")
public class TimeTableView extends View
{
	private int viewWidth, viewHeight, width, height;
	private float blkPadX, blkPadY;

	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintBG = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintBlk = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected TextPaint paintTxt = new TextPaint(Paint.ANTI_ALIAS_FLAG);

	protected ArrayList<LessonBlock> lessons = new ArrayList<LessonBlock>();

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

		setClickable(true);

		Log.v("tester", "TimeTable initialize");
		width = height = SizeUtil.dp2px(blkSize);
		viewHeight = height * 12;
		viewWidth = width * 7;
		blkPadX = width / 8f;
		blkPadY = height / 8f;

		paintLine.setColor(Color.LTGRAY);
		paintLine.setStrokeWidth(1.0f);
		paintLine.setStyle(Style.STROKE);
		paintBG.setColor(0xffdde2e7);
		paintTxt.setTextSize(width * 0.24f);
		paintTxt.setColor(Color.WHITE);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(viewWidth, viewHeight);
		Log.v("tester", "TTV onMeasure " + viewWidth + "," + viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// super.onDraw(canvas);
		int w = canvas.getWidth(), h = canvas.getHeight();
		Log.v("tester", "TTV draw " + w + "," + h);

		canvas.drawRect(0, 0, viewWidth, viewHeight, paintBG);

		float baseY = 0;
		for (int a = 0; a < 12; a++)
		{
			baseY += height;
			canvas.drawLine(0, baseY, viewWidth, baseY, paintLine);
		}

		for (LessonBlock lb : lessons)
		{
			drawBlock(canvas, lb);
		}

	}

	private void drawBlock(Canvas canvas, LessonBlock lb)
	{
		paintBlk.setColor(lb.getBlkColor());
		int left = lb.getWeekDay() * width;
		int[] time = lb.getTime();
		int top = time[0] * height;
		int last = time[1] - time[0];
		canvas.drawRect(left, top, left + width, top + last * height, paintBlk);

		StaticLayout sl = new StaticLayout(lb.getName(), paintTxt,
				(int) (width - blkPadX * 2), Alignment.ALIGN_NORMAL, 1.0f, 0.0f,
				true);
		canvas.save();
		canvas.translate(left + blkPadX, top + blkPadY);
		sl.draw(canvas);
		canvas.restore();
	}
}
