package swim1;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;



public class DisplaySorted extends List implements CommandListener
{
	private static Display display;
	Displayable backScreen;
	
	RecordStore set;
	private RecordEnumeration iterator;
	
	private Command back;
	private Command update;

	public DisplaySorted(String title, int mode, Displayable backScr) 
	{
		super(title, mode);
		display = Main.myDisplay();

		backScreen = backScr;
		
		initList();
		
		back = new Command("Back", Command.BACK, 1);
		update = new Command("Update", Command.ITEM, 1);
		this.addCommand(back);
		this.addCommand(update);
		
		this.setCommandListener(this);	
	}

	private void initList()
	{
		RecordFilter f = new NameRecordFilter();
		
		try 
		{
			set = RecordStore.openRecordStore("AnimalsStore", true);
			iterator = set.enumerateRecords(f, new Comparator(), false);
			byte[] rekord;
			while(iterator.hasNextElement())
			{
				rekord = iterator.nextRecord();
				String text = new String(rekord);
				this.append(text, null);
			}
			
		} catch (RecordStoreException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void commandAction(Command c, Displayable d) 
	{
		if(c == back)
		{
			display.setCurrent(backScreen);
		}
		else if(c == update)
		{
			try 
			{
				set.closeRecordStore();
				this.deleteAll();
				initList();
			} catch (RecordStoreException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
