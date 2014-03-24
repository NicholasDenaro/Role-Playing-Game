package denaro.nick.RPG;

import java.util.ArrayList;

import denaro.nick.core.Identifiable;

public class Inventory extends Identifiable
{
	public Inventory(int size)
	{
		items=new ItemStack[size];
	}
	
	public boolean addItem(Item item)
	{
		//search if there is a stack
		for(int i=0;i<items.length;i++)
		{
			if(items[i]!=null&&items[i].item().id()==item.id())
			{
				items[i].add();
				return(true);
			}
		}
		//there was no stack, search for first open spot
		for(int i=0;i<items.length;i++)
		{
			if(items[i]==null)
			{
				items[i]=new ItemStack(item);
				return(true);
			}
		}
		return(false);
	}
	
	public boolean addItem(Item item, int index)
	{
		if(items[index]==null)
		{
			items[index]=new ItemStack(item);
			return(true);
		}
		else if(items[index].item().id()==item.id())
		{
			items[index].add();
			return(true);
		}
		
		return(false);
	}
	
	public ItemStack remove(int index)
	{
		ItemStack stack=items[index];
		items[index]=null;
		return(stack);
	}
	
	public ItemStack get(int index)
	{
		return(items[index]);
	}
	
	/** Full in this case means that there are no more slots for items of a different stack*/
	public boolean isFull()
	{
		for(int i=0;i<items.length;i++)
		{
			if(items[i]==null)
				return(false);
		}
		return(true);
	}
	
	public boolean isEmpty()
	{
		for(int i=0;i<items.length;i++)
		{
			if(items[i]!=null)
				return(false);
		}
		return(true);
	}
	
	public int size()
	{
		return(items.length);
	}

	private ItemStack[] items;
}
