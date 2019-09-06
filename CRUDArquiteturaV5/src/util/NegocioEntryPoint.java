package util;

import interfaces.IReferenciaAcaoNegocio;

public class NegocioEntryPoint {
	private String entryPointName;
	private IReferenciaAcaoNegocio metodoEntryPoint;
	
	public NegocioEntryPoint(String entryPointName, IReferenciaAcaoNegocio metodoEntryPoint) {
		this.entryPointName = entryPointName;
		this.metodoEntryPoint = metodoEntryPoint;
	}

	public String getEntryPointName() {
		return entryPointName;
	}

	public IReferenciaAcaoNegocio getMetodoEntryPoint() {
		return metodoEntryPoint;
	}

	@Override
	public String toString() {
		return "NegocioEntryPoint [entryPointName=" + entryPointName + ", metodoEntryPoint=" + metodoEntryPoint + "]";
	}
}
