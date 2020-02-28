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

package com.github.klikli_dev.occultism.datagen;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.OccultismBlocks;
import com.github.klikli_dev.occultism.common.block.ChalkGlyphBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

public class StandardBlockStateProvider extends BlockStateProvider {

    //region Initialization
    public StandardBlockStateProvider(DataGenerator gen,
                                      ExistingFileHelper exFileHelper) {
        super(gen, Occultism.MODID, exFileHelper);
    }
    //endregion Initialization

    //region Overrides
    @Override
    protected void registerStatesAndModels() {
        generateChalkGlyphBlockState();
    }
    //endregion Overrides

    //region Methods
    protected void generateChalkGlyphBlockState() {
        //get existing chalk glyph model.
        ModelFile.ExistingModelFile parent = models().getExistingFile(modLoc("block/chalk_glyph"));
        getVariantBuilder(OccultismBlocks.CHALK_GLYPH_WHITE.get())
                .forAllStates(state -> {
                            //this is called for every state combination
                            //create a child model for each glyph texture option
                            int sign = state.get(ChalkGlyphBlock.SIGN);
                            ModelFile subModel = models().getBuilder("block/chalk_glyph/" + sign).parent(parent)
                                                         .texture("#texture", modLoc("block/chalk_glyph/" + sign));

                            return ConfiguredModel.builder()
                                           //load the child model
                                           .modelFile(subModel)
                                           //
                                           .rotationY((int) state.get(BlockStateProperties.HORIZONTAL_FACING)
                                                                    .getHorizontalAngle())
                                           .build();
                        });
    }
    //endregion Methods

}
