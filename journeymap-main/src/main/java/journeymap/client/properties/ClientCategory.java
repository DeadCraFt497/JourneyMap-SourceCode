/*    */ package journeymap.client.properties;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import journeymap.common.properties.catagory.Category;
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
/*    */ public class ClientCategory
/*    */ {
/* 18 */   private static int order = 1;
/*    */   
/* 20 */   public static final List<Category> values = Lists.newArrayList((Object[])new Category[] { Category.Inherit, Category.Hidden });
/* 21 */   public static final Category MiniMap1 = create("MiniMap1", "jm.config.category.minimap");
/* 22 */   public static final Category MiniMap2 = create("MiniMap2", "jm.config.category.minimap2");
/* 23 */   public static final Category FullMap = create("FullMap", "jm.config.category.fullmap");
/* 24 */   public static final Category WebMap = create("WebMap", "jm.config.category.webmap");
/* 25 */   public static final Category Waypoint = create("Waypoint", "jm.config.category.waypoint");
/* 26 */   public static final Category WaypointBeacon = create("WaypointBeacon", "jm.config.category.waypoint_beacons");
/* 27 */   public static final Category Cartography = create("Cartography", "jm.config.category.cartography");
/* 28 */   public static final Category MapFilters = create("MapFilters", "jm.config.category.map_filters");
/* 29 */   public static final Category Advanced = create("Advanced", "jm.config.category.advanced");
/* 30 */   public static final Category AdvancedMapRendering = create("AdvancedMapRendering", "jm.config.category.advanced.rendering");
/* 31 */   public static final Category MinimapPosition = create("Position", "jm.config.category.minimap_position", true);
/*    */ 
/*    */   
/*    */   public static Category create(String name, String key) {
/* 35 */     return create(name, key, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Category create(String name, String key, boolean unique) {
/* 40 */     Category cat = new Category(name, order++, key, unique);
/* 41 */     values.add(cat);
/* 42 */     return cat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Category create(String name, String key, String tooltip) {
/* 47 */     Category cat = new Category(name, order++, key, tooltip);
/* 48 */     values.add(cat);
/* 49 */     return cat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Category valueOf(String name) {
/* 54 */     for (Category category : values) {
/*    */       
/* 56 */       if (category.getName().equalsIgnoreCase(name))
/*    */       {
/* 58 */         return category;
/*    */       }
/*    */     } 
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\ClientCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */