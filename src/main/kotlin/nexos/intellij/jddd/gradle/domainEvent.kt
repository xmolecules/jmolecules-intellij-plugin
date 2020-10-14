package nexos.intellij.jddd.gradle

import nexos.intellij.jddd.DomainEventLib

class DomainEventProvider: Provider(DomainEventLib) {
    companion object {
          val INSTANCE = DomainEventProvider()
    }

    override fun getFrameworkType() = DomainEventFramework.INSTANCE
}

class DomainEventFramework: FrameworkType(DomainEventLib) {
    companion object {
        val INSTANCE = DomainEventFramework()
    }

    override fun createProvider() = DomainEventProvider.INSTANCE
}