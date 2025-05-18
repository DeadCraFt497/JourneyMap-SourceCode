/*    */ package journeymap.api.client.impl;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import journeymap.api.v2.client.event.RegistryEvent;
/*    */ import journeymap.client.ui.theme.ThemeLabelSource;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2588;
/*    */ import net.minecraft.class_7417;
/*    */ 
/*    */ 
/*    */ public class InfoSlotFactory
/*    */   implements RegistryEvent.InfoSlotRegistryEvent.InfoSlotRegistrar
/*    */ {
/*    */   public void register(String modId, class_2561 component, long cacheMillis, Supplier<class_2561> supplier) {
/* 15 */     String key = component.getString();
/* 16 */     class_7417 class_7417 = component.method_10851(); if (class_7417 instanceof class_2588) { class_2588 translatableContents = (class_2588)class_7417;
/*    */       
/* 18 */       key = translatableContents.method_11022(); }
/*    */     
/* 20 */     ThemeLabelSource.create(modId, key, cacheMillis, 1L, supplier);
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\InfoSlotFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */