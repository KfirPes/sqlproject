public class DepartmentPositionPair {
    private int departmentId;
    private int positionId;

    public DepartmentPositionPair(int departmentId, int positionId) {
        this.departmentId = departmentId;
        this.positionId = positionId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getPositionId() {
        return positionId;
    }
}
