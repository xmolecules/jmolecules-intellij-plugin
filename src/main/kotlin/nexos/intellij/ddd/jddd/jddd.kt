package nexos.intellij.ddd.jddd

/*
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

object jDDD: Framework("jDDD") {
    val all by lazy { listOf(Repository, AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent) }
}

val CoreLib = Library(jDDD, ExternalLibraryDescriptor("org.jddd", "jddd-core"))
val ArchitectureLayeredLib = Library(jDDD, ExternalLibraryDescriptor("org.jddd", "jddd-architecture-layered"))
val DomainEventLib = Library(jDDD, ExternalLibraryDescriptor("org.jddd", "jddd-events"))

val Repository = Info("org.jddd.core.annotation.Repository", ddd_Repository, CoreLib)
val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", ddd_AggregateRoot, CoreLib)
val Entity = Info("org.jddd.core.annotation.Entity", ddd_Entity,  CoreLib)
val Factory = Info("org.jddd.core.annotation.Factory", ddd_Factory,  CoreLib)
val Service = Info("org.jddd.core.annotation.Service", ddd_Service,  CoreLib)
val ValueObject = Info("org.jddd.core.annotation.ValueObject",ddd_ValueObject,  CoreLib)
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", ddd_ApplicationLayer,  ArchitectureLayeredLib)
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", ddd_DomainLayer, ArchitectureLayeredLib)
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", ddd_InfrastructureLayer,  ArchitectureLayeredLib)
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer",ddd_InterfaceLayer,  ArchitectureLayeredLib)
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", ddd_DomainEvent,  DomainEventLib)
*/
