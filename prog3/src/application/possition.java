package application;

import java.io.Serializable;
import java.util.Vector;

public class possition implements Serializable{
	private int id;
	private String name;
	private employee emp;
	private boolean isPossible=true;
	public possition(String name,employee emp, boolean isPossible)
	{
		this.name=name;
		this.emp=emp;
		this.isPossible=isPossible;
	}
	
	public possition(int id, String name, boolean isPossible) {
		super();
		this.id = id;
		this.name = name;
		this.isPossible = isPossible;
	}
	
	public possition(String name, boolean isPossible) {
		super();
		this.name = name;
		this.isPossible = isPossible;
	}

	public String getName()
	{
	return name;
	}
	public employee getEmp()
	{
	return emp;
	}
	public boolean getPos()
	{
	return isPossible;
	}
	public double calc()
	{
		if(isPossible)
		{

			return emp.getPref().getAdd();
		}
		else
			return 0;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
	