/*    */ package journeymap.api.services;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import journeymap.client.event.handlers.KeyEventHandler;
/*    */ import journeymap.client.event.handlers.keymapping.KeyConflictContext;
/*    */ import journeymap.client.event.handlers.keymapping.KeyModifier;
/*    */ import journeymap.client.event.handlers.keymapping.UpdateAwareKeyBinding;
/*    */ import journeymap.client.model.block.BlockMD;
/*    */ import net.minecraft.class_1058;
/*    */ import net.minecraft.class_1087;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_3675;
/*    */ import net.minecraft.class_777;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ClientPlatformService
/*    */ {
/*    */   public static final int DEFAULT_WATER_COLOR = 4159204;
/*    */   
/*    */   class_1058 getTextureAtlasSprite(BlockMD paramBlockMD);
/*    */   
/*    */   List<class_777> getQuads(class_1087 paramclass_1087, @Nullable class_2680 paramclass_2680, @Nullable class_2350 paramclass_2350, @Nullable class_2338 paramclass_2338, class_1921 paramclass_1921);
/*    */   
/*    */   int getFluidTint(BlockMD paramBlockMD);
/*    */   
/*    */   UpdateAwareKeyBinding getKeyBinding(String paramString1, KeyConflictContext paramKeyConflictContext, KeyModifier paramKeyModifier, class_3675.class_307 paramclass_307, int paramInt, String paramString2, KeyEventHandler paramKeyEventHandler);
/*    */   
/*    */   default float getFarPlane() {
/* 34 */     return 21000.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\services\ClientPlatformService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */