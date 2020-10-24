package nexos.intellij.ddd

import nexos.intellij.ddd.jddd.jDDD
import nexos.intellij.ddd.jmolecules.jMolecules
import nexos.intellij.ddd.jpa.JPA

val all by lazy { jDDD.all + jMolecules.all + JPA.all}