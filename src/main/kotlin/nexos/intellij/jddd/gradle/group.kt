package nexos.intellij.jddd.gradle

import com.intellij.framework.FrameworkAvailabilityCondition
import com.intellij.framework.FrameworkGroup
import com.intellij.framework.FrameworkVersion
import com.intellij.icons.AllIcons
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel

class JDDDVersion(val version:String): FrameworkAvailabilityCondition(), FrameworkVersion {
    companion object {
        val all = listOf(JDDDVersion("0.1.0"))
    }
    override fun getPresentableName() = "jDDD"

    override fun getVersionNumber() = version

    override fun getId() = "jddd$version"

    override fun getAvailabilityCondition() = this

    override fun isAvailableFor(model: FrameworkSupportModel) = true
}

class JDDDGroup: FrameworkGroup<JDDDVersion>("jDDD") {
    companion object {
        val INSTANCE = JDDDGroup()
    }
    override fun getPresentableName() = "jDDD"

    override fun getIcon() = AllIcons.Ide.LocalScope

    override fun getGroupVersions() = JDDDVersion.all.toMutableList()
}