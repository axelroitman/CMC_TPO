package cmc;

import graficos.Punto;

public class InfoPunto implements Comparable<InfoPunto>{

	int costoacum;
	int distancia;
	int acum;
	InfoPunto predecesor;
	Punto punto;
	boolean seleccionado;
	
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	public int getCostoacum() {
		return costoacum;
	}
	public void setCostoacum(int costoacum) {
		this.costoacum = costoacum;
	}
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	public int getAcum() {
		return acum;
	}
	public void setAcum(int acum) {
		this.acum = acum;
	}
	public InfoPunto getPredecesor() {
		return predecesor;
	}
	public void setPredecesor(InfoPunto predecesor) {
		this.predecesor = predecesor;
	}
	public Punto getPunto() {
		return punto;
	}
	public void setPunto(Punto punto) {
		this.punto = punto;
	}
	@Override
	public int compareTo(InfoPunto arg0) {

		if(this.acum < arg0.acum)
		{
			return -1;
		}
		else if(this.acum > arg0.acum)
		{
			return 1;
		}
		else
		{
			if(this.distancia < arg0.distancia)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}
	
}
