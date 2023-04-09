package main.rules;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

import main.FileWrapper;

public class ChangeArgOrder implements Rule {

	@Override
	public void modify(FileWrapper fileWrapper) {
		for (TypeDeclaration<?> type : fileWrapper.getParsedJavaCode().getResult().get().getTypes()) {
			change(type);
		}
	}

	private void change(Node node) {
		if (node.getChildNodes().isEmpty()) {
			return;
		}

		for (Node obj : node.getChildNodes()) {
			change(obj);
			if (obj instanceof MethodCallExpr) {
				MethodCallExpr method = (MethodCallExpr) obj;
				if (method.getName().asString().matches("^assert.*")
						&& method.getArguments().getFirst().get() instanceof StringLiteralExpr) {
					NodeList<Expression> args = method.getArguments();
					Expression first = args.getFirst().get();
					args.removeFirst();
					args.add(first);
				}
			}
		}
	}

}
