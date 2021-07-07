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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.intellij.ide.favoritesTreeView.ProjectViewNodeWithChildrenList;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

/**
 * {@link TreeStructureProvider} to group jMolecules stereotyped classes under a dedicated jMolecules node within the
 * package.
 *
 * @author Oliver Drotbohm
 */
class JMoleculesTreeStructureProvider implements TreeStructureProvider {

	/*
	 * (non-Javadoc)
	 * @see com.intellij.ide.projectView.TreeStructureProvider#modify(com.intellij.ide.util.treeView.AbstractTreeNode, java.util.Collection, com.intellij.ide.projectView.ViewSettings)
	 */
	@Override
	public @NotNull Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> parent,
			@NotNull Collection<AbstractTreeNode<?>> children, ViewSettings settings) {

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

					var file = manager.findFile(it.getVirtualFile());
					fileToNode.put(file, it);

					return file;
				})
				.collect(Collectors.toList());

		Map<Concept, List<PsiFile>> grouped = Concepts.groupByConcept(files);

		if (grouped.isEmpty()) {
			return children;
		}

		var stereotype = new StereotypeNode(project, "jMolecules", settings);

		for (Entry<Concept, List<PsiFile>> entry : grouped.entrySet()) {

			var stereotypeNode = new StereotypeNode(project, entry.getKey().getPlural(), settings);

			entry.getValue().stream()
					.map(it -> fileToNode.get(it))
					.forEach(stereotypeNode::addChild);

			stereotype.addChild(stereotypeNode);
		}

		List<AbstractTreeNode<?>> result = new ArrayList<>();

		result.add(stereotype);
		result.addAll(children);

		return result;
	}

	static class StereotypeNode extends ProjectViewNodeWithChildrenList<String> {

		private List<PsiFileNode> files = new ArrayList<>();

		public StereotypeNode(Project project, @NotNull String title, ViewSettings settings) {
			super(project, title, settings);
		}

		/*
		 * (non-Javadoc)
		 * @see com.intellij.ide.favoritesTreeView.ProjectViewNodeWithChildrenList#addChild(com.intellij.ide.util.treeView.AbstractTreeNode)
		 */
		@Override
		public void addChild(AbstractTreeNode<?> node) {

			super.addChild(node);

			if (node instanceof PsiFileNode) {
				files.add((PsiFileNode) node);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.intellij.ide.util.treeView.PresentableNodeDescriptor#update(com.intellij.ide.projectView.PresentationData)
		 */
		@Override
		protected void update(@NotNull PresentationData presentation) {
			presentation.setPresentableText(getValue());
		}

		/*
		 * (non-Javadoc)
		 * @see com.intellij.ide.projectView.ProjectViewNode#contains(com.intellij.openapi.vfs.VirtualFile)
		 */
		@Override
		public boolean contains(@NotNull VirtualFile file) {

			return files.stream()
					.map(PsiFileNode::getVirtualFile)
					.anyMatch(it -> it.equals(file));
		}
	}
}
