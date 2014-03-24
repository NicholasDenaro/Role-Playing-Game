package denaro.nick.RPG;

import denaro.nick.core.Identifiable;

public class ItemStack extends Identifiable
{
	public ItemStack(Item item)
	{
		this(item,1);
	}
	
	public ItemStack(Item item, int count)
	{
		this.item=item;
		this.count=count;
	}
	
	public void add()
	{
		count++;
	}
	
	public boolean isFull()
	{
		return(count==item.maxStack());
	}
	
	public int count()
	{
		return(count);
	}
	
	public Item item()
	{
		return(item);
	}
	
	private int count;
	
	private Item item;
}
