package application;

import java.io.Serializable;
import java.util.Vector;

public class depSave implements Serializable {
	private String name;
	private boolean isPossible;
	private boolean isSyn=false;
	private Vector<role>roles=new Vector<role>();
	private preference Pref;
	public depSave(String name,boolean isPossible)
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
	public void add(role Role)
	{
		roles.add(Role);
	}
}
