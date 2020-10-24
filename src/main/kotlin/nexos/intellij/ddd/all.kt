package nexos.intellij.ddd

import nexos.intellij.ddd.jddd.jDDD
import nexos.intellij.ddd.jmolecules.jMolecules

val all by lazy { jDDD.all + jMolecules.all}