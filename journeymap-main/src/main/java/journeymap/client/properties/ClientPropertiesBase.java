/*     */ package journeymap.client.properties;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import journeymap.client.Constants;
/*     */ import journeymap.client.io.FileHandler;
/*     */ import journeymap.common.log.LogFormatter;
/*     */ import journeymap.common.properties.PropertiesBase;
/*     */ import journeymap.common.properties.catagory.Category;
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
/*     */ public abstract class ClientPropertiesBase
/*     */   extends PropertiesBase
/*     */ {
/*  25 */   private static final String[] HEADERS = new String[] {
/*  26 */       "// " + Constants.getString("jm.config.file_header_1"), "// " + 
/*  27 */       Constants.getString("jm.config.file_header_2", new Object[] {
/*     */           
/*     */           Constants.CONFIG_DIR
/*  30 */         }), "// " + Constants.getString("jm.config.file_header_5", new Object[] { "http://journeymap.info/Options_Manager" })
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  41 */     return String.format("journeymap.%s.config", new Object[] { getName() });
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
/*     */   public File getFile() {
/*  53 */     if (this.sourceFile == null) {
/*     */       
/*  55 */       this.sourceFile = new File(FileHandler.getWorldConfigDir(false), getFileName());
/*  56 */       if (!this.sourceFile.canRead())
/*     */       {
/*  58 */         this.sourceFile = new File(FileHandler.StandardConfigDirectory, getFileName());
/*     */       }
/*     */     } 
/*  61 */     return this.sourceFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWorldConfig() {
/*  71 */     if (class_310.method_1551() != null) {
/*     */       
/*  73 */       File worldConfigDir = FileHandler.getWorldConfigDir(false);
/*  74 */       return (worldConfigDir != null && worldConfigDir.equals(getFile().getParentFile()));
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends PropertiesBase> void updateFrom(T otherInstance) {
/*  82 */     super.updateFrom((PropertiesBase)otherInstance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyToWorldConfig(boolean overwrite) {
/*  93 */     if (!isWorldConfig()) {
/*     */ 
/*     */       
/*     */       try {
/*  97 */         File worldConfig = getFile();
/*  98 */         if (overwrite || !worldConfig.exists())
/*     */         {
/* 100 */           save();
/* 101 */           Files.copy(this.sourceFile, worldConfig);
/* 102 */           return worldConfig.canRead();
/*     */         }
/*     */       
/* 105 */       } catch (IOException e) {
/*     */         
/* 107 */         error("Couldn't copy config to world config: " + String.valueOf(e), e);
/*     */       } 
/* 109 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 113 */     throw new IllegalStateException("Can't create World config from itself.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(boolean fix) {
/* 120 */     boolean valid = super.isValid(fix);
/* 121 */     return valid;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getHeaders() {
/* 127 */     return HEADERS;
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
/*     */   
/*     */   public Category getCategoryByName(String name) {
/* 140 */     Category category = super.getCategoryByName(name);
/* 141 */     if (category == null)
/*     */     {
/* 143 */       category = ClientCategory.valueOf(name);
/*     */     }
/* 145 */     return category;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyToStandardConfig() {
/* 155 */     if (isWorldConfig()) {
/*     */       
/*     */       try {
/*     */         
/* 159 */         save();
/* 160 */         File standardConfig = new File(FileHandler.StandardConfigDirectory, getFileName());
/* 161 */         Files.copy(this.sourceFile, standardConfig);
/* 162 */         return standardConfig.canRead();
/*     */       }
/* 164 */       catch (IOException e) {
/*     */         
/* 166 */         error("Couldn't copy config to world config: " + LogFormatter.toString(e));
/* 167 */         return false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 172 */     throw new IllegalStateException("Can't replace standard config with itself.");
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\properties\ClientPropertiesBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */