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

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.job.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismSpiritJobs {
    //region Fields
    public static IForgeRegistry<SpiritJobFactory> REGISTRY = RegistryManager.ACTIVE
                                                                      .getRegistry(SpiritJobFactory.class);
    public static DeferredRegister<SpiritJobFactory> JOBS = new DeferredRegister<>(REGISTRY, Occultism.MODID);

    public static final RegistryObject<SpiritJobFactory> LUMBERJACK = JOBS.register("lumberjack",
            () -> new SpiritJobFactory(LumberjackJob::new));

    public static final RegistryObject<SpiritJobFactory> MANAGE_MACHINE = JOBS.register("manage_machine",
            () -> new SpiritJobFactory(ManageMachineJob::new));

    //Trade jobs
    public static final RegistryObject<SpiritJobFactory> TRADE_OTHERSTONE = JOBS.register("trade_otherstone",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity, modLoc("spirit_trade/test"))));
    public static final RegistryObject<SpiritJobFactory> TRADE_OTHERWORLD_SAPLINGS = JOBS.register("trade_otherworld_saplings",
            () -> new SpiritJobFactory((entity) -> new TraderJob(entity, modLoc("spirit_trade/test"))));

    //Crushing jobs
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER1 = JOBS.register("crush_tier1",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity, 2.0f)));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER2 = JOBS.register("crush_tier2",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity, 1.0f)));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER3 = JOBS.register("crush_tier3",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity, 0.5f)));
    public static final RegistryObject<SpiritJobFactory> CRUSH_TIER4 = JOBS.register("crush_tier4",
            () -> new SpiritJobFactory((entity) -> new CrusherJob(entity, 0.2f)));
    //endregion Fields
}