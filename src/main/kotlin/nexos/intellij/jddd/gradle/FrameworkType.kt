package nexos.intellij.jddd.gradle

import com.intellij.framework.FrameworkTypeEx
import com.intellij.icons.AllIcons
import com.intellij.openapi.roots.ExternalLibraryDescriptor

abstract class FrameworkType(private val library: ExternalLibraryDescriptor):
        FrameworkTypeEx("jDDD" + library.libraryArtifactId) {
    override fun getPresentableName() =  library.libraryArtifactId

    override fun getIcon() = AllIcons.Ide.LocalScope

    override fun getParentGroup() = JDDDGroup.INSTANCE
}