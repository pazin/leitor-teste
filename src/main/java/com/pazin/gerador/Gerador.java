package com.pazin.gerador;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;

public class Gerador {

	private Environment env;
	
	public Gerador( Environment env )
	{
		super();
		this.env = env;
	}

	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final Object [] FILE_HEADER = {"id", "nome", "level", "souls", "vitality", "attunement", "endurance", "strength", "dexterity", "resistance", "intelligence", "faith", "humanity", 
		"hp", "stamina", "equipLoad", "poise", "bleedResist", "poisonResist", "curseResist", "itemDiscovery"};
	
	public Personagem geraPersonagemAleatorio(){
		
		ThreadLocalRandom rnd = ThreadLocalRandom.current();

		Personagem result = new Personagem();
		
		result.setId( rnd.nextInt() );
		result.setNome( RandomStringUtils.randomAlphabetic( 10, 40 ) );
		result.setLevel( rnd.nextInt( 200 ) );
		result.setSouls( rnd.nextInt( 30000000 ) );
		result.setVitality( rnd.nextInt(99) );
		result.setAttunement( rnd.nextInt(99) );
		result.setEndurance( rnd.nextInt(99) );
		result.setStrength( rnd.nextInt( 99 ) );
		result.setDexterity( rnd.nextInt( 99 ) );
		result.setResistance( rnd.nextInt( 99 ) );
		result.setIntelligence( rnd.nextInt( 99 ) );
		result.setFaith( rnd.nextInt( 99 ) );
		result.setHumanity( rnd.nextInt( 15 ) );
		result.setHp( rnd.nextInt( 15000 ) );
		result.setStamina( rnd.nextInt( 1000 ) );
		
		result.setEquipLoad( rnd.nextDouble(400.0) );
		result.setPoise( rnd.nextDouble(80.0) );
		result.setBleedResist( rnd.nextDouble(200.0) );
		result.setPoisonResist( rnd.nextDouble(200.0) );
		result.setCurseResist( rnd.nextDouble(200.0) );
		result.setItemDiscovery( rnd.nextDouble(500.0) );
		
		return result;
	}
	
	public void gerarArquivoGigante(){
		
		String arquivo = env.getProperty( "arquivo.path" );
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter( ';' ).withRecordSeparator( NEW_LINE_SEPARATOR );
		
		FileWriter fw = null;
		
		CSVPrinter csvPrinter = null;


		System.out.println("Gerando aquivo");
		
		try {
			
			fw = new FileWriter( arquivo, true);
			
			csvPrinter = new CSVPrinter( fw, csvFileFormat );
			
			csvPrinter.printRecord( FILE_HEADER );
			
			for (int i = 0; i <= 8000000; i++){
				
				Personagem chosenUndead = geraPersonagemAleatorio();
				
				List<String> personagens = new ArrayList<String>();
				
				personagens.add( String.valueOf( chosenUndead.getId() ) );
				personagens.add( chosenUndead.getNome() );
				personagens.add( String.valueOf( chosenUndead.getLevel() ) );
				personagens.add( String.valueOf( chosenUndead.getSouls() ) );
				personagens.add( String.valueOf( chosenUndead.getVitality() ) );
				personagens.add( String.valueOf( chosenUndead.getAttunement() ) );
				personagens.add( String.valueOf( chosenUndead.getEndurance() ) );
				personagens.add( String.valueOf( chosenUndead.getStrength() ) );
				personagens.add( String.valueOf( chosenUndead.getDexterity() ) );
				personagens.add( String.valueOf( chosenUndead.getResistance() ) );
				personagens.add( String.valueOf( chosenUndead.getIntelligence() ) );
				personagens.add( String.valueOf( chosenUndead.getFaith() ) );
				personagens.add( String.valueOf( chosenUndead.getHumanity() ) );
				personagens.add( String.valueOf( chosenUndead.getHp() ) );
				personagens.add( String.valueOf( chosenUndead.getStamina() ) );
				personagens.add( String.valueOf( chosenUndead.getEquipLoad() ) );
				personagens.add( String.valueOf( chosenUndead.getPoise() ) );
				personagens.add( String.valueOf( chosenUndead.getBleedResist() ) );
				personagens.add( String.valueOf( chosenUndead.getPoisonResist() ) );
				personagens.add( String.valueOf( chosenUndead.getCurseResist() ) );
				personagens.add( String.valueOf( chosenUndead.getItemDiscovery() ) );
				
				csvPrinter.printRecord( personagens );
			}
			
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				fw.flush();
				fw.close();
				csvPrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
			}	
		}

		System.out.println("Arquivo gerado");
	}
}
