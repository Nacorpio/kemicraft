package com.periodiccraft.pcm.core.element;

import com.periodiccraft.pcm.helper.BiomeTeperature;
import com.periodiccraft.pcm.helper.MoleculeTree;

public class OrganicMolecule implements IMolecule
{
	MoleculeTree moleculeTree;
	private float temperature = BiomeTeperature.getDefaultTemperature();
	private int count;
	private String name;
	
	public OrganicMolecule(int count, String name, String... structure) 
	{
		moleculeTree = new MoleculeTree(structure);
		this.count = count;
		this.name = name;
	}

	@Override
	public boolean isCompound() {
		return true;
	}

	@Override
	public Atom getFirstAtom() {
		return moleculeTree.getRoot().getValue();
	}

	@Override
	public Atom[] getAtoms() {
		return moleculeTree.getAtoms(moleculeTree.getRoot());
	}

	@Override
	public void setTemperature(float par1) {
		temperature = par1;
	}

	@Override
	public float getTemperature() {
		return temperature;
	}

	@Override
	public IMolecule setCount(int par1) {
		this.count = par1;
		return this;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public String getFormula() {
		return moleculeTree.convertToSimpleMolecule().getFormula();
	}

	@Override
	public String getObfuscatedFormula() {
		return moleculeTree.convertToSimpleMolecule().getObfuscatedFormula();
	}

	@Override
	public boolean hasName() {
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

}
