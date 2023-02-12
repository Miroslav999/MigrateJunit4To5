package main.rules;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import main.Imports;

public class ReplaceTestImports implements Rule {

	@Override
	public void modify(ParseResult<CompilationUnit> parsedJavaCode) {

		for (ImportDeclaration importDec : parsedJavaCode.getResult().get().getImports()) {
			if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Test")) {
				importDec.replace(Imports.Test.getNode());
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Before")) {
				importDec.replace(Imports.BeforeEach.getNode());
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.BeforeClass")) {
				importDec.replace(Imports.BeforeAll.getNode());
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.After")) {
				importDec.replace(Imports.AfterEach.getNode());
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.AfterClass")) {
				importDec.replace(Imports.AfterAll.getNode());
			}

		}

	}

}
