package com.danielgamer321.rotp_th.action.stand;

import com.danielgamer321.rotp_th.client.particle.CustomEraseHelper;
import com.danielgamer321.rotp_th.entity.stand.stands.TheHandEntity;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TheHandEraseItem extends StandEntityAction {
    public static final StandPose ERASE_ITEM_POSE = new StandPose("ERASE_ITEM");

    public TheHandEraseItem(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        ItemStack itemToErase = user.getOffhandItem();
        if (itemToErase.getItem() == Items.BEDROCK || itemToErase.getItem() == Items.BARRIER) {
            return conditionMessage("not_possible_erase_article");
        }
        else if (itemToErase == null || itemToErase.isEmpty()) {
            return conditionMessage("item_offhand");
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (!world.isClientSide()) {
            TheHandEntity thehand = (TheHandEntity) standEntity;
            thehand.setErase(true);
        }
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        LivingEntity user = userPower.getUser();
        if (user != null) {
            if (!world.isClientSide()) {
                ItemStack itemToErase = itemToErase(user);
                if (!itemToErase.isEmpty()) {
                    user.getOffhandItem().shrink(1);
                }
            }
        }
        if (world.isClientSide() && userPower.getUser() != null && ClientUtil.canSeeStands()) {
            CustomEraseHelper.createEraseParticle(standEntity, Hand.MAIN_HAND);
        }
    }

    private ItemStack itemToErase(LivingEntity entity) {
        return entity.getOffhandItem();
    }

    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, @Nullable StandEntityAction newAction) {
        if (!world.isClientSide()) {
            TheHandEntity thehand = (TheHandEntity) standEntity;
            thehand.setErase(false);
        }
    }
}
