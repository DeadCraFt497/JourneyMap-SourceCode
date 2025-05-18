/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import journeymap.common.Journeymap;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParsedWaypoint
/*     */   extends Record
/*     */ {
/*     */   private final String name;
/*     */   private final String dim;
/*     */   private final Integer x;
/*     */   private final int y;
/*     */   private final Integer z;
/*     */   private final boolean showDeviation;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #291	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #291	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #291	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public ParsedWaypoint(String name, String dim, Integer x, int y, Integer z, boolean showDeviation) {
/* 291 */     this.name = name; this.dim = dim; this.x = x; this.y = y; this.z = z; this.showDeviation = showDeviation; } public String name() { return this.name; } public String dim() { return this.dim; } public Integer x() { return this.x; } public int y() { return this.y; } public Integer z() { return this.z; } public boolean showDeviation() { return this.showDeviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String xaerosDimParser(String dim) {
/* 305 */     dim = dim.replace("Internal-", "").replace("-waypoints", "").replace("dim%", "").replace("$", ":");
/* 306 */     if ("overworld".equals(dim) || "the-nether".equals(dim) || "the-end".equals(dim))
/*     */     {
/* 308 */       dim = "minecraft:" + dim;
/*     */     }
/*     */     
/* 311 */     if ("External".equals(dim))
/*     */     {
/* 313 */       dim = "minecraft:overworld";
/*     */     }
/* 315 */     dim = dim.replace("-", "_");
/*     */     
/* 317 */     return dim;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ParsedWaypoint parse(String string) {
/* 322 */     String name = null;
/* 323 */     String raw = string.replaceAll("[\\[\\]]", "");
/* 324 */     if (string.startsWith("xaero-waypoint:")) {
/*     */       
/* 326 */       String[] split = string.split(":");
/* 327 */       String dimName = xaerosDimParser(split[9]);
/* 328 */       name = split[1].replace("gui.", "");
/* 329 */       int i = Integer.parseInt(split[3]);
/* 330 */       int j = Integer.parseInt(split[4].replace("~", "63"));
/* 331 */       int k = Integer.parseInt(split[5]);
/*     */       
/* 333 */       return new ParsedWaypoint(name, dimName, Integer.valueOf(i), j, Integer.valueOf(k), false);
/*     */     } 
/*     */     
/* 336 */     String[] nameMatch = StringUtils.substringsBetween(string, "\"", "\"");
/* 337 */     if (nameMatch != null && nameMatch.length > 0)
/*     */     {
/* 339 */       name = nameMatch[0];
/*     */     }
/*     */     
/* 342 */     Integer x = null;
/* 343 */     int y = 63;
/* 344 */     Integer z = null;
/* 345 */     String dim = "minecraft:overworld";
/*     */     
/* 347 */     boolean showDeviation = false;
/* 348 */     for (String part : raw.split(",")) {
/*     */       
/* 350 */       if (part.contains(":")) {
/*     */         
/* 352 */         String[] prop = part.split(":");
/* 353 */         if (prop.length == 2 || (prop.length == 3 && part.contains("dim:"))) {
/*     */           
/* 355 */           String key = prop[0].trim().toLowerCase();
/* 356 */           String val = prop[1].trim();
/*     */           
/*     */           try {
/* 359 */             if ("x".equals(key)) {
/*     */               
/* 361 */               x = Integer.valueOf(Integer.parseInt(val));
/*     */             }
/* 363 */             else if ("y".equals(key)) {
/*     */               
/* 365 */               y = Integer.parseInt(val);
/*     */             }
/* 367 */             else if ("z".equals(key)) {
/*     */               
/* 369 */               z = Integer.valueOf(Integer.parseInt(val));
/*     */             }
/* 371 */             else if ("dim".equals(key)) {
/*     */               
/* 373 */               dim = val + ":" + val;
/*     */             }
/* 375 */             else if ("name".equals(key)) {
/*     */               
/* 377 */               if (name == null)
/*     */               {
/* 379 */                 name = val.replace("\"", "");
/*     */               }
/*     */             } 
/* 382 */             if ("showDeviation".equals(key))
/*     */             {
/* 384 */               showDeviation = Boolean.parseBoolean(val);
/*     */             }
/*     */           }
/* 387 */           catch (Exception e) {
/*     */             
/* 389 */             Journeymap.getLogger().warn("Bad format in waypoint text part: " + part + ": " + String.valueOf(e));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 394 */     if (name == null)
/*     */     {
/* 396 */       name = "" + x + "," + x;
/*     */     }
/* 398 */     return new ParsedWaypoint(name, dim, x, y, z, showDeviation);
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\WaypointParser$ParsedWaypoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */