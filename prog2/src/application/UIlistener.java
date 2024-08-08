package application;

public interface UIlistener {
	public void addDepToUI(department Dep);
	public void add(String name,employee emp,boolean isPossibol,String depName);
	public void calcComp();
	public void calcDep(String dep);
	public void calcEmp(String Emp);
	public preference setPref(String Pref);
	public void save();
}
