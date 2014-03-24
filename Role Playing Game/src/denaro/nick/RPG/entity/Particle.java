package denaro.nick.RPG.entity;

import denaro.nick.RPG.Main;
import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public class Particle extends Entity
{

	public Particle(Sprite sprite,double x,double y)
	{
		this(sprite,x,y,-1);
	}
	
	public Particle(Sprite sprite,double x,double y,int time)
	{
		super(sprite,x,y);
		this.time=time;
	}

	@Override
	public void tick()
	{
		if(time>0)
			time--;
		if(time==0)
			Main.engine().removeEntity(this,Main.engine().location());
	}
	
	private int time;
}
