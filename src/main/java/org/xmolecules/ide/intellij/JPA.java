package org.xmolecules.ide.intellij;

import com.intellij.openapi.roots.ExternalLibraryDescriptor;
import nexos.intellij.ddd.Framework;

import java.util.List;

public class JPA {
    public static final Framework JPA = new Framework("JPA");
    public static final Library LIB = new Library(JPA, new ExternalLibraryDescriptor("javax.persistence", "javax.persistence-api"));

    public static final ConceptImplementation Entity =  new ConceptViaTypeAnnotation(Concepts.ENTITY, LIB, "javax.persistence.Entity");
    public static final ConceptImplementation Embeddable =  new ConceptViaTypeAnnotation(Concepts.VALUE_OBJECT, LIB, "javax.persistence.Embeddable");
    public static final List<ConceptImplementation> ALL = List.of(Entity, Embeddable);
}
