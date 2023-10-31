package org.xmolecules.ide.intellij;

import com.intellij.lang.jvm.JvmModifier;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Types {

    static boolean isTypeAnnotatedWith(final String annotation, final PsiJavaFile file) {
        return Arrays.stream(file.getClasses())
                .filter(it -> !it.isAnnotationType()) // exclude top-level annotations
                .flatMap(Types::getAllAnnotations)
                .anyMatch(it -> it.getQualifiedName().equals(annotation));
    }

    static boolean implementsType(final String name, final PsiJavaFile file) {
        return Arrays.stream(file.getClasses())
                .filter(it -> !it.hasModifier(JvmModifier.ABSTRACT) || it.isInterface())
                .flatMap(Types::getAllSuperTypes)
                .flatMap(Types::getAllInterfaces)
                .peek(System.out::println) //TODO remove
                .anyMatch(it -> it.getQualifiedName().equals(name));
    }

    static boolean hasMethodAnnotatedWith(final PsiJavaFile file, final String... name) {

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

    private static Stream<PsiAnnotation> getAllAnnotations(final PsiClass type) {
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

    private static Stream<PsiClass> getAllInterfaces(final PsiClass type) {

        Stream<PsiClass> interfaces = Arrays.stream(type.getInterfaces());
        Stream<PsiClass> self = type.isInterface() ? Stream.of(type) : Stream.empty();

        return Stream.concat(self, interfaces.flatMap(Types::getAllInterfaces));
    }

    private static Stream<PsiClass> getAllSuperTypes(final PsiClass type) {

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
