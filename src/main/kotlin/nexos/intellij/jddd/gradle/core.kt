package nexos.intellij.jddd.gradle

import nexos.intellij.jddd.core

class CoreProvider: Provider(core) {
    companion object {
          val INSTANCE = CoreProvider()
    }

    override fun getFrameworkType() = CoreFramework.INSTANCE
}

class CoreFramework: FrameworkType(core) {
    companion object {
        val INSTANCE = CoreFramework()
    }

    override fun createProvider() = CoreProvider.INSTANCE
}