package io.github.merchantpug.cursedorigins.entity.damage;

import io.github.merchantpug.cursedorigins.access.DamageSourceAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.*;

public class CannonBadRespawnPointDamageSource extends DamageSource {
    public CannonBadRespawnPointDamageSource() {
        super("cannonBadRespawnPoint");
        this.setScaledWithDifficulty();
        this.setExplosive();
        ((DamageSourceAccess)this).setGlassGolemSource();
    }

    public Text getDeathMessage(LivingEntity entity) {
        Text text = Texts.bracketed(new TranslatableText("death.attack.cannonBadRespawnPoint.link")).styled((style) -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=dQw4w9WgXcQ")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Very Trustworthy :)"))));
        return new TranslatableText("death.attack.cannonBadRespawnPoint.message", entity.getDisplayName(), text);
    }
}
