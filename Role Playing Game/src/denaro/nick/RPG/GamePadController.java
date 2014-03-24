package denaro.nick.RPG;

import java.util.HashMap;

import denaro.nick.controllertest.XBoxAnalogEvent;
import denaro.nick.controllertest.XBoxButtonEvent;
import denaro.nick.controllertest.XBoxController;
import denaro.nick.controllertest.XBoxControllerListener;
import denaro.nick.core.Controller;
import denaro.nick.core.ControllerEvent;
import denaro.nick.core.ControllerListener;
import denaro.nick.core.GameEngine;

public class GamePadController extends Controller implements XBoxControllerListener
{
	public GamePadController()
	{
		super();
		
		if(defaultKeymap==null)
			createDefaultKeymap();
		keymap=defaultKeymap;
	}
	
	@Override
	public void init(GameEngine engine)
	{
		XBoxController controller=new XBoxController();
		controller.addXBoxControllerListener(this);
		this.addControllerListener(engine);
	}
	
	public void createDefaultKeymap()
	{
		defaultKeymap=new HashMap<Integer,Integer>();
		defaultKeymap.put(XBoxController.LEFTSTICK,-1);
		defaultKeymap.put(XBoxController.A,Main.ACTION);
		defaultKeymap.put(XBoxController.B,Main.CANCEL);
		defaultKeymap.put(XBoxController.X,Main.SKILL1);
		defaultKeymap.put(XBoxController.Y,Main.SKILL2);
		defaultKeymap.put(XBoxController.TRIGGERS,Main.ACTIONMODIFIER1);
		defaultKeymap.put(XBoxController.RIGHTBUMPER,Main.INVENTORY);
	}

	@Override
	public void buttonPressed(XBoxButtonEvent event)
	{
		if(!keymap.containsKey(event.getButtonCode()))
			return;
		//System.out.println("Button pressed!");
		ControllerEvent ce=new ControllerEvent(ControllerEvent.PRESSED,keymap.get(event.getButtonCode()));
		for(ControllerListener listener:listeners())
			listener.actionPerformed(ce);
	}

	@Override
	public void buttonReleased(XBoxButtonEvent event)
	{
		if(!keymap.containsKey(event.getButtonCode()))
			return;
		ControllerEvent ce=new ControllerEvent(ControllerEvent.RELEASED,keymap.get(event.getButtonCode()));
		for(ControllerListener listener:listeners())
			listener.actionPerformed(ce);
	}

	@Override
	public void analogMoved(XBoxAnalogEvent event)
	{
		//System.out.println("analog!");
		int keyX=Integer.MAX_VALUE;
		int keyY=Integer.MAX_VALUE;
		if(event.getButtonCode()==XBoxController.LEFTSTICK)
		{
			keyX=-Main.MOVEX-1;
			keyY=-Main.MOVEY-1;
			if(!event.inDeadZone())
			{
				keyX=Main.MOVEX;
				keyY=Main.MOVEY;
			}
			
			if(keyX!=Integer.MAX_VALUE&&keyX>=0)
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,keyX,event.pollDataX()));
			else if(keyX<0)
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,-keyX-1,0));
			if(keyY!=Integer.MAX_VALUE&&keyY>=0)
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,keyY,event.pollDataY()));
			else if(keyY<0)
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,-keyY-1,0));
		}
		if(event.getButtonCode()==XBoxController.TRIGGERS)
		{
			if(!event.inDeadZone()&&event.pollDataX()<-0.5f)
			{
				keyX=Main.ACTIONMODIFIER1;
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,keyX,1));
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,Main.ACTIONMODIFIER2,0));
			}
			else if(!event.inDeadZone()&&event.pollDataX()>0.5f)
			{
				keyX=Main.ACTIONMODIFIER2;
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,keyX,1));
				for(ControllerListener listener:listeners())
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,Main.ACTIONMODIFIER1,0));
			}
			else
			{
				for(ControllerListener listener:listeners())
				{
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,Main.ACTIONMODIFIER1,0));
					listener.actionPerformed(new ControllerEvent(ControllerEvent.MOVED,Main.ACTIONMODIFIER2,0));
				}
			}
		}
		
	}
	
	private HashMap<Integer,Integer> keymap;
	private static HashMap<Integer,Integer> defaultKeymap;
}
