package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.UIlistener;
import application.modelListener;
import application.viewer;
import application.company;
public class controller implements UIlistener,modelListener,Serializable{
	private company comp;
	private viewer view;
public controller(company comp,viewer view)throws FileNotFoundException, IOException, ClassNotFoundException {
	this.comp=comp;
	this.view=view;
	view.addlistener(this);
	comp.addListener(this);
	try {
	ObjectInputStream inFile = 
			new ObjectInputStream(new FileInputStream("comp.dat"));
			compSave p2 = (compSave)inFile.readObject();
			inFile.close();
			for(int i=0;i<p2.getDep().size();i++)
			{
				department dep=new department(p2.getDep().get(i).getName(),p2.getDep().get(i).getPos());
				comp.addDep(dep);
				if(p2.getDep().get(i).getSyn())
					dep.setPref(p2.getDep().get(i).getPref());
				for(int j=0;j<p2.getDep().get(i).getPossitions().size();j++)
				{
				comp.add(p2.getDep().get(i).getPossitions().get(j).getName(),p2.getDep().get(i).getPossitions().get(j).getEmp(),p2.getDep().get(i).getPossitions().get(j).getPos(),p2.getDep().get(i).getName());
				}
				
				}
	}
	catch(FileNotFoundException e)
	{
		
	}
	
}
@Override
public void addDepToUI(department Dep) {
	Dep.addListener(this);
comp.addDep(Dep);
}
@Override
public void addDepToModel(department Dep)
{
	view.addDepUI(Dep);
	}
@Override
public void add(String name, employee emp, boolean isPossibol, String depName) {
	comp.add(name, emp, isPossibol, depName);
}

@Override
public void calcComp(double calc) {
	view.calcComp(calc);
	
}
@Override
public void calcComp() {
	comp.calc();
	
}
@Override
public void calcDep(String dep) {
	for(int i=0;i<comp.getDep().size();i++)
		if(comp.getDep().get(i).getName().equals(dep))
		{
			comp.getDep().get(i).addListener(this);
			comp.getDep().get(i).calc();
		}
	
	
}
@Override
public void calcDep(double calc) {
	view.calcDep(calc);
	
}
@Override
public void calcEmp(String Emp) {
comp.calcEmp(Emp);
}
@Override
public void calcEmp(double calc) {
	view.calcEmp(calc);
	
}
@Override
public void addEmpModel(String name) {
	view.addEmpUI(name);
	
}
//@Override
//public void save()
//{
//	ObjectOutputStream outFile = null;
//	try {
//		outFile = new ObjectOutputStream(new FileOutputStream("comp.dat"));
//	} catch (FileNotFoundException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	} catch (IOException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//			try {
//				compSave c=new compSave();
//				for(int i=0;i<comp.getDep().size();i++)
//				{
//					depSave dep=new depSave(comp.getDep().get(i).getName(),comp.getDep().get(i).getPos());
//					if(comp.getDep().get(i).getSyn())
//						dep.setPref(comp.getDep().get(i).getPref());
//					for(int j=0;j<comp.getDep().get(i).getPossitions().size();j++)
//					dep.add(comp.getDep().get(i).getPossitions().get(j));
//					c.addDep(dep);
//					}
//				outFile.writeObject(c);
//				outFile.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//
//}
//
//}
@Override
public void save() {
	int companyId=DatabaseConnection.insertCompany(comp.getName());
	List<department> departments = comp.getDep();
	List<Integer> departmentIds = DatabaseConnection.insertDepartments(departments,companyId);
	List<possition> possitions = new ArrayList<>();
	List<DepartmentPositionPair> departmentPositionPairs = new ArrayList<>();
	List<employee>employees=new ArrayList<>();
	List<preference>prefDeps=new ArrayList<>();
	List<preference>prefEmps=new ArrayList<>();
	//create postions list to insert
	//fill departments with ids
	for(int i=0; i<departmentIds.size(); i++){
		department depart = departments.get(i);
		depart.setId(departmentIds.get(i));
		possitions.addAll(depart.getPossitions());
		prefDeps.add(departments.get(i).getPref());
	}
	
	List<Integer> prefDepsIds=DatabaseConnection.insertPreferences(prefDeps);
	DatabaseConnection.insertDepartmentPreference(departmentIds, prefDepsIds);
	List<Integer> positionIds = DatabaseConnection.insertRoles(possitions);
	for (int i = 0; i < positionIds.size(); i++) {
		employees.add(possitions.get(i).getEmp());
		prefEmps.add(employees.get(i).getPref());
	}
	List<Integer>prefEmpsIds=DatabaseConnection.insertPreferences(prefEmps);
	List<Integer>employeesIds=DatabaseConnection.insertEmployees(employees,prefEmpsIds);
	int numPairs = Math.min(departmentIds.size(), positionIds.size()); 

	for (int i = 0; i < numPairs; i++) {
		departmentPositionPairs.add(new DepartmentPositionPair(departmentIds.get(i), positionIds.get(i)));
	}
	
	List<Integer> departmentPositionPairIds = DatabaseConnection.insertDepartmentPositionPairs(departmentPositionPairs);
}
@Override
public preference setPref(String pref)
{
	preference later=new preference("later",0.2);
	preference earlier=new preference("earlier",0.2);
	preference same=new preference("same",0);
	preference home=new preference("home",0.9);
	if(pref.equals("later"))
		return later;
	else
		if(pref.equals("earlier"))
			return earlier;
		else
			if(pref.equals("same"))
				return same;
			else
				
					return home;
				
	}
@Override
public void add(possition Role) {
	view.addEmpUI(Role.getEmp().getName());
	
}
}
