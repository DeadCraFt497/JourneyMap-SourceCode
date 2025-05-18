/*    */ package journeymap.client.waypoint;
/*    */ 
/*    */ import java.util.TreeSet;
/*    */ import journeymap.client.texture.IgnSkin;
/*    */ import journeymap.common.helper.DimensionHelper;
/*    */ import journeymap.common.waypoint.WaypointIcon;
/*    */ import journeymap.common.waypoint.WaypointPos;
/*    */ import journeymap.common.waypoint.WaypointSettings;
/*    */ import net.minecraft.class_1043;
/*    */ import net.minecraft.class_1657;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerPoint
/*    */   extends ClientWaypointImpl
/*    */ {
/*    */   private final class_1657 player;
/*    */   
/*    */   public PlayerPoint(class_1657 player) {
/* 20 */     super(player
/* 21 */         .method_5476().getString(), new WaypointPos(player
/* 22 */           .method_24515(), 
/* 23 */           DimensionHelper.getDimKeyName(player.method_37908().method_27983())), 
/* 24 */         Integer.valueOf(-1), new WaypointSettings(), "player", new TreeSet<>(), new WaypointIcon(), "player_icon_display");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getIconColor() {
/* 37 */     return Integer.valueOf(-1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 43 */     return this.player.method_5476().getString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getX() {
/* 49 */     return this.player.method_24515().method_10263();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getZ() {
/* 55 */     return this.player.method_24515().method_10260();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getY() {
/* 61 */     return this.player.method_24515().method_10264();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInPlayerDimension() {
/* 67 */     return super.isInPlayerDimension();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasCustomTexture() {
/* 73 */     return super.hasCustomTexture();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasCustomIconColor() {
/* 79 */     return super.hasCustomIconColor();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public class_1043 getTexture() {
/* 85 */     return IgnSkin.getFace(this.player.method_7334());
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\PlayerPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */