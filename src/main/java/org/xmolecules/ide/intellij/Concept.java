package org.xmolecules.ide.intellij;

import java.util.function.Predicate;

import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

class Concept implements Predicate<PsiJavaFile>, Comparable<Concept> {
	private final String name, plural;
	private final Predicate<PsiJavaFile> condition;

	Concept(
			@NotNull final String name,
			@NotNull final String plural,
			@NotNull final Predicate<PsiJavaFile> condition) {
		this.name = name;
		this.plural = plural;
		this.condition = condition;
	}

	@NotNull String getName() {
		return name;
	}

	@NotNull String getPlural() {
		return plural;
	}

	@Override
	public boolean test(@NotNull final PsiJavaFile psiJavaFile) {
		return condition.test(psiJavaFile);
	}

	@Override
	public int compareTo(Concept o) {
		return getName().compareTo(o.getName());
	}
}
