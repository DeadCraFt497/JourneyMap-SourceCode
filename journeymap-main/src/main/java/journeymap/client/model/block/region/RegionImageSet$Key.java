/*     */ package journeymap.client.model.region;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.StringJoiner;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_5321;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Key
/*     */ {
/*     */   private final File worldDir;
/*     */   private final int regionX;
/*     */   private final int regionZ;
/*     */   private final class_5321<class_1937> dimension;
/*     */   
/*     */   private Key(File worldDir, int regionX, int regionZ, class_5321<class_1937> dimension) {
/* 196 */     this.worldDir = worldDir;
/* 197 */     this.regionX = regionX;
/* 198 */     this.regionZ = regionZ;
/* 199 */     this.dimension = dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Key from(RegionCoord rCoord) {
/* 204 */     return new Key(rCoord.worldDir, rCoord.regionX, rCoord.regionZ, rCoord.dimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 210 */     if (this == o)
/*     */     {
/* 212 */       return true;
/*     */     }
/* 214 */     if (o == null || getClass() != o.getClass())
/*     */     {
/* 216 */       return false;
/*     */     }
/*     */     
/* 219 */     Key key = (Key)o;
/*     */     
/* 221 */     if (this.dimension != key.dimension)
/*     */     {
/* 223 */       return false;
/*     */     }
/* 225 */     if (this.regionX != key.regionX)
/*     */     {
/* 227 */       return false;
/*     */     }
/* 229 */     if (this.regionZ != key.regionZ)
/*     */     {
/* 231 */       return false;
/*     */     }
/* 233 */     if (!this.worldDir.equals(key.worldDir))
/*     */     {
/* 235 */       return false;
/*     */     }
/*     */     
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 244 */     int result = this.worldDir.hashCode();
/* 245 */     result = 31 * result + this.regionX;
/* 246 */     result = 31 * result + this.regionZ;
/* 247 */     result = 31 * result + this.dimension.hashCode();
/* 248 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 254 */     return (new StringJoiner(", ", Key.class.getSimpleName() + "[", "]"))
/* 255 */       .add("worldDir=" + String.valueOf(this.worldDir))
/* 256 */       .add("regionX=" + this.regionX)
/* 257 */       .add("regionZ=" + this.regionZ)
/* 258 */       .add("dimension=" + String.valueOf(this.dimension))
/* 259 */       .toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\region\RegionImageSet$Key.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */