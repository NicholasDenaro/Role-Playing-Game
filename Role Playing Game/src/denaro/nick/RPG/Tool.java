package denaro.nick.RPG;

import denaro.nick.RPG.entity.LivingEntity;
import denaro.nick.core.Sprite;

public abstract class Tool extends Equipment
{

	public Tool(String name,Sprite sprite,String description)
	{
		super(name,sprite,description,1);
	}
	
	public abstract void action(LivingEntity entity);
	
}
