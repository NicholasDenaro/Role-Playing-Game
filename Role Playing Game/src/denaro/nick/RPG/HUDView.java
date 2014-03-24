package denaro.nick.RPG;

import java.awt.Graphics2D;

import denaro.nick.RPG.menu.Menu;
import denaro.nick.core.GameView2D;
import denaro.nick.core.Location;

public class HUDView extends GameView2D
{

	public HUDView(int width,int height,double hscale,double vscale, HUD hud)
	{
		super(width,height,hscale,vscale);
		this.hud=hud;
	}
	
	@Override
	public void drawLocation(Location location, Graphics2D g)
	{
		super.drawLocation(location,g);
		
		g.drawImage(hud.image(),0,0,null);
		
		for(Menu menu:Main.menus())
		{
			g.drawImage(menu.image(),menu.x(),menu.y(),null);
		}
	}
	
	private HUD hud;
}
