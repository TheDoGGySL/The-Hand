package com.danielgamer321.rotp_th.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EraseParticle extends SpriteTexturedParticle {

    private static final float PARTICLE_SCALE = 2.0F;
    private static final int MAX_LIFETIME = 10;
    private final IAnimatedSprite spriteSet;

    protected EraseParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite spriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.spriteSet = spriteSet;
        this.setSize(0.2F, 0.2F);
        this.scale(PARTICLE_SCALE);
        this.lifetime = MAX_LIFETIME;
        this.alpha = 1.0F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();
        this.alpha = 1.0F - (float)this.age / (float)this.lifetime;
        this.setSpriteFromAge(this.spriteSet);
        this.scale(PARTICLE_SCALE);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private static Factory instance;
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
            instance = this;
        }

        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new EraseParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }

        public static IAnimatedSprite getSprite() {
            return instance.spriteSet;
        }
    }
}
