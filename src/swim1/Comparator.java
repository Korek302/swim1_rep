package swim1;

import javax.microedition.rms.RecordComparator;

public class Comparator implements RecordComparator
{
	public int compare(byte[] rec1, byte[] rec2)
	{
		String arg1 = new String(rec1);
		String arg2 = new String(rec2);
		int relacja = arg1.compareTo(arg2);
		if(relacja < 0)
			return -1;
		else if(relacja > 0)
			return 1;
		else return 0;
	}
}
