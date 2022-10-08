package moe.inx.simple_end_disable.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

@Mixin(EnderEyeItem.class)
public abstract class EnderEyeItemMixin extends Item {
	protected EnderEyeItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;pushEntitiesUpBeforeBlockChange(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), cancellable = true)
	public void simple_end_disable$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> ci) {
		Text playerMsg = Text.literal("The End is disabled").formatted(Formatting.RED, Formatting.BOLD);
		context.getPlayer().sendMessage(playerMsg, true);
		ci.setReturnValue(ActionResult.FAIL);
	}
}
