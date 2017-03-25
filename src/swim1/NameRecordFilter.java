package swim1;

import javax.microedition.rms.RecordFilter;

public class NameRecordFilter implements RecordFilter
{
	public boolean matches(byte[] candidate) 
	{
		String sCand = new String(candidate);
		if(!sCand.equals("mammal") && !sCand.equals("bird") && 
				!sCand.equals("fish") && !sCand.equals("insect") && 
				!sCand.equals("reptile") && !isNumeric(sCand))
			return true;
		return false;
	}
	
	private boolean isNumeric(String str)
	{
		try
		{
			double d = Double.parseDouble(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
}
