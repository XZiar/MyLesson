package xziar.mylesson.data;

import java.io.Serializable;

import xziar.mylesson.view.lessonview.LessonBlock;

public class LessonBean implements LessonBlock, Serializable
{
	private static final long serialVersionUID = 509138607467911125L;
	
	public int LID = -1;
	public String lessonName = "";
	public String place = "";
	public String teacher = "";
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
		return timeFrom - 1;
	}

	@Override
	public boolean testWeek(int week)
	{
		return (week <= weekTo && week >= weekFrom);
	}

	@Override
	public int getWeekDay()
	{
		return timeWeek - 1;
	}

	@Override
	public int getLast()
	{
		return timeLast;
	}

}
