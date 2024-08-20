package application;

import java.io.Serializable;

public class employee implements Serializable {
	private String type;
	private String name;
	private preference Pref;
	public employee(String type,String name,preference Pref)
	{
		this.name=name;
		this.Pref=Pref;
		this.type=type;
	}
	public String getType()
	{
	return type;
	}
	public String getName()
	{
	return name;
	}
	public preference getPref()
	{
	return Pref;
	}
}
