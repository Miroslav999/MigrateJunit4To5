package main;

import com.github.javaparser.ast.ImportDeclaration;

public enum Imports {

	Test("Test"),
	BeforeAll("BeforeAll"),
	BeforeEach("BeforeEach"), AfterAll("AfterAll"), AfterEach("AfterEach");

	private String name;

	Imports(String name) {
		this.name = name;
	}

	public ImportDeclaration getNode() {
		return new ImportDeclaration("org.junit.jupiter.api." + name, false, false);
	}

}
