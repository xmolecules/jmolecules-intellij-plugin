package nexos.intellij.jddd.gradle

import nexos.intellij.jddd.ArchitectureLayeredLib

class ArchitectureLayeredProvider: Provider(ArchitectureLayeredLib) {
    companion object {
          val INSTANCE = ArchitectureLayeredProvider()
    }

    override fun getFrameworkType() = ArchitectureLayeredFramework.INSTANCE
}

class ArchitectureLayeredFramework: FrameworkType(ArchitectureLayeredLib) {
    companion object {
        val INSTANCE = ArchitectureLayeredFramework()
    }

    override fun createProvider() = ArchitectureLayeredProvider.INSTANCE
}