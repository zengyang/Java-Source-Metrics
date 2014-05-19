package nl.rug.jbi.jsm.frontend;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import nl.rug.jbi.jsm.core.JSMCore;
import nl.rug.jbi.jsm.core.calculator.MetricResult;
import nl.rug.jbi.jsm.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class DebugFrontend implements Frontend {
    private final static Logger logger = LogManager.getLogger(DebugFrontend.class);
    private final JSMCore core;
    private final String target;

    public DebugFrontend(final JSMCore core, final String target) {
        this.target = target;
        this.core = Preconditions.checkNotNull(core);
    }

    @Override
    public void init() {
        try {
            final File file = new File(this.target);
            this.core.process(this, Sets.newHashSet(FileUtils.findClassNames(file)), file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResult(final List<MetricResult> resultList) {
        logger.debug(resultList);
    }

    @Override
    public void signalDone() {
        logger.trace("Execution finished.");
    }
}
