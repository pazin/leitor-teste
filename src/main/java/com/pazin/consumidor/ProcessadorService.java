package com.pazin.consumidor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class ProcessadorService {
	
	private Map<TipoLeitor, AtomicInteger> resultados = Maps.newConcurrentMap();

	private Map<TipoLeitor, AtomicBoolean> bandeirada = Maps.newConcurrentMap();
	
	@Autowired
	private Environment env;
	
	public void processar(){
		
		String arquivo = env.getProperty( "arquivo.path" );
		
		LeitorResistances leitor1 = new LeitorResistances( arquivo, this.resultados, bandeirada );
		LeitorBuild leitor2 = new LeitorBuild( arquivo, this.resultados, bandeirada );
		
		ExecutorService executorSingle = Executors.newSingleThreadExecutor();

		Future<Object> futureProgresso = executorSingle.submit( new MonitorProgresso( resultados, bandeirada ) );

		ExecutorService executor = Executors.newFixedThreadPool( 5 );
		
		List<Future<Object>> futures = Lists.newArrayList();

		futures.add( executor.submit( leitor2 ) );
		futures.add( executor.submit( leitor1 ) );
		
		executor.shutdown();
		
		trataExcecoes( futures );
		
	}

	private void trataExcecoes( List<Future<Object>> resultados )
	{
		if ( resultados != null ){

			resultados.forEach( f -> {
				
				Throwable t = null;
				
				try
				{
					if ( f.isDone() )
						f.get();
				}
				catch ( CancellationException ce )
				{
					t = ce;
				}
				catch ( ExecutionException ee )
				{
					t = ee;
				}
				catch ( InterruptedException ie )
				{
					Thread.currentThread().interrupt();
				}
				
				if ( t!= null){
					System.out.println(t);
					t.printStackTrace();
				}
			});
		}
	}

	public Map<TipoLeitor, AtomicInteger> getResultados()
	{
		return resultados;
	}

	public void setResultados( Map<TipoLeitor, AtomicInteger> resultados )
	{
		this.resultados = resultados;
	}

	public Map<TipoLeitor, AtomicBoolean> getBandeirada()
	{
		return bandeirada;
	}

	public void setBandeirada( Map<TipoLeitor, AtomicBoolean> bandeirada )
	{
		this.bandeirada = bandeirada;
	}
	
	
	

}
