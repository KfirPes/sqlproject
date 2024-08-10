package application;

import java.io.Serializable;
import java.util.Vector;

public class company implements Serializable {
	private static int id=0;
	private String name;
	private String address;
	private Vector<modelListener>listeners=new Vector<modelListener>();
	private Vector<department>dep=new Vector<department>();
	public company() {}
	public company(int company_id,String company_name) {
		this.id=company_id;
		this.name=company_name;
	}
	
	public company(String name) {
		super();
		this.name = name;
		this.id+=1;
	}
	public void addDep(department Dep)
	{
		dep.add(Dep);
		for(modelListener l:listeners)
		{
			l.addDepToModel(Dep);
		}
	}
	public void calc()
	{
		double count=0;
		for(int i=0;i<dep.size();i++)
			count+=dep.get(i).calc();
		for(modelListener l:listeners)
			l.calcComp(count);
	}
	public Vector<department>getDep()
	{
		return dep;
	}
	public void add(String name, employee emp, boolean isPossibol, String depName) {
		for(int i=0;i<getDep().size();i++)
			if(getDep().get(i).getName().equals(depName))
				getDep().get(i).add(name, emp, isPossibol);
		for(modelListener l:listeners)
			l.addEmpModel(emp.getName());
	}
	public void calcEmp(String Emp) {
		for(int i=0;i<getDep().size();i++)
			for(int j=0;j<getDep().get(i).getPossitions().size();j++)
				if(getDep().get(i).getPossitions().get(j).getEmp().getName().equals(Emp))
				{if(getDep().get(i).getSyn())
				{
					if(getDep().get(i).getPref().getType().equals(getDep().get(i).getPossitions().get(j).getEmp().getPref().getType()))
						for(modelListener l:listeners)
						l.calcEmp(getDep().get(i).getPref().getAdd());
					else
						if(getDep().get(i).getPref().getAdd()==0)
							for(modelListener l:listeners)
							l.calcEmp(0);
						else
							if(getDep().get(i).getPref().getAdd()==getDep().get(i).getPossitions().get(j).getEmp().getPref().getAdd())
								for(modelListener l:listeners)
								l.calcEmp(-getDep().get(i).getPref().getAdd());
						else {
							for(modelListener l:listeners)
								l.calcEmp(-Math.abs(getDep().get(i).getPref().getAdd()-getDep().get(i).getPossitions().get(j).calc()));
						}
					
				}
				else
					for(modelListener l:listeners)
					l.calcEmp(getDep().get(i).getPossitions().get(j).calc());
				}
	}
	public void addListener(modelListener listener) {
		listeners.add(listener);
		}
	public String getName(){
		return this.name;
	}
}

