package nl.rug.jbi.jsm.metrics;

import com.google.common.collect.Lists;
import nl.rug.jbi.jsm.bcel.CompositeBCELClassLoader;
import nl.rug.jbi.jsm.bcel.JavaClassDefinition;
import nl.rug.jbi.jsm.core.calculator.MetricScope;
import nl.rug.jbi.jsm.core.calculator.MetricState;
import nl.rug.jbi.jsm.core.calculator.ProducerMetric;
import nl.rug.jbi.jsm.core.event.Subscribe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ClassSourceProducer extends ProducerMetric {
    private static AtomicReference<CompositeBCELClassLoader> CLOADER = new AtomicReference<CompositeBCELClassLoader>(null);
    private final AtomicReference<CompositeBCELClassLoader> cLoaderCopy;

    public ClassSourceProducer() {
        super(MetricScope.CLASS, MetricScope.CLASS);
        this.cLoaderCopy = CLOADER;
    }

    public static void setCBCL(final CompositeBCELClassLoader cLoader) {
        CLOADER.set(cLoader);
        CLOADER = new AtomicReference<CompositeBCELClassLoader>(null);
    }

    @Subscribe
    public void onClass(final MetricState state, final JavaClassDefinition ignored) {
        final CompositeBCELClassLoader CBCL = this.cLoaderCopy.get();
        if (CBCL != null) {
            state.setValue("source", CBCL.getSource(state.getIdentifier()));
        }
    }

    @Override
    public List<Produce> getProduce(Map<String, MetricState> states) {
        final List<Produce> ret = Lists.newLinkedList();

        for (final Map.Entry<String, MetricState> entry : states.entrySet()) {
            final String source = entry.getValue().getValue("source");
            ret.add(new Produce<ClassSource>(entry.getKey(), new ClassSource(entry.getKey(), source)));
        }

        return ret;
    }

    @Override
    public Class getProducedClass() {
        return ClassSource.class;
    }
}