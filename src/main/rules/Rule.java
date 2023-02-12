package main.rules;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

public interface Rule {
	void modify(ParseResult<CompilationUnit> parsedJavaCode);
}
