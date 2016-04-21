// 
// Decompiled by Procyon v0.5.30
// 

package com.rwtema.extrautils.tileentity.chests;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import com.rwtema.extrautils.ExtraUtils;
import com.rwtema.extrautils.helper.XURandom;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import java.util.Random;
import net.minecraft.block.Block;

public class BlockFullChest extends Block
{
    private Random random;
    IIcon icon_front;
    IIcon icon_side;
    IIcon icon_top;
    
    public BlockFullChest() {
        super(Material.wood);
        this.random = XURandom.getInstance();
        this.setCreativeTab((CreativeTabs)ExtraUtils.creativeTabExtraUtils);
        this.setHardness(2.5f).setStepSound(BlockFullChest.soundTypeWood);
        this.setBlockName("extrautils:chestFull");
    }
    
    public boolean isOpaqueCube() {
        return true;
    }
    
    public boolean renderAsNormalBlock() {
        return true;
    }
    
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase p_149689_5_, final ItemStack itemstack) {
        byte meta = 0;
        final int l = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (l == 0) {
            meta = 2;
        }
        if (l == 1) {
            meta = 3;
        }
        if (l == 2) {
            meta = 0;
        }
        if (l == 3) {
            meta = 1;
        }
        world.setBlockMetadataWithNotify(x, y, z, (int)meta, 3);
        if (itemstack.hasDisplayName()) {
            ((TileFullChest)world.getTileEntity(x, y, z)).func_145976_a(itemstack.getDisplayName());
        }
    }
    
    public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
        final TileFullChest tileentitychest = (TileFullChest)world.getTileEntity(x, y, z);
        if (tileentitychest != null) {
            for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1) {
                final ItemStack itemstack = tileentitychest.getStackInSlot(i1);
                if (itemstack != null) {
                    final float f = this.random.nextFloat() * 0.8f + 0.1f;
                    final float f2 = this.random.nextFloat() * 0.8f + 0.1f;
                    final float f3 = this.random.nextFloat() * 0.8f + 0.1f;
                    while (itemstack.stackSize > 0) {
                        int j1 = this.random.nextInt(21) + 10;
                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }
                        final ItemStack itemStack = itemstack;
                        itemStack.stackSize -= j1;
                        final EntityItem entityitem = new EntityItem(world, (double)(x + f), (double)(y + f2), (double)(z + f3), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        final float f4 = 0.05f;
                        entityitem.motionX = (float)this.random.nextGaussian() * f4;
                        entityitem.motionY = (float)this.random.nextGaussian() * f4 + 0.2f;
                        entityitem.motionZ = (float)this.random.nextGaussian() * f4;
                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                        world.spawnEntityInWorld((Entity)entityitem);
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (world.isRemote) {
            return true;
        }
        final TileFullChest tileentitychest = (TileFullChest)world.getTileEntity(x, y, z);
        player.displayGUIChest((IInventory)tileentitychest);
        return true;
    }
    
    public boolean hasTileEntity(final int metadata) {
        return true;
    }
    
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileFullChest();
    }
    
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    public int getComparatorInputOverride(final World world, final int x, final int y, final int z, final int p_149736_5_) {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(x, y, z));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("planks_oak");
        this.icon_front = p_149651_1_.registerIcon("extrautils:fullblockchest_front");
        this.icon_side = p_149651_1_.registerIcon("extrautils:fullblockchest_side");
        this.icon_top = p_149651_1_.registerIcon("extrautils:fullblockchest_top");
    }
    
    public IIcon getIcon(final int side, final int meta) {
        if (side <= 1) {
            return this.icon_top;
        }
        if (meta == 2 && side == 2) {
            return this.icon_front;
        }
        if (meta == 3 && side == 5) {
            return this.icon_front;
        }
        if (meta == 0 && side == 3) {
            return this.icon_front;
        }
        if (meta == 1 && side == 4) {
            return this.icon_front;
        }
        return this.icon_side;
    }
}

