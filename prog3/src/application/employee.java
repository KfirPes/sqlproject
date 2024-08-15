package application;

import java.io.Serializable;

public class employee implements Serializable {
	private int id;
	private String type;
	private String name;
	private preference Pref;
	public employee(String name,String type,preference Pref)
	{
		this.name=name;
		this.type=type;
		this.Pref=Pref;
	}
	
	public employee(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
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
	
	@Override
	public String toString() {
		return this.name;
	}
}
