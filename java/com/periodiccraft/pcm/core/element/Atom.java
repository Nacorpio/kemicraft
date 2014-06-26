package com.periodiccraft.pcm.core.element;

import com.periodiccraft.pcm.core.element.Element.STATE;
import com.periodiccraft.pcm.core.registry.SubstanceRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Atom implements Cloneable {
	
	protected final int baseElectrons;
	protected final int baseNeutrons;
	protected final int baseProtons;
	
	protected int electrons;
	protected int neutrons;
	protected int protons;
	
	protected float boilingPoint;
	protected float meltingPoint;
	protected float heatOfVaporization;
	protected float density;
	protected float weight;
	
	protected int amount = 1;
	
	private STATE state;
	
	public Atom(int electrons, int neutrons, int protons, float boilingPoint, float meltingPoint, float heatOfVaporization, float density, float weight, STATE state)
	{
		this.baseElectrons = electrons;
		this.baseNeutrons = neutrons;
		this.baseProtons = protons;
		
		this.protons = electrons;
		this.neutrons = neutrons;
		this.protons = protons;
		
		this.boilingPoint = boilingPoint;
		this.meltingPoint = meltingPoint;
		this.heatOfVaporization = heatOfVaporization;
		this.density = density;
		
		this.weight = weight;
		this.state = state;
	}
	
	public Atom setAmount(int amount)
	{
		this.amount = amount;
		return this;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public final boolean isPositive() {
		return protons > electrons;
	}
	
	public final boolean isNegative() {
		return electrons > protons;
	}
	
	public int getCharge() {
		return protons - electrons;
	}
	
	public void setCharge(int charge) {
		electrons = baseElectrons;
		electrons -= charge;
		if(electrons < 0) throw new IllegalArgumentException("The number of electrons has to be > 0!");
	}
	
	public final int getElectrons() {
		return this.electrons;
	}
	
	public final int getNeutrons() {
		return this.neutrons;
	}
	
	public final int getProtons() {
		return this.protons;
	}
	
	public final float getWeight() {
		return this.weight;
	}

	@Override
	public Atom clone() {
		try {
			return (Atom)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj == null) return false;
		if(!(obj instanceof Atom))return false;
		Atom a2 = (Atom)obj;
		return a2.protons == protons && a2.electrons == electrons && a2.neutrons == neutrons;
	}
	
	@Override
	public int hashCode() {
		return protons * electrons * neutrons;
	}

	@Override
	public String toString()
	{		
		return "Atom(" + getProtons() + ", " + getNeutrons() + ", " + getElectrons() + ")";
	}
}
