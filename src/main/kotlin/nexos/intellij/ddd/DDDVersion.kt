package nexos.intellij.ddd

import com.intellij.framework.FrameworkAvailabilityCondition
import com.intellij.framework.FrameworkVersion
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel

class DDDVersion(private val framework:Framework, private val version:String): FrameworkAvailabilityCondition(), FrameworkVersion {
    override fun getPresentableName() = framework.name

    override fun getVersionNumber() = version

    override fun getId() = "${framework.name}$version"

    override fun getAvailabilityCondition() = this

    override fun isAvailableFor(model: FrameworkSupportModel) = true
}