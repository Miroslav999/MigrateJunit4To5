package main;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;

public enum Annotation {

	BeforeEach("BeforeEach"), BeforeAll("BeforeAll"), AfterEach("AfterEach"), AfterAll("AfterAll"), Disable(
			"Disable");

	private String name;

	Annotation(String name) {
		this.name = name;
	}

	public AnnotationExpr getAnnotationExpr() {
		return new MarkerAnnotationExpr(name);
	}
}
