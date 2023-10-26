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

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link TreeStructureProvider} to group jMolecules stereotyped classes under a dedicated jMolecules node within the
 * package.
 *
 * @author Oliver Drotbohm
 */
class JMoleculesTreeStructureProvider implements TreeStructureProvider {

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(
            final @NotNull AbstractTreeNode<?> parent,
            final @NotNull Collection<AbstractTreeNode<?>> children,
            final ViewSettings settings) {
        if (!(parent instanceof PsiDirectoryNode)) {
            return children;
        }
        var project = parent.getProject();
        var manager = PsiManager.getInstance(project);
        var fileToNode = new HashMap<PsiFile, PsiFileNode>();
        var files = children.stream()
                .filter(PsiFileNode.class::isInstance)
                .map(PsiFileNode.class::cast)
                .map(it -> {
                    final var vf = it.getVirtualFile();
                    if (vf == null) {
                        return null;
                    }
                    final var file = manager.findFile(vf);
                    fileToNode.put(file, it);

                    return file;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        final Map<Concept, List<PsiFile>> grouped = Concepts.groupByConcept(files);
        if (grouped.isEmpty()) {
            return children;
        }
        final var stereotype = new StereotypeNode(project, "jMolecules", settings);
        for (final Entry<Concept, List<PsiFile>> entry : grouped.entrySet()) {
            var stereotypeNode = new StereotypeNode(project, entry.getKey().getPlural(), settings);
            entry.getValue().stream()
                    .map(fileToNode::get)
                    .forEach(stereotypeNode::addChild);
            stereotype.addChild(stereotypeNode);
        }
        final var result = new ArrayList<AbstractTreeNode<?>>();
        result.add(stereotype);
        result.addAll(children);
        return result;
    }

    static class StereotypeNode extends ProjectViewNode<String> {
        private final List<PsiFileNode> files = new ArrayList<>();
        private final List<AbstractTreeNode<?>> myChildren;

        public StereotypeNode(
                final Project project,
                final @NotNull String title,
                final ViewSettings settings) {
            super(project, title, settings);

            myChildren = new ArrayList<>();
        }

        @NotNull
        @Override
        public Collection<? extends AbstractTreeNode<?>> getChildren() {
            return myChildren;
        }

        public void addChild(final AbstractTreeNode<?> node) {
            myChildren.add(node);
            node.setParent(this);

            if (node instanceof PsiFileNode) {
                files.add((PsiFileNode) node);
            }
        }

        @Override
        protected void update(final @NotNull PresentationData presentation) {
            presentation.setPresentableText(getValue());
        }

        @Override
        public boolean contains(final @NotNull VirtualFile file) {
            return files.stream()
                    .map(PsiFileNode::getVirtualFile)
                    .filter(Objects::nonNull)
                    .anyMatch(it -> it.equals(file));
        }
    }
}
