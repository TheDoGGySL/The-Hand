package com.danielgamer321.rotp_th.init;

import com.danielgamer321.rotp_th.RotpTheHandAddon;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, RotpTheHandAddon.MOD_ID);

    public static final RegistryObject<BasicParticleType> ERASE = PARTICLES.register("erase", () -> new BasicParticleType(false));
}
