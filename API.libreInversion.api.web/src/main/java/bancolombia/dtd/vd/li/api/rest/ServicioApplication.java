package bancolombia.dtd.vd.li.api.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

//@ApplicationPath("rest")
public class ServicioApplication extends Application {
    
	 @Override
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> classes = new HashSet<>();
	        classes.add(ControladorDatosCliente.class);
	        return classes;
	    }
}

