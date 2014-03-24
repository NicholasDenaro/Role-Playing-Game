package denaro.nick.RPG.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import denaro.nick.RPG.Main;
import denaro.nick.core.EntityEvent;
import denaro.nick.core.EntityListener;
import denaro.nick.core.Sprite;

public class FieldOutliner extends Particle implements EntityListener
{

	public FieldOutliner(Sprite sprite,double x,double y)
	{
		super(sprite,x,y);
	}

	@Override
	public void tick()
	{
		super.tick();
		this.depth((int)y());
	}
	
	@Override
	public Image image()
	{
		if(Main.engine().location().entitiesAtPoint(x(),y(),Field.class).isEmpty())
			return(null);
		return(super.image());
	}
	
	@Override
	public void entityMove(EntityEvent event)
	{
		Player player=(Player)event.source();
		final double x=player.lookPosX();
		final double y=player.lookPosY();
		
		move((int)((x)/16)*16,(int)((y)/16)*16);
	}

	@Override
	public void entityDepthChange(EntityEvent event)
	{
		// TODO Auto-generated method stub
		
	}
	
}
