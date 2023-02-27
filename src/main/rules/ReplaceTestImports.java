package main.rules;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;

import main.FileWrapper;
import main.Imports;

public class ReplaceTestImports implements Rule {

	public static final String BASE_PACKAGE_NAME = "org.junit.jupiter.api.";

	@Override
	public void modify(FileWrapper fileWrapper) {

		for (ImportDeclaration importDec : fileWrapper.getParsedJavaCode().getResult().get().getImports()) {
			if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Test")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.Test, importDec.isStatic(), importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Before")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.BeforeEach, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.BeforeClass")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.BeforeAll, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.After")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.AfterEach, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.AfterClass")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.AfterAll, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Ignore")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.Disable, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().contains("org.junit.Assert")) {
				String identifier = ((Name) importDec.getChildNodes().get(0)).getIdentifier();
				StringBuilder builder = new StringBuilder();
				builder.append(BASE_PACKAGE_NAME);
				builder.append(Imports.Assertions);

				if (!identifier.equalsIgnoreCase("Assert")) {
					builder.append(".");
					builder.append(identifier);
				}

				ImportDeclaration assertionsImport = new ImportDeclaration(
						builder.toString(),
						importDec.isStatic(),
						importDec.isAsterisk());
				fileWrapper.replaceNode(importDec, assertionsImport);

			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.FixMethodOrder")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.TestMethodOrder, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.runners.MethodSorters")) {
				fileWrapper.replaceNode(importDec,
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.MethodOrderer, importDec.isStatic(),
						importDec.isAsterisk()));
			}
		}
	}
}
