/*    */ package journeymap.client.model.grid;
/*    */ 
/*    */ import journeymap.client.model.map.MapType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GridSpecs
/*    */ {
/* 15 */   public static final GridSpec DEFAULT_DAY = new GridSpec(GridSpec.Style.Squares, 0.5F, 0.5F, 0.5F, 0.5F);
/* 16 */   public static final GridSpec DEFAULT_NIGHT = new GridSpec(GridSpec.Style.Squares, 0.5F, 0.5F, 1.0F, 0.3F);
/* 17 */   public static final GridSpec DEFAULT_UNDERGROUND = new GridSpec(GridSpec.Style.Squares, 0.5F, 0.5F, 0.5F, 0.3F);
/*    */   
/*    */   private GridSpec day;
/*    */   
/*    */   private GridSpec night;
/*    */   private GridSpec underground;
/*    */   
/*    */   public GridSpecs() {
/* 25 */     this(DEFAULT_DAY.clone(), DEFAULT_NIGHT.clone(), DEFAULT_UNDERGROUND.clone());
/*    */   }
/*    */ 
/*    */   
/*    */   public GridSpecs(GridSpec day, GridSpec night, GridSpec underground) {
/* 30 */     this.day = day;
/* 31 */     this.night = night;
/* 32 */     this.underground = underground;
/*    */   }
/*    */ 
/*    */   
/*    */   public GridSpec getSpec(MapType mapType) {
/* 37 */     switch (mapType.name) {
/*    */       
/*    */       case day:
/* 40 */         return this.day;
/*    */       case night:
/* 42 */         return this.night;
/*    */       case underground:
/* 44 */         return this.underground;
/*    */     } 
/* 46 */     return this.day;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpec(MapType mapType, GridSpec newSpec) {
/* 52 */     switch (mapType.name) {
/*    */ 
/*    */       
/*    */       case day:
/* 56 */         this.day = newSpec.clone();
/*    */         return;
/*    */ 
/*    */       
/*    */       case night:
/* 61 */         this.night = newSpec.clone();
/*    */         return;
/*    */ 
/*    */       
/*    */       case underground:
/* 66 */         this.underground = newSpec.clone();
/*    */         return;
/*    */     } 
/*    */ 
/*    */     
/* 71 */     this.day = newSpec.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GridSpecs clone() {
/* 78 */     return new GridSpecs(this.day.clone(), this.night.clone(), this.underground.clone());
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateFrom(GridSpecs other) {
/* 83 */     this.day = other.day.clone();
/* 84 */     this.night = other.night.clone();
/* 85 */     this.underground = other.underground.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\grid\GridSpecs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */