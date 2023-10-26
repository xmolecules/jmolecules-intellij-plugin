package org.xmolecules.ide.intellij;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ProjectViewNodeDecorator;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

class JMoleculesDecorator implements ProjectViewNodeDecorator {

	@Override
	public void decorate(
			final @Nullable ProjectViewNode<?> node,
			final @Nullable PresentationData data) {
		if (data == null || node == null) {
			return;
		}
		final var virtualFile = node.getVirtualFile();
		final var project = node.getProject();
		if (project == null || virtualFile == null || virtualFile.getFileType() != JavaFileType.INSTANCE) {
			return;
		}
		final var manager = PsiManager.getInstance(project);
		final var psiFile = manager.findFile(virtualFile);
		final var fileConcepts = Concepts.getConcepts(psiFile);
		if (fileConcepts.isEmpty()) {
			return;
		}
		final var conceptsString = fileConcepts.stream()
				.map(Concept::getName)
				.collect(Collectors.joining(", ", "\u00AB", "\u00BB"));
		data.setLocationString(conceptsString);
	}
}
