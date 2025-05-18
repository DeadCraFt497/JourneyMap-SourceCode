/*     */ package journeymap.client.model.block;
/*     */ 
/*     */ import journeymap.client.model.map.MapType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Dataset
/*     */ {
/*     */   private BlockDataArrays.DataArray<Integer> ints;
/*     */   private BlockDataArrays.DataArray<Float> floats;
/*     */   private BlockDataArrays.DataArray<Boolean> booleans;
/*     */   private BlockDataArrays.DataArray<Object> objects;
/*     */   
/*     */   Dataset() {}
/*     */   
/*     */   public Dataset(MapType mapType) {}
/*     */   
/*     */   public void clear() {
/*  75 */     this.ints = null;
/*  76 */     this.floats = null;
/*  77 */     this.booleans = null;
/*  78 */     this.objects = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Integer> ints() {
/*  88 */     if (this.ints == null)
/*     */     {
/*  90 */       this.ints = new BlockDataArrays.DataArray<>(() -> new Integer[16][16]);
/*     */     }
/*  92 */     return this.ints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Float> floats() {
/* 102 */     if (this.floats == null)
/*     */     {
/* 104 */       this.floats = new BlockDataArrays.DataArray<>(() -> new Float[16][16]);
/*     */     }
/* 106 */     return this.floats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Boolean> booleans() {
/* 116 */     if (this.booleans == null)
/*     */     {
/* 118 */       this.booleans = new BlockDataArrays.DataArray<>(() -> new Boolean[16][16]);
/*     */     }
/* 120 */     return this.booleans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockDataArrays.DataArray<Object> objects() {
/* 130 */     if (this.objects == null)
/*     */     {
/* 132 */       this.objects = new BlockDataArrays.DataArray(() -> new Object[16][16]);
/*     */     }
/* 134 */     return this.objects;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\block\BlockDataArrays$Dataset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */