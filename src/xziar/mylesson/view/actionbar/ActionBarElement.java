package xziar.mylesson.view.actionbar;

public interface ActionBarElement
{
	public static enum AlignType
	{
		center, left, right;
	}
	
	public AlignType getAlign();
}
