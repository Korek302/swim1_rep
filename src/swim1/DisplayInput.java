package swim1;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStoreException;

public class DisplayInput extends Form implements CommandListener
{
	private static Display display;
	Displayable backScreen;
	private Command back;
	private Command save;
	private TextField name;
	private TextField legs;
	private Gauge size;
	private ChoiceGroup group;
	
	private String[] choice = {"mammal", "bird", "fish", "insect", "reptile"};

	public DisplayInput(String title, Displayable backScr) 
	{
		super(title);
		display = Main.myDisplay();
		backScreen = backScr;
		
		name = new TextField("Name:", "put name here...", 256, TextField.ANY);
		size = new Gauge("Size:", true, 10, 5);
		group = new ChoiceGroup("Group:", Choice.POPUP, choice, null);
		legs = new TextField("Number of limbs:", "", 256, TextField.NUMERIC);
		
		this.append(name);
		this.append(size);
		this.append(group);
		this.append(legs);
		
		back = new Command("Back", Command.BACK, 1);
		save = new Command("Save", Command.ITEM, 2);
		this.addCommand(back);
		this.addCommand(save);
		
		this.setCommandListener(this);
	}
	
	private void add(byte[] record)
	{
		if(record.length > 0)
		{
			try
			{
				Main.magazyn.addRecord(record,  0,  record.length);
			}catch(RecordStoreException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void commandAction(Command c, Displayable d) 
	{
		if(c == back)
		{
			display.setCurrent(backScreen);
		}
		else if(c == save)
		{
			if(name.getString().getBytes().length > 0 && legs.getString().getBytes().length > 0)
			{
				add(name.getString().getBytes());
				add(new Integer(size.getValue()).toString().getBytes());
				add(group.getString(group.getSelectedIndex()).getBytes());
				add(legs.getString().getBytes());
				this.setTitle("saved!");
			}
			else
				this.setTitle("...nothing has been saved");
		}
	}

}