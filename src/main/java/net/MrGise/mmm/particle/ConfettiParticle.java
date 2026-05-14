package net.MrGise.mmm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ConfettiParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected ConfettiParticle (ClientLevel level, double x, double y, double z,
                           SpriteSet sprites, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.friction = 1f;

        this.quadSize = 1f;
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

        this.sprites = sprites;
        this.pickSprite(this.sprites);
    }

    protected ConfettiParticle (ClientLevel level, double x, double y, double z,
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
            return new ConfettiParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed, 0.8f);
        }
    }

    private int spriteLoopCounterMax = 8;
    private int spriteLoopCounter = 0;
    @Override
    public void tick() {
        super.tick();
        if (this.spriteLoopCounter != this.spriteLoopCounterMax) {
            this.spriteLoopCounter++;
        } else {
            this.spriteLoopCounter = 0;
            this.pickSprite(this.sprites);
        }
    }

    private int spriteLoop = 1;
    private int spriteLoopMax = 2;
    @Override
    public void pickSprite(SpriteSet spriteSet) {
        this.setSprite(spriteSet.get(this.spriteLoop, 2));
        this.spriteLoop++;
        if (this.spriteLoop > this.spriteLoopMax) {this.spriteLoop = 1;}
    }
}
