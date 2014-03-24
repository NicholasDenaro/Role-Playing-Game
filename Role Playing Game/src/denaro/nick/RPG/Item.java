package denaro.nick.RPG;

import java.awt.Image;

import denaro.nick.core.Identifiable;
import denaro.nick.core.Sprite;


public class Item extends Identifiable
{
	public Item(String name, Sprite sprite, String description, int maxStack)
	{
		this.sprite=sprite;
		this.name=name;
		this.description=description;
		this.maxStack=maxStack;
	}
	
	public String name()
	{
		return(name);
	}
	
	public Sprite sprite()
	{
		return(sprite);
	}
	
	public Image image()
	{
		return(sprite.subimage(id()));
	}
	
	public String description()
	{
		return(description);
	}
	
	public int maxStack()
	{
		return(maxStack);
	}
	
	private Sprite sprite;
	private String name;
	private String description;
	private int maxStack;
}
