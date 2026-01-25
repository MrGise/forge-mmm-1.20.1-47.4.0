package net.MrGise.mmm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ManaParticle extends TextureSheetParticle {
    protected ManaParticle(ClientLevel level, double x, double y, double z,
                           SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.7f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.quadSize += 0.75f;
        this.lifetime = (int) (this.random.nextFloat() * 5 + 15);

        this.setSpriteFromAge(spriteSet);
    }

    protected ManaParticle(ClientLevel level, double x, double y, double z,
                           SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, float grav) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.98f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.quadSize += 0.75f;
        this.lifetime = (int) (3.0f / (this.random.nextFloat() * 1.1f + 0.1f));

        this.setSpriteFromAge(spriteSet);

        this.gravity = grav;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class BasicProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BasicProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ManaParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
    public static class GravProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public GravProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ManaParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed, 1);
        }
    }
}
