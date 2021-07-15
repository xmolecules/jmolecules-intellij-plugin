package org.xmolecules.ide.intellij;

import java.util.List;

import static org.xmolecules.ide.intellij.Types.hasMethodAnnotatedWith;

public class ConceptViaMethodAnnotation extends ConceptImplementation {
    public ConceptViaMethodAnnotation(final Concept concept, final Library library, final String fqName) {
        super(concept, library, fqName, it -> hasMethodAnnotatedWith(it, fqName));
    }
}
