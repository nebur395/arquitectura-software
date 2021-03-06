package web.servlets;

import java.util.Iterator;
import java.util.ArrayList;

import web.database.valueObject.PuntuacionVO;

public abstract class AbstractServletP extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AbstractServletP() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected static String puntuacionToString(int puntuacion){
		
		if(puntuacion==0 || puntuacion==1){ return "una"; }
		else if(puntuacion==2){ return "dos"; }
		else if(puntuacion==3){ return "tres"; }
		else if(puntuacion==4){ return "cuatro"; }
		else { return "cinco"; }
	}
	
	protected static String calcularPuntuacion (ArrayList<PuntuacionVO> list){
		int puntuacion = 0;
		Iterator<PuntuacionVO> itr = list.iterator();
		while(itr.hasNext()){
			PuntuacionVO vo = itr.next();
			puntuacion += vo.getPuntuacion();
		}
		
		if (puntuacion != 0){
			puntuacion = puntuacion/list.size();
		}
		
		if(puntuacion==0 || puntuacion==1){ return "unaEstrella"; }
		else if(puntuacion==2){ return "dosEstrella"; }
		else if(puntuacion==3){ return "tresEstrella"; }
		else if(puntuacion==4){ return "cuatroEstrella"; }
		else { return "cincoEstrella"; }
	}
}
