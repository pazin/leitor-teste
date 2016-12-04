package com.pazin.consumidor;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitorProgresso implements Callable<Object> {

	@Override
	public Object call() throws Exception
	{
		monitorar();
		return null;
	}
	
	public MonitorProgresso( Map<TipoLeitor, AtomicInteger> resultados, Map<TipoLeitor, AtomicBoolean> bandeirada )
	{
		super();
		this.resultados = resultados;
		this.bandeirada = bandeirada;
	}

	private Map<TipoLeitor, AtomicBoolean> bandeirada;
	private Map<TipoLeitor, AtomicInteger> resultados;

	private void monitorar(){
		
		String template = "%s - Total : %d | ";
		
		while(true){
			
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Fui interrompida!");
		        throw new RuntimeException("OPA!!!!!!!!!!!"); 
		    }
			
			StringBuilder sb = new StringBuilder();

			try
			{
				Thread.sleep( 500 );
			}
			catch ( InterruptedException e )
			{
//				e.printStackTrace();
				break;
			}
			
			for (Entry<TipoLeitor, AtomicInteger> entry : resultados.entrySet()){
				sb.append( String.format(template, entry.getKey().name(), entry.getValue().get() ) );
			}
			
			System.out.println(sb.toString());

			boolean completo = true;
			
			for (Entry<TipoLeitor, AtomicBoolean> entry : bandeirada.entrySet()){
				completo = completo && entry.getValue().get();
			}

			if ( completo )
				break;
		}
		
	}
}
