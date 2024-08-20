package application;

import java.io.Serializable;
import java.util.Vector;

public class department implements Serializable {
	private Vector<modelListener>listeners=new Vector<modelListener>();
	private String name;
	private boolean isPossible;
	private boolean isSyn=false;
	private Vector<role>roles=new Vector<role>();
	private preference Pref;
	public department(String name,boolean isPossible)
	{
		this.name=name;
		this.isPossible=isPossible;
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
	public Vector<role> getRoles()
	{
		return roles;
	}
	public void add(String name,employee emp,boolean isPossibol)
	{
		role Role=new role(name,emp,isPossibol);
		roles.add(Role);
	}
	public void addListener(modelListener listener) {
		listeners.add(listener);
		}
	public double calc()
	{
		if(!isPossible)
			return 0;
		double count=0;
		for(int i=0;i<roles.size();i++)
		{
			if(isSyn)
			{
				if(Pref.getType().equals(roles.get(i).getEmp().getPref().getType()))
					count+=Pref.getAdd();
				else
					if(Pref.getAdd()==0)
						count=0;
					else
						if(Pref.getAdd()==roles.get(i).getEmp().getPref().getAdd())
							count+=-Pref.getAdd();
					else {
						count+= -Math.abs(Pref.getAdd()-roles.get(i).calc());
					}
				
			}
			else
			count+=roles.get(i).calc();
		}
		for(modelListener l:listeners)
			l.calcDep(count);
		return count;
	}
}
