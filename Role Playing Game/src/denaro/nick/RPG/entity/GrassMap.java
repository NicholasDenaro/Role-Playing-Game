package denaro.nick.RPG.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public class GrassMap extends Entity
{

	public GrassMap(double x,double y,boolean[][] map)
	{
		super(Sprite.sprite("Grass"),x,y);
		this.map=map;
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Image image()
	{
		Image image=super.image();
		BufferedImage img=new BufferedImage(image.getWidth(null)*map.length,image.getHeight(null)*map[0].length,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		
		for(int a=0;a<map[0].length;a++)
		{
			for(int i=0;i<map.length;i++)
				if(map[i][a])
					g.drawImage(image,i*image.getWidth(null),a*image.getHeight(null),null);
		}
		
		g.dispose();
		return(img);
	}
	
	private boolean[][] map;
}
