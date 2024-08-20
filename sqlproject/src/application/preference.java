package application;

import java.io.Serializable;

public class preference implements Serializable {
	private String type;
	private double addition;
	public preference(String type,double addition)
	{
		this.type=type;
		this.addition=addition;
	}
	public String getType()
	{
	return type;
	}
	public double getAdd()
	{
	return addition;
	}
}
