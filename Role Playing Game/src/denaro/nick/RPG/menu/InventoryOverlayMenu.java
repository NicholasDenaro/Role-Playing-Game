package denaro.nick.RPG.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import denaro.nick.RPG.Inventory;
import denaro.nick.RPG.ItemStack;
import denaro.nick.RPG.Main;
import denaro.nick.RPG.Tool;
import denaro.nick.core.ControllerEvent;
import denaro.nick.core.Sprite;

public class InventoryOverlayMenu extends Menu
{

	public InventoryOverlayMenu(Sprite sprite,int x,int y, Inventory inventory)
	{
		super(sprite,x,y);
		this.inventory=inventory;
		index=0;
		reset=false;
		lock=new Object();
	}

	@Override
	public BufferedImage image()
	{
		BufferedImage img=new BufferedImage(sprite().width(),sprite().height(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g=img.createGraphics();
		
		g.drawImage(sprite().subimage(0),0,0,null);
		
		synchronized(lock)
		{
			for(int i=0;i<8;i++)
			{
				ItemStack stack=inventory.get(i);
				if(stack!=null)
				{
					g.drawImage(stack.item().image(),8+i*24,8,null);
				}
				
			}
		}
		
		g.drawRect(8+index*24-2,8-2,20,20);
		
		return(img);
	}
	
	@Override
	public void actionPerformed(ControllerEvent event)
	{
		if(event.action()==ControllerEvent.RELEASED)
		{
			if(event.code()==Main.INVENTORY)
			{
				Main.closeMenu();
			}
		}
		if(event.action()==ControllerEvent.PRESSED)
		{
			if(event.code()==Main.CANCEL)
			{
				synchronized(lock)
				{
					if(inventory.get(index)==null)
					{
						if(Main.player().mainHand()!=null)
							inventory.addItem(Main.player().mainHand(),index);
						Main.player().mainHand(null);
					}
					else if(inventory.get(index).item() instanceof Tool)
					{
						Tool tool=Main.player().mainHand();
						ItemStack stack=inventory.remove(index);
						Main.player().mainHand((Tool)stack.item());
						if(tool!=null)
							inventory.addItem(tool,index);
					}
				}
			}
		}
		if(event.action()==ControllerEvent.MOVED)
		{
			if((event.code()==Main.MOVEX))
			{
				if(!reset)
				{
					if(event.modifier()>0.5)
					{
						index++;
						reset=true;
					}
					else if(event.modifier()<-0.5)
					{
						index--;
						reset=true;
					}
				}
				else
				{
					if(event.modifier()<=0.5&&event.modifier()>=-0.5)
						reset=false;
				}
			}
			index=(index+inventory.size())%inventory.size();
		}
	}
	
	
	private Object lock;
	
	private boolean reset;
	
	private Inventory inventory;
	
	private int index;
}
