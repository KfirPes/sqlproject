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
	
	
	
	public employee(int id, String type, String name, preference pref) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		Pref = pref;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPref(preference pref) {
		Pref = pref;
	}

	public int getId() {
		return id;
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
