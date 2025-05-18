/*     */ package journeymap.client.properties;
/*     */ 
/*     */ import journeymap.api.v2.client.event.InfoSlotDisplayEvent;
/*     */ import journeymap.client.ui.minimap.Orientation;
/*     */ import journeymap.client.ui.minimap.Position;
/*     */ import journeymap.client.ui.minimap.ReticleOrientation;
/*     */ import journeymap.client.ui.minimap.Shape;
/*     */ import journeymap.client.ui.option.MapTypeProvider;
/*     */ import journeymap.client.ui.option.TimeFormat;
/*     */ import journeymap.client.ui.theme.ThemeLabelSource;
/*     */ import journeymap.common.properties.catagory.Category;
/*     */ import journeymap.common.properties.config.BooleanField;
/*     */ import journeymap.common.properties.config.EnumField;
/*     */ import journeymap.common.properties.config.FloatField;
/*     */ import journeymap.common.properties.config.IntegerField;
/*     */ import journeymap.common.properties.config.StringField;
/*     */ import journeymap.common.properties.config.custom.MinimapCaveLayerIntField;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MiniMapProperties
/*     */   extends InGameMapProperties
/*     */ {
/*  38 */   public final BooleanField enabled = new BooleanField(Category.Inherit, "jm.minimap.enable_minimap", true, true, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public final BooleanField showDayNight = new BooleanField(Category.Inherit, "jm.common.show_day_night", true, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public final StringField minimapLockedMapType = new StringField(Category.Inherit, "jm.minimap.map_type", MapTypeProvider.ANY.key(), MapTypeProvider.class, 150);
/*     */   
/*  50 */   public final IntegerField caveLayer = (IntegerField)new MinimapCaveLayerIntField(Category.Inherit, "jm.minimap.cave_slice.slider", -4, 15, 4, 1, 151);
/*  51 */   public final StringField info1Label = new StringField(Category.Inherit, "jm.minimap.info1_label.button", ThemeLabelSource.Blank.getKey(), ThemeLabelSource.class, 126);
/*  52 */   public final EnumField<InfoSlotDisplayEvent.Position> info1LabelPosition = new EnumField(Category.Inherit, "jm.minimap.info1_label.position", (Enum)InfoSlotDisplayEvent.Position.Top, 127);
/*  53 */   public final StringField info2Label = new StringField(Category.Inherit, "jm.minimap.info2_label.button", ThemeLabelSource.GameTime.getKey(), ThemeLabelSource.class, 128);
/*  54 */   public final EnumField<InfoSlotDisplayEvent.Position> info2LabelPosition = new EnumField(Category.Inherit, "jm.minimap.info2_label.position", (Enum)InfoSlotDisplayEvent.Position.Top, 129);
/*  55 */   public final StringField info3Label = new StringField(Category.Inherit, "jm.minimap.info3_label.button", ThemeLabelSource.Location.getKey(), ThemeLabelSource.class, 130);
/*  56 */   public final EnumField<InfoSlotDisplayEvent.Position> info3LabelPosition = new EnumField(Category.Inherit, "jm.minimap.info3_label.position", (Enum)InfoSlotDisplayEvent.Position.Bottom, 131);
/*  57 */   public final StringField info4Label = new StringField(Category.Inherit, "jm.minimap.info4_label.button", ThemeLabelSource.Biome.getKey(), ThemeLabelSource.class, 132);
/*  58 */   public final EnumField<InfoSlotDisplayEvent.Position> info4LabelPosition = new EnumField(Category.Inherit, "jm.minimap.info4_label.position", (Enum)InfoSlotDisplayEvent.Position.Bottom, 132);
/*     */   
/*  60 */   public final FloatField infoSlotAlpha = new FloatField(Category.Inherit, "jm.minimap.info_slot.background_alpha", 0.0F, 1.0F, 0.7F, 145);
/*     */   
/*  62 */   public final FloatField infoSlotFontScale = new FloatField(Category.Inherit, "jm.minimap.info_slot.font_scale", 0.5F, 5.0F, 1.0F, 125);
/*     */   
/*  64 */   public final StringField infoSlotTimeFormat = new StringField(Category.Inherit, "jm.common.time_format", TimeFormat.Provider.class, 146);
/*     */   
/*  66 */   public final StringField systemTimeRealFormat = new StringField(Category.Inherit, "jm.common.system_time_format", TimeFormat.Provider.class, 147);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public final EnumField<ReticleOrientation> reticleOrientation = new EnumField(Category.Inherit, "jm.minimap.reticle_orientation", (Enum)ReticleOrientation.Compass, 186);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public final EnumField<Shape> shape = new EnumField(Category.Inherit, "jm.minimap.shape", (Enum)Shape.Circle, 180);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public final IntegerField sizePercent = new IntegerField(Category.Inherit, "jm.minimap.size", 1, 100, 30, 205);
/*     */ 
/*     */ 
/*     */   
/*  85 */   public final IntegerField frameAlpha = new IntegerField(Category.Inherit, "jm.minimap.frame_alpha", 0, 100, 100, 190);
/*     */ 
/*     */ 
/*     */   
/*  89 */   public final IntegerField terrainAlpha = new IntegerField(Category.Inherit, "jm.minimap.terrain_alpha", 0, 100, 100, 195);
/*  90 */   public final FloatField backgroundAlpha = new FloatField(Category.Inherit, "jm.minimap.terrain_background_alpha", 0.0F, 1.0F, 0.8F, 200);
/*     */ 
/*     */ 
/*     */   
/*  94 */   public final EnumField<Orientation> orientation = new EnumField(Category.Inherit, "jm.minimap.orientation.button", (Enum)Orientation.North, 185);
/*     */ 
/*     */ 
/*     */   
/*  98 */   public final FloatField compassFontScale = new FloatField(Category.Inherit, "jm.minimap.compass_font_scale", 0.5F, 4.0F, 1.0F, 120);
/*     */ 
/*     */ 
/*     */   
/* 102 */   public final BooleanField showCompass = new BooleanField(Category.Inherit, "jm.minimap.show_compass", true, 10);
/*     */ 
/*     */ 
/*     */   
/* 106 */   public final BooleanField showReticle = new BooleanField(Category.Inherit, "jm.minimap.show_reticle", true, 15);
/*     */   
/* 108 */   public final FloatField positionX = new FloatField(Category.Hidden, "jm.minimap.position_x", 0.0F, 1.0F, 0.9F);
/*     */   
/* 110 */   public final FloatField positionY = new FloatField(Category.Hidden, "jm.minimap.position_y", 0.0F, 1.0F, 0.25F);
/*     */   
/* 112 */   public final BooleanField moveEffectIcons = new BooleanField(ClientCategory.MinimapPosition, "jm.hud.effects.enable", true);
/* 113 */   public final BooleanField hideEffectIcons = new BooleanField(ClientCategory.MinimapPosition, "jm.hud.effects.hide", false);
/*     */   
/* 115 */   public final IntegerField effectTranslateX = new IntegerField(Category.Hidden, "jm.hud.effects.location.button", -10000, 0, 0);
/* 116 */   public final IntegerField effectTranslateY = new IntegerField(Category.Hidden, "jm.hud.effects.location.button", 0, 10000, 0);
/* 117 */   public final BooleanField effectVertical = new BooleanField(ClientCategory.MinimapPosition, "jm.hud.effects.style.vertical", false);
/* 118 */   public final BooleanField effectReversed = new BooleanField(ClientCategory.MinimapPosition, "jm.hud.effects.style.reverse", false);
/* 119 */   public final FloatField minimapKeyMovementSpeed = new FloatField(ClientCategory.MinimapPosition, "jm.hud.minimap.key_movement_speed", 0.001F, 0.025F, 0.001F, 0.001F, 3);
/* 120 */   public final EnumField<Position> position = new EnumField(ClientCategory.MinimapPosition, "jm.minimap.position", (Enum)Position.TopRight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final transient int id;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean active = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MiniMapProperties(int id) {
/* 138 */     this.id = id;
/* 139 */     setPropertiesId(Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 145 */     return String.format("minimap%s", new Object[] { (this.id > 1) ? Integer.valueOf(this.id) : "" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 155 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 165 */     if (this.active != active) {
/*     */       
/* 167 */       this.active = active;
/* 168 */       save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 179 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends journeymap.common.properties.PropertiesBase> void updateFrom(T otherInstance) {
/* 185 */     super.updateFrom(otherInstance);
/* 186 */     if (otherInstance instanceof MiniMapProperties)
/*     */     {
/* 188 */       setActive(((MiniMapProperties)otherInstance).isActive());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 199 */     return (int)Math.max(128.0D, Math.floor(this.sizePercent.get().intValue() / 100.0D * class_310.method_1551().method_22683().method_4507()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postLoad(boolean isNew) {
/* 205 */     super.postLoad(isNew);
/*     */     
/* 207 */     if (isNew)
/*     */     {
/* 209 */       if (getId() == 1) {
/*     */         
/* 211 */         setActive(true);
/* 212 */         if (class_310.method_1551() != null && (class_310.method_1551()).field_1772.method_1726())
/*     */         {
/* 214 */           this.fontScale.set(Float.valueOf(2.0F));
/* 215 */           this.infoSlotFontScale.set(Float.valueOf(2.0F));
/* 216 */           this.compassFontScale.set(Float.valueOf(2.0F));
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 222 */         setActive(false);
/* 223 */         this.positionX.set(Float.valueOf(90.0F));
/* 224 */         this.positionY.set(Float.valueOf(25.0F));
/* 225 */         this.position.set((Enum)Position.TopRight);
/* 226 */         this.shape.set((Enum)Shape.Rectangle_Horizontal);
/* 227 */         this.frameAlpha.set(Integer.valueOf(100));
/* 228 */         this.terrainAlpha.set(Integer.valueOf(100));
/* 229 */         this.orientation.set((Enum)Orientation.North);
/* 230 */         this.reticleOrientation.set((Enum)ReticleOrientation.Compass);
/* 231 */         this.sizePercent.set(Integer.valueOf(30));
/* 232 */         if (class_310.method_1551() != null && (class_310.method_1551()).field_1772.method_1726()) {
/*     */           
/* 234 */           this.fontScale.set(Float.valueOf(2.0F));
/* 235 */           this.infoSlotFontScale.set(Float.valueOf(2.0F));
/* 236 */           this.compassFontScale.set(Float.valueOf(2.0F));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\MiniMapProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */