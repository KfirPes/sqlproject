package application;

public class EmployeePositionPair {

    private int employeeId;
    private int positionId;
    
	public EmployeePositionPair(int employeeId, int positionId) {
		super();
		this.employeeId = employeeId;
		this.positionId = positionId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
    
    
}
