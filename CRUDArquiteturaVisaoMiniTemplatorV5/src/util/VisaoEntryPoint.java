package util;

import javax.naming.InsufficientResourcesException;

import interfaces.IReferenciaVisaoAcao;

public class VisaoEntryPoint {
	private String entryPointName;
	private IReferenciaVisaoAcao metodoEntryPoint;
	
	public VisaoEntryPoint(String entryPointName, IReferenciaVisaoAcao metodoEntryPoint) {
		this.entryPointName = entryPointName;
		this.metodoEntryPoint = metodoEntryPoint;
	}

	public String getEntryPointName() {
		return entryPointName;
	}

	public IReferenciaVisaoAcao getMetodoEntryPoint() {
		return metodoEntryPoint;
	}
}
