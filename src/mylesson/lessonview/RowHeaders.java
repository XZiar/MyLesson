/**
 * @author XZiar
 */
package mylesson.lessonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.util.Log;
import android.graphics.Typeface;
import android.view.View;
import mylesson.util.SizeUtil;

public class RowHeaders extends View
{
	private final static String[] times = { "8:00", "8:55", "9:55", "10:50",
			"11:45", "13:30", "14:25", "15:25", "16:20", "18:30", "19:25",
			"20:20" };

	protected Paint paintTime = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintCnt = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintBG = new Paint(Paint.ANTI_ALIAS_FLAG);

	private int viewWidth, viewHeight, width, height;// in px

	/**
	 * constructor of RowHeaders
	 * 
	 * @param context
	 *            context
	 * @param width
	 *            width
	 * @param rowHeight
	 *            height of one row
	 */
	public RowHeaders(Context context, int width, int rowHeight)
	{
		super(context);

		viewWidth = this.width = SizeUtil.dp2px(width);
		this.height = SizeUtil.dp2px(rowHeight);
		viewHeight = this.height * 12;

		paintTime.setTextAlign(Paint.Align.CENTER);
		paintTime.setTextSize(width * 0.45f);
		paintTime.setColor(Color.GRAY);
		paintCnt.setTextAlign(Paint.Align.CENTER);
		paintCnt.setTextSize(width * 0.65f);
		paintCnt.setTypeface(Typeface.DEFAULT_BOLD);
		paintCnt.setColor(Color.BLACK);
		paintLine.setColor(Color.LTGRAY);
		paintLine.setStrokeWidth(1.0f);
		paintLine.setStyle(Style.STROKE);
		paintBG.setColor(0xffdde2e7);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.v("tester", "Row onMeasure " + viewWidth + "," + viewHeight);
		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		int w = canvas.getWidth(), h = canvas.getHeight();
		Log.v("tester", "rowH draw " + w + "," + h);
		
		FontMetricsInt fontMetrics = paintTime.getFontMetricsInt();
		float baselineTime = (height * 1 / 2 - fontMetrics.top
				- fontMetrics.bottom) / 2f;
		fontMetrics = paintCnt.getFontMetricsInt();
		float baselineCnt = (height * 4 / 3 - fontMetrics.top
				- fontMetrics.bottom) / 2f;

		canvas.drawRect(0, 0, viewWidth, viewHeight, paintBG);
		
		float baseX = width / 2f, baseY = 0;
		for (Integer a = 0; a < times.length;)
		{
			canvas.drawText(times[a], baseX, baseY + baselineTime, paintTime);
			a++;
			canvas.drawText(a.toString(), baseX, baseY + baselineCnt, paintCnt);
			baseY += height;
			canvas.drawLine(0, baseY, width, baseY, paintLine);
		}
	}
}
