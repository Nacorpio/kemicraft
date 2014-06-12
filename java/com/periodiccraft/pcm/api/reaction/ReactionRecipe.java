package com.periodiccraft.pcm.api.reaction;

import com.periodiccraft.pcm.core.element.IMolecule;
import com.periodiccraft.pcm.core.element.Element;

public class ReactionRecipe implements IReactionRecipe {

	private IMolecule[] input1;
	private IMolecule[] input2;
	
	private IMolecule[] output;
	
	public ReactionRecipe(IMolecule[] par1, IMolecule[] par2, IMolecule... par3) {
		this.input1 = par1;
		this.input2 = par2;
		this.output = par3;
	}
	
	@Override
	public IMolecule[] getInputOne() {
		return this.input1;
	}

	
	
	@Override
	public IMolecule[] getInputTwo() {
		return this.input2;
	}

	@Override
	public IMolecule[] getProduct() {
		return this.output;
	}

}
