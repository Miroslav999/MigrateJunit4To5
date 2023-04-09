package main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;

import main.rules.ReplaceClassInCode;
import main.rules.ReplaceTestAnnotation;
import main.rules.ReplaceTestImports;
import main.rules.Rule;

public class RefactorHelper {

	private List<Rule> rules;

	public RefactorHelper() {
		rules = new ArrayList<>();
		rules.add(new ReplaceTestImports());
		rules.add(new ReplaceTestAnnotation());
		rules.add(new ReplaceClassInCode());
		// rules.add(new ChangeArgOrder());
	}

	public void migrateClasses(List<IFile> files) {
		if (files.isEmpty()) {
			return;
		}

		for (IFile file : files) {
			FileWrapper fileWrapper = new FileWrapper(file);
			for (Rule rule : rules) {
				rule.modify(fileWrapper);
				if (fileWrapper.getFormattedCodeAsString() == null) {
					// TODO error description
					continue;
				}
				if (fileWrapper.isChange()) {
					Utils.setContent(file,
							fileWrapper.getFormattedCodeAsString());
				}
			}
		}
	}
}
