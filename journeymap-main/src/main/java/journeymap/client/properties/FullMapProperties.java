/*    */ package journeymap.client.properties;
/*    */ 
/*    */ import journeymap.common.properties.catagory.Category;
/*    */ import journeymap.common.properties.config.BooleanField;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FullMapProperties
/*    */   extends InGameMapProperties
/*    */ {
/* 21 */   public final BooleanField showKeys = new BooleanField(Category.Inherit, "jm.common.show_keys", true, 3);
/* 22 */   public final BooleanField showThemeButton = new BooleanField(Category.Inherit, "jm.fullscreen.show_theme_button", true, 4);
/* 23 */   public final BooleanField showPlayerLoc = new BooleanField(Category.Inherit, "jm.common.show_player_loc", true, 1);
/* 24 */   public final BooleanField showMouseLoc = new BooleanField(Category.Inherit, "jm.common.show_mouse_loc", true, 2);
/* 25 */   public final BooleanField minimalMode = new BooleanField(Category.Inherit, "jm.common.fullscreen.minimal_mode", false, 10);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FullMapProperties() {
/* 32 */     setPropertiesId(Integer.valueOf(0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void postLoad(boolean isNew) {
/* 38 */     super.postLoad(isNew);
/*    */     
/* 40 */     if (isNew && class_310.method_1551() != null)
/*    */     {
/* 42 */       if ((class_310.method_1551()).field_1772.method_1726())
/*    */       {
/* 44 */         this.fontScale.set(Float.valueOf(2.0F));
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 52 */     return "fullmap";
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\FullMapProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */