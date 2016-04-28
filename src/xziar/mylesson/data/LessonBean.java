package xziar.mylesson.data;

import xziar.mylesson.view.lessonview.LessonBlock;

public class LessonBean implements LessonBlock
{
	public int LID = -1;
	public String lessonName = "";
	public String place = "";
	public int timeFrom, timeLast, timeWeek, weekFrom, weekTo;
	public int color = 0xff444444;

	@Override
	public String getName()
	{
		return lessonName;
	}

	@Override
	public String getAppendix()
	{
		return place;
	}

	@Override
	public int getBlkColor()
	{
		return color;
	}

	@Override
	public int getTime()
	{
		return timeFrom;
	}

	@Override
	public boolean testWeek(int week)
	{
		return (week <= weekTo && week >= weekFrom);
	}

	@Override
	public int getWeekDay()
	{
		return timeWeek;
	}

	@Override
	public int getLast()
	{
		return timeLast;
	}

}
