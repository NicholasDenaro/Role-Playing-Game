package denaro.nick.RPG;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import denaro.nick.RPG.entity.Field;
import denaro.nick.RPG.entity.FieldOutliner;
import denaro.nick.RPG.entity.GrassMap;
import denaro.nick.RPG.entity.ItemEntity;
import denaro.nick.RPG.entity.ItemEntitySolid;
import denaro.nick.RPG.entity.LivingEntity;
import denaro.nick.RPG.entity.NPC;
import denaro.nick.RPG.entity.Player;
import denaro.nick.RPG.menu.Menu;
import denaro.nick.core.Entity;
import denaro.nick.core.GameEngine;
import denaro.nick.core.GameEngineByTick;
import denaro.nick.core.GameFrame;
import denaro.nick.core.GameMap;
import denaro.nick.core.GameView2D;
import denaro.nick.core.Location;
import denaro.nick.core.Sprite;

public class Main
{
	public static void main(String[] args)
	{
		engine=(GameEngineByTick)GameEngineByTick.instance();
		engine.setTicksPerSecond(60);
		engine.setFramesPerSecond(60);
		
		engine.view(new HUDView(240,160,2,2,new HUD()));
		
		GameFrame frame=new GameFrame("Game",engine);
		frame.setVisible(true);
		Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screen.width/2-frame.getWidth()/2,screen.height/2-frame.getHeight()/2);
		engine.addGameViewListener(frame);
		
		GamePadController controller=new GamePadController();
		controller.init(engine);
		
		
		Location testRoom=new Location();
		engine.location(testRoom);
		
		Sprite sprite;
		try
		{
			new Sprite("Inventory Overlay","Inventory Overlay.png",240,32,null);
			new Sprite("PlayerWatering", "PlayerWatering.png", 16, 26, new Point(8,24));
			sprite=new Sprite("Player", "Player.png", 16, 24, new Point(8,24));
			player=new Player(sprite,16,16);
			player.mask(new Area(new Rectangle(4-player.sprite().anchor().x,16-player.sprite().anchor().y,8,8)));
			engine.addEntity(player,testRoom);
			engine.addControllerListener(player);
			engine.requestFocus(player);
			
			NPC npc=new NPC(sprite,64,64);
			npc.mask(new Area(new Rectangle(4-player.sprite().anchor().x,16-player.sprite().anchor().y,8,8)));
			engine.addEntity(npc,testRoom);
			
			sprite=new Sprite("Field","Field.png",16,16,new Point(0,0));
			Field field=new Field(16,48);
			engine.addEntity(field,testRoom);
			field=new Field(32,48);
			engine.addEntity(field,testRoom);
			field=new Field(16,64);
			engine.addEntity(field,testRoom);
			field=new Field(32,64);
			engine.addEntity(field,testRoom);
			
			sprite=new Sprite("Grass","Grass.png",16,16,new Point(0,0));
			boolean[][] gr=new boolean[10][10];
			for(int a=0;a<10;a++)
				for(int i=0;i<10;i++)
					gr[i][a]=true;
			GrassMap grass=new GrassMap(0,0,gr);
			engine.addEntity(grass,testRoom);
			
			sprite=new Sprite("Field Outline","Field Outline.png",16,16,new Point(0,0));
			FieldOutliner outline=new FieldOutliner(sprite,0,0);
			player.addListener(outline);
			engine.addEntity(outline,testRoom);
			
			sprite=new Sprite("Items","Items.png",16,16,new Point(0,0));
			
			sprite=new Sprite("Stone","Stone.png",16,16,new Point(8,8));
			Item item=new Item("Rock",sprite,"It's a small rock.",15);
			
			ItemEntity it=new ItemEntity(item,32,32);
			engine.addEntity(it,testRoom);
			
			item=new Item("Lg. Rock",sprite,"It's just a stupid boulder.",1);
			ItemEntitySolid its=new ItemEntitySolid(item,96,96);
			its.imageIndex(1);
			engine.addEntity(its,testRoom);
			
			createItems();
			
			player.mainHand((Tool)items.get(0));
			
			player.addItem(items.get(1));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		menus=new Stack<Menu>();
		
		engine.start();
	}
	
	private static void createItems()
	{
		items=new GameMap<Item>();
		
		Tool hoe=new Tool("Hoe",Sprite.sprite("Items"),"This is a hoe.")
		{
			@Override
			public void action(final LivingEntity entity)
			{
				// TODO Auto-generated method stub
				final double x=entity.lookPosX();
				final double y=entity.lookPosY();
				entity.action(new Action(30,false)
				{
					@Override
					public void init()
					{
						entity.imageIndex(entity.directionImage());
					}
					
					@Override
					public void action()
					{
						ArrayList<Entity> fields=Main.engine.location().entitiesAtPoint(x,y,Field.class);
						for(Entity ent:fields)
						{
							Field field=(Field)ent;
							field.till();
						}
					}
				});
			}
		
		};
		items.add(hoe);
		
		Tool wateringcan=new Tool("Watering Can",Sprite.sprite("Items"),"This is a watering can.")
		{

			@Override
			public void action(final LivingEntity entity)
			{
				// TODO Auto-generated method stub
				final double x=entity.lookPosX();
				final double y=entity.lookPosY();
				entity.action(new Action(30,false)
				{
					@Override
					public void init()
					{
						entity.imageIndex(entity.directionImage());
						entity.sprite(Sprite.sprite("PlayerWatering"));
					}
					
					@Override
					public void action()
					{
						ArrayList<Entity> fields=Main.engine.location().entitiesAtPoint(x,y,Field.class);
						for(Entity ent:fields)
						{
							Field field=(Field)ent;
							field.water();
						}
					}
				});
			}
		
		};
		items.add(wateringcan);
	}
	
	public static Stack<Menu> menus()
	{
		return(menus);
	}
	
	public static boolean isMenuOpen()
	{
		return(!menus.isEmpty());
	}
	
	public static void openMenu(Menu menu)
	{
		menus.push(menu);
		engine.requestFocus(menu);
	}
	
	public static void closeMenu()
	{
		menus.pop();
		if(!menus.isEmpty())
			engine.requestFocus(menus.peek());
		else
			engine.requestFocus(player);
	}
	
	public static GameEngineByTick engine()
	{
		return(engine);
	}
	
	public static Player player()
	{
		return(player);
	}
	
	private static GameEngineByTick engine;
	private static Player player;
	
	private static Stack<Menu> menus;
	
	public static GameMap<Item> items;
	
	public static final int MOVEX=0;
	public static final int MOVEY=1;
	public static final int ACTION=2;
	public static final int CANCEL=3;
	public static final int SKILL1=4;
	public static final int SKILL2=5;
	public static final int ACTIONMODIFIER1=6;
	public static final int ACTIONMODIFIER2=7;
	public static final int INVENTORY=8;
	
	public static final int LAST_ACTION=8;
}