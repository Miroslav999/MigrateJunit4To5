package main.rules;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;

import main.Imports;

public class ReplaceTestImports implements Rule {

	public static final String BASE_PACKAGE_NAME = "org.junit.jupiter.api.";

	@Override
	public void modify(ParseResult<CompilationUnit> parsedJavaCode) {

		for (ImportDeclaration importDec : parsedJavaCode.getResult().get().getImports()) {
			if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Test")) {
				importDec.replace(
						new ImportDeclaration(BASE_PACKAGE_NAME + Imports.Test, importDec.isStatic(), importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Before")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.BeforeEach, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.BeforeClass")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.BeforeAll, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.After")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.AfterEach, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.AfterClass")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.AfterAll, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.Ignore")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.Disable, importDec.isStatic(),
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
				importDec
						.replace(assertionsImport);

			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.FixMethodOrder")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.TestMethodOrder, importDec.isStatic(),
						importDec.isAsterisk()));
			} else if (importDec.getNameAsString().equalsIgnoreCase("org.junit.runners.MethodSorters")) {
				importDec.replace(new ImportDeclaration(BASE_PACKAGE_NAME + Imports.MethodOrderer, importDec.isStatic(),
						importDec.isAsterisk()));
			}
		}
	}
}
