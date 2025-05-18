/*     */ package journeymap.client.model.block;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.IntStream;
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
/*     */ public class BlockDataArrays
/*     */ {
/*  24 */   private HashMap<MapType, Dataset> datasets = new HashMap<>(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAll() {
/*  31 */     this.datasets.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dataset get(MapType mapType) {
/*  42 */     Dataset dataset = this.datasets.get(mapType);
/*  43 */     if (dataset == null) {
/*     */       
/*  45 */       dataset = new Dataset();
/*  46 */       this.datasets.put(mapType, dataset);
/*     */     } 
/*  48 */     return dataset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Dataset
/*     */   {
/*     */     private BlockDataArrays.DataArray<Integer> ints;
/*     */ 
/*     */     
/*     */     private BlockDataArrays.DataArray<Float> floats;
/*     */ 
/*     */     
/*     */     private BlockDataArrays.DataArray<Boolean> booleans;
/*     */ 
/*     */     
/*     */     private BlockDataArrays.DataArray<Object> objects;
/*     */ 
/*     */ 
/*     */     
/*     */     Dataset() {}
/*     */ 
/*     */     
/*     */     public Dataset(MapType mapType) {}
/*     */ 
/*     */     
/*     */     public void clear() {
/*  75 */       this.ints = null;
/*  76 */       this.floats = null;
/*  77 */       this.booleans = null;
/*  78 */       this.objects = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockDataArrays.DataArray<Integer> ints() {
/*  88 */       if (this.ints == null)
/*     */       {
/*  90 */         this.ints = new BlockDataArrays.DataArray<>(() -> new Integer[16][16]);
/*     */       }
/*  92 */       return this.ints;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockDataArrays.DataArray<Float> floats() {
/* 102 */       if (this.floats == null)
/*     */       {
/* 104 */         this.floats = new BlockDataArrays.DataArray<>(() -> new Float[16][16]);
/*     */       }
/* 106 */       return this.floats;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockDataArrays.DataArray<Boolean> booleans() {
/* 116 */       if (this.booleans == null)
/*     */       {
/* 118 */         this.booleans = new BlockDataArrays.DataArray<>(() -> new Boolean[16][16]);
/*     */       }
/* 120 */       return this.booleans;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BlockDataArrays.DataArray<Object> objects() {
/* 130 */       if (this.objects == null)
/*     */       {
/* 132 */         this.objects = new BlockDataArrays.DataArray(() -> new Object[16][16]);
/*     */       }
/* 134 */       return this.objects;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DataArray<T>
/*     */   {
/* 145 */     private final HashMap<String, T[][]> map = new HashMap<>(4);
/*     */ 
/*     */ 
/*     */     
/*     */     private final Supplier<T[][]> initFn;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DataArray(Supplier<T[][]> initFn) {
/* 155 */       this.initFn = initFn;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean has(String name) {
/* 166 */       return this.map.containsKey(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T[][] get(String name) {
/* 177 */       return this.map.computeIfAbsent(name, s -> (Object[][])this.initFn.get());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get(String name, int x, int z) {
/* 190 */       return get(name)[z][x];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean set(String name, int x, int z, T value) {
/* 204 */       T[][] arr = get(name);
/* 205 */       T old = arr[z][x];
/* 206 */       arr[z][x] = value;
/* 207 */       return (value != old);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T[][] copy(String name) {
/* 218 */       T[][] src = get(name);
/* 219 */       T[][] dest = this.initFn.get();
/* 220 */       for (int i = 0; i < src.length; i++)
/*     */       {
/* 222 */         System.arraycopy(src[i], 0, dest[i], 0, (src[0]).length);
/*     */       }
/* 224 */       return dest;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void copyTo(String srcName, String dstName) {
/* 235 */       this.map.put(dstName, copy(srcName));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear(String name) {
/* 245 */       this.map.remove(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areIdentical(int[][] arr, int[][] arr2) {
/* 258 */     boolean match = true;
/* 259 */     for (int j = 0; j < arr.length; j++) {
/*     */       
/* 261 */       int[] row = arr[j];
/* 262 */       int[] row2 = arr2[j];
/* 263 */       match = IntStream.range(0, row.length).map(i -> row[i] ^ 0xFFFFFFFF | row2[i]).allMatch(n -> (n == -1));
/* 264 */       if (!match) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 269 */     return match;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\block\BlockDataArrays.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */