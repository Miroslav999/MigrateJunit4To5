package main.rules;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import main.Annotation;

public class ReplaceTestAnnotation implements Rule {

	@Override
	public void modify(ParseResult<CompilationUnit> parsedJavaCode) {
		for (TypeDeclaration<?> type : parsedJavaCode.getResult().get().getTypes()) {
			for (Object body : type.getMembers()) {
				if (body instanceof MethodDeclaration) {
					MethodDeclaration meth = (MethodDeclaration) body;
					for (AnnotationExpr ann : meth.getAnnotations()) {
						if (ann.getNameAsString().equalsIgnoreCase("Before")) {
							ann.replace(Annotation.BeforeEach.getAnnotationExpr());
						} else if (ann.getNameAsString().equalsIgnoreCase("BeforeClass")) {
							ann.replace(Annotation.BeforeAll.getAnnotationExpr());
						} else if (ann.getNameAsString().equalsIgnoreCase("After")) {
							ann.replace(Annotation.AfterEach.getAnnotationExpr());
						} else if (ann.getNameAsString().equalsIgnoreCase("AfterClass")) {
							ann.replace(Annotation.AfterAll.getAnnotationExpr());
						}
					}
				}
			}
		}
	}

}
