package org.xmolecules.ide.intellij;

import com.intellij.openapi.roots.ExternalLibraryDescriptor;
import com.intellij.psi.PsiJavaFile;
import nexos.intellij.ddd.Framework;

import java.util.List;

import static org.xmolecules.ide.intellij.Types.hasMethodAnnotatedWith;
import static org.xmolecules.ide.intellij.Types.implementsType;
import static org.xmolecules.ide.intellij.Types.isTypeAnnotatedWith;

public class Spring {
    private static final String BASE_PACKAGE = "org.springframework.data";

    private static final Framework FRAMEWORK = new Framework("Spring");
    private static final Library LIB = new Library(FRAMEWORK, new ExternalLibraryDescriptor("org.springframework", ""));
    public static final ConceptImplementation REPOSITORY_VIA_IMPLEMENTS = new ConceptViaImplements(Concepts.REPOSITORY,LIB, BASE_PACKAGE + ".repository.Repository");
    public static final ConceptImplementation REPOSITORY_VIA_TYPE_ANNOTATION = new ConceptViaTypeAnnotation(Concepts.REPOSITORY,LIB, "org.springframework.stereotype.Repository");
    public static final ConceptImplementation EVENT_LISTENER = new ConceptViaMethodAnnotation(Concepts.EVENT_LISTENER, LIB, "org.springframework.context.event.EventListener");
    public static final ConceptImplementation EVENT_LISTENER_TRANSACTIONAL = new ConceptViaMethodAnnotation(Concepts.EVENT_LISTENER, LIB,"org.springframework.transaction.event.TransactionalEventListener");

    public static final List<ConceptImplementation> ALL = List.of(REPOSITORY_VIA_IMPLEMENTS, REPOSITORY_VIA_TYPE_ANNOTATION, EVENT_LISTENER, EVENT_LISTENER_TRANSACTIONAL);
}
