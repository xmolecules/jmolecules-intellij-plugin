package org.xmolecules.ide.intellij;

import com.intellij.psi.PsiJavaFile;

import java.util.function.Predicate;

public abstract class ConceptImplementation implements Predicate<PsiJavaFile> {
    private final Concept concept;
    private final Library library;
    private final Predicate<PsiJavaFile> condition;
    private final String fqName;

    public ConceptImplementation(final Concept concept, final Library library, final String fqName, final Predicate<PsiJavaFile> condition) {
        this.concept = concept;
        this.library = library;
        this.condition = condition;
        this.fqName = fqName;
    }

    public Concept getConcept() {
        return concept;
    }

    public Library getLibrary() {
        return library;
    }

    /**
     * @see com.intellij.codeInsight.daemon.quickFix.ExternalLibraryResolver
     * @see nexos.intellij.ddd.DDDLibraryResolver
     */
    public String getFqName() {
        return fqName;
    }

    /*
     * (non-Javadoc)
     * @see java.util.function.Predicate#test(java.lang.Object)
     */
    @Override
    public boolean test(final PsiJavaFile psiJavaFile) {
        return condition.test(psiJavaFile);
    }
}
