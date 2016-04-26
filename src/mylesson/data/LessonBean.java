package mylesson.data;

import mylesson.lessonview.LessonBlock;

public class LessonBean implements LessonBlock
{
	public String lessonName = "";
	public String place = "";
	public int timeFrom, timeTo, timeWeek, weekFrom, weekTo;
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
	public int[] getTime()
	{
		return new int[] { timeFrom, timeTo };
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

}
