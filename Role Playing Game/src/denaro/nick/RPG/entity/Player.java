package denaro.nick.RPG.entity;

import java.util.ArrayList;

import denaro.nick.RPG.Action;
import denaro.nick.RPG.Inventory;
import denaro.nick.RPG.Item;
import denaro.nick.RPG.Main;
import denaro.nick.RPG.Tool;
import denaro.nick.RPG.menu.InventoryOverlayMenu;
import denaro.nick.core.ControllerEvent;
import denaro.nick.core.ControllerListener;
import denaro.nick.core.Entity;
import denaro.nick.core.Sprite;

public class Player extends LivingEntity implements ControllerListener, Solid
{

	public Player(Sprite sprite, double x, double y)
	{
		super(sprite,x,y);

		inventory=new Inventory(8);
		
		buttons=new float[Main.LAST_ACTION+1];
		lastButtons=new float[Main.LAST_ACTION+1];
		attackFrame=0;
	}
	
	@Override
	public double lookPosY()
	{
		int offset=this.mask().getBounds().height/2;
		return(y()-offset+Math.sin(direction())*reach());
	}
	
	public boolean addItem(Item item)
	{
		return(inventory.addItem(item));
	}
	
	public void mainHand(Tool tool)
	{
		mainHand=tool;
	}
	
	public Tool mainHand()
	{
		return(mainHand);
	}
	
	public void pickupItem(ItemEntity item)
	{
		if(inventory.addItem(item.item()))
		{
			Main.engine().removeEntity(item,Main.engine().location());
		}
	}
	
	public boolean buttonPressed(int button)
	{
		return(lastButtons[button]==0&&buttons[button]==1);
	}
	
	public boolean buttonDown(int button)
	{
		return(buttons[button]==1);
	}

	public NPC checkForNPC()
	{
		ArrayList<Entity> npcs=Main.engine().location().entitiesAtPoint(lookPosX(),lookPosY(),NPC.class);
		if(!npcs.isEmpty())
			return ((NPC)npcs.get(0));
		return(null);
	}
	
	public void decideAction()
	{
		if(action()==null)
		{
			NPC npc;
			if((npc=checkForNPC())!=null)
			{
				String text=npc.getDialog();
				System.out.println(text);
				npc.direction(npc.direction(this));
			}
			else if(heldItem==null)
			{
				/*if(mainHand!=null)
				{
					mainHand.action(this);
				}
				else*/
				{
					final double x=lookPosX();
					final double y=lookPosY();
					ArrayList<Entity> items=Main.engine().location().entitiesAtPoint(x,y,ItemEntity.class);
					if(!items.isEmpty())
					{
						if(Solid.class.isAssignableFrom(items.get(0).getClass())==false)
						{
							ItemEntity ent=(ItemEntity)items.get(0);
							heldItem=ent;
							Main.engine().removeEntity(ent,Main.engine().location());
						}
					}
				}
			}
			else
			{
				final double x=lookPosX();
				final double y=lookPosY();
				action(new Action(15,false)
				{
					@Override
					public void action()
					{
						heldItem.move(x,y);
						Main.engine().addEntity(heldItem,Main.engine().location());
						heldItem=null;
					}
				});
			}
		}
	}
	
	private void tickButtons()
	{
		for(int i=0;i<buttons.length;i++)
			lastButtons[i]=buttons[i];
	}
	
	@Override
	public void tick()
	{
		if(Main.isMenuOpen())
		{
			return;
		}
		if(action()!=null)
		{
			if(action().shouldEnd())
			{
				action(null);
				sprite(Sprite.sprite("Player"));
				//System.out.println("action ended");
				attackFrame=0;
			}
			else
			{
				if(sprite()!=Sprite.sprite("Player"))
					attackFrame+=8.0/30;
				imageIndex(directionImage()+(int)attackFrame);
				tickButtons();
				return;
			}
		}
		/*if(buttonDown(Main.INVENTORY))
		{
			if(buttonPressed(Main.MOVEX))
			{
				if(buttons[Main.MOVEX]>0)
				{
					
				}
			}
			tickButtons();
			return;
		}*/
		if(buttons[Main.MOVEX]!=0||buttons[Main.MOVEY]!=0)
		{
			int walk=2;
			if(buttons[Main.ACTIONMODIFIER1]!=0)
				walk=1;
			if(buttons[Main.ACTIONMODIFIER2]==1&&lastButtons[Main.ACTIONMODIFIER2]==0)
			{
				//roll
				final double x=Math.cos(direction())*4;
				final double y=Math.sin(direction())*4;
				
				action(new Action(4,true)
				{
					@Override
					public void action()
					{
						ArrayList<Entity> solids=Main.engine().location().entityList(Solid.class);
						if(!collision(x()+x,y(),solids))
						{
							moveDelta(x,0);
						}
						solids=Main.engine().location().entityList(Solid.class);
						if(!collision(x(),y()+y,solids))
						{
							moveDelta(0,y);
						}
					}
				});
				
				return;
			}
			double x=buttons[Main.MOVEX]/walk;
			double y=buttons[Main.MOVEY]/walk;
			ArrayList<Entity> solids=Main.engine().location().entityList(Solid.class);
			if(!collision(x()+x,y(),solids))
			{
				moveDelta(x,0);
			}
			solids=Main.engine().location().entityList(Solid.class);
			if(!collision(x(),y()+y,solids))
			{
				moveDelta(0,y);
			}
			
			direction(Math.atan2(y,x));
			walkCycle(walkCycle()+0.25/walk);
			this.imageIndex((int)walkCycle()%7+directionImage()+(heldItem==null?0:32));
			this.depth((int)y());
		}
		else
		{
			walkCycle(0);
			this.imageIndex((int)walkCycle()%7+directionImage()+(heldItem==null?0:32));
		}
		if(buttons[Main.ACTIONMODIFIER2]==1)
		{
			if(buttonPressed(Main.SKILL1))
			{
				System.out.println("Button pressed");
				if(inventory.addItem(mainHand()))
				{
					System.out.println("added item");
					mainHand(null);
				}
			}
		}
		if(buttonPressed(Main.SKILL2))
		{
			mainHand=null;
		}
		if(buttonPressed(Main.ACTION))
		{
			decideAction();
		}
		if(buttonPressed(Main.CANCEL))
		{
			if(heldItem==null)
				mainHand.action(this);
		}
		if(buttonPressed(Main.INVENTORY))
		{
			Main.openMenu(new InventoryOverlayMenu(Sprite.sprite("Inventory Overlay"),0,0,inventory));
			buttons[Main.INVENTORY]=0;
		}
		tickButtons();
	}
	
	/*@Override
	public Image image()
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
	
	@Override
	public void actionPerformed(ControllerEvent event)
	{
		//System.out.println("action!");
		if(event.action()==ControllerEvent.PRESSED)
			buttons[event.code()]=1;
		if(event.action()==ControllerEvent.RELEASED)
			buttons[event.code()]=0;
		if(event.action()==ControllerEvent.MOVED)
			buttons[event.code()]=event.modifier();
		
		
	}
	
	private float[] buttons;
	private float[] lastButtons;
	
	private double attackFrame;

	private Tool mainHand;
	
	private ItemEntity heldItem;
	
	private Inventory inventory;
}
