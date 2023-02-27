package main.rules;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.SimpleName;

import main.FileWrapper;

public class ReplaceClassInCode implements Rule {

	@Override
	public void modify(FileWrapper fileWrapper) {
		for (TypeDeclaration<?> type : fileWrapper.getParsedJavaCode().getResult().get().getTypes()) {
			replaceAssertClass(type);
		}

	}

	private void replaceAssertClass(Node node) {
		if (node.getChildNodes().isEmpty()) {
			return;
		}

		for (Node obj: node.getChildNodes()) {
			replaceAssertClass(obj);
			if (obj instanceof SimpleName) {
				SimpleName name = (SimpleName) obj;
				if (name.asString().equalsIgnoreCase("Assert")) {
					name.setId("Assertions");
				}
			}
		}
	}

}
