package nexos.intellij.jddd.gradle

import nexos.intellij.jddd.CoreLib

class CoreProvider: Provider(CoreLib) {
    companion object {
          val INSTANCE = CoreProvider()
    }

    override fun getFrameworkType() = CoreFramework.INSTANCE
}

class CoreFramework: FrameworkType(CoreLib) {
    companion object {
        val INSTANCE = CoreFramework()
    }

    override fun createProvider() = CoreProvider.INSTANCE
}