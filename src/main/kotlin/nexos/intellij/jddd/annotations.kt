package nexos.intellij.jddd

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.ColorKey.createColorKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor

data class Info(val fqName: String, val displayName: String) {
    val textAttributesKey by lazy { createTextAttributesKey(displayName, HighlighterColors.TEXT) }
    val attributesDescriptor by lazy { AttributesDescriptor(displayName, textAttributesKey) }
    val colorDescriptor by lazy { ColorDescriptor(displayName, createColorKey(displayName), ColorDescriptor.Kind.BACKGROUND_WITH_TRANSPARENCY) }
}

val AggregateRoot = Info("org.jddd.core.annotation.AggregateRoot", "Aggregate Root")
val Entity = Info("org.jddd.core.annotation.Entity", "Entity")
val Factory = Info("org.jddd.core.annotation.Factory", "Factory")
val Service = Info("org.jddd.core.annotation.Service", "Service")
val ValueObject = Info("org.jddd.core.annotation.ValueObject","Value Object")
val ApplicationLayer = Info("org.jddd.architecture.layered.ApplicationLayer", "Application Layer")
val DomainLayer = Info("org.jddd.architecture.layered.DomainLayer", "Domain Layer")
val InfrastructureLayer = Info("org.jddd.architecture.layered.InfrastructureLayer", "Infrastructure Layer")
val InterfaceLayer = Info("org.jddd.architecture.layered.InterfaceLayer","Interface Layer")
val DomainEvent = Info("org.jddd.event.annotation.DomainEvent", "Domain Event")

val annotations = listOf(AggregateRoot, Entity, Factory, Service, ValueObject, ApplicationLayer, DomainLayer, InfrastructureLayer, InterfaceLayer, DomainEvent)