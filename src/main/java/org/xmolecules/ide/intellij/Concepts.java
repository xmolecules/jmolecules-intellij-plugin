/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xmolecules.ide.intellij;

import com.intellij.lang.jvm.JvmModifier;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.xmolecules.ide.intellij.Concepts.Predicates.any;
import static org.xmolecules.ide.intellij.Concepts.Types.hasMethodAnnotatedWith;
import static org.xmolecules.ide.intellij.Concepts.Types.implementsType;
import static org.xmolecules.ide.intellij.Concepts.Types.isTypeAnnotatedWith;

/**
 * @author Oliver Drotbohm
 */
class Concepts {
	private static final Set<Concept> CONCEPTS;

	static {
        CONCEPTS = Set.of(
				new Concept("Factory", "Factories", JMolecules::isFactory),
				new Concept("Service", "Services", JMolecules::isService),
				new Concept("Repository", "Repositories", any(JMolecules::isRepository, Spring::isRepository)),
				new Concept("Identifier", "Identifiers", JMolecules::isIdentifier),
				new Concept("Value Object", "Value objects",  it -> JMolecules.isValueObject(it) && !JMolecules.isIdentifier(it)),
				new Concept("Entity", "Entities", it -> JMolecules.isEntity(it) && !JMolecules.isAggregateRoot(it)),
				new Concept("Aggregate Root", "Aggregate roots", JMolecules::isAggregateRoot),
				new Concept("Event", "Events", JMolecules::isEvent),
				new Concept("Event listener", "Event listeners",
                any(JMolecules::isEventListener, Spring::isEventListener))
		);
	}

	public static @NotNull Map<Concept, List<PsiFile>> groupByConcept(final @NotNull Collection<PsiFile> files) {
		final var result = new TreeMap<Concept, List<PsiFile>>();
		for (final PsiFile file : files) {
			for (final Concept concept : getConcepts(file)) {
				result.computeIfAbsent(concept, __ -> new ArrayList<>()).add(file);
			}
		}

		return result;
	}

	public static @NotNull List<Concept> getConcepts(final @NotNull PsiFile file) {
		if (!(file instanceof PsiJavaFile)) {
			return Collections.emptyList();
		}

		return CachedValuesManager.getCachedValue(file, () ->
			new CachedValueProvider.Result<>(CONCEPTS.stream()
					.filter(it -> it.test((PsiJavaFile) file))
					.collect(Collectors.toList()), file)
		);
	}

	private static class JMolecules {

		private static final String BASE_PACKAGE = "org.jmolecules";
		private static final String DDD_BASE_PACKAGE = BASE_PACKAGE + ".ddd";
		private static final String EVENT_BASE_PACKAGE = BASE_PACKAGE + ".event";

		static boolean isFactory(final @NotNull PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.Factory", file);
		}

		static boolean isService(final @NotNull PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.Service", file);
		}

		static boolean isRepository(final @NotNull PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "Repository", file);
		}

		static boolean isIdentifier(final @NotNull PsiJavaFile file) {
			return implementsType(DDD_BASE_PACKAGE + ".types.Identifier", file);
		}

		static boolean isEntity(final @NotNull PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "Entity", file);
		}

		static boolean isAggregateRoot(final @NotNull PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "AggregateRoot", file);
		}

		static boolean isValueObject(final @NotNull PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.ValueObject", file);
		}

		static boolean isEvent(final @NotNull PsiJavaFile file) {
			return implementsOrAnnotated(EVENT_BASE_PACKAGE, "DomainEvent", file);
		}

		static boolean isEventListener(final @NotNull PsiJavaFile file) {
			return isTypeAnnotatedWith(EVENT_BASE_PACKAGE + ".types.DomainEventHandler", file);
		}

		private static boolean implementsOrAnnotated(final @NotNull String basePackage,
													 final @NotNull String name,
													 final @NotNull PsiJavaFile file) {

			return implementsType(basePackage + ".types." + name, file) ||
					isTypeAnnotatedWith(basePackage + ".annotation." + name, file);
		}
	}

	private static class Spring {
		private static final String BASE_PACKAGE = "org.springframework.data";

		static boolean isRepository(final @NotNull PsiJavaFile file) {
			return implementsType(BASE_PACKAGE + ".repository.Repository", file)
					|| isTypeAnnotatedWith("org.springframework.stereotype.Repository", file);
		}

		static boolean isEventListener(final @NotNull PsiJavaFile file) {
			return hasMethodAnnotatedWith(file, "org.springframework.context.event.EventListener",
					"org.springframework.transaction.event.TransactionalEventListener");
		}
	}

	static class Types {
		static boolean isTypeAnnotatedWith(final @NotNull String annotation, final @NotNull PsiJavaFile file) {
			return Arrays.stream(file.getClasses())
					.filter(it -> !it.isAnnotationType()) // exclude top-level annotations
					.flatMap(Types::getAllAnnotations)
					.anyMatch(it -> Objects.equals(it.getQualifiedName(), annotation));
		}

		static boolean implementsType(final @NotNull String name, final @NotNull PsiJavaFile file) {
			return Arrays.stream(file.getClasses())
					.filter(it -> !it.hasModifier(JvmModifier.ABSTRACT) || it.isInterface())
					.flatMap(Types::getAllSuperTypes)
					.flatMap(Types::getAllInterfaces)
					.peek(System.out::println)
					.anyMatch(it -> Objects.equals(it.getQualifiedName(), name));
		}

		static boolean hasMethodAnnotatedWith(final @NotNull PsiJavaFile file, final @NotNull String... name) {
			final Predicate<PsiAnnotation> nameMatches = it -> Arrays.asList(name).contains(it.getQualifiedName());
			final List<PsiAnnotation> annotations = Arrays.stream(file.getClasses())
					.flatMap(it -> Arrays.stream(it.getMethods()))
					.flatMap(it -> Arrays.stream(it.getAnnotations()))
					.distinct()
					.collect(Collectors.toList());

			if (annotations.stream().anyMatch(nameMatches)) {
				return true;
			}

			return annotations.stream()
					.map(PsiAnnotation::resolveAnnotationType)
					.flatMap(Types::getAllAnnotations)
					.distinct()
					.anyMatch(nameMatches);
		}

		private static @NotNull Stream<PsiAnnotation> getAllAnnotations(final @Nullable PsiClass type) {
			if (type == null) {
				return Stream.empty();
			}

			final List<PsiAnnotation> annotations = Arrays.stream(type.getAnnotations())
					.filter(it -> !it.getQualifiedName().startsWith("java"))
					.collect(Collectors.toList());

			final Stream<PsiAnnotation> metaAnnotations = annotations.stream()
					.map(PsiAnnotation::resolveAnnotationType)
					.flatMap(Types::getAllAnnotations);

			return Stream.concat(annotations.stream(), metaAnnotations).distinct();
		}

		private static @NotNull Stream<PsiClass> getAllInterfaces(final @NotNull PsiClass type) {
			final Stream<PsiClass> interfaces = Arrays.stream(type.getInterfaces());
			final Stream<PsiClass> self = type.isInterface() ? Stream.of(type) : Stream.empty();
			return Stream.concat(self, interfaces.flatMap(Types::getAllInterfaces));
		}

		private static @NotNull Stream<PsiClass> getAllSuperTypes(final @NotNull PsiClass type) {
			final var thisType = Stream.of(type);
			if (type.isInterface()) {
				return thisType;
			}
			final var superClass = type.getSuperClass();

			return superClass == null || Objects.equals(superClass.getQualifiedName(), "java.lang.Object")
					? thisType
					: Stream.concat(thisType, getAllSuperTypes(superClass));

		}
	}

	static class Predicates {
		@SafeVarargs
		static @NotNull <T> Predicate<T> any(@NotNull final Predicate<T>... predicates) {
			return it -> Arrays.stream(predicates).anyMatch(predicate -> predicate.test(it));
		}
	}
}
