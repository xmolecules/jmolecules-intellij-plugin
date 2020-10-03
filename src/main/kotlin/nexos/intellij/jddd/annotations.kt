package nexos.intellij.jddd

data class Info(val fqName: String, val displayName: String, val defaultColorName: String?) {
    companion object {
        val all by lazy { listOf(Repository, AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent) }
    }
}

val Repository = Info("org.jddd.core.annotation.Repository", "Repository", null)
val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", "AggregateRoot", null)
val Entity = Info("org.jddd.core.annotation.Entity", "Entity", null)
val Factory = Info("org.jddd.core.annotation.Factory", "Factory", null)
val Service = Info("org.jddd.core.annotation.Service", "Service", null)
val ValueObject = Info("org.jddd.core.annotation.ValueObject","ValueObject", null)
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", "ApplicationLayer", null)
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", "DomainLayer", null)
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", "InfrastructureLayer", null)
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer","InterfaceLayer", null)
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", "DomainEvent", null)

