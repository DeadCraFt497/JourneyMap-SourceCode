/*    */ package journeymap.client.model.region;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Objects;
/*    */ import java.util.StringJoiner;
/*    */ import journeymap.client.model.map.MapType;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegionKey
/*    */ {
/*    */   private final File worldDir;
/*    */   private final int regionX;
/*    */   private final int regionZ;
/*    */   private final class_5321<class_1937> dimension;
/*    */   private final String mapTypeKey;
/*    */   
/*    */   private RegionKey(File worldDir, int regionX, int regionZ, MapType mapType, class_5321<class_1937> dimension) {
/* 21 */     this.worldDir = worldDir;
/* 22 */     this.regionX = regionX;
/* 23 */     this.regionZ = regionZ;
/* 24 */     this.dimension = dimension;
/* 25 */     this.mapTypeKey = mapType.toCacheKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public static RegionKey from(RegionCoord rCoord, MapType mapType) {
/* 30 */     return new RegionKey(rCoord.worldDir, rCoord.regionX, rCoord.regionZ, mapType, rCoord.dimension);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 36 */     if (this == o)
/*    */     {
/* 38 */       return true;
/*    */     }
/* 40 */     if (o == null || getClass() != o.getClass())
/*    */     {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     RegionKey key = (RegionKey)o;
/*    */     
/* 47 */     if (this.dimension != key.dimension)
/*    */     {
/* 49 */       return false;
/*    */     }
/* 51 */     if (this.regionX != key.regionX)
/*    */     {
/* 53 */       return false;
/*    */     }
/* 55 */     if (this.regionZ != key.regionZ)
/*    */     {
/* 57 */       return false;
/*    */     }
/* 59 */     if (!Objects.equals(this.mapTypeKey, key.mapTypeKey))
/*    */     {
/* 61 */       return false;
/*    */     }
/* 63 */     return this.worldDir.equals(key.worldDir);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 69 */     int result = this.worldDir.hashCode();
/* 70 */     result = 31 * result + this.regionX;
/* 71 */     result = 31 * result + this.regionZ;
/* 72 */     result = 31 * result + this.dimension.hashCode();
/* 73 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     return (new StringJoiner(", ", RegionImageSet.Key.class.getSimpleName() + "[", "]"))
/* 80 */       .add("worldDir=" + String.valueOf(this.worldDir))
/* 81 */       .add("regionX=" + this.regionX)
/* 82 */       .add("regionZ=" + this.regionZ)
/* 83 */       .add("dimension=" + String.valueOf(this.dimension))
/* 84 */       .toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionKey.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */