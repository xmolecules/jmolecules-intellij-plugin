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

import static org.xmolecules.ide.intellij.Concepts.Predicates.*;
import static org.xmolecules.ide.intellij.Concepts.Types.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.lang.jvm.JvmModifier;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;

/**
 * @author Oliver Drotbohm
 */
class Concepts {

	private static final Set<Concept> CONCEPTS;

	static {

		Set<Concept> all = new HashSet<>();
		all.add(new Concept("Factory", "Factories", JMolecules::isFactory));
		all.add(new Concept("Service", "Services", JMolecules::isService));
		all.add(new Concept("Repository", "Repositories", any(JMolecules::isRepository, Spring::isRepository)));

		all.add(new Concept("Identifier", "Identifiers", JMolecules::isIdentifier));
		all.add(new Concept("Value Object", "Value objects",
				it -> JMolecules.isValueObject(it) && !JMolecules.isIdentifier(it)));
		all.add(new Concept("Entity", "Entities", it -> JMolecules.isEntity(it) && !JMolecules.isAggregateRoot(it)));
		all.add(new Concept("Aggregate Root", "Aggregate roots", JMolecules::isAggregateRoot));

		all.add(new Concept("Event", "Events", JMolecules::isEvent));
		all.add(new Concept("Event listener", "Event listeners",
				any(JMolecules::isEventListener, Spring::isEventListener)));

		CONCEPTS = Collections.unmodifiableSet(all);
	}

	public static Map<Concept, List<PsiFile>> groupByConcept(Collection<PsiFile> files) {

		var result = new TreeMap<Concept, List<PsiFile>>();

		for (PsiFile file : files) {
			for (Concept concept : getConcepts(file)) {
				result.computeIfAbsent(concept, __ -> new ArrayList<>()).add(file);
			}
		}

		return result;
	}

	public static List<Concept> getConcepts(PsiFile file) {

		if (!(file instanceof PsiJavaFile)) {
			return Collections.emptyList();
		}

		return CachedValuesManager.getCachedValue((PsiJavaFile) file, () -> {
			return new CachedValueProvider.Result<>(CONCEPTS.stream()
					.filter(it -> it.test((PsiJavaFile) file))
					.collect(Collectors.toList()), (PsiJavaFile) file);
		});
	}

	private static class JMolecules {

		private static final String BASE_PACKAGE = "org.jmolecules";
		private static final String DDD_BASE_PACKAGE = BASE_PACKAGE + ".ddd";
		private static final String EVENT_BASE_PACKAGE = BASE_PACKAGE + ".event";

		static boolean isFactory(PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.Factory", file);
		}

		static boolean isService(PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.Service", file);
		}

		static boolean isRepository(PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "Repository", file);
		}

		static boolean isIdentifier(PsiJavaFile file) {
			return implementsType(DDD_BASE_PACKAGE + ".types.Identifier", file);
		}

		static boolean isEntity(PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "Entity", file);
		}

		static boolean isAggregateRoot(PsiJavaFile file) {
			return implementsOrAnnotated(DDD_BASE_PACKAGE, "AggregateRoot", file);
		}

		static boolean isValueObject(PsiJavaFile file) {
			return isTypeAnnotatedWith(DDD_BASE_PACKAGE + ".annotation.ValueObject", file);
		}

		static boolean isEvent(PsiJavaFile file) {
			return implementsOrAnnotated(EVENT_BASE_PACKAGE, "DomainEvent", file);
		}

		static boolean isEventListener(PsiJavaFile file) {
			return isTypeAnnotatedWith(EVENT_BASE_PACKAGE + ".types.DomainEventHandler", file);
		}

		private static boolean implementsOrAnnotated(String basePackage, String name, PsiJavaFile file) {

			return implementsType(basePackage + ".types." + name, file) ||
					isTypeAnnotatedWith(basePackage + ".annotation." + name, file);
		}
	}

	private static class Spring {

		private static final String BASE_PACKAGE = "org.springframework.data";

		static boolean isRepository(PsiJavaFile file) {
			return implementsType(BASE_PACKAGE + ".repository.Repository", file)
					|| isTypeAnnotatedWith("org.springframework.stereotype.Repository", file);
		}

		static boolean isEventListener(PsiJavaFile file) {
			return hasMethodAnnotatedWith(file, "org.springframework.context.event.EventListener",
					"org.springframework.transaction.event.TransactionalEventListener");
		}
	}

	static class Types {

		static boolean isTypeAnnotatedWith(String annotation, PsiJavaFile file) {

			return Arrays.stream(file.getClasses())
					.filter(it -> !it.isAnnotationType()) // exclude top-level annotations
					.flatMap(it -> getAllAnnotations(it))
					.anyMatch(it -> it.getQualifiedName().equals(annotation));
		}

		static boolean implementsType(String name, PsiJavaFile file) {

			return Arrays.stream(file.getClasses())
					.filter(it -> !it.hasModifier(JvmModifier.ABSTRACT) || it.isInterface())
					.flatMap(Types::getAllSuperTypes)
					.flatMap(it -> getAllInterfaces(it))
					.peek(System.out::println)
					.anyMatch(it -> it.getQualifiedName().equals(name));
		}

		static boolean hasMethodAnnotatedWith(PsiJavaFile file, String... name) {

			Predicate<PsiAnnotation> nameMatches = it -> Arrays.asList(name).contains(it.getQualifiedName());

			List<PsiAnnotation> annotations = Arrays.stream(file.getClasses())
					.flatMap(it -> Arrays.stream(it.getMethods()))
					.flatMap(it -> Arrays.stream(it.getAnnotations()))
					.distinct()
					.collect(Collectors.toList());

			if (annotations.stream().anyMatch(nameMatches)) {
				return true;
			}

			return annotations.stream()
					.map(it -> it.resolveAnnotationType())
					.flatMap(Types::getAllAnnotations)
					.distinct()
					.anyMatch(nameMatches);
		}

		private static Stream<PsiAnnotation> getAllAnnotations(PsiClass type) {

			if (type == null) {
				return Stream.empty();
			}

			List<PsiAnnotation> annotations = Arrays.stream(type.getAnnotations())
					.filter(it -> !it.getQualifiedName().startsWith("java"))
					.collect(Collectors.toList());

			Stream<PsiAnnotation> metaAnnotations = annotations.stream()
					.map(it -> it.resolveAnnotationType())
					.flatMap(Types::getAllAnnotations);

			return Stream.concat(annotations.stream(), metaAnnotations).distinct();
		}

		private static Stream<PsiClass> getAllInterfaces(PsiClass type) {

			Stream<PsiClass> interfaces = Arrays.stream(type.getInterfaces());
			Stream<PsiClass> self = type.isInterface() ? Stream.of(type) : Stream.empty();

			return Stream.concat(self, interfaces.flatMap(Types::getAllInterfaces));
		}

		private static Stream<PsiClass> getAllSuperTypes(PsiClass type) {

			var thisType = Stream.of(type);

			if (type.isInterface()) {
				return thisType;
			}

			var superClass = type.getSuperClass();

			return superClass == null || superClass.getQualifiedName().equals("java.lang.Object")
					? thisType
					: Stream.concat(thisType, getAllSuperTypes(superClass));

		}
	}

	static class Predicates {

		@SafeVarargs
		static <T> Predicate<T> any(Predicate<T>... predicates) {
			return it -> Arrays.stream(predicates).anyMatch(predicate -> predicate.test(it));
		}
	}
}
