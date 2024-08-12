package application;

import java.io.Serializable;
import java.util.Vector;

public class compSave implements Serializable {
	private Vector<depSave>dep=new Vector<depSave>();
	public compSave() {}
	
	public void addDep(depSave Dep)
	{
		dep.add(Dep);
	}
	public Vector<depSave>getDep()
	{
		return dep;
	}
}
