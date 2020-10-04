package nexos.intellij.jddd

import com.intellij.openapi.roots.ExternalLibraryDescriptor

data class Info(val fqName: String, val displayName: String, val defaultColorName: String?, val lib: ExternalLibraryDescriptor) {
    companion object {
        val all by lazy { listOf(Repository, AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent) }
    }
}

val core = ExternalLibraryDescriptor("org.jddd", "jddd-core")
val layered = ExternalLibraryDescriptor("org.jddd", "jddd-architecture-layered")
val domainEventLib = ExternalLibraryDescriptor("org.jddd", "jddd-events")

val Repository = Info("org.jddd.core.annotation.Repository", "Repository", null, core)
val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", "AggregateRoot", null, core)
val Entity = Info("org.jddd.core.annotation.Entity", "Entity", null, core)
val Factory = Info("org.jddd.core.annotation.Factory", "Factory", null, core)
val Service = Info("org.jddd.core.annotation.Service", "Service", null, core)
val ValueObject = Info("org.jddd.core.annotation.ValueObject","ValueObject", null, core)
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", "ApplicationLayer", null, layered)
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", "DomainLayer", null, layered)
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", "InfrastructureLayer", null, layered)
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer","InterfaceLayer", null, layered)
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", "DomainEvent", null, domainEventLib)

