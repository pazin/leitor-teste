package com.pazin.consumidor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.common.base.Splitter;

public class LeitorResistances implements Callable<Object> {

	@Override
	public Object call() throws Exception
	{
		this.start();
		return null;
	}
	
	private Map<TipoLeitor, AtomicInteger> mapaResultados;
	private Map<TipoLeitor, AtomicBoolean> bandeirada;
	
	private String path;
	
	public LeitorResistances( String path, Map<TipoLeitor, AtomicInteger> mapaResultados, Map<TipoLeitor, AtomicBoolean> bandeirada )
	{
		super();
		this.path = path;
		this.mapaResultados = mapaResultados;
		this.bandeirada = bandeirada;
		
		this.bandeirada.put( TipoLeitor.LEITOR_RESISTANCES, new AtomicBoolean( false ) );
	}
	
	private void start(){
		
		File arquivo = new File(this.path);		
		
		double bleedSum = 0.0;
		double poisonSum = 0.0;
		double curseSum = 0.0;
		
		long linhas = 0;


		AtomicInteger contador = mapaResultados.get(TipoLeitor.LEITOR_RESISTANCES);
		
		if ( contador == null ) {
			contador = new AtomicInteger();
			mapaResultados.put( TipoLeitor.LEITOR_RESISTANCES, contador );
		}
			
		LineIterator it = null;
		try {
			
			it = FileUtils.lineIterator( arquivo, "UTF-8" );

			while (it.hasNext()) {

		        String line = it.nextLine();
		        
		        List<String> list = Splitter.on( ";" ).splitToList( line );
		        
		        String bleedStr = list.get( 17 );
		        String poisonStr = list.get( 18 );
		        String curseStr = list.get( 19 );

		        
		        double bleed = 0.0;
				double poison = 0.0;
				double curse = 0.0;
				try
				{
					bleed = Double.parseDouble(bleedStr);
					poison = Double.parseDouble(poisonStr);
					curse = Double.parseDouble(curseStr);
				}
				catch ( NumberFormatException e )
				{
//					e.printStackTrace();
					continue;
				}
		        

		        bleedSum += bleed;
		        poisonSum += poison;
		        curseSum += curse;

		        linhas++;
		        
		        contador.addAndGet( 1 );
		        
//		        if (i % 200000 == 0)V
//		        	System.out.println("To vivo... "+linhas);
		    }
			
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	LineIterator.closeQuietly( it );
		}
		
		System.out.println("Terminado Resistances! " + linhas);
		System.out.println("Bleed : " + ( bleedSum / linhas ));
		System.out.println("Poison : " + ( poisonSum / linhas ));
		System.out.println("Curse : " + ( curseSum / linhas ));

		AtomicBoolean b = bandeirada.get( TipoLeitor.LEITOR_RESISTANCES);
		
		b.set( true );
	}

}
