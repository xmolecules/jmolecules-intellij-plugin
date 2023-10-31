package nexos.intellij.ddd

/*
import com.intellij.openapi.externalSystem.model.project.ProjectId
import com.intellij.openapi.module.JavaModuleType
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableModelsProvider
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.ui.components.JBLabel
import org.jetbrains.plugins.gradle.frameworkSupport.BuildScriptDataBuilder
import org.jetbrains.plugins.gradle.frameworkSupport.GradleFrameworkSupportProvider

abstract class Provider(private val library: Library): GradleFrameworkSupportProvider() {
    override fun createComponent() = JBLabel(library.externalLibrary.presentableName)

    override fun addSupport(projectId: ProjectId, module: Module, rootModel: ModifiableRootModel, modifiableModelsProvider: ModifiableModelsProvider, buildScriptData: BuildScriptDataBuilder) {
        val version = frameworkType.parentGroup.groupVersions.last().versionNumber
        buildScriptData.addDependencyNotation("implementation(\"${library.externalLibrary.libraryGroupId}:${library.externalLibrary.libraryArtifactId}:${version}\")")
    }

    override fun isEnabledForModuleType(moduleType: ModuleType<*>) = moduleType is JavaModuleType

    abstract override fun getFrameworkType(): FrameworkType
}

*/
