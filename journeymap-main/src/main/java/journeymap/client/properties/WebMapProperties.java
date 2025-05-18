/*    */ package journeymap.client.properties;
/*    */ 
/*    */ import journeymap.common.properties.config.BooleanField;
/*    */ import journeymap.common.properties.config.CustomField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WebMapProperties
/*    */   extends ClientPropertiesBase
/*    */ {
/* 17 */   public final BooleanField enabled = new BooleanField(ClientCategory.WebMap, "jm.webmap.enable", false, true);
/* 18 */   public final CustomField port = new CustomField(ClientCategory.WebMap, "jm.advanced.port", Integer.valueOf(80), Integer.valueOf(65535), Integer.valueOf(8080), Boolean.valueOf(false));
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 23 */     return "webmap";
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\WebMapProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */