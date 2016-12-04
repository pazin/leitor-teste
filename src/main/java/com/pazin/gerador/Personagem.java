package com.pazin.gerador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Personagem {
	
	private long id;
	
	private String nome;

	private Integer level;
	
	private Integer souls;

	private Integer vitality;

	private Integer attunement;

	private Integer endurance;

	private Integer strength;
	
	private Integer dexterity;
	
	private Integer resistance;
	
	private Integer intelligence;

	private Integer faith;

	private Integer humanity;

	private Integer hp;

	private Integer stamina;

	private Double equipLoad;

	private Double poise;

	private Double bleedResist;

	private Double poisonResist;

	private Double curseResist;

	private Double itemDiscovery;

	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome( String nome )
	{
		this.nome = nome;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel( Integer level )
	{
		this.level = level;
	}

	public Integer getSouls()
	{
		return souls;
	}

	public void setSouls( Integer souls )
	{
		this.souls = souls;
	}

	public Integer getVitality()
	{
		return vitality;
	}

	public void setVitality( Integer vitality )
	{
		this.vitality = vitality;
	}

	public Integer getAttunement()
	{
		return attunement;
	}

	public void setAttunement( Integer attunement )
	{
		this.attunement = attunement;
	}

	public Integer getEndurance()
	{
		return endurance;
	}

	public void setEndurance( Integer endurance )
	{
		this.endurance = endurance;
	}

	public Integer getStrength()
	{
		return strength;
	}

	public void setStrength( Integer strength )
	{
		this.strength = strength;
	}

	public Integer getDexterity()
	{
		return dexterity;
	}

	public void setDexterity( Integer dexterity )
	{
		this.dexterity = dexterity;
	}

	public Integer getResistance()
	{
		return resistance;
	}

	public void setResistance( Integer resistance )
	{
		this.resistance = resistance;
	}

	public Integer getIntelligence()
	{
		return intelligence;
	}

	public void setIntelligence( Integer intelligence )
	{
		this.intelligence = intelligence;
	}

	public Integer getFaith()
	{
		return faith;
	}

	public void setFaith( Integer faith )
	{
		this.faith = faith;
	}

	public Integer getHumanity()
	{
		return humanity;
	}

	public void setHumanity( Integer humanity )
	{
		this.humanity = humanity;
	}

	public Integer getHp()
	{
		return hp;
	}

	public void setHp( Integer hp )
	{
		this.hp = hp;
	}

	public Integer getStamina()
	{
		return stamina;
	}

	public void setStamina( Integer stamina )
	{
		this.stamina = stamina;
	}

	public Double getEquipLoad()
	{
		return equipLoad;
	}

	public void setEquipLoad( Double equipLoad )
	{
		this.equipLoad = equipLoad;
	}

	public Double getPoise()
	{
		return poise;
	}

	public void setPoise( Double poise )
	{
		this.poise = poise;
	}

	public Double getBleedResist()
	{
		return bleedResist;
	}

	public void setBleedResist( Double bleedResist )
	{
		this.bleedResist = bleedResist;
	}

	public Double getPoisonResist()
	{
		return poisonResist;
	}

	public void setPoisonResist( Double poisonResist )
	{
		this.poisonResist = poisonResist;
	}

	public Double getCurseResist()
	{
		return curseResist;
	}

	public void setCurseResist( Double curseResist )
	{
		this.curseResist = curseResist;
	}

	public Double getItemDiscovery()
	{
		return itemDiscovery;
	}

	public void setItemDiscovery( Double itemDiscovery )
	{
		this.itemDiscovery = itemDiscovery;
	}

	@Override
	public String toString()
	{
//		ToStringHelper x = MoreObjects.toStringHelper(this);
//		
//		x.add( "id", id );
//		x.add( "nome", nome );
//		x.add( "level", level );
//		x.add( "souls", souls );
//		x.add( "vitality", vitality );
//		x.add( "attunement", attunement );
//		x.add( "endurance", endurance );
//		x.add( "strength", strength );
//		x.add( "dexterity", dexterity );
//		x.add( "resistance", resistance );
//		x.add( "intelligence", intelligence );
//		x.add( "faith", faith );
//		x.add( "humanity", humanity );
//		x.add( "hp", hp );
//		x.add( "stamina", stamina );
//		x.add( "equipload", equipLoad );
//		x.add( "poise", poise );
//		x.add( "bleedResist", bleedResist );
//		x.add( "poisonResist", poisonResist );
//		x.add( "curseResist", curseResist );
//		x.add( "itemDiscovery", itemDiscovery );
//		
//		return x.toString();

		String x = "";
		try
		{
			ObjectMapper om = new ObjectMapper();
			x = om.writerWithDefaultPrettyPrinter().writeValueAsString( this );
		}
		catch ( JsonProcessingException e )
		{
			e.printStackTrace();
		}

		return x;
	}

	public Personagem()
	{
		super();
	}

	public Personagem( long id, String nome, Integer level, Integer souls, Integer vitality, Integer attunement, Integer endurance, Integer strength, Integer dexterity, Integer resistance,
			Integer intelligence, Integer faith, Integer humanity, Integer hp, Integer stamina, Double equipLoad, Double poise, Double bleedResist, Double poisonResist, Double curseResist,
			Double itemDiscovery )
	{
		super();
		this.id = id;
		this.nome = nome;
		this.level = level;
		this.souls = souls;
		this.vitality = vitality;
		this.attunement = attunement;
		this.endurance = endurance;
		this.strength = strength;
		this.dexterity = dexterity;
		this.resistance = resistance;
		this.intelligence = intelligence;
		this.faith = faith;
		this.humanity = humanity;
		this.hp = hp;
		this.stamina = stamina;
		this.equipLoad = equipLoad;
		this.poise = poise;
		this.bleedResist = bleedResist;
		this.poisonResist = poisonResist;
		this.curseResist = curseResist;
		this.itemDiscovery = itemDiscovery;
	}

}
