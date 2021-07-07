package org.xmolecules.ide.intellij;

import java.util.function.Predicate;

import com.intellij.psi.PsiJavaFile;

class Concept implements Predicate<PsiJavaFile>, Comparable<Concept> {

	private final String name, plural;
	private final Predicate<PsiJavaFile> condition;

	Concept(String name, String plural, Predicate<PsiJavaFile> condition) {

		this.name = name;
		this.plural = plural;
		this.condition = condition;
	}

	String getName() {
		return name;
	}

	String getPlural() {
		return plural;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.function.Predicate#test(java.lang.Object)
	 */
	@Override
	public boolean test(PsiJavaFile psiJavaFile) {
		return condition.test(psiJavaFile);
	}

	/*
	* (non-Javadoc)
	* @see java.lang.Comparable#compareTo(java.lang.Object)
	*/
	@Override
	public int compareTo(Concept o) {
		return getName().compareTo(o.getName());
	}
}
