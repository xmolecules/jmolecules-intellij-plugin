package nexos.intellij.jddd.gradle

import nexos.intellij.jddd.domainEventLib

class DomainEventProvider: Provider(domainEventLib) {
    companion object {
          val INSTANCE = CoreProvider()
    }

    override fun getFrameworkType() = DomainEventFramework.INSTANCE
}

class DomainEventFramework: FrameworkType(domainEventLib) {
    companion object {
        val INSTANCE = DomainEventFramework()
    }

    override fun createProvider() = DomainEventProvider.INSTANCE
}