/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.ritual;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import com.github.klikli_dev.occultism.common.job.SpiritJob;
import com.github.klikli_dev.occultism.common.tile.GoldenSacrificialBowlTileEntity;
import com.github.klikli_dev.occultism.registry.OccultismEntities;
import com.github.klikli_dev.occultism.registry.OccultismItems;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismSpiritJobs;
import com.github.klikli_dev.occultism.util.ItemNBTUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SummonAfritThunderWeatherRitual extends SummonSpiritRitual {
    //region Fields
    public static final ResourceLocation cowTag = new ResourceLocation("forge", "cows");
    //endregion Fields

    //region Initialization
    public SummonAfritThunderWeatherRitual() {
        super(null,
                OccultismRituals.SUMMON_AFRIT_PENTACLE.get(),
                Ingredient.fromItems(OccultismItems.BOOK_OF_BINDING_BOUND_AFRIT.get()),
                "summon_afrit_thunder_weather", 60);
        this.sacrificePredicate =
                (entity) -> EntityTypeTags.getCollection().getOrCreate(cowTag).contains(entity.getType());
    }
    //endregion Initialization

    //region Overrides
    @Override
    public void finish(World world, BlockPos goldenBowlPosition, GoldenSacrificialBowlTileEntity tileEntity,
                       PlayerEntity castingPlayer, ItemStack activationItem) {

        super.finish(world, goldenBowlPosition, tileEntity, castingPlayer, activationItem);

        //consume activation item
        ItemStack copy = activationItem.copy();
        activationItem.shrink(1);

        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, goldenBowlPosition.getX() + 0.5,
                goldenBowlPosition.getY() + 0.5, goldenBowlPosition.getZ() + 0.5, 1, 0, 0, 0, 0);

        //set up the afrit entity
        SpiritEntity spirit = OccultismEntities.AFRIT.get().create(world);
        this.prepareSpiritForSpawn(spirit, world, goldenBowlPosition, castingPlayer,
                ItemNBTUtil.getBoundSpiritName(copy));

        //set up the job
        SpiritJob job = OccultismSpiritJobs.THUNDER_WEATHER.get().create(spirit);
        job.init();
        spirit.setJob(job);
        spirit.setSpiritMaxAge(240);

        //notify players nearby and spawn
        this.spawnEntity(spirit, world);
    }

    @Override
    public boolean identify(World world, BlockPos goldenBowlPosition, ItemStack activationItem) {
        return Occultism.CONFIG.rituals.enableThunderWeatherRitual.get() &&
               super.identify(world, goldenBowlPosition, activationItem);
    }
    //endregion Overrides
}
