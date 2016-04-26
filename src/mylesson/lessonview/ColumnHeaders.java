/**
 * @author XZiar
 */
package mylesson.lessonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.util.Log;
import android.view.View;
import mylesson.util.SizeUtil;

public class ColumnHeaders extends View
{
	private final static String[] days = { "周一", "周二", "周三", "周四", "周五", "周六",
			"周日" };

	protected Paint paintDay = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintTime = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected Paint paintBG = new Paint(Paint.ANTI_ALIAS_FLAG);

	private int viewWidth, viewHeight, width, height;// in px

	/**
	 * constructor of ColumnHeaders
	 * 
	 * @param context
	 *            context
	 * @param columnWidth
	 *            width of one column
	 * @param height
	 *            height
	 */
	public ColumnHeaders(Context context, int columnWidth, int height)
	{
		super(context);

		this.width = SizeUtil.dp2px(columnWidth);
		viewHeight = this.height = SizeUtil.dp2px(height);
		viewWidth = this.width * 7;

		paintDay.setTextAlign(Paint.Align.CENTER);
		paintDay.setTextSize(height * 0.45f);
		paintDay.setColor(Color.BLACK);
		paintTime.setTextAlign(Paint.Align.CENTER);
		paintTime.setTextSize(height * 0.35f);
		paintTime.setColor(Color.GRAY);
		paintBG.setColor(0xfff7f7f7);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.v("tester", "Col onMeasure " + viewWidth + "," + viewHeight);
		setMeasuredDimension(viewWidth, viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		int w = canvas.getWidth(), h = canvas.getHeight();
		Log.v("tester", "colH draw " + w + "," + h);
		
		FontMetricsInt fontMetrics = paintDay.getFontMetricsInt();
		float baselineDay = (height * 3 / 5 - fontMetrics.top - fontMetrics.bottom)
				/ 2f;
		fontMetrics = paintTime.getFontMetricsInt();
		float baselineTime = (height * 7 / 5 - fontMetrics.top
				- fontMetrics.bottom) / 2f;

		canvas.drawRect(0, 0, viewWidth, viewHeight, paintBG);
		
		for (Integer a = 0; a < days.length; a++)
		{
			float baseX = width * a + width / 2f;
			canvas.drawText(days[a], baseX, baselineDay, paintDay);
			canvas.drawText(a.toString(), baseX, baselineTime, paintTime);
		}
	}
}
