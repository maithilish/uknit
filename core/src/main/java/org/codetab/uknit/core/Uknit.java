package org.codetab.uknit.core;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.di.UknitModule;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.output.TestWriter;
import org.codetab.uknit.core.parse.SourceParser;

public class Uknit {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private DInjector di;

    public static void main(final String[] args) throws Exception {
        UknitModule module = new UknitModule();
        DInjector di = new DInjector(module).instance(DInjector.class);
        Uknit uknit = di.instance(Uknit.class);
        uknit.run();
    }

    public void run() {
        Configs configs = di.instance(Configs.class);

        try {
            Controller ctl = di.instance(Controller.class);
            ctl.setup();

            SourceParser sourceParser = di.instance(SourceParser.class);
            sourceParser.parseClass();
            sourceParser.parseSuperClasses();
            LOG.debug("cu cache size {}", ctl.getCuCache().size());

            sourceParser.process();

            TestWriter testWriter = di.instance(TestWriter.class);
            testWriter.write(ctl);

        } catch (Exception e) {
            String message = "uKnit terminated with error";
            LOG.error("{}", e.getMessage());
            LOG.error(String.join(", ", message, "see log."));
            if (configs.getConfig("uknit.mode.dev", false)) {
                LOG.error("", e);
            } else {
                LOG.debug("{}", e);
            }
            if (configs.getConfig("uknit.logger.shutdown", true)) {
                LogManager.shutdown();
            }
            throw e;
        }
    }
}
