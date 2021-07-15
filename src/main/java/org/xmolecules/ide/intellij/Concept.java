package org.xmolecules.ide.intellij;

import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class Concept implements Predicate<PsiJavaFile>, Comparable<Concept> {
	private final String name, plural, defaultColorName;
	private final Predicate<PsiJavaFile> condition;

	Concept(
			@NotNull final String name,
			@NotNull final String plural,
			@NotNull final String defaultColorName,
			@NotNull final Predicate<PsiJavaFile> condition) {
		this.name = name;
		this.plural = plural;
		this.defaultColorName = defaultColorName;
		this.condition = condition;
	}

	public @NotNull String getName() {
		return name;
	}

	public @NotNull String getPlural() {
		return plural;
	}

	public @NotNull String getDefaultColorName() {
            return defaultColorName;
        }

	@Override
	public boolean test(@NotNull final PsiJavaFile psiJavaFile) {
		return condition.test(psiJavaFile);
	}

	@Override
	public int compareTo(final Concept o) {
		return getName().compareTo(o.getName());
	} //TODO Compare by instance
}
