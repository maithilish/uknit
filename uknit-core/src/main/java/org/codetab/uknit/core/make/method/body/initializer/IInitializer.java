package org.codetab.uknit.core.make.method.body.initializer;

import java.util.Optional;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Pack;

interface IInitializer {

    Optional<Initializer> getInitializer(Pack pack, Pack iniPack, Heap heap);
}
