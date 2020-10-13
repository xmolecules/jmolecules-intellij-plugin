package nexos.intellij.jddd.gradle

import com.intellij.openapi.externalSystem.model.project.ProjectId
import com.intellij.openapi.module.JavaModuleType
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ExternalLibraryDescriptor
import com.intellij.openapi.roots.ModifiableModelsProvider
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.ui.components.JBLabel
import org.jetbrains.plugins.gradle.frameworkSupport.BuildScriptDataBuilder
import org.jetbrains.plugins.gradle.frameworkSupport.GradleFrameworkSupportProvider

abstract class Provider(private val library: ExternalLibraryDescriptor): GradleFrameworkSupportProvider() {
    override fun createComponent() = JBLabel(library.presentableName)

    override fun addSupport(projectId: ProjectId, module: Module, rootModel: ModifiableRootModel, modifiableModelsProvider: ModifiableModelsProvider, buildScriptData: BuildScriptDataBuilder) {
        val version = frameworkType.parentGroup.groupVersions.last().version
        buildScriptData.addDependencyNotation("implementation(\"${library.libraryGroupId}:${library.libraryArtifactId}:${version}\")")
    }

    override fun isEnabledForModuleType(moduleType: ModuleType<*>): Boolean {
        return (moduleType is JavaModuleType)
    }

    abstract override fun getFrameworkType(): FrameworkType
}

