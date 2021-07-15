package org.xmolecules.ide.intellij;

import static org.xmolecules.ide.intellij.Types.isTypeAnnotatedWith;

public class ConceptViaTypeAnnotation  extends ConceptImplementation {
    public ConceptViaTypeAnnotation(final Concept concept, final Library library, final String fqName) {
        super(concept, library, fqName, it -> isTypeAnnotatedWith(fqName, it));
    }
}
