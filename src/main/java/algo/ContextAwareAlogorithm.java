package algo;

/**
 * An interface for an algorithm which can see values it has received in the passed in the range of its window
 */
public interface ContextAwareAlogorithm {

    int getWindow();

    void setWindow(int window);
    int getIteration();

    void nextInput(double newPrice);
    double output();
    void undo();
}
