/*     */ package journeymap.client.model.entity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_630;
/*     */ import net.minecraft.class_7833;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ import software.bernie.geckolib.animatable.GeoAnimatable;
/*     */ import software.bernie.geckolib.animation.state.BoneSnapshot;
/*     */ import software.bernie.geckolib.cache.object.BakedGeoModel;
/*     */ import software.bernie.geckolib.cache.object.GeoBone;
/*     */ import software.bernie.geckolib.cache.object.GeoCube;
/*     */ import software.bernie.geckolib.cache.object.GeoQuad;
/*     */ import software.bernie.geckolib.cache.object.GeoVertex;
/*     */ import software.bernie.geckolib.model.GeoModel;
/*     */ import software.bernie.geckolib.util.RenderUtil;
/*     */ 
/*     */ public class GeckoLibHelper
/*     */ {
/*     */   public static EntityHelper.IconData getIconData(class_1297 entity) {
/*  36 */     GeoModel<GeoAnimatable> geoModel = RenderUtil.getGeoModelForEntity(entity);
/*  37 */     if (geoModel == null)
/*     */     {
/*  39 */       return null;
/*     */     }
/*     */     
/*  42 */     class_2960 modelResourceLocation = geoModel.getModelResource((GeoAnimatable)entity, null);
/*  43 */     String path = modelResourceLocation.method_12832();
/*  44 */     if (path.endsWith(".geo.json")) {
/*     */       
/*  46 */       path = path.substring(0, path.length() - 9);
/*     */     }
/*  48 */     else if (path.endsWith(".json")) {
/*     */       
/*  50 */       path = path.substring(0, path.length() - 5);
/*     */     } 
/*     */     
/*  53 */     if (path.startsWith("geo/"))
/*     */     {
/*  55 */       path = path.substring(4);
/*     */     }
/*     */     
/*  58 */     path = path.replace(".", "_");
/*  59 */     path = path.replace("/", "_");
/*     */     
/*  61 */     EntityHelper.IconData iconData = new EntityHelper.IconData();
/*  62 */     iconData.type = modelResourceLocation.method_45136(path);
/*  63 */     return iconData;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<class_630> getModelParts(class_1297 entity, class_2960 mobLocation, boolean onlyHead) {
/*  68 */     GeoModel<GeoAnimatable> geoModel = RenderUtil.getGeoModelForEntity(entity);
/*  69 */     if (geoModel == null)
/*     */     {
/*  71 */       return null;
/*     */     }
/*     */     
/*  74 */     class_2960 modelResourceLocation = geoModel.getModelResource((GeoAnimatable)entity, null);
/*  75 */     BakedGeoModel model = geoModel.getBakedModel(modelResourceLocation);
/*     */     
/*  77 */     String type = mobLocation.method_12832();
/*  78 */     String type2 = type.substring(0, 1).toUpperCase() + type.substring(0, 1).toUpperCase();
/*     */     
/*  80 */     if (onlyHead) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       Optional<GeoBone> geoBone = model.getBone("head").or(() -> model.getBone("Head")).or(() -> model.getBone("heads")).or(() -> model.getBone("Heads")).or(() -> model.getBone(type)).or(() -> model.getBone(type2));
/*     */       
/*  88 */       if (geoBone.isPresent())
/*     */       {
/*  90 */         return List.of(toVanillaModelPart(geoBone.get()));
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return toVanillaModelParts(model.topLevelBones());
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<class_630> toVanillaModelParts(List<GeoBone> geoBones) {
/*  99 */     List<class_630> modelParts = new ArrayList<>();
/* 100 */     for (GeoBone bone : geoBones)
/*     */     {
/* 102 */       modelParts.add(toVanillaModelPart(bone));
/*     */     }
/* 104 */     return modelParts;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_630 toVanillaModelPart(GeoBone geoBone) {
/* 109 */     class_4587 poseStack = new class_4587();
/* 110 */     poseStack.method_22907((Quaternionfc)class_7833.field_40718.rotation(3.1415927F));
/* 111 */     return toVanillaModelPart(poseStack, geoBone);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_630 toVanillaModelPart(class_4587 poseStack, GeoBone geoBone) {
/* 116 */     poseStack.method_22903();
/* 117 */     BoneSnapshot initial = geoBone.getInitialSnapshot();
/*     */ 
/*     */     
/* 120 */     poseStack.method_46416(-initial.getOffsetX() / 16.0F, initial.getOffsetY() / 16.0F, initial.getOffsetZ() / 16.0F);
/*     */     
/* 122 */     RenderUtil.translateToPivotPoint(poseStack, geoBone);
/*     */ 
/*     */     
/* 125 */     if (initial.getRotZ() != 0.0F)
/*     */     {
/* 127 */       poseStack.method_22907((Quaternionfc)class_7833.field_40718.rotation(initial.getRotZ()));
/*     */     }
/*     */     
/* 130 */     if (initial.getRotY() != 0.0F)
/*     */     {
/* 132 */       poseStack.method_22907((Quaternionfc)class_7833.field_40716.rotation(initial.getRotY()));
/*     */     }
/*     */     
/* 135 */     if (initial.getRotX() != 0.0F)
/*     */     {
/* 137 */       poseStack.method_22907((Quaternionfc)class_7833.field_40714.rotation(initial.getRotX()));
/*     */     }
/*     */ 
/*     */     
/* 141 */     poseStack.method_22905(initial.getScaleX(), initial.getScaleY(), initial.getScaleZ());
/*     */     
/* 143 */     RenderUtil.translateAwayFromPivotPoint(poseStack, geoBone);
/*     */     
/* 145 */     List<class_630.class_628> cubes = new ArrayList<>();
/* 146 */     for (GeoCube geoCube : geoBone.getCubes())
/*     */     {
/* 148 */       cubes.add(toVanillaCube(poseStack, geoCube));
/*     */     }
/*     */     
/* 151 */     Map<String, class_630> children = Maps.newHashMap();
/* 152 */     for (GeoBone childGeoBone : geoBone.getChildBones())
/*     */     {
/* 154 */       children.put(childGeoBone.getName(), toVanillaModelPart(poseStack, childGeoBone));
/*     */     }
/*     */     
/* 157 */     poseStack.method_22909();
/*     */     
/* 159 */     return new class_630(cubes, children);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_630.class_628 toVanillaCube(class_4587 poseStack, GeoCube geoCube) {
/* 164 */     class_630.class_628 cube = new class_630.class_628(0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false, 1.0F, 1.0F, EnumSet.allOf(class_2350.class));
/*     */     
/* 166 */     poseStack.method_22903();
/* 167 */     RenderUtil.translateToPivotPoint(poseStack, geoCube);
/* 168 */     RenderUtil.rotateMatrixAroundCube(poseStack, geoCube);
/* 169 */     RenderUtil.translateAwayFromPivotPoint(poseStack, geoCube);
/* 170 */     Matrix3f normalisedPoseState = poseStack.method_23760().method_23762();
/* 171 */     Matrix4f poseState = new Matrix4f((Matrix4fc)poseStack.method_23760().method_23761());
/*     */     
/* 173 */     for (int quadIndex = 0; quadIndex < Math.min((geoCube.quads()).length, 6); quadIndex++) {
/*     */       
/* 175 */       class_630.class_618[] vertices = new class_630.class_618[4];
/* 176 */       Arrays.fill((Object[])vertices, new class_630.class_618(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
/* 177 */       cube.field_3649[quadIndex] = new class_630.class_593(vertices, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, false, class_2350.field_11036);
/*     */       
/* 179 */       GeoQuad geoQuad = geoCube.quads()[quadIndex];
/* 180 */       for (int vertexIndex = 0; vertexIndex < (geoQuad.vertices()).length; vertexIndex++) {
/*     */         
/* 182 */         GeoVertex geoVertex = geoQuad.vertices()[vertexIndex];
/* 183 */         Vector3f geoPosition = geoVertex.position();
/* 184 */         Vector4f position = poseState.transform(new Vector4f(geoPosition.x(), geoPosition.y(), geoPosition.z(), 1.0F));
/*     */         
/* 186 */         position.mul(16.0F);
/*     */         
/* 188 */         cube.field_3649[quadIndex].comp_3184()[vertexIndex] = new class_630.class_618(new Vector3f(position.x(), position.y(), position.z()), geoVertex.texU(), geoVertex.texV());
/*     */       } 
/*     */       
/* 191 */       Vector3f normal = normalisedPoseState.transform(new Vector3f((Vector3fc)geoQuad.normal()));
/* 192 */       RenderUtil.fixInvertedFlatCube(geoCube, normal);
/* 193 */       cube.field_3649[quadIndex].comp_3185().set((Vector3fc)normal);
/*     */     } 
/*     */     
/* 196 */     poseStack.method_22909();
/*     */     
/* 198 */     return cube;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\model\entity\GeckoLibHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */