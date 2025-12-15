package tk.jandev.donutauctions.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.jandev.donutauctions.DonutAuctions;
import tk.jandev.donutauctions.scraper.cache.ItemCache;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract int getCount();

    @Inject(
        method = "appendTooltip",
        at = @At("TAIL")
    )
    private void donutauctions$appendAfterLore(
            Item.TooltipContext context,
            PlayerEntity player,
            TooltipType type,
            List<Text> lines,
            CallbackInfo ci
    ) {
        ItemStack stack = (ItemStack) (Object) this;

        if (DonutAuctions.getInstance().shouldRenderItem(stack)) {
            lines.add(
                ItemCache.getInstance()
                        .getPrice(stack)
                        .getMessage(getCount())
            );
        }
    }
}
