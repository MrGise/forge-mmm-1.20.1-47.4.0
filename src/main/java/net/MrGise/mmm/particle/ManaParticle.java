package net.MrGise.mmm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ManaParticle extends TextureSheetParticle {
    protected ManaParticle(ClientLevel level, double x, double y, double z,
                           SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.quadSize += 0.75f;
        this.lifetime = 20;

        this.setSpriteFromAge(spriteSet);
    }

    protected ManaParticle(ClientLevel level, double x, double y, double z,
                           SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, float r, float g, float b) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.quadSize += 0.75f;
        this.lifetime = 20;

        this.setSpriteFromAge(spriteSet);

        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ManaParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}
