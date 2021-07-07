package org.xmolecules.ide.intellij;

import java.util.stream.Collectors;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.packageDependencies.ui.PackageDependenciesNode;
import com.intellij.psi.PsiManager;
import com.intellij.ui.ColoredTreeCellRenderer;

class JMoleculesDecorator implements ProjectViewNodeDecorator {

	@Override
	public void decorate(ProjectViewNode<?> node, PresentationData data) {

		if (data == null || node == null) {
			return;
		}

		var virtualFile = node.getVirtualFile();
		var project = node.getProject();

		if (project == null || virtualFile == null || virtualFile.getFileType() != JavaFileType.INSTANCE) {
			return;
		}

		var manager = PsiManager.getInstance(project);
		var psiFile = manager.findFile(virtualFile);
		var fileConcepts = Concepts.getConcepts(psiFile);

		if (fileConcepts.isEmpty()) {
			return;
		}

		var conceptsString = fileConcepts.stream()
				.map(Concept::getName)
				.collect(Collectors.joining(", ", "\u00AB", "\u00BB"));

		data.setLocationString(conceptsString);
	}

	@Override
	public void decorate(PackageDependenciesNode node, ColoredTreeCellRenderer cellRenderer) {}
}
