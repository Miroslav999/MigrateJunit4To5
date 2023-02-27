package main.rules;

import main.FileWrapper;

public interface Rule {
	void modify(FileWrapper fileWrapper);
}
