package swim1;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;



public class DisplayOutput extends List implements CommandListener
{
	private static Display display;
	Displayable backScreen;
	
	RecordStore set;
	private RecordEnumeration iterator;
	
	private Command back;
	private Command update;
	private Alert alert;

	public DisplayOutput(String title, int mode, Displayable backScr) 
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
		try 
		{
			set = RecordStore.openRecordStore("AnimalsStore", true);
			int i = 0;
			iterator = set.enumerateRecords(null, null, false);
			byte[] rekord;
			while(iterator.hasNextElement())
			{
				rekord = iterator.nextRecord();
				String text = new String(rekord);
				if(i%4 == 0)
					this.append(text, null);
				i++;
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
		else if(c == List.SELECT_COMMAND)
		{
			int index = this.getSelectedIndex();
			String selection = this.getString(index);
			String info = "";
			
			try 
			{
				info = "Size: " + new String(set.getRecord(4*index + 2)) + "\nGroup: " + 
						new String(set.getRecord(4*index + 3)) + "\nNumber of limbs: " + 
						new String(set.getRecord(4*index + 4));
			} catch (RecordStoreException e) 
			{
				e.printStackTrace();
			}
			
			alert = new Alert(selection.toUpperCase(), info, null, AlertType.INFO);
			alert.setTimeout(Alert.FOREVER);
			display.setCurrent(alert);
		}
	}
}
