package swim1;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class Main extends MIDlet implements CommandListener
{

	private static Display display;
	
	Form frm;
	Form screen2;
	List screen3;
	List screen4;
	
	private Command finish;
	private Command display2;
	private Command display3;
	private Command display4;
	
	private int mode = List.IMPLICIT;
	Image img;
	
	static RecordStore magazyn;
	

	
	public Main() 
	{
		display = Display.getDisplay(this);
		frm = new Form("List of animals");
		try
		{
			img = Image.createImage("/Cat1.png");
		}catch(java.io.IOException e)
		{
			e.printStackTrace();
			img = null;
		}
		
		frm.append(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null));
		
		finish = new Command("Finish", Command.EXIT, 1);
		display2 = new Command("Input", Command.SCREEN, 1);
		display3 = new Command("Output", Command.SCREEN, 1);
		display4 = new Command("GroupSortedOutput", Command.SCREEN, 1);
		
		frm.addCommand(finish);
		frm.addCommand(display2);
		frm.addCommand(display3);
		frm.addCommand(display4);
		
		screen2 = new DisplayInput("Input", frm);
		screen3 = new DisplayOutput("Output", mode, frm);
		screen4 = new DisplaySorted("Group sorted output", mode, frm);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException 
	{
		System.err.println("*** destroyApp ***");
		try
		{
			magazyn.closeRecordStore();
		}catch(RecordStoreException ex)
		{
			ex.printStackTrace();
		}
	}

	protected void pauseApp() 
	{}

	protected void startApp() throws MIDletStateChangeException 
	{
		display.setCurrent(frm);
		frm.setCommandListener((CommandListener)this);
		try
		{
			magazyn = RecordStore.openRecordStore("AnimalsStore", true, 
					RecordStore.AUTHMODE_PRIVATE, false);
		}catch(RecordStoreException ex)
		{
			ex.printStackTrace();
		}
	}

	public void commandAction(Command c, Displayable d) 
	{
		if (c == finish)
		{
			try 
			{
				destroyApp(false);
			} catch (MIDletStateChangeException e) 
			{
				e.printStackTrace();
			}
			notifyDestroyed();
		}
		else if(c == display2)
		{
			display.setCurrent(screen2);
		}
		else if(c == display3)
		{
			display.setCurrent(screen3);
		}
		else if(c == display4)
		{
			display.setCurrent(screen4);
		}
	}
	
	public static Display myDisplay()
	{
		return display;
	}
}
