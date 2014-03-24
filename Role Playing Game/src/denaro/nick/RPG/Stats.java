package denaro.nick.RPG;

public class Stats
{
	public Stats(int vit, int end, int str, int dex, int agi, int inte)
	{
		vitality=vit;
		endurance=end;
		strength=str;
		dexterity=dex;
		agility=agi;
		intellect=inte;
	}
	
	public int maxHealth()
	{
		//TODO calculation for max health based on vitality and possibly others
		return((int)(50+Math.pow(1.1,vitality)*20));
	}
	
	public int maxStamina()
	{
		//TODO calculation for max stamina based on endurance and possibly others
		return((int)(50+Math.pow(1.05,endurance)*5));
	}
	
	public int health()
	{
		return(health);
	}
	
	public int stamina()
	{
		return(stamina);
	}
	
	public int vitality()
	{
		return(vitality);
	}
	
	public int endurance()
	{
		return(endurance);
	}
	
	public int strength()
	{
		return(strength);
	}
	
	public int dexterity()
	{
		return(dexterity);
	}
	
	public int agility()
	{
		return(agility);
	}
	
	public int intellect()
	{
		return(intellect);
	}
	
	private int health;
	private int stamina;
	
	private int vitality;
	private int endurance;
	private int strength;
	private int dexterity;
	private int agility;
	private int intellect;
}
