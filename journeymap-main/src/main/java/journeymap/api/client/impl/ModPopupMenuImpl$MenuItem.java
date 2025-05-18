/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import journeymap.api.v2.client.fullscreen.ModPopupMenu;
/*     */ import journeymap.client.Constants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuItem
/*     */ {
/*     */   private final ModPopupMenu.Action action;
/*     */   private final String label;
/*     */   private boolean autoClose = true;
/*     */   
/*     */   public MenuItem(String label, ModPopupMenu.Action action) {
/* 168 */     this.action = action;
/* 169 */     this.label = Constants.getString(label);
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuItem(String label, ModPopupMenuImpl.SubMenuAction subMenu, boolean autoClose) {
/* 174 */     this.autoClose = autoClose;
/* 175 */     this.action = subMenu;
/* 176 */     this.label = Constants.getString(label);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModPopupMenu.Action getAction() {
/* 181 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 186 */     return this.label;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoCloseable() {
/* 191 */     return this.autoClose;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModPopupMenuImpl.SubMenuAction getSubMenuAction() {
/* 196 */     return (ModPopupMenuImpl.SubMenuAction)this.action;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\ModPopupMenuImpl$MenuItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */