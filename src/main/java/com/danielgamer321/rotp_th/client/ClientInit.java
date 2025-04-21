package com.danielgamer321.rotp_th.client;

import com.danielgamer321.rotp_th.RotpTheHandAddon;
import com.danielgamer321.rotp_th.client.particle.CustomEraseHelper;
import com.danielgamer321.rotp_th.client.particle.EraseParticle;
import com.danielgamer321.rotp_th.client.render.entity.renderer.stand.*;
import com.danielgamer321.rotp_th.init.AddonStands;
import com.danielgamer321.rotp_th.init.InitEntities;

import com.danielgamer321.rotp_th.init.InitParticles;
import com.github.standobyte.jojo.client.particle.AirStreamParticle;
import com.github.standobyte.jojo.client.particle.HamonSparkParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpTheHandAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.THE_HAND.getEntityType(), TheHandRenderer::new);
    }
    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        mc.particleEngine.register(InitParticles.ERASE.get(), EraseParticle.Factory::new);
        CustomEraseHelper.saveSprites(mc);
    }
}
