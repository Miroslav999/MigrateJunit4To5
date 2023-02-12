package main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

import main.rules.ReplaceTestAnnotation;
import main.rules.ReplaceTestImports;
import main.rules.Rule;

public class RefactorHelper {

	private List<Rule> rules;
	private JavaParser parser;

	public RefactorHelper() {
		parser = new JavaParser();
		rules = new ArrayList<>();
		rules.add(new ReplaceTestImports());
		rules.add(new ReplaceTestAnnotation());
	}

	public void migrateClasses(List<IFile> files) {
		if (files.isEmpty()) {
			return;
		}

		for (IFile file : files) {
			try {
				ParseResult<CompilationUnit> parsedJavaCode = parser.parse(file.getContents());
				for (Rule rule : rules) {
					rule.modify(parsedJavaCode);
					Utils.setContent(file, parsedJavaCode.getResult().get().toString());
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

	}
}
