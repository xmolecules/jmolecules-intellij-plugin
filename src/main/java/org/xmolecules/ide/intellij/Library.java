package org.xmolecules.ide.intellij;

import com.intellij.openapi.roots.ExternalLibraryDescriptor;
import nexos.intellij.ddd.Framework;

public class Library {
    private final Framework framework;
    private final ExternalLibraryDescriptor externalLibrary;

    public Library(final Framework framework, final ExternalLibraryDescriptor externalLibrary) {
        this.framework = framework;
        this.externalLibrary = externalLibrary;
    }

    public Framework getFramework() {
        return framework;
    }

    public ExternalLibraryDescriptor getExternalLibrary() {
        return externalLibrary;
    }
}
