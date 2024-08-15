package application;

@FunctionalInterface
public interface PositionCallback {
    void onPositionAdded(possition insertedPos);
}