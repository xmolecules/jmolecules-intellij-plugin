package nexos.intellij.ddd.jpa

import com.intellij.openapi.roots.ExternalLibraryDescriptor
import nexos.intellij.ddd.Framework
import nexos.intellij.ddd.Info
import nexos.intellij.ddd.Library
import nexos.intellij.ddd.Entity as ddd_Entity
import nexos.intellij.ddd.ValueObject as ddd_ValueObject

object JPA: Framework("JPA") {
    //Version 2.2, 2.3-SNAPSHOT
    val all by lazy { listOf(Entity, Embeddable) }
}
val lib = Library(JPA, ExternalLibraryDescriptor("javax.persistence", "javax.persistence-api"))

val Entity =  Info("javax.persistence.Entity", ddd_Entity, lib)
val Embeddable =  Info("javax.persistence.Embeddable", ddd_ValueObject, lib)