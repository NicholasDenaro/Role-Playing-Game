package denaro.nick.RPG.menu;

import java.awt.image.BufferedImage;

import denaro.nick.core.ControllerListener;
import denaro.nick.core.Focusable;
import denaro.nick.core.Sprite;

public abstract class Menu implements Focusable, ControllerListener
{
	public Menu(Sprite sprite, int x, int y)
	{
		this.sprite=sprite;
		this.x=x;
		this.y=y;
		
	}
	
	public int x()
	{
		return(x);
	}
	
	public int y()
	{
		return(y);
	}
	
	public Sprite sprite()
	{
		return(sprite);
	}
	
	public abstract BufferedImage image();
	/*public BufferedImage image()
	{
		BufferedImage img=new BufferedImage(sprite.width(),sprite.height(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		
		g.drawImage(sprite.subimage(0),0,0,null);
		
		return(img);
	}*/
	
	private int x;
	private int y;
	
	private Sprite sprite;
}
