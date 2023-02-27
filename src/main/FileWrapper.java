package main;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

public class FileWrapper {
	private boolean isChange;
	private IFile file;
	private ParseResult<CompilationUnit> parsedJavaCode;
	private JavaParser parser;

	public FileWrapper(IFile file) {
		parser = new JavaParser();
		this.file = file;
	}

	public boolean isChange() {
		return isChange;
	}

	public void change() {
		isChange = true;
	}

	public IFile getFile() {
		return file;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	public ParseResult<CompilationUnit> getParsedJavaCode() {
		if (parsedJavaCode == null) {
			try {
				parsedJavaCode = parser.parse(file.getContents());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return parsedJavaCode;
	}

	public String getFormattedCodeAsString() {
		try {
			return new Formatter().formatSource(getParsedJavaCode().getResult().get().toString());
		} catch (FormatterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setParsedJavaCode(ParseResult<CompilationUnit> parsedJavaCode) {
		this.parsedJavaCode = parsedJavaCode;
	}

	public void replaceNode(Node replaceable, Node replacement) {
		replaceable.replace(replacement);
		change();
	}

}
