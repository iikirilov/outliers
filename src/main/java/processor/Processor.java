package processor;

import algo.ContextAwareAlogorithm;

public interface Processor<E extends ContextAwareAlogorithm> {
    void process(final E algo);
}
