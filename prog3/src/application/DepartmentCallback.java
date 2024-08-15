package application;

@FunctionalInterface
public interface DepartmentCallback {
    void onDepartmentAdded(department insertedDep);
}