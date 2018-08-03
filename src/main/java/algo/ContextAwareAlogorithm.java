package algo;

/**
 * An interface which
 */
public interface ContextAwareAlogorithm {
    int getIteration();
    void nextInput(double newPrice);
    double output();
    void undo();
    int getWindow();
}
