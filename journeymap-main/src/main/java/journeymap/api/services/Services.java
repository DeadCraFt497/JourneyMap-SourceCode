/*    */ package journeymap.api.services;
/*    */ 
/*    */ import java.util.ServiceLoader;
/*    */ import journeymap.common.Journeymap;
/*    */ 
/*    */ 
/*    */ public class Services
/*    */ {
/*  9 */   public static final WebMapService WEB_MAP_SERVICE = load(WebMapService.class);
/* 10 */   public static final ClientPlatformService CLIENT_SERVICE = load(ClientPlatformService.class);
/* 11 */   public static final CommonPlatformService COMMON_SERVICE = load(CommonPlatformService.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> T load(Class<T> clazz) {
/* 16 */     T loadedService = ServiceLoader.<T>load(clazz).findFirst().orElse(null);
/* 17 */     Journeymap.getLogger().info("Loaded {} for service {}", loadedService, clazz);
/* 18 */     return loadedService;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\services\Services.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */