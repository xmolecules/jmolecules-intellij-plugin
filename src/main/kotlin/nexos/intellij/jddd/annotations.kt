package nexos.intellij.jddd

import com.intellij.openapi.roots.ExternalLibraryDescriptor

data class Info(val fqName: String, val displayName: String, val defaultColorName: String?, val lib: ExternalLibraryDescriptor) {
    companion object {
        val all by lazy { listOf(Repository, AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent) }
    }
}

val CoreLib = ExternalLibraryDescriptor("org.jddd", "jddd-core")
val ArchitectureLayeredLib = ExternalLibraryDescriptor("org.jddd", "jddd-architecture-layered")
val DomainEventLib = ExternalLibraryDescriptor("org.jddd", "jddd-events")

val Repository = Info("org.jddd.core.annotation.Repository", "Repository", null, CoreLib)
val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", "AggregateRoot", null, CoreLib)
val Entity = Info("org.jddd.core.annotation.Entity", "Entity", null, CoreLib)
val Factory = Info("org.jddd.core.annotation.Factory", "Factory", null, CoreLib)
val Service = Info("org.jddd.core.annotation.Service", "Service", null, CoreLib)
val ValueObject = Info("org.jddd.core.annotation.ValueObject","ValueObject", null, CoreLib)
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", "ApplicationLayer", null, ArchitectureLayeredLib)
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", "DomainLayer", null, ArchitectureLayeredLib)
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", "InfrastructureLayer", null, ArchitectureLayeredLib)
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer","InterfaceLayer", null, ArchitectureLayeredLib)
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", "DomainEvent", null, DomainEventLib)

