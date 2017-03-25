package swim1;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;

public class Textbox extends TextBox implements CommandListener
{
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot;
	private Command zapisz;
	private Command wyswietl;
	private RecordEnumeration iterator;
	
	public Textbox(Displayable ekranPowrotny)
	{
		super("Okno 2", "...Witaj w oknie 2", 256, 0);
		wyswietlacz = Main.myDisplay();
		ekranP = ekranPowrotny;
		powrot = new Command("Powrot", Command.BACK, 1);
		zapisz = new Command("Zapisz", Command.ITEM, 2);
		wyswietl = new Command("Wyswietl", Command.ITEM, 1);
		
		this.addCommand(powrot);
		this.addCommand(zapisz);
		this.addCommand(wyswietl);
		this.setCommandListener(this);
	}
	

	public void commandAction(Command komenda, Displayable elemEkranu) 
	{
		if(komenda == powrot)
		{
			wyswietlacz.setCurrent(ekranP);
		}
		else if(komenda == zapisz)
		{
			byte[] rekord = this.getString().getBytes();
			if(rekord.length > 0)
			{
				try
				{
					Main.magazyn.addRecord(rekord, 0, rekord.length);
					this.setString("...zapisane");
				}catch(RecordStoreException ex)
				{
					ex.printStackTrace();
				}
			}
			else
				this.setString("...nic nie zapisano");
		}
		else if(komenda == wyswietl)
		{
			String CalyTekst = "";
			try
			{
				iterator = Main.magazyn.enumerateRecords(null, 
						null, false);
				
				while(iterator.hasNextElement())
				{
					byte[] rekord = iterator.nextRecord();
					String Tekst = new String(rekord);
					CalyTekst += (Tekst + "\n");
				}
			}catch(RecordStoreException ex)
			{
				ex.printStackTrace();
			}
			this.setString(CalyTekst);
		}
	}
}
