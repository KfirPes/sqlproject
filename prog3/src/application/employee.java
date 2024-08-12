package application;

import java.io.Serializable;

public class employee implements Serializable {
	private int id;
	private String type;
	private String name;
	private preference Pref;
	public employee(String type,String name,preference Pref)
	{
		this.name=name;
		this.Pref=Pref;
		this.type=type;
	}
	
	public employee(int id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
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
