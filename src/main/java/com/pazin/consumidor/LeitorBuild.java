package com.pazin.consumidor;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class LeitorBuild implements Callable<Object> {

	@Override
	public Object call() throws Exception
	{
		this.start();
		return null;
	}
	
	private Map<TipoLeitor, AtomicInteger> mapaResultados;

	private Map<TipoLeitor, AtomicBoolean> bandeirada;
	
	private Map<TipoBuild, AtomicInteger> diversidadeBuilds = Maps.newHashMap();
	
	private String path;
	
	public LeitorBuild( String path, Map<TipoLeitor, AtomicInteger> mapaResultados, Map<TipoLeitor, AtomicBoolean> bandeirada  )
	{
		super();
		this.path = path;
		this.mapaResultados = mapaResultados;
		this.bandeirada = bandeirada;

		this.bandeirada.put( TipoLeitor.LEITOR_ATRIBUTOS, new AtomicBoolean( false ) );
	}
	
	private enum AtributoType {
		STR,
		DEX,
		INT,
		FTH
	}
	
	private static class Atributo implements Comparable<Atributo> {

		private AtributoType type;
		
		private double amount;

		@Override
		public int compareTo( Atributo o )
		{
			return Double.compare(this.amount, o.amount);
		}

		public Atributo( AtributoType type, double amount )
		{
			super();
			this.type = type;
			this.amount = amount;
		}
	}

	private void start(){

		File arquivo = new File(this.path);		
		
		double vitalitySum = 0.0;
		double attunementSum = 0.0;
		double enduranceSum = 0.0;
		double strengthSum = 0.0;
		double dexteritySum = 0.0;
		double resistanceSum = 0.0;
		double intelligenceSum = 0.0;
		double faithSum = 0.0;
		
		long linhas = 0;


		AtomicInteger contador = mapaResultados.get(TipoLeitor.LEITOR_ATRIBUTOS);
		
		if ( contador == null ) {
			contador = new AtomicInteger();
			mapaResultados.put( TipoLeitor.LEITOR_ATRIBUTOS, contador );
		}
		
		LineIterator it = null;
		try {
			
			it = FileUtils.lineIterator( arquivo, "UTF-8" );
			
			while (it.hasNext()) {

		        String line = it.nextLine();
		        
		        List<String> list = Splitter.on( ";" ).splitToList( line );
		        
		        String vitalityStr = list.get( 4 );
				String attunementStr = list.get( 5 );
				String enduranceStr = list.get( 6 );
				String strengthStr = list.get( 7 );
				String dexterityStr = list.get( 8 );
				String resistanceStr = list.get( 9 );
				String intelligenceStr = list.get( 10 );
				String faithStr = list.get( 11 );


		        double vitality = 0.0;
				double attunement = 0.0;
				double endurance = 0.0;
				double strength = 0.0;
				double dexterity = 0.0;
				double resistance = 0.0;
				double intelligence = 0.0;
				double faith = 0.0;

				try
				{
					vitality = Double.parseDouble(vitalityStr);
					attunement = Double.parseDouble(attunementStr);
					endurance = Double.parseDouble(enduranceStr);
					strength = Double.parseDouble( strengthStr );
					dexterity = Double.parseDouble( dexterityStr );
					resistance = Double.parseDouble( resistanceStr );
					intelligence = Double.parseDouble( intelligenceStr );
					faith = Double.parseDouble( faithStr );
				}
				catch ( NumberFormatException e )
				{
//					e.printStackTrace();
					continue;
				}
		        
		        vitalitySum += vitality;
		        attunementSum += attunement;
		        enduranceSum += endurance;
				strengthSum += strength;
				dexteritySum += dexterity;
				resistanceSum += resistance;
				intelligenceSum += intelligence;
				faithSum += faith;



				trataBuilds( strength, dexterity, intelligence, faith );


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
		
		System.out.println("Terminado Build! " + linhas);
		System.out.println("vitality : " + ( vitalitySum / linhas ));
		System.out.println("attunement : " + ( attunementSum / linhas ));
		System.out.println("endurance : " + ( enduranceSum / linhas ));
		System.out.println("strength : " + ( strengthSum / linhas ));
		System.out.println("dexterity : " + ( dexteritySum / linhas ));
		System.out.println("resistance : " + ( resistanceSum / linhas ));
		System.out.println("intelligence : " + ( intelligenceSum / linhas ));
		System.out.println("faith : " + ( faithSum / linhas ));
		
		System.out.println("");

		diversidadeBuilds.forEach( (b, qtd) -> {
			System.out.println( String.format( "%s : %d", b.name(), qtd.get() ) );
		});

		AtomicBoolean b = bandeirada.get( TipoLeitor.LEITOR_ATRIBUTOS );
		
		b.set( true );
	}

	private void trataBuilds( double strength, double dexterity, double intelligence, double faith )
	{
		List<Atributo> atributosBuild = Lists.newArrayList();
		
		atributosBuild.add( new Atributo( AtributoType.STR, strength )  );
		atributosBuild.add( new Atributo( AtributoType.DEX, dexterity )  );
		atributosBuild.add( new Atributo( AtributoType.FTH, faith )  );
		atributosBuild.add( new Atributo( AtributoType.INT, intelligence )  );

		Collections.sort(atributosBuild, Collections.reverseOrder());
		
		Atributo maior = atributosBuild.get( 0 );
		Atributo segundo = atributosBuild.get( 1 );
		
		if ( almostEquals( maior.amount, segundo.amount, EPSILON ))
			determinaBuildDoisAtributos( maior, segundo );
		else 
			determinaBuildUmAtributo( maior );
		
	}


	private void determinaBuildDoisAtributos(Atributo atributo1, Atributo atributo2){

		Set<AtributoType> setAtributos = Sets.newHashSet();
		setAtributos.add( atributo1.type );
		setAtributos.add( atributo2.type );
			
		if ( setAtributos.contains( AtributoType.STR ) && setAtributos.contains( AtributoType.DEX ) )
			incrementaDiversidade( TipoBuild.QUALITY );
		else if ( setAtributos.contains( AtributoType.FTH ) && setAtributos.contains( AtributoType.DEX ) )
			incrementaDiversidade( TipoBuild.FAITH_DEX );
		else
			determinaBuildUmAtributo( atributo1 );
	}
	
	private void determinaBuildUmAtributo(Atributo atributo){
		
		switch ( atributo.type )
		{
		case STR:
			incrementaDiversidade( TipoBuild.STR_BUILD );
			break;
		case FTH:
			incrementaDiversidade( TipoBuild.FAITH );
			break;
		case DEX:
			incrementaDiversidade( TipoBuild.FUCKING_CASUAL );
			break;
		case INT:
			incrementaDiversidade( TipoBuild.SORCERER );
			break;
		}
	}
	
	
	private void incrementaDiversidade(TipoBuild tipo){
		
		AtomicInteger qtd = diversidadeBuilds.getOrDefault( tipo, new AtomicInteger() );
		qtd.addAndGet( 1 );
		diversidadeBuilds.put( tipo, qtd );
	}

	public static double EPSILON = 0.0001;	
	
	public static boolean almostEquals(double d1, double d2, double epsilon) {
		return Math.abs(d1 - d2) < epsilon;
	}	

}
