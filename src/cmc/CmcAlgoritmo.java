package cmc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import graficos.Punto;
import mapa.MapaInfo;

public class CmcAlgoritmo {

	private MapaInfo mapa;
	private CmcImple cmc;
	
	public CmcAlgoritmo(MapaInfo mapa, CmcImple cmc) {
		this.mapa = mapa;
		this.cmc = cmc;
		obtenerCamino();
	}
	
	private void obtenerCamino() {
		Punto a = null, b = null;	
		Iterator<Punto> iter = mapa.getPuntos().iterator(); 
		InfoPunto[][] matrizMapa = new InfoPunto[800][600];
		Queue<InfoPunto> listaNodoPuntosAEvaluar = new PriorityQueue<InfoPunto>();	
		Queue<InfoPunto> listaNodoPuntosSeleccionados = new PriorityQueue<InfoPunto>();
		List<Punto> camino = new ArrayList<Punto>();

		int costototal = 0;
		boolean existeCamino = true;

		if (iter.hasNext()) {
			a = iter.next();
			
			while(iter.hasNext()) {
				b = iter.next();
				
				InfoPunto inicio = new InfoPunto();
				inicio.punto = a;
				inicio.costoacum = 0;
				inicio.distancia = calcularDistancia(a, b);
				inicio.predecesor = null;
				inicio.acum = inicio.costoacum + inicio.distancia;

				while(calcularDistancia(inicio.getPunto(),b) != 0 && existeCamino == true) {
				
					matrizMapa = contiguosMatriz(inicio, b, matrizMapa, listaNodoPuntosAEvaluar);
				
					if(listaNodoPuntosAEvaluar.size() == 0)
					{
						existeCamino = false;
					}
					else { 
						InfoPunto seleccionado = listaNodoPuntosAEvaluar.iterator().next();

						Iterator<InfoPunto> i;
						i = listaNodoPuntosAEvaluar.iterator();
						seleccionado = i.next();

						inicio = seleccionado;
						listaNodoPuntosSeleccionados.add(inicio);
						inicio.setSeleccionado(true);
						listaNodoPuntosAEvaluar.poll();					
					}
				}
				if(existeCamino == true) {
					costototal = inicio.costoacum; 
					while(inicio.predecesor != null) 
					{
						camino.add(inicio.punto); 
						inicio = inicio.predecesor;
					}
					cmc.dibujarCamino(camino);
				}
			}
			if(existeCamino == true) {
				mapa.enviarMensaje("El camino tiene " + camino.size() + " puntos. Costo: " + costototal + "."); 
			}
			else
			{
				mapa.enviarMensaje("No existe el camino."); 
			}
		}
	}
	private InfoPunto[][] contiguosMatriz(InfoPunto evaluar, Punto b, InfoPunto[][] matrizMapa, Queue<InfoPunto> expandidos)
	{		
		int cont = 0; 
		boolean supizq = true; 
		boolean supder = true; 
		boolean infizq = true; 
		boolean infder = true; 
		boolean noExiste; 
		while(cont < 8) 
		{
			noExiste = false; 
			
			
			Punto ubicacionAExpandir; 
			if(cont == 0) 
			{
				if((int)evaluar.getPunto().getX() == 799) 
				{
					noExiste = true; 
				}
					ubicacionAExpandir = new Punto((int) evaluar.punto.getX() + 1, (int) evaluar.punto.getY()); 
			}
			else if(cont == 1) 
			{
				if((int)evaluar.getPunto().getY() == 599) 
				{
					noExiste = true; 
				}
					ubicacionAExpandir = new Punto((int) evaluar.punto.getX(), (int) evaluar.punto.getY()+1); 
			}
			else if(cont == 2) 
			{
				if((int)evaluar.getPunto().getX() == 0) { 
					noExiste = true; 
				}
					ubicacionAExpandir = new Punto((int) evaluar.punto.getX() - 1, (int) evaluar.punto.getY()); 
				
			}
			else if(cont == 3) 
			{
				if((int)evaluar.getPunto().getY() == 0) { 
					noExiste = true; 
				}
					ubicacionAExpandir = new Punto((int) evaluar.punto.getX(), (int) evaluar.punto.getY() - 1); 
			}
			else if(cont == 4) 
			{
				if((int)evaluar.getPunto().getX() == 799 ||(int)evaluar.getPunto().getY() == 0) 
				{
					noExiste = true; 
				}
				ubicacionAExpandir = new Punto((int) evaluar.punto.getX() + 1, (int) evaluar.punto.getY() - 1); 
			}
			else if(cont == 5) 
			{
				if((int)evaluar.getPunto().getX() == 799 ||(int)evaluar.getPunto().getY() == 599) 
				{
					noExiste = true; 
				}
				ubicacionAExpandir = new Punto((int) evaluar.punto.getX() + 1, (int) evaluar.punto.getY() + 1); 

			}
			else if(cont == 6) 
			{
				if((int)evaluar.getPunto().getX() == 0 ||(int)evaluar.getPunto().getY() == 599) 
				{
					noExiste = true; 
				}
				ubicacionAExpandir = new Punto((int) evaluar.punto.getX() - 1, (int) evaluar.punto.getY() + 1); 

			}
			else 
			{
				if((int)evaluar.getPunto().getX() == 0 ||(int)evaluar.getPunto().getY() == 0) 
				{
					noExiste = true; 
				}
				ubicacionAExpandir = new Punto((int) evaluar.punto.getX() - 1, (int) evaluar.punto.getY() - 1); 

			}
			
			if(noExiste == false || (noExiste == false && cont == 4 && supder == true) || (noExiste == false && cont == 5 && infder == true) || (noExiste == false && cont == 6 && infizq == true) || (noExiste == false && cont == 7 && supizq == true)) //N
				{

				int densidadpunto = (mapa.getDensidad(ubicacionAExpandir) + 1)*10 ; 
				if(cont > 3)  
				{
					densidadpunto = (int) (densidadpunto * 1.4); 
				}
				
				
				InfoPunto aExpandir = new InfoPunto(); 
				aExpandir.predecesor = evaluar; 
				aExpandir.punto = ubicacionAExpandir; 
				aExpandir.costoacum = aExpandir.predecesor.costoacum + densidadpunto; 
				aExpandir.distancia = calcularDistancia(aExpandir.punto, b) * 10; 
				aExpandir.acum = aExpandir.costoacum + aExpandir.distancia; 
				aExpandir.seleccionado = false; 
				boolean infranqueable = false; 
				
				
				if(mapa.getDensidad(ubicacionAExpandir) == 4) 
				{
					infranqueable = true; 
					if(cont == 0) 
					{
						supder = false; 
						infder = false; 
					}
					else if(cont == 1) 
					{
						infizq = false; 
						infder = false; 
					}
					else if(cont == 2) 
					{
						supizq = false; 
						infizq = false; 
					}
					else 
					{
						supizq = false; 
						supder = false; 
					}

				}
				if(infranqueable == false) { 
					
					if(matrizMapa[(int) aExpandir.getPunto().getX()][(int) aExpandir.getPunto().getY()] != null) 
					{
						InfoPunto aComparar = matrizMapa[(int) aExpandir.getPunto().getX()][(int) aExpandir.getPunto().getY()]; 
					
						if(aComparar.isSeleccionado() == false) { 
							if(aComparar.getAcum() > aExpandir.getAcum()) 
							{
								expandidos.remove(aComparar); 
								expandidos.add(aExpandir);  
								aComparar.costoacum = aExpandir.costoacum; 
								aComparar.acum = aExpandir.acum; 
								aComparar.predecesor = evaluar; 
							}
						}
						
					}
					else 
					{
						matrizMapa[(int) aExpandir.getPunto().getX()][(int) aExpandir.getPunto().getY()] = aExpandir; 
						expandidos.add(aExpandir); 
					}
				
				}
			}
			cont++; 
			
		}
				
		return matrizMapa; 
		
	}		
	private int calcularDistancia(Punto actual, Punto b)
	{
		int distanciamenor; 
		if((b.x < actual.x && actual.y < b.y)) 
		{
			distanciamenor = -(b.x - actual.x) + (b.y - actual.y); 
		}
		else if(b.y < actual.y && actual.x < b.x) 
		{
			distanciamenor = -(b.x - actual.x) + (b.y - actual.y); 
		}
		else 
		{
			distanciamenor = (b.x - actual.x) + (b.y - actual.y); 
		}
		
		if(distanciamenor < 0) 
		{
			distanciamenor = distanciamenor * -1; 
		}
		
		return distanciamenor; 
	}
}
