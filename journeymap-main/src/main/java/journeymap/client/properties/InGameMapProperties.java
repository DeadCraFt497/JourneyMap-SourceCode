/*    */ package journeymap.client.properties;
/*    */ 
/*    */ import journeymap.client.ui.minimap.EntityDisplay;
/*    */ import journeymap.client.ui.option.LocationFormat;
/*    */ import journeymap.common.properties.catagory.Category;
/*    */ import journeymap.common.properties.config.BooleanField;
/*    */ import journeymap.common.properties.config.EnumField;
/*    */ import journeymap.common.properties.config.FloatField;
/*    */ import journeymap.common.properties.config.StringField;
/*    */ import journeymap.common.properties.config.custom.ShowEntityNamesBooleanField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InGameMapProperties
/*    */   extends MapProperties
/*    */ {
/*    */   private Integer propertiesId;
/* 23 */   public final EnumField<EntityDisplay> playerDisplay = new EnumField(Category.Inherit, "jm.minimap.player_display", (Enum)EntityDisplay.OutlinedIcons, 165);
/* 24 */   public final FloatField selfDisplayScale = new FloatField(Category.Inherit, "jm.minimap.self_display_scale", 0.01F, 5.0F, 1.0F, 175);
/* 25 */   public final FloatField playerDisplayScale = new FloatField(Category.Inherit, "jm.minimap.player_display_scale", 0.01F, 5.0F, 1.0F, 170);
/* 26 */   public final BooleanField showPlayerHeading = new BooleanField(Category.Inherit, "jm.minimap.player_heading", true, 25);
/* 27 */   public final EnumField<EntityDisplay> mobDisplay = new EnumField(Category.Inherit, "jm.minimap.mob_display", (Enum)EntityDisplay.DotsAndOutlinedIcons, 155);
/* 28 */   public final FloatField mobDisplayScale = new FloatField(Category.Inherit, "jm.minimap.mob_display_scale", 0.01F, 5.0F, 1.0F, 160);
/* 29 */   public final BooleanField showMobHeading = new BooleanField(Category.Inherit, "jm.minimap.mob_heading", true, 20);
/* 30 */   public final BooleanField showMobs = new BooleanField(Category.Inherit, "jm.common.show_mobs", true, 30);
/* 31 */   public final BooleanField showAnimals = new BooleanField(Category.Inherit, "jm.common.show_animals", true, 35);
/* 32 */   public final BooleanField showAmbientCreatures = new BooleanField(Category.Inherit, "jm.common.show_ambient_creatures", false, 37);
/* 33 */   public final BooleanField showVillagers = new BooleanField(Category.Inherit, "jm.common.show_villagers", true, 40);
/* 34 */   public final BooleanField showPets = new BooleanField(Category.Inherit, "jm.common.show_pets", true, 45);
/* 35 */   public final BooleanField showPlayers = new BooleanField(Category.Inherit, "jm.common.show_players", true, 50);
/*    */   
/* 37 */   public final FloatField fontScale = new FloatField(Category.Inherit, "jm.common.font_scale", 0.5F, 5.0F, 1.0F, 125);
/* 38 */   public final BooleanField showWaypointLabels = new BooleanField(Category.Inherit, "jm.minimap.show_waypointlabels", true, 105);
/* 39 */   public final FloatField waypointLabelScale = new FloatField(Category.Inherit, "jm.minimap.waypointlabel_scale", 0.5F, 5.0F, 1.0F, 121);
/* 40 */   public final FloatField waypointIconScale = new FloatField(Category.Inherit, "jm.minimap.waypointicon_scale", 1.0F, 5.0F, 1.0F, 122);
/* 41 */   public final BooleanField locationFormatVerbose = new BooleanField(Category.Inherit, "jm.common.location_format_verbose", true, 110);
/* 42 */   public final StringField locationFormat = new StringField(Category.Inherit, "jm.common.location_format", LocationFormat.IdProvider.class, 140);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPropertiesId(Integer id) {
/* 50 */     this.propertiesId = id;
/* 51 */     ((ShowEntityNamesBooleanField)this.showHostileNames).setId(id.intValue());
/* 52 */     ((ShowEntityNamesBooleanField)this.showPassiveNames).setId(id.intValue());
/* 53 */     ((ShowEntityNamesBooleanField)this.showPetNames).setId(id.intValue());
/* 54 */     ((ShowEntityNamesBooleanField)this.showNpcNames).setId(id.intValue());
/* 55 */     ((ShowEntityNamesBooleanField)this.showVillagerNames).setId(id.intValue());
/* 56 */     ((ShowEntityNamesBooleanField)this.showAmbientNames).setId(id.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer getPropertiesId() {
/* 61 */     return this.propertiesId;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\InGameMapProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */