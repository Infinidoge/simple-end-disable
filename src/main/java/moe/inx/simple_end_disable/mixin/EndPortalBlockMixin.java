package moe.inx.simple_end_disable.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin extends BlockWithEntity {

	/* Generic constructor */
	protected EndPortalBlockMixin(Settings settings) {
		super(settings);
	}

	/*
	 * Redirect the onEntityCollision() method from the EndPortalBlock class to our
	 * custom method below.
	 */
	@Redirect(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;"))
	private Entity simple_end_disable$disableEndPortal(Entity entity, ServerWorld serverWorld) {
		/* Check to see if the dimension is enabled or disabled */
		if (entity instanceof PlayerEntity) {
			/*
			 * Create and send the message to send to players if the dimension is disabled.
			 */
			Text playerMsg = Text.literal("The End is disabled").formatted(Formatting.RED, Formatting.BOLD);
			((PlayerEntity) entity).sendMessage(playerMsg, true);
		}
		return entity;
	}
}
