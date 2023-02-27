package main.rules;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import main.Annotation;
import main.FileWrapper;

public class ReplaceTestAnnotation implements Rule {

	@Override
	public void modify(FileWrapper fileWrapper) {

		for (TypeDeclaration<?> type : fileWrapper.getParsedJavaCode().getResult().get().getTypes()) {
			String annFixMethodOrder = "FixMethodOrder";
			if (type.isAnnotationPresent(annFixMethodOrder)) {
				SingleMemberAnnotationExpr ann = (SingleMemberAnnotationExpr) type.getAnnotationByName(annFixMethodOrder).get();
				ann.replace(getTestMethodOrderAnn(ann.getMemberValue().asFieldAccessExpr().getName().asString()));
			}

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
						} else if (ann.getNameAsString().equalsIgnoreCase("Ignore")) {
							ann.replace(Annotation.Disabled.getAnnotationExpr());
						}
					}
				}
			}
		}
	}

	private AnnotationExpr getTestMethodOrderAnn(String methodSort) {
		String methodClass = "Unkown";
		
		switch(methodSort) {
		case "NAME_ASCENDING":
			methodClass = "MethodName.class";
			break;
		}
		
		return new SingleMemberAnnotationExpr(new Name("TestMethodOrder"),
				new FieldAccessExpr(new NameExpr("MethodOrderer"), methodClass));
	}

}
