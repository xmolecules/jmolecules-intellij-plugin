package org.xmolecules.ide.intellij;

import com.intellij.openapi.roots.ExternalLibraryDescriptor;
import com.intellij.psi.PsiJavaFile;
import nexos.intellij.ddd.Framework;

import java.util.List;

import static org.xmolecules.ide.intellij.Types.implementsType;
import static org.xmolecules.ide.intellij.Types.isTypeAnnotatedWith;

public class JMolecules {

    public static final Framework FRAMEWORK = new Framework("JMolecules");
    public static final Library DDDLib = new Library(FRAMEWORK, new ExternalLibraryDescriptor("org.jmolecules","jmolecules-ddd"));
    public static final Library EventsLib = new Library(FRAMEWORK, new ExternalLibraryDescriptor("org.jmolecules","jmolecules-events"));
    public static final Library LayeredArchitectureLib = new Library(FRAMEWORK, new ExternalLibraryDescriptor("org.jmolecules","jmolecules-layered-architecture"));
    public static final Library OnionArchitectureLib = new Library(FRAMEWORK, new ExternalLibraryDescriptor("org.jmolecules","jmolecules-onion-architecture"));

    private static final String BASE_PACKAGE = "org.jmolecules";
    private static final String DDD_BASE_PACKAGE = BASE_PACKAGE + ".ddd";
    private static final String EVENT_BASE_PACKAGE = BASE_PACKAGE + ".event";

    public static final ConceptImplementation FACTORY = new ConceptViaTypeAnnotation(Concepts.FACTORY, DDDLib, DDD_BASE_PACKAGE + ".annotation.Factory");
    public static final ConceptImplementation SERVICE = new ConceptViaTypeAnnotation(Concepts.SERVICE, DDDLib, DDD_BASE_PACKAGE + ".annotation.Service");
    public static final ConceptImplementation REPOSITORY_VIA_IMPLEMENTS = new ConceptViaImplements(Concepts.REPOSITORY,DDDLib, DDD_BASE_PACKAGE + ".types.Repository");
    public static final ConceptImplementation REPOSITORY_VIA_TYPE_ANNOTATION = new ConceptViaTypeAnnotation(Concepts.REPOSITORY,DDDLib, DDD_BASE_PACKAGE + ".annotation.Repository");
    public static final ConceptImplementation IDENTIFIER = new ConceptViaImplements(Concepts.IDENTIFIER, DDDLib, DDD_BASE_PACKAGE + ".types.Identifier");

  //FIXME  public static final ConceptImplementation VALUE_OBJECT = new ConceptImplementation(Concepts.VALUE_OBJECT, DDDLib, it -> JMolecules.isValueObject(it) && !JMolecules.isIdentifier(it), fqName);
  //FIXME  public static final ConceptImplementation ENTITY = new ConceptImplementation(Concepts.ENTITY, DDDLib, it -> JMolecules.isEntity(it) && !JMolecules.isAggregateRoot(it), fqName);

    public static final ConceptImplementation AGGREGATE_ROOT_VIA_IMPLEMENTS = new ConceptViaImplements(Concepts.AGGREGATE_ROOT, DDDLib, DDD_BASE_PACKAGE + ".types.AggregateRoot");
    public static final ConceptImplementation AGGREGATE_ROOT_VIA_TYPE_ANNOTATION = new ConceptViaTypeAnnotation(Concepts.AGGREGATE_ROOT, DDDLib, DDD_BASE_PACKAGE + ".annotation.AggregateRoot");
    public static final ConceptImplementation EVENT_VIA_IMPLEMENTS = new ConceptViaImplements(Concepts.EVENT,EventsLib, EVENT_BASE_PACKAGE + ".types.DomainEvent");
    public static final ConceptImplementation EVENT_VIA_TYPE_ANNOTATION = new ConceptViaTypeAnnotation(Concepts.EVENT,EventsLib, EVENT_BASE_PACKAGE + ".annotation.DomainEvent");
    public static final ConceptImplementation EVENT_LISTENER = new ConceptViaTypeAnnotation(Concepts.EVENT_LISTENER, EventsLib,EVENT_BASE_PACKAGE + ".types.DomainEventHandler");

    public static final List<ConceptImplementation> ALL = List.of(FACTORY, SERVICE, REPOSITORY_VIA_IMPLEMENTS,
            REPOSITORY_VIA_TYPE_ANNOTATION, IDENTIFIER, /* VALUE_OBJECT, ENTITY, */ AGGREGATE_ROOT_VIA_IMPLEMENTS,
            AGGREGATE_ROOT_VIA_TYPE_ANNOTATION, EVENT_VIA_IMPLEMENTS, EVENT_VIA_TYPE_ANNOTATION, EVENT_LISTENER);

    static boolean isIdentifier(final PsiJavaFile file) {
        return implementsType(DDD_BASE_PACKAGE + ".types.Identifier", file);
    }

    static boolean isEntity(final PsiJavaFile file) {
        return implementsType(DDD_BASE_PACKAGE + ".types." + "Entity", file) ||
                isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation." + "Entity", file);
    }

    static boolean isAggregateRoot(final PsiJavaFile file) {
        return implementsType(DDD_BASE_PACKAGE + ".types." + "AggregateRoot", file) ||
                isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation." + "AggregateRoot", file);
    }

    static boolean isValueObject(final PsiJavaFile file) {
        return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.ValueObject", file);
    }
}

/*
val DomainEvent = Info("org.jmolecules.event.annotation.DomainEvent", ddd_DomainEvent,  EventsLib)

val ApplicationLayer = Info("org.jmolecules.architecture.layered.ApplicationLayer", ddd_ApplicationLayer,  LayeredArchitectureLib)
val DomainLayer = Info("org.jmolecules.architecture.layered.DomainLayer", ddd_DomainLayer,  LayeredArchitectureLib)
val InfrastructureLayer = Info("org.jmolecules.architecture.layered.InfrastructureLayer", ddd_InfrastructureLayer,  LayeredArchitectureLib)
val InterfaceLayer = Info("org.jmolecules.architecture.layered.InterfaceLayer", ddd_InterfaceLayer,  LayeredArchitectureLib)

val ClassicApplicationServiceRing = Info("org.jmolecules.architecture.onion.classical.ApplicationServiceRing", ddd_ClassicApplicationServiceRing,  OnionArchitectureLib)
val ClassicDomainModelRing = Info("org.jmolecules.architecture.onion.classical.DomainModelRing", ddd_ClassicDomainModelRing,  OnionArchitectureLib)
val ClassicInfrastructureRing = Info("org.jmolecules.architecture.onion.classical.InfrastructureRing", ddd_ClassicInfrastructureRing,  OnionArchitectureLib)

val SimplifiedApplicationRing = Info("org.jmolecules.architecture.onion.simplified.ApplicationRing", ddd_SimplifiedApplicationRing,  OnionArchitectureLib)
val SimplifiedDomainRing = Info("org.jmolecules.architecture.onion.simplified.DomainRing", ddd_SimplifiedDomainRing,  OnionArchitectureLib)
val SimplifiedInfrastructureRing = Info("org.jmolecules.architecture.onion.simplified.InfrastructureRing", ddd_SimplifiedInfrastructureRing,  OnionArchitectureLib)

 */
