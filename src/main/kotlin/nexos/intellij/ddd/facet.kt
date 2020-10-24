package nexos.intellij.ddd

import com.intellij.facet.Facet
import com.intellij.facet.FacetConfiguration
import com.intellij.facet.FacetType
import com.intellij.facet.FacetTypeId
import com.intellij.facet.ui.FacetEditorContext
import com.intellij.facet.ui.FacetEditorTab
import com.intellij.facet.ui.FacetValidatorsManager
import com.intellij.openapi.module.JavaModuleType
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType

class DDDConfiguration(): FacetConfiguration {
    override fun createEditorTabs(editorContext: FacetEditorContext?, validatorsManager: FacetValidatorsManager?): Array<FacetEditorTab> {
        return arrayOf()
    }
}

class DDDFacet(
        val library: Library,
        facetType: DDDFacetType,
        module: Module,
        name: String,
        configuration: DDDConfiguration,
        underlyingFacet: Facet<*>?):
        Facet<DDDConfiguration>(facetType, module, name, configuration, underlyingFacet)

abstract class DDDFacetType(
        id: FacetTypeId<DDDFacet>,
        private val library: Library):
        FacetType<DDDFacet, DDDConfiguration>(
                id,
                library.externalLibrary.libraryGroupId + ':' + library.externalLibrary.libraryArtifactId,
                library.externalLibrary.presentableName,
                null) {

    override fun createDefaultConfiguration() = DDDConfiguration()

    override fun createFacet(module: Module, name: String?, configuration: DDDConfiguration, underlyingFacet: Facet<*>?)
            = DDDFacet(library,this, module, name?: library.framework.name, configuration, underlyingFacet)

    override fun isSuitableModuleType(moduleType: ModuleType<*>?) = moduleType is JavaModuleType
}
