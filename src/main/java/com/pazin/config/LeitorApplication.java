package com.pazin.config;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LeitorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LeitorApplication.class, args);
		
//		ProcessadorService pr = (ProcessadorService) context.getBean( ProcessadorService.class );
//		
//		pr.processar();
		
//		Gerador gerador = (Gerador) context.getBean("geradorCSV");
//		
//		gerador.gerarArquivoGigante();
		
		
		DirectedAcyclicGraph<String, String> dag = (DirectedAcyclicGraph<String, String>) context.getBean( "grafoDependencia4" );
		
		for (Iterator<String> iterator = dag.iterator(); iterator.hasNext(); ){
			String s = iterator.next();
			
			System.out.println(s);
		}
		
	}
}
