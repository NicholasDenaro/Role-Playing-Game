package denaro.nick.RPG;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import denaro.nick.RPG.entity.Player;
import denaro.nick.core.GameView2D;

public class HUD
{
	
	public Image image()
	{
		GameView2D view=(GameView2D)Main.engine().view();
		BufferedImage img=new BufferedImage(view.width(),view.height(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		
		Player player=Main.player();
		
		int xx=(int)(player.lookPosX());
		int yy=(int)(player.lookPosY());
		
		g.setColor(Color.cyan);
		g.fillOval(xx-2,yy-2,3,3);
		
		
		//Draws A
		g.setColor(new Color(255,255,255,150));
		g.fillOval(view.width()-32,32,16,16);
		g.setColor(new Color(150,150,150,150));
		g.drawString("A",view.width()-32+4,12+32);
		
		
		//Draws B
		g.setColor(new Color(255,255,255,150));
		g.fillOval(view.width()-16,16,16,16);
		g.setColor(new Color(150,150,150,150));
		g.drawString("B",view.width()-16+4,12+16);
		if(Main.player().mainHand()!=null)
			g.drawImage(player.mainHand().image(),view.width()-16,16,null);
		
		//Draws X
		g.setColor(new Color(255,255,255,150));
		g.fillOval(view.width()-48,16,16,16);
		g.setColor(new Color(150,150,150,150));
		g.drawString("X",view.width()-48+4,12+16);
		if(player.buttonDown(Main.ACTIONMODIFIER2))
			g.drawString("#",view.width()-48+4,12+16);
				
		//Draws Y
		g.setColor(new Color(255,255,255,150));
		g.fillOval(view.width()-32,0,16,16);
		g.setColor(new Color(150,150,150,150));
		g.drawString("Y",view.width()-32+4,12);

		
		g.dispose();
		return(img);
	}
}
