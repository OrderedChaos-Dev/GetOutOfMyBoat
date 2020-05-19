package getoutofmyboat;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GetOutOfMyBoat.MOD_ID)
public class GetOutOfMyBoat {
	public static final String MOD_ID = "getoutofmyboat";
	
	public GetOutOfMyBoat() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
	}
	
	private void commonSetup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void evictMob(PlayerInteractEvent.EntityInteract event) {
		Entity vehicle = event.getTarget();
		if(vehicle instanceof BoatEntity && event.getPlayer().isCrouching()) {
			BoatEntity boat = (BoatEntity)vehicle;
			List<Entity> passengers = boat.getPassengers();
			for(Entity entity : passengers) {
				if(entity instanceof PlayerEntity) {
					return;
				}
			}
			
			boat.removePassengers();
		} else if(vehicle instanceof AbstractMinecartEntity) {
			AbstractMinecartEntity minecart = (AbstractMinecartEntity)vehicle;
			List<Entity> passengers = minecart.getPassengers();
			for(Entity entity : passengers) {
				if(entity instanceof PlayerEntity) {
					return;
				}
			}
			
			minecart.removePassengers();
		}
	}
}
