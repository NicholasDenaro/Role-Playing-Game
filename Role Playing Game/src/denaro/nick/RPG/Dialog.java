package denaro.nick.RPG;

import denaro.nick.core.ControllerEvent;
import denaro.nick.core.ControllerListener;
import denaro.nick.core.Entity;
import denaro.nick.core.Focusable;

public class Dialog extends Entity implements Focusable, ControllerListener
{
	public Dialog(String text)
	{
		super(null,0,0);
		this.text=text;
		cursor=0;
		advance=false;
	}
	
	@Override
	public void tick()
	{
		if(cursor<text.length())
			cursor+=0.25;
		else
			cursor=text.length();
		
		if(advance)
		{
			Main.engine().removeEntity(this,Main.engine().location());
		}
	}
	
	@Override
	public void actionPerformed(ControllerEvent event)
	{
		if(event.action()==Main.ACTION)
			if(cursor==text.length())
				advance=true;
	}
	
	private String text;
	private double cursor;
	
	private boolean advance;
}
