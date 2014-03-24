package denaro.nick.RPG.entity;

import denaro.nick.RPG.Action;
import denaro.nick.RPG.Stats;
import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public abstract class LivingEntity extends Entity
{
	public LivingEntity(Sprite sprite,double x,double y)
	{
		super(sprite,x,y);
		direction=0;
		walkCycle=0;
		action=null;
		
		//TODO something with stats...
		stats=new Stats(1, 1, 1, 1, 1, 1);
		
		reach=BASE_REACH;
	}
	
	public int reach()
	{
		return(reach);
	}
	
	public double lookPosX()
	{
		return(x()+Math.cos(direction())*reach);
	}
	
	public double lookPosY()
	{
		return(y()+Math.sin(direction())*reach);
	}
	
	public int directionImage()
	{	
		if((direction>Math.PI*5/4)&&(direction<Math.PI*7/4))
		{
			return(8);
		}
		else if((direction>Math.PI*1/4)&&(direction<Math.PI*3/4))
		{
			return(0);
		}
		else if((direction<Math.PI*1/4)||(direction>Math.PI*7/4))
		{
			return(16);
		}
		return(24);
	}

	public double direction()
	{
		return(direction);
	}
	
	protected void direction(double direction)
	{
		double dir=Math.atan2(Math.sin(direction),Math.cos(direction))+Math.PI*2;
		if(dir>Math.PI*2)
			dir-=Math.PI*2;
		this.direction=dir;
	}
	
	public double walkCycle()
	{
		return(walkCycle);
	}
	
	protected void walkCycle(double walkCycle)
	{
		this.walkCycle=walkCycle;
	}
	
	public Action action()
	{
		return(action);
	}
	
	public void action(Action action)
	{
		this.action=action;
	}
	
	private Stats stats;
	
	private double direction;
	
	private double walkCycle;
	
	private Action action;
	
	private int reach;
	
	public static final int BASE_REACH=15;
}
