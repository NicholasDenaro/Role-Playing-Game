package denaro.nick.RPG;

public abstract class Action
{
	public Action(int duration, boolean active)
	{
		init();
		this.duration=duration;
		this.active=active;
	}
	
	public void init()
	{
		
	}
	
	public abstract void action();
	
	public boolean shouldEnd()
	{
		if(duration-->0)
		{
			if(active)
				action();
			return(false);
		}
		else
		{
			action();
			return(true);
		}
	}
	
	/**duration in ticks(usually)*/
	private int duration;
	
	private boolean active;
}
