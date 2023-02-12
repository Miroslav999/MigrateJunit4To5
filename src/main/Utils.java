package main;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class Utils {

	public static List<IFile> getFiles(IContainer container, String ext) {
		List<IFile> files = new ArrayList<>();
		try {
			for (IResource res : container.members()) {
				if (res instanceof IContainer) {
					files.addAll(getFiles((IContainer) res, ext));
				} else {
					IFile file = (IFile) res;
					if (ext.equals(file.getFileExtension())) {
						files.add(file);
					}
				}

			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return files;
	}

	public static void setContent(IFile file, String content) {
		try {
			file.setContents(new ByteArrayInputStream(content.getBytes()), IResource.FORCE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
