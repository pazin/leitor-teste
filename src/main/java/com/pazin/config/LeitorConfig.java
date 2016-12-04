package com.pazin.config;

import org.jgrapht.EdgeFactory;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.pazin.gerador.Gerador;


@Configuration
@ComponentScan(basePackages={"com.pazin.*"})
public class LeitorConfig {
	
	@Autowired
	private Environment env;
	
	@Bean("geradorCSV")
	public Gerador getGerador(){
		return new Gerador(env);
	}

	
	@Bean("grafoDependencia1")
	public DirectedAcyclicGraph<String, String> getGrafo(){

		String sete = "7";
		String tres = "3";
		String dez = "10";
		String cinco = "5";
		String onze = "11";
		String nove = "9";
		String dois = "2";
		String oito = "8";
		
		EdgeFactory<String, String> factory = new ClassBasedEdgeFactory<String, String>(String.class);
		
		DirectedAcyclicGraph<String, String> dag = new DirectedAcyclicGraph<String, String>( factory );

		dag.addVertex( tres );
		dag.addVertex( sete );
		dag.addVertex( oito );
		dag.addVertex( cinco );
		dag.addVertex( onze );
		dag.addVertex( dez );
		dag.addVertex( dois );
		dag.addVertex( nove );
		
		try
		{
			dag.addDagEdge( cinco, onze );
			dag.addDagEdge( sete, onze );
			dag.addDagEdge( tres, oito );
			dag.addDagEdge( sete, oito );

			dag.addDagEdge( onze, dois );
			dag.addDagEdge( onze, nove );
			dag.addDagEdge( onze, dez );

			dag.addDagEdge( oito, nove );
			
			dag.addDagEdge( tres, dez );
		}
		catch ( CycleFoundException e )
		{
			e.printStackTrace();
		}
		
		return dag;
	}
	
	
	/**
	 * 
	 * ESSE AQUI DEU ERRADO USANDO O JGRAPHT
	 * 
	 * @return
	 */
	@Bean("grafoDependencia2")
	public DirectedAcyclicGraph<String, String> getGrafo2(){

		String um = "1";
		String dois = "2";
		String tres = "3";
		String quatro = "4";
		String cinco = "5";
		String seis = "6";
		String sete = "7";
		
		EdgeFactory<String, String> factory = new ClassBasedEdgeFactory<String, String>(String.class);
		
		DirectedAcyclicGraph<String, String> dag = new DirectedAcyclicGraph<String, String>( factory );

		dag.addVertex( sete );
		dag.addVertex( seis );
		dag.addVertex( cinco );
		dag.addVertex( quatro );
		dag.addVertex( tres );
		dag.addVertex( dois );
		dag.addVertex( um );
		
		try
		{
			dag.addDagEdge( um, quatro );
			dag.addDagEdge( um, tres );

			dag.addDagEdge( dois, quatro );
			dag.addDagEdge( dois, cinco );

			dag.addDagEdge( tres, seis );

			dag.addDagEdge( quatro, tres );
			dag.addDagEdge( quatro, seis );
			dag.addDagEdge( quatro, sete );

			dag.addDagEdge( cinco, quatro );
			dag.addDagEdge( cinco, sete );
			
			dag.addDagEdge( tres, seis );
		}
		catch ( CycleFoundException e )
		{
			e.printStackTrace();
		}
		
		return dag;
	}


	@Bean("grafoDependencia3")
	public DirectedAcyclicGraph<String, String> getGrafo3(){

		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";
		String e = "e";
		String f = "f";
		
		EdgeFactory<String, String> factory = new ClassBasedEdgeFactory<String, String>(String.class);
		
		DirectedAcyclicGraph<String, String> dag = new DirectedAcyclicGraph<String, String>( factory );

		dag.addVertex( a );
		dag.addVertex( b );
		dag.addVertex( c );
		dag.addVertex( d );
		dag.addVertex( e );
		dag.addVertex( f );
		
		try
		{
			dag.addDagEdge( d, c );
			dag.addDagEdge( d, b );

			dag.addDagEdge( e, c );
			dag.addDagEdge( e, f );

			dag.addDagEdge( b, a );

			dag.addDagEdge( a, f );

		}
		catch ( CycleFoundException ex )
		{
			ex.printStackTrace();
		}
		
		return dag;
	}


	@Bean("grafoDependencia4")
	public DirectedAcyclicGraph<String, String> getGrafo4(){

		String undershorts = "undershorts";
		String socks = "socks";
		String watch = "watch";
		String pants = "pants";
		String shoes = "shoes";
		String belt = "belt";
		String shirt = "shirt";
		String tie = "tie";
		String jacket = "jacket";
		
		EdgeFactory<String, String> factory = new ClassBasedEdgeFactory<String, String>(String.class);
		
		DirectedAcyclicGraph<String, String> dag = new DirectedAcyclicGraph<String, String>( factory );

		dag.addVertex( undershorts );
		dag.addVertex( socks );
		dag.addVertex( watch );
		dag.addVertex( pants);
		dag.addVertex( shoes );
		dag.addVertex( belt );
		dag.addVertex( shirt );
		dag.addVertex( tie );
		dag.addVertex( jacket );
		
		try
		{
			dag.addDagEdge( undershorts, shoes );
			dag.addDagEdge( undershorts, pants );

			dag.addDagEdge( socks, shoes );

			dag.addDagEdge( pants, shoes );
			dag.addDagEdge( pants, belt );

			dag.addDagEdge( shirt, belt );
			dag.addDagEdge( shirt, tie );

			dag.addDagEdge( belt, jacket );

			dag.addDagEdge( tie, jacket );

		}
		catch ( CycleFoundException ex )
		{
			ex.printStackTrace();
		}
		
		return dag;
	}
}
