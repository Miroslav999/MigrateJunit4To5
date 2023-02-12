package main;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;

public class MigrateHandler implements IHandler {

	@Override
	public void dispose() {

	}

	@SuppressWarnings("restriction")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelection selection = selectionService.getSelection();
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IJavaProject) {
				IProject project = ((IJavaProject) element).getProject();
				if (project.isOpen()) {
					List<IFile> javaFiles = Utils.getFiles(project, "java");
					new RefactorHelper().migrateClasses(javaFiles);
				}
			} else if (element instanceof org.eclipse.jdt.internal.core.CompilationUnit) {
				/* Код - только прототип, требует отладки */
//				org.eclipse.jdt.internal.core.CompilationUnit unit = (org.eclipse.jdt.internal.core.CompilationUnit) element;
//				IFile javaFile = ResourcesPlugin
//							.getWorkspace()
//							.getRoot()
//							.getFileForLocation(
//								unit.getPath());
//	
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return false;
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {

	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
