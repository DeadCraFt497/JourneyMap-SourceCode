/*    */ package journeymap.api.client.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import journeymap.api.v2.client.fullscreen.IThemeButton;
/*    */ import journeymap.api.v2.client.fullscreen.ThemeButtonDisplay;
/*    */ import journeymap.client.ui.theme.Theme;
/*    */ import journeymap.client.ui.theme.ThemeButton;
/*    */ import net.minecraft.class_2960;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public class ThemeButtonDisplayFactory
/*    */   extends ThemeFactory
/*    */   implements ThemeButtonDisplay
/*    */ {
/*    */   private final List<ThemeButton> themeButtonList;
/*    */   
/*    */   public ThemeButtonDisplayFactory(Theme theme) {
/* 19 */     super(theme);
/* 20 */     this.themeButtonList = new ArrayList<>();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IThemeButton addThemeToggleButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull class_2960 icon, boolean toggled, @NotNull IThemeButton.Action onPress) {
/* 26 */     ThemeButton button = getThemeToggleButton(labelOn, labelOff, icon, onPress);
/* 27 */     button.setToggled(Boolean.valueOf(toggled));
/* 28 */     this.themeButtonList.add(button);
/* 29 */     return (IThemeButton)button;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IThemeButton addThemeButton(@NotNull String labelOn, @NotNull String labelOff, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 35 */     ThemeButton button = getThemeButton(labelOn, labelOff, icon, onPress);
/* 36 */     this.themeButtonList.add(button);
/* 37 */     return (IThemeButton)button;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IThemeButton addThemeToggleButton(@NotNull String label, @NotNull class_2960 icon, boolean toggled, @NotNull IThemeButton.Action onPress) {
/* 43 */     ThemeButton button = getThemeToggleButton(label, icon, onPress);
/* 44 */     button.setToggled(Boolean.valueOf(toggled));
/* 45 */     this.themeButtonList.add(button);
/* 46 */     return (IThemeButton)button;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IThemeButton addThemeButton(@NotNull String label, @NotNull class_2960 icon, @NotNull IThemeButton.Action onPress) {
/* 52 */     ThemeButton button = getThemeButton(label, icon, onPress);
/* 53 */     this.themeButtonList.add(button);
/* 54 */     return (IThemeButton)button;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ThemeButton> getThemeButtonList() {
/* 59 */     return this.themeButtonList;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\ThemeButtonDisplayFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */