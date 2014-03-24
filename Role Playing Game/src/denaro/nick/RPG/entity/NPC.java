package denaro.nick.RPG.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public class NPC extends LivingEntity implements Solid
{

	public NPC(Sprite sprite,double x,double y)
	{
		super(sprite,x,y);
	}
	
	public String getDialog()
	{
		
		return("This string is hard-coded!");
	}

	@Override
	public void tick()
	{
		this.depth((int)y());
		this.imageIndex((int)walkCycle()%7+directionImage());
	}
	
	/*public Image image()
	{
		Image image=super.image();
		BufferedImage img=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		g.drawImage(image,0,0,null);
		g.setColor(Color.white);
		g.fillArc(0,0,img.getWidth(),img.getHeight(),(int)(Math.toDegrees(direction())-5),10);
		g.dispose();
		return(img);
	}*/
}
