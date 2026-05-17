package net.MrGise.mmm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SmallConfettiParticle extends TextureSheetParticle {
    protected SmallConfettiParticle(ClientLevel level, double x, double y, double z,
                                    SpriteSet sprites, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.friction = 1f;

        this.quadSize = 0.75f;
        this.lifetime = 100 + this.random.nextIntBetweenInclusive(-10, 10);

        this.rCol = this.random.nextBoolean() ? 1 : 0;
        this.gCol = this.random.nextBoolean() ? 1 : 0;
        this.bCol = this.random.nextBoolean() ? 1 : 0;

        if (this.rCol == 0 && this.gCol == 0 && this.bCol == 0) {
            switch (this.random.nextIntBetweenInclusive(1, 3)) {
                case 1: this.rCol = 1; break;
                case 2: this.gCol = 1; break;
                case 3: this.bCol = 1; break;
            }
        } else if (this.rCol == 1 && this.gCol == 1 && this.bCol == 1) {
            switch (this.random.nextIntBetweenInclusive(1, 3)) {
                case 1: this.rCol = 0; break;
                case 2: this.gCol = 0; break;
                case 3: this.bCol = 0; break;
            }
        }

        this.rCol = this.rCol * 0.9f;
        this.gCol = this.gCol * 0.9f;
        this.bCol = this.bCol * 0.9f;

        this.setSpriteFromAge(sprites);
    }

    protected SmallConfettiParticle(ClientLevel level, double x, double y, double z,
                                    SpriteSet sprites, double xSpeed, double ySpeed, double zSpeed, float grav) {
        this(level, x, y, z, sprites, xSpeed, ySpeed, zSpeed);

        this.gravity = grav;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class GravProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public GravProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmallConfettiParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed, 0.95f);
        }
    }
}
