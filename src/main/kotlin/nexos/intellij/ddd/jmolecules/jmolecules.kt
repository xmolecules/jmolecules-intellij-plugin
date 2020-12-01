package nexos.intellij.ddd.jmolecules

import com.intellij.openapi.roots.ExternalLibraryDescriptor
import nexos.intellij.ddd.Framework
import nexos.intellij.ddd.Info
import nexos.intellij.ddd.Library
import nexos.intellij.ddd.Repository as ddd_Repository
import nexos.intellij.ddd.AggregateRoot as ddd_AggregateRoot
import nexos.intellij.ddd.Entity as ddd_Entity
import nexos.intellij.ddd.Factory as ddd_Factory
import nexos.intellij.ddd.Service as ddd_Service
import nexos.intellij.ddd.ValueObject as ddd_ValueObject
import nexos.intellij.ddd.ApplicationLayer as ddd_ApplicationLayer
import nexos.intellij.ddd.DomainLayer as ddd_DomainLayer
import nexos.intellij.ddd.InfrastructureLayer as ddd_InfrastructureLayer
import nexos.intellij.ddd.InterfaceLayer as ddd_InterfaceLayer
import nexos.intellij.ddd.DomainEvent as ddd_DomainEvent
import nexos.intellij.ddd.BoundedContext as ddd_BoundedContext
import nexos.intellij.ddd.Module as ddd_Module
import nexos.intellij.ddd.ClassicApplicationServiceRing as ddd_ClassicApplicationServiceRing
import nexos.intellij.ddd.ClassicDomainModelRing as ddd_ClassicDomainModelRing
import nexos.intellij.ddd.ClassicInfrastructureRing as ddd_ClassicInfrastructureRing
import nexos.intellij.ddd.SimplifiedApplicationRing as ddd_SimplifiedApplicationRing
import nexos.intellij.ddd.SimplifiedDomainRing as ddd_SimplifiedDomainRing
import nexos.intellij.ddd.SimplifiedInfrastructureRing as ddd_SimplifiedInfrastructureRing

object jMolecules: Framework("jMolecules") {
    val all by lazy { listOf(
            Entity, AggregateRoot, BoundedContext, Factory, Module, Repository, Service, ValueObject,
            DomainEvent,
            ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer,
            ClassicApplicationServiceRing, ClassicDomainModelRing, ClassicInfrastructureRing,
            SimplifiedApplicationRing, SimplifiedDomainRing, SimplifiedInfrastructureRing
    )}
}

val DDDLib = Library(jMolecules, ExternalLibraryDescriptor("org.jmolecules","jmolecules-ddd"))
val EventsLib = Library(jMolecules, ExternalLibraryDescriptor("org.jmolecules","jmolecules-events"))
val LayeredArchitectureLib = Library(jMolecules, ExternalLibraryDescriptor("org.jmolecules","jmolecules-layered-architecture"))
val OnionArchitectureLib = Library(jMolecules, ExternalLibraryDescriptor("org.jmolecules","jmolecules-onion-architecture"))

val Entity = Info("org.jmolecules.ddd.annotation.Entity", ddd_Entity, DDDLib)
val AggregateRoot = Info("org.jmolecules.ddd.annotation.AggregateRoot", ddd_AggregateRoot,  DDDLib)
val BoundedContext = Info("org.jmolecules.ddd.annotation.BoundedContext", ddd_BoundedContext,  DDDLib)
val Factory = Info("org.jmolecules.ddd.annotation.Factory", ddd_Factory,  DDDLib)
val Module = Info("org.jmolecules.ddd.annotation.Module", ddd_Module,  DDDLib)
val Repository = Info("org.jmolecules.ddd.annotation.Repository", ddd_Repository,  DDDLib)
val Service = Info("org.jmolecules.ddd.annotation.Service", ddd_Service,  DDDLib)
val ValueObject = Info("org.jmolecules.ddd.annotation.ValueObject", ddd_ValueObject,  DDDLib)

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
