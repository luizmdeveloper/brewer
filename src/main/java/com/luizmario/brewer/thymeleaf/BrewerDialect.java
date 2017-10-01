package com.luizmario.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.luizmario.brewer.thymeleaf.processor.ClassForErrorAtributeTagProcessor;
import com.luizmario.brewer.thymeleaf.processor.ElementMessageTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("LuizMario brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processores = new HashSet<>();
		processores.add(new ClassForErrorAtributeTagProcessor(dialectPrefix));
		processores.add(new ElementMessageTagProcessor(dialectPrefix));
		return processores;
	}

}
