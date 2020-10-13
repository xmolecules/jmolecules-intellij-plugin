package nexos.intellij.jddd

import com.intellij.facet.Facet
import com.intellij.facet.FacetConfiguration
import com.intellij.facet.FacetType
import com.intellij.facet.FacetTypeId
import com.intellij.facet.ui.FacetBasedFrameworkSupportProvider
import com.intellij.facet.ui.FacetEditorContext
import com.intellij.facet.ui.FacetEditorTab
import com.intellij.facet.ui.FacetValidatorsManager
import com.intellij.ide.util.frameworkSupport.FrameworkVersion
import com.intellij.openapi.module.JavaModuleType
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ExternalLibraryDescriptor
import com.intellij.openapi.roots.JavaProjectModelModificationService
import com.intellij.openapi.roots.ModifiableRootModel

class JDDDConfiguration(): FacetConfiguration {
    override fun createEditorTabs(editorContext: FacetEditorContext?, validatorsManager: FacetValidatorsManager?): Array<FacetEditorTab> {
        return arrayOf()
    }
}

class JDDDFacet(
        val lib: ExternalLibraryDescriptor,
        facetType: JDDDFacetType,
        module: Module,
        name: String,
        configuration: JDDDConfiguration,
        underlyingFacet: Facet<*>?):
        Facet<JDDDConfiguration>(facetType, module, name, configuration, underlyingFacet)

class CoreFacetType: JDDDFacetType(ID, core) {
    companion object {
        val ID = FacetTypeId<JDDDFacet>("jDDD core")
        val INSTANCE = CoreFacetType()
    }
}

class DomainEventType: JDDDFacetType(ID, domainEventLib) {
    companion object {
        val ID = FacetTypeId<JDDDFacet>("jDDD domain event")
        val INSTANCE = DomainEventType()
    }
}

abstract class JDDDFacetType(
        id:FacetTypeId<JDDDFacet>,
        private val lib: ExternalLibraryDescriptor):
        FacetType<JDDDFacet, JDDDConfiguration>(
                id,
                lib.libraryGroupId + ':' + lib.libraryArtifactId,
                lib.presentableName,
                null) {

    override fun createDefaultConfiguration() = JDDDConfiguration()

    override fun createFacet(module: Module, name: String?, configuration: JDDDConfiguration, underlyingFacet: Facet<*>?)
            = JDDDFacet(lib,this, module, name?: "jDDD", configuration, underlyingFacet)

    override fun isSuitableModuleType(moduleType: ModuleType<*>?) = moduleType is JavaModuleType
}

class RootFacetType:  FacetType<JDDDFacet, JDDDConfiguration>(ID, "jDDD", "jDDD") {
    companion object {
        val ID = FacetTypeId<JDDDFacet>("jDDD")
        val INSTANCE = RootFacetType()
    }

    override fun createDefaultConfiguration() = null

    override fun createFacet(module: Module, name: String?, configuration: JDDDConfiguration, underlyingFacet: Facet<*>?)
            = null

    override fun isSuitableModuleType(moduleType: ModuleType<*>?) = moduleType is JavaModuleType
}

class Support(): FacetBasedFrameworkSupportProvider<JDDDFacet>(RootFacetType.INSTANCE) {
    override fun setupConfiguration(facet: JDDDFacet?, rootModel: ModifiableRootModel?, version: FrameworkVersion?) {
        if (facet != null  && rootModel != null && version != null) {
            JavaProjectModelModificationService
                    .getInstance(rootModel.project)
                    .addDependency(rootModel.module, facet.lib)
        }
    }
}