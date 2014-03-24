package denaro.nick.RPG.entity;

import denaro.nick.RPG.Item;
import denaro.nick.core.Entity;

public class ItemEntity extends Entity
{

	public ItemEntity(Item item,double x,double y)
	{
		super(item.sprite(),x,y);
		this.item=item;
	}

	public Item item()
	{
		return(item);
	}
	
	@Override
	public void tick()
	{
		depth((int)y());
	}
	
	private Item item;
}
