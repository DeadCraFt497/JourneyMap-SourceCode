/*    */ package journeymap.client.properties;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import journeymap.api.client.impl.OptionsDisplayFactory;
/*    */ import journeymap.client.io.FileHandler;
/*    */ import journeymap.common.properties.catagory.Category;
/*    */ import journeymap.common.properties.config.ConfigField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddonProperties
/*    */   extends ClientPropertiesBase
/*    */ {
/*    */   private String name;
/*    */   private Map<String, ConfigField<?>> fields;
/*    */   
/*    */   public Category getParentCategory() {
/* 24 */     Category category = ClientCategory.valueOf("mod_" + this.name);
/* 25 */     if (category == null)
/*    */     {
/* 27 */       category = ClientCategory.create("mod_" + this.name, this.name);
/*    */     }
/* 29 */     return category;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFileName() {
/* 35 */     return String.format("addon.%s.config", new Object[] { getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public File getFile() {
/* 41 */     if (this.sourceFile == null) {
/*    */       
/* 43 */       File path = new File(FileHandler.StandardConfigDirectory, "addons");
/* 44 */       this.sourceFile = new File(path, getFileName());
/*    */     } 
/*    */     
/* 47 */     return this.sourceFile;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 58 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public AddonProperties setFieldMap(Map<String, ConfigField<?>> fields) {
/* 63 */     this.fields = fields;
/* 64 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, ConfigField<?>> getConfigFields() {
/* 71 */     if (this.configFields == null) {
/*    */       
/* 73 */       this.fields = (this.fields == null) ? (Map<String, ConfigField<?>>)OptionsDisplayFactory.MOD_FIELD_REGISTRY.get(this.name) : this.fields;
/* 74 */       this.fields.forEach((name, field) -> {
/*    */             field.setOwner(name, this);
/*    */             
/*    */             Category category = field.getCategory();
/*    */             if (category != null) {
/*    */               this.categories.add(category);
/*    */             }
/*    */           });
/* 82 */       this.configFields = Collections.unmodifiableMap(this.fields);
/*    */     } 
/* 84 */     return this.configFields;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\AddonProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */