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
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel

class JDDDConfiguration: FacetConfiguration {
    override fun createEditorTabs(editorContext: FacetEditorContext?, validatorsManager: FacetValidatorsManager?): Array<FacetEditorTab> {
        return arrayOf()
    }
}

class JDDDFacet(facetType: JDDDFacetType, module: Module,  name: String, configuration: JDDDConfiguration, underlyingFacet: Facet<*>?):
        Facet<JDDDConfiguration>(facetType, module, name, configuration, underlyingFacet)

class JDDDFacetType(): FacetType<JDDDFacet, JDDDConfiguration>(ID, "jDDD", "jDDD") {
    companion object{
        val ID = FacetTypeId<JDDDFacet>("jDDD")
        val INSTANCE = JDDDFacetType()
    }

    override fun createDefaultConfiguration() = JDDDConfiguration()

    override fun createFacet(module: Module, name: String?, configuration: JDDDConfiguration, underlyingFacet: Facet<*>?)
            = JDDDFacet(this, module, name?: "jDDD", configuration, underlyingFacet)


    override fun isSuitableModuleType(moduleType: ModuleType<*>?): Boolean {
      return true
    }
}

class Support(): FacetBasedFrameworkSupportProvider<JDDDFacet>(JDDDFacetType.INSTANCE) {
    override fun setupConfiguration(facet: JDDDFacet?, rootModel: ModifiableRootModel?, version: FrameworkVersion?) {
        if (facet != null  && rootModel != null && version != null) {
          //  val library = MavenLibraryUtil.createMavenJarInfo
          //  addSupport(facet.module, rootModel, version, library)
        }
    }
}