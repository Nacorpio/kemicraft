package com.periodiccraft.pcm.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class WrappedGenerator implements IWorldGenerator {

	public static class Instruction {
		
		private Block blockType;
		private int[] biomeIds = new int[]{};
		
		private int maxHeight = 64;
		private int blocksPerVein = 7;
		private int veinsPerChunk = 10;
		
		private int[] dimensions = new int[] {};
		
		private boolean overworld = true;
		private boolean nether = false;
		private boolean end = false;
		
		private boolean strict = false;
		private boolean enabled = true;
	
		/**
		 * Create a new generation instructions for the generator.
		 * @param par1 the block type to use when generating.
		 * @param par2 the maximum height the blocks will appear on (64 is surface).
		 * @param par3 the maximum amount of blocks that can be generated in a vein.
		 * @param par4 the amount of veins to generate in each chunk.
		 * @param par5 the id's of the biomes to run this instruction in.
		 */
		public Instruction(Block par1, int par2, int par3, int par4, int... par5) {
			this(par1, par2, par3, par4);
			this.biomeIds = par5;
		}
		
		/**
		 * Create a new generation instructions for the generator.
		 * @param par1 the block type to use when generating.
		 * @param par2 the maximum height the blocks will appear on (64 is surface).
		 * @param par3 the maximum amount of blocks that can be generated in a vein.
		 * @param par4 the amount of veins to generate in each chunk.
		 */
		public Instruction(Block par1, int par2, int par3, int par4) {
			this.blockType = par1;
			this.maxHeight = par2;
			this.blocksPerVein = par3;
			this.veinsPerChunk = par4;
		}
		
		/**
		 * Sets the biomes that this instruction will be run in.<br>
		 * If you want the block to generate everywhere, don't use this.
		 * @param par1 the biomes.
		 */
		public final Instruction setBiomes(int... par1) {
			this.biomeIds = par1;
			return this;
		}
		
		/**
		 * Set whether this instruction should be ran when the world generation starts.<br>
		 * Set to false if you want the instruction to be ignored by the wrapper.
		 * @param par1 the value.
		 */
		public final Instruction setEnabled(boolean par1) {
			this.enabled = par1;
			return this;
		}
		
		/**
		 * Returns the dimensions to run this instruction on. Just set to <b>null</b>.<br>
		 * if you don't have any additional dimension to generate on.
		 * @return the dimensions.
		 */
		public final int[] getDimensions() {
			return this.dimensions;
		}
		
		/**
		 * Set the dimensions to run this instruction on.
		 * @param par1 an {@link #Integer} array representing the dimension id's to run.
		 * @return the Instruction for construction conveniences. 
		 */
		public final Instruction setDimensions(int[] par1) {
			this.dimensions = par1;
			return this;
		}
		
		/**
		 * Allow the block to generate in the overworld (0).
		 * @param par1 true/false.
		 * @return the Instruction for structuring convenience.
		 */
		public final Instruction setOverworld(boolean par1) {
			this.overworld = par1;
			return this;
		}
		
		/**
		 * Allow the block to generate in the nether (-1).
		 * @param par1 true/false.
		 * @return the Instruction for structuring convenience.
		 */
		public final Instruction setNether(boolean par1) {
			this.nether = par1;
			return this;
		}
		
		/**
		 * Allow the block to generate in the end (1).
		 * @param par1 true/false.
		 * @return the Instruction for structuring convenience.
		 */
		public final Instruction setEnd(boolean par1) {
			this.end = par1;
			return this;
		}
		
		public final int[] getBiomes() {
			return biomeIds;
		}
		
		/**
		 * Whether this generator instruction is enabled.
		 * @return if the instruction is enabled.
		 */
		public final boolean isEnabled() {
			return this.enabled;
		}
		
		/**
		 * Returns the block type to use when generating.
		 * @return the block type.
		 */
		public final Block getBlockType() {
			return this.blockType;
		}
		
		/**
		 * Set whether you want the instruction to generate from max height<br>
		 * and down, or if you want it to generate on the max height ONLY.
		 * @param par1 true if you want it to generate on max height only.<br>
		 * Set to false if you want it to generate from max height and down.
		 */
		public final void setStrict(boolean par1) {
			this.strict = par1;
		}
		
		/**
		 * Returns whether the generator should generate from the max height<br>
		 * and down, or if it should generate on the max height ONLY.
		 * @return true/false.
		 */
		public final boolean isStrict() {
			return false;
		}
		
		/**
		 * The maximum height that the block can be generated on.
		 * @return the max height.
		 */
		public final int getMaxHeight() {
			return this.maxHeight;
		}
		
		/**
		 * The maximum amount of blocks that can be generated in every vein.
		 * @return the amount of blocks per vein.
		 */
		public final int getBlocksPerVein() {
			return this.blocksPerVein;
		}
		
		/**
		 * The amount of veins to generate in each chunk.
		 * @return the amount of veins per chunk.
		 */
		public final int getVeinsPerChunk() {
			return this.veinsPerChunk;
		}
		
		private final boolean containsBiome(int par1) {
			for (int var: this.biomeIds) {
				if (var == par1)
					return true;
			}
			return false;
		}
		
	}
	
	private Map<String, Instruction> generations = new HashMap<String, Instruction>();
	
	public WrappedGenerator(int par1, Instruction... par2) {
		GameRegistry.registerWorldGenerator(this, par1);
		for (Instruction var: par2) {
			if (var.getBlocksPerVein() > 0 && var.getVeinsPerChunk() > 0 && var.getMaxHeight() > 0)
				generations.put(var.getBlockType().getUnlocalizedName(), var);
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (Instruction var: this.generations.values()) {
			
			BiomeGenBase b1 = world.provider.getBiomeGenForCoords(chunkX, chunkZ);
			
			int var1 = world.provider.dimensionId;
			int var2 = b1.biomeID;
			
			if (var1 == -1) {
				if (var.nether) {	
					if (var.getBiomes().length > 0) {
						if (var.containsBiome(var2))
							generateInNether(var, world, random, chunkX * 16, chunkZ * 16);
							return;
					}
					generateInNether(var, world, random, chunkX * 16, chunkZ * 16);
				}
			} else if (var1 == 0) {
				if (var.overworld) {
					if (var.getBiomes().length > 0) {
						if (var.containsBiome(var2))
							generateInOverworld(var, world, random, chunkX * 16, chunkZ * 16);
							return;
					}
					generateInNether(var, world, random, chunkX * 16, chunkZ * 16);
				}
			} else if (var1 == 1) {
				if (var.end) {
					if (var.getBiomes().length > 0) {
						if (var.containsBiome(var2))
							generateInEnd(var, world, random, chunkX * 16, chunkZ * 16);
							return;
					}
					generateInNether(var, world, random, chunkX * 16, chunkZ * 16);
				}
			} else {
				for (int v: var.getDimensions()) {
					if (var1 == v)
						this.generateInDimension(var, world, random, chunkX * 16, chunkZ * 16);
				}
			}
			
		}
	}

	private void generateInDimension(Instruction par1, World world, Random random, int x, int z) {
		for(int k = 0; k < par1.getVeinsPerChunk(); k++) {
			int chunkX = x + random.nextInt(16);
			int chunkY = (par1.isStrict() ? par1.getMaxHeight() : random.nextInt(par1.getMaxHeight()));
			int chunkZ = z + random.nextInt(16);
			
			(new WorldGenMinable(par1.getBlockType(), par1.getBlocksPerVein())).generate(world, random, chunkX, chunkY, chunkZ);
			
		}
	}
	
	private void generateInEnd(Instruction par1, World world, Random random, int x, int z) {
		for(int k = 0; k < par1.getVeinsPerChunk(); k++) {
			int chunkX = x + random.nextInt(16);
			int chunkY = (par1.isStrict() ? par1.getMaxHeight() : random.nextInt(par1.getMaxHeight()));
			int chunkZ = z + random.nextInt(16);
			
			(new WorldGenMinable(par1.getBlockType(), par1.getBlocksPerVein())).generate(world, random, chunkX, chunkY, chunkZ);
			
		}
	}
	
	private void generateInOverworld(Instruction par1, World world, Random random, int x, int z) {
		for(int k = 0; k < par1.getVeinsPerChunk(); k++) {
			int chunkX = x + random.nextInt(16);
			int chunkY = (par1.isStrict() ? par1.getMaxHeight() : random.nextInt(par1.getMaxHeight()));
			int chunkZ = z + random.nextInt(16);
			
			(new WorldGenMinable(par1.getBlockType(), par1.getBlocksPerVein())).generate(world, random, chunkX, chunkY, chunkZ);
			
		}
	}
	
	private void generateInNether(Instruction par1, World world, Random random, int x, int z) {
		for(int k = 0; k < par1.getVeinsPerChunk(); k++) {
			int chunkX = x + random.nextInt(16);
			int chunkY = (par1.isStrict() ? par1.getMaxHeight() : random.nextInt(par1.getMaxHeight()));
			int chunkZ = z + random.nextInt(16);
			
			(new WorldGenMinable(par1.getBlockType(), par1.getBlocksPerVein())).generate(world, random, chunkX, chunkY, chunkZ);
			
		}
	}
	
}
