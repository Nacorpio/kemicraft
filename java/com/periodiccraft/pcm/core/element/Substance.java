package com.periodiccraft.pcm.core.element;

import net.minecraft.client.Minecraft;

import com.periodiccraft.pcm.PeriodicCraft;
import com.periodiccraft.pcm.core.registry.ResearchRegistry;
import com.periodiccraft.pcm.core.registry.ResearchRegistry.Research;
import com.periodiccraft.pcm.core.registry.SubstanceRegistry;
import com.periodiccraft.pcm.helper.ChatUtil;

public class Substance {

	public static enum CATEGORY {
		ALKALI_METALS,
		ALKALINE_EARTH_METALS,
		TRANSITION_METALS,
		TRANSITION_AND_POST_TRANSISTION_METALS,
		POST_TRANSITION_METALS,
		LANTHANIDES,
		ACTINIDES,
		HALOGENS,
		NOBLE_GASES,
		NON_METALS,
		SEMI_METALS,
		UNKNOWN;
	}
	
	public static enum STATE {
		
		GAS("Gas"),
		LIQUID("Liquid"),
		SOLID("Solid"),
		PLASMA("Plasma");
		
		private String text;
		
		STATE(String par1) {
			this.text = par1;
		}
		
		public final String getText() {
			return this.text;
		}
		
	}
	
	public static enum TIER {
		
		ONE("I", ChatUtil.StringHandler.cyan),
		TWO("II", ChatUtil.StringHandler.dark_cyan),
		THREE("III", ChatUtil.StringHandler.blue),
		FOUR("IV", ChatUtil.StringHandler.purple),
		FIVE("V", ChatUtil.StringHandler.red);
		
		private String text;
		private String color;
		
		TIER(String par1, String par2) {
			this.text = par1;
			this.color = par2;
		}
		
		public final String getText() {
			return this.text;
		}
		
		public final String getColor() {
			return this.color;
		}
		
	}
	
	private int id;
	
	private String name;
	private String symbol;
	private String color;
	
	private float atomicWeight;
	
	private float temperature = 10.0F;
	
	private float boilingPoint;
	private float meltingPoint;
	private float heatOfVaporization;
	
	private CATEGORY category;
	
	private TIER tier = TIER.ONE;
	private STATE defaultState;
	private STATE state;
	
	public Substance(int par, String par1, String par2, String par3, float par4, float par5, float par6, float par7, CATEGORY par8, STATE par9) {
		
		this.id = par;
		this.name = par1;
		this.symbol = par2;
		this.color = par3;
		this.atomicWeight = par4;
		this.boilingPoint = par5;
		this.meltingPoint = par6;
		this.heatOfVaporization = par7;
		this.category = par8;
		this.defaultState = par9;
		
		SubstanceRegistry.addSubstance(par, this);
		ResearchRegistry.addResearch(this.id, new Research(this, Minecraft.getMinecraft().thePlayer));
		SubstanceRegistry.bindSubstance(PeriodicCraft.MODID + ":item.element" + this.name, this);
		SubstanceRegistry.addItem("element" + this.name, this);
		
	}
	
	public final Substance setTier(TIER par1) {
		this.tier = par1;
		return this;
	}
	
	public final TIER getTier() {
		return this.tier;
	}
	
	public final void setTemperature(float par1) {
		this.temperature = par1;
	}
	
	public final int getSubstanceId() {
		return this.id;
	}
	
	public final CATEGORY getCategory() {
		return this.category;
	}
	
	public final STATE getDefaultState() {
		return this.defaultState;
	}
	
	public final STATE getState() {
		return (((temperature >= this.meltingPoint && temperature < this.boilingPoint) ? (temperature < this.meltingPoint ? STATE.SOLID : STATE.LIQUID) : STATE.LIQUID));
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String getSymbol() {
		return this.symbol;
	}
	
	public final String getColor() {
		return this.color;
	}
	
	public final float getAtomicWeight() {
		return this.atomicWeight;
	}
	
	public final float getBoilingPoint() {
		return this.boilingPoint;
	}
	
	public final float getMeltingPoint() {
		return this.meltingPoint;
	}
	
	public final float getTemperature() {
		return this.temperature;
	}
	
	public final float getHeatOfVaporization() {
		return this.heatOfVaporization;
	}
	
}
