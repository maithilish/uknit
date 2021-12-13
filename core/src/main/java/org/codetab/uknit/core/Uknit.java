package org.codetab.uknit.core;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.di.UknitModule;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.parse.SourceParser;
import org.codetab.uknit.core.write.TestWriter;

public class Uknit {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private DInjector di;

    public static void main(final String[] args)
            throws URISyntaxException, IOException {
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
            sourceParser.parseAndProcess();

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
            LogManager.shutdown();
        }
    }
}
