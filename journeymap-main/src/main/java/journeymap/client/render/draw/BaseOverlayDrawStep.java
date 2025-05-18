/*     */ package journeymap.client.render.draw;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Objects;
/*     */ import journeymap.api.v2.client.display.Context;
/*     */ import journeymap.api.v2.client.display.Overlay;
/*     */ import journeymap.api.v2.client.model.TextProperties;
/*     */ import journeymap.api.v2.client.util.UIState;
/*     */ import journeymap.client.render.map.Renderer;
/*     */ import net.minecraft.class_332;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public abstract class BaseOverlayDrawStep<T extends Overlay>
/*     */   implements OverlayDrawStep
/*     */ {
/*     */   public final T overlay;
/*  28 */   protected Rectangle2D.Double screenBounds = new Rectangle2D.Double();
/*  29 */   protected Point2D.Double titlePosition = null;
/*  30 */   protected Point2D.Double labelPosition = new Point2D.Double();
/*  31 */   protected UIState lastUiState = null;
/*     */   
/*     */   protected boolean dragging = false;
/*     */   
/*     */   protected boolean enabled = true;
/*     */   protected String[] labelLines;
/*     */   protected String[] titleLines;
/*     */   
/*     */   protected BaseOverlayDrawStep(T overlay) {
/*  40 */     this.overlay = overlay;
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
/*     */   protected abstract void updatePositions(Renderer paramRenderer, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawText(class_332 graphics, DrawStep.Pass pass, double xOffset, double yOffset, Renderer renderer, double fontScale, double rotation) {
/*  62 */     TextProperties textProperties = this.overlay.getTextProperties();
/*     */     
/*  64 */     if (textProperties.isActiveIn(renderer.getUIState()))
/*     */     {
/*  66 */       if (pass == DrawStep.Pass.Text) {
/*     */         
/*  68 */         if (this.labelPosition != null)
/*     */         {
/*  70 */           if (this.labelLines == null)
/*     */           {
/*  72 */             updateTextFields();
/*     */           }
/*     */           
/*  75 */           if (this.labelLines != null)
/*     */           {
/*  77 */             double x = this.labelPosition.x + xOffset;
/*  78 */             double y = this.labelPosition.y + yOffset;
/*     */             
/*  80 */             DrawUtil.drawLabels(graphics, this.labelLines, x, y, DrawUtil.HAlign.Center, DrawUtil.VAlign.Middle, 
/*  81 */                 Integer.valueOf(textProperties.getBackgroundColor()), textProperties
/*  82 */                 .getBackgroundOpacity(), 
/*  83 */                 Integer.valueOf(textProperties.getColor()), textProperties
/*  84 */                 .getOpacity(), textProperties
/*  85 */                 .getScale() * fontScale, textProperties
/*  86 */                 .hasFontShadow(), rotation);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*  91 */       } else if (pass == DrawStep.Pass.Tooltip && (renderer.getUIState()).ui != Context.UI.Minimap) {
/*     */         
/*  93 */         if (this.titlePosition != null) {
/*     */           
/*  95 */           if (this.titleLines == null)
/*     */           {
/*  97 */             updateTextFields();
/*     */           }
/*     */           
/* 100 */           if (this.titleLines != null) {
/*     */             
/* 102 */             double x = this.titlePosition.x + 5.0D + xOffset;
/* 103 */             double y = this.titlePosition.y + yOffset;
/*     */             
/* 105 */             DrawUtil.drawLabels(graphics, this.titleLines, x, y, DrawUtil.HAlign.Right, DrawUtil.VAlign.Above, 
/*     */                 
/* 107 */                 Integer.valueOf(textProperties.getBackgroundColor()), textProperties
/* 108 */                 .getBackgroundOpacity(), 
/* 109 */                 Integer.valueOf(textProperties.getColor()), textProperties
/* 110 */                 .getOpacity(), textProperties
/* 111 */                 .getScale() * fontScale, textProperties
/* 112 */                 .hasFontShadow(), rotation);
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   public boolean isOnScreen(double xOffset, double yOffset, Renderer renderer, double rotation) {
/* 128 */     if (!this.enabled)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     UIState uiState = renderer.getUIState();
/*     */ 
/*     */     
/* 136 */     if (!this.overlay.isActiveIn(uiState))
/*     */     {
/*     */       
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     if (!this.overlay.isInZoomRange(uiState))
/*     */     {
/* 144 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 148 */     boolean draggingDone = false;
/* 149 */     if (xOffset != 0.0D || yOffset != 0.0D) {
/*     */       
/* 151 */       this.dragging = true;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       draggingDone = this.dragging;
/* 156 */       this.dragging = false;
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (draggingDone || uiState.ui == Context.UI.Minimap || this.overlay.getNeedsRerender() || !Objects.equals(uiState, this.lastUiState)) {
/*     */ 
/*     */       
/* 163 */       this.lastUiState = uiState;
/* 164 */       updatePositions(renderer, rotation);
/*     */       
/* 166 */       this.overlay.clearFlagForRerender();
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (this.screenBounds == null)
/*     */     {
/* 172 */       return false;
/*     */     }
/* 174 */     return renderer.isOnScreen(this.screenBounds);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateTextFields() {
/* 179 */     if (this.labelPosition != null) {
/*     */       
/* 181 */       String labelText = this.overlay.getLabel();
/* 182 */       if (!Strings.isNullOrEmpty(labelText)) {
/*     */         
/* 184 */         this.labelLines = labelText.split("\n");
/*     */       }
/*     */       else {
/*     */         
/* 188 */         this.labelLines = null;
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     if (this.titlePosition != null) {
/*     */       
/* 194 */       String titleText = this.overlay.getTitle();
/* 195 */       if (!Strings.isNullOrEmpty(titleText)) {
/*     */         
/* 197 */         this.titleLines = titleText.split("\n");
/*     */       }
/*     */       else {
/*     */         
/* 201 */         this.titleLines = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitlePosition(@Nullable Point2D.Double titlePosition) {
/* 209 */     this.titlePosition = titlePosition;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDisplayOrder() {
/* 215 */     return this.overlay.getDisplayOrder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModId() {
/* 221 */     return this.overlay.getModId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D.Double getBounds() {
/* 227 */     return this.screenBounds;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T getOverlay() {
/* 233 */     return this.overlay;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 239 */     this.enabled = enabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\draw\BaseOverlayDrawStep.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */