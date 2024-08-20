package application;

public interface DepartmentCallback {
    void onDepartmentAdded(department insertedDep);
    void onDepartmentUpdated(department insertedDep);
}