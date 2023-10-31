package org.xmolecules.ide.intellij;

import static org.xmolecules.ide.intellij.Types.implementsType;

public class ConceptViaImplements extends ConceptImplementation {
    public ConceptViaImplements(final Concept concept, final Library library, final String fqName) {
        super(concept, library, fqName, it -> implementsType(fqName, it));
    }
}
