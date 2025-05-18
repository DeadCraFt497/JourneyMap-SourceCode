/*    */ package journeymap.api.client.impl;
/*    */ 
/*    */ import journeymap.api.v2.client.fullscreen.IThemeButton;
/*    */ import journeymap.client.ui.theme.Theme;
/*    */ import journeymap.client.ui.theme.ThemeButton;
/*    */ import journeymap.client.ui.theme.ThemeToggle;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4185;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public abstract class ThemeFactory
/*    */ {
/*    */   protected final Theme theme;
/*    */   
/*    */   protected ThemeFactory(Theme theme) {
/* 16 */     this.theme = theme;
/*    */   }
/*    */ 
/*    */   
/*    */   public ThemeButton getThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 21 */     return (ThemeButton)new ThemeToggle(this.theme, labelOn, labelOff, icon, b -> onPress.doAction((IThemeButton)b));
/*    */   }
/*    */ 
/*    */   
/*    */   public ThemeButton getThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 26 */     return new ThemeButton(this.theme, labelOn, labelOff, icon, null, b -> onPress.doAction((IThemeButton)b));
/*    */   }
/*    */ 
/*    */   
/*    */   public ThemeButton getThemeToggleButton(@NotNull String label, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 31 */     return (ThemeButton)new ThemeToggle(this.theme, label, icon, b -> onPress.doAction((IThemeButton)b));
/*    */   }
/*    */ 
/*    */   
/*    */   public ThemeButton getThemeButton(@NotNull String label, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 36 */     return new ThemeButton(this.theme, label, icon, b -> onPress.doAction((IThemeButton)b));
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\ThemeFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */