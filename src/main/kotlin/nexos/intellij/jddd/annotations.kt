package nexos.intellij.jddd

data class Info(val fqName: String, val displayName: String)

val Repository = Info("org.jddd.core.annotation.Repository", "Repository")
val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", "AggregateRoot")
val Entity = Info("org.jddd.core.annotation.Entity", "Entity")
val Factory = Info("org.jddd.core.annotation.Factory", "Factory")
val Service = Info("org.jddd.core.annotation.Service", "Service")
val ValueObject = Info("org.jddd.core.annotation.ValueObject","ValueObject")
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", "ApplicationLayer")
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", "DomainLayer")
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", "InfrastructureLayer")
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer","InterfaceLayer")
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", "DomainEvent")

val annotations = listOf(Repository, AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent)