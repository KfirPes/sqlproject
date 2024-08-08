package application;

import java.io.Serializable;
import java.util.Vector;

public class department implements Serializable {
	private Vector<modelListener>listeners=new Vector<modelListener>();
	private int id;
	private String name;
	private boolean isPossible;
	private boolean isSyn=false;
	private Vector<possition>possitions=new Vector<possition>();
	private preference Pref;
	public department(String name,boolean isPossible)
	{
		this.name=name;
		this.isPossible=isPossible;
	}

	
	public department(int id, String name, boolean isPossible, boolean isSyn) {
		super();
		this.id = id;
		this.name = name;
		this.isPossible = isPossible;
		this.isSyn = isSyn;
	}
	public void setPref(preference Pref)
	{
		if(isPossible)
		this.Pref=Pref;
		isSyn=true;
	}
	public String getName()
	{
	return name;
	}
	public preference getPref()
	{
	return Pref;
	}
	public boolean getSyn()
	{
	return isSyn;
	}
	public boolean getPos()
	{
	return isPossible;
	}
	public Vector<possition> getPossitions()
	{
		return possitions;
	}
	public void add(String name,employee emp,boolean isPossibol)
	{
		possition Possition=new possition(name,emp,isPossibol);
		possitions.add(Possition);
	}
	public void addListener(modelListener listener) {
		listeners.add(listener);
		}
	public double calc()
	{
		if(!isPossible)
			return 0;
		double count=0;
		for(int i=0;i<possitions.size();i++)
		{
			if(isSyn)
			{
				if(Pref.getType().equals(possitions.get(i).getEmp().getPref().getType()))
					count+=Pref.getAdd();
				else
					if(Pref.getAdd()==0)
						count=0;
					else
						if(Pref.getAdd()==possitions.get(i).getEmp().getPref().getAdd())
							count+=-Pref.getAdd();
					else {
						count+= -Math.abs(Pref.getAdd()-possitions.get(i).calc());
					}
				
			}
			else
			count+=possitions.get(i).calc();
		}
		for(modelListener l:listeners)
			l.calcDep(count);
		return count;
	}
}
