package org.xmolecules.ide.intellij;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class All {
    public static final List<ConceptImplementation> ALL = concat(JMolecules.ALL , Spring.ALL, JPA.ALL);

    private static <T> List<T> concat(List<T> ... list) {
        var size = 0;
        for( var l : list) {
            size += l.size();
        }
        var buffer = new ArrayList<T>(size);
        for( var l : list) {
            buffer.addAll(l);
        }
        return Collections.unmodifiableList(buffer);
    }
}
