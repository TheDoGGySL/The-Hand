package com.danielgamer321.rotp_th.client.particle;

import com.danielgamer321.rotp_th.init.InitParticles;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.particle.custom.EntityPosParticle;
import com.github.standobyte.jojo.util.mc.reflection.ClientReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomEraseHelper {

    private static final Map<ResourceLocation, IAnimatedSprite> SPRITE_SETS = new HashMap<>();

    public static void saveSprites(Minecraft mc) {
        Map<ResourceLocation, ? extends IAnimatedSprite> spritesMap = ClientReflection.getSpriteSets(mc.particleEngine);
        CustomEraseHelper.saveSprites(spritesMap, InitParticles.ERASE.get());
    }

    public static IAnimatedSprite getSavedSpriteSet(ParticleType<?> particleType) {
        return SPRITE_SETS.get(ForgeRegistries.PARTICLE_TYPES.getKey(particleType));
    }

    private static void saveSprites(Map<ResourceLocation, ? extends IAnimatedSprite> sprites, ParticleType<?> particleType) {
        ResourceLocation key = ForgeRegistries.PARTICLE_TYPES.getKey(particleType);
        SPRITE_SETS.put(key, sprites.get(key));
    }


    public static boolean createEraseParticle(LivingEntity entity, Hand hand) {
        if (!ClientUtil.canSeeStands()) return false;

        EntityPosParticle particle = EraseHandParticle.createCustomParticle((ClientWorld) entity.level, entity, hand);
        return addParticle(particle, particle.getPos(), false, false);
    }


    public static boolean addParticle(Particle particle, Vector3d particlePos, boolean overrideLimiter, boolean alwaysVisible) {
        Minecraft mc = Minecraft.getInstance();
        ActiveRenderInfo activerenderinfo = mc.gameRenderer.getMainCamera();
        if (activerenderinfo.isInitialized() && mc.particleEngine != null && activerenderinfo.getPosition().distanceToSqr(particlePos) < 1024.0D) {
            if (alwaysVisible || calculateParticleLevel(mc, mc.level, alwaysVisible) != ParticleStatus.MINIMAL) {
                mc.particleEngine.add(particle);
                return true;
            }
        }
        return false;
    }

    private static ParticleStatus calculateParticleLevel(Minecraft mc, ClientWorld world, boolean overrideLimiter) {
        ParticleStatus status = mc.options.particles;
        if (overrideLimiter && status == ParticleStatus.MINIMAL && world.random.nextInt(10) == 0) {
            status = ParticleStatus.DECREASED;
        }

        if (status == ParticleStatus.DECREASED && world.random.nextInt(3) == 0) {
            status = ParticleStatus.MINIMAL;
        }

        return status;
    }
}
