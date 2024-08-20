package application;

import java.io.Serializable;
import java.util.Vector;

public class role implements Serializable{
	private String name;
	private employee emp;
	private boolean isPossible=true;
	public role(String name,employee emp, boolean isPossible)
	{
		this.name=name;
		this.emp=emp;
		this.isPossible=isPossible;
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
}
	