package denaro.nick.RPG.entity;

import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public class Field extends Entity
{

	public Field(double x,double y)
	{
		super(Sprite.sprite("Field"),x,y);
		tilled=false;
		water=0;
		this.depth((int)y()-sprite().height());
	}
	
	public void till()
	{
		tilled=true;
		imageIndex((tilled?2:0)+(water>0?1:0));
	}
	
	public void water()
	{
		water++;
		imageIndex((tilled?2:0)+(water>0?1:0));
	}
	
	@Override
	public void tick()
	{
		
	}
	
	private boolean tilled;
	private int water;
}
