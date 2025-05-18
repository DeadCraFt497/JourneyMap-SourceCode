/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import journeymap.api.services.EventBus;
/*     */ import journeymap.api.v2.client.event.EntityRegistrationEvent;
/*     */ import journeymap.api.v2.common.event.ClientEventRegistry;
/*     */ import journeymap.api.v2.common.event.impl.JourneyMapEvent;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1314;
/*     */ import net.minecraft.class_1421;
/*     */ import net.minecraft.class_1427;
/*     */ import net.minecraft.class_1429;
/*     */ import net.minecraft.class_1480;
/*     */ import net.minecraft.class_1646;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityRegistrationFactory
/*     */ {
/*     */   private EntityRegistrationEvent event;
/*     */   private List<Class<? extends class_1297>> passives;
/*     */   private List<Class<? extends class_1297>> hostiles;
/*     */   private List<Class<? extends class_1297>> villagers;
/*     */   private List<Class<? extends class_1297>> ambient;
/*     */   private List<Class<? extends class_1297>> entities;
/*     */   private static EntityRegistrationFactory instance;
/*     */   
/*     */   public static void init() {
/*  35 */     prepare();
/*  36 */     ClientEventRegistry.ENTITY_REGISTRATION_EVENT.subscribe("journeymap", EntityRegistrationFactory::register);
/*  37 */     EventBus.post((JourneyMapEvent)(getInstance()).event);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void prepare() {
/*  42 */     EntityRegistrationFactory factory = getInstance();
/*  43 */     Map<EntityRegistrationEvent.Type, List<Class<? extends class_1297>>> entityClasses = new HashMap<>();
/*  44 */     entityClasses.put(EntityRegistrationEvent.Type.PASSIVE, new ArrayList<>());
/*  45 */     entityClasses.put(EntityRegistrationEvent.Type.HOSTILE, new ArrayList<>());
/*  46 */     entityClasses.put(EntityRegistrationEvent.Type.VILLAGER, new ArrayList<>());
/*  47 */     entityClasses.put(EntityRegistrationEvent.Type.AMBIENT, new ArrayList<>());
/*  48 */     entityClasses.put(EntityRegistrationEvent.Type.ENTITY, new ArrayList<>());
/*  49 */     factory.event = new EntityRegistrationEvent(entityClasses);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void register(EntityRegistrationEvent event) {
/*  54 */     event.addHostileEntity(class_1314.class)
/*  55 */       .addVillagerEntity(class_1646.class)
/*  56 */       .addPassiveEntity(class_1429.class)
/*  57 */       .addPassiveEntity(class_1427.class)
/*  58 */       .addPassiveEntity(class_1480.class)
/*  59 */       .addPassiveEntity(class_1314.class)
/*  60 */       .addAmbientEntity(class_1421.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityRegistrationFactory getInstance() {
/*  66 */     if (instance == null)
/*     */     {
/*  68 */       instance = new EntityRegistrationFactory();
/*     */     }
/*  70 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Class<? extends class_1297>> getPassives() {
/*  75 */     if (this.passives == null)
/*     */     {
/*  77 */       this.passives = this.event.getEntityClasses(EntityRegistrationEvent.Type.PASSIVE);
/*     */     }
/*  79 */     return this.passives;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Class<? extends class_1297>> getHostiles() {
/*  84 */     if (this.hostiles == null)
/*     */     {
/*  86 */       this.hostiles = this.event.getEntityClasses(EntityRegistrationEvent.Type.HOSTILE);
/*     */     }
/*  88 */     return this.hostiles;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Class<? extends class_1297>> getVillagers() {
/*  93 */     if (this.villagers == null)
/*     */     {
/*  95 */       this.villagers = this.event.getEntityClasses(EntityRegistrationEvent.Type.VILLAGER);
/*     */     }
/*  97 */     return this.villagers;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Class<? extends class_1297>> getAmbient() {
/* 102 */     if (this.ambient == null)
/*     */     {
/* 104 */       this.ambient = this.event.getEntityClasses(EntityRegistrationEvent.Type.AMBIENT);
/*     */     }
/* 106 */     return this.ambient;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Class<? extends class_1297>> getEntities() {
/* 111 */     if (this.entities == null)
/*     */     {
/* 113 */       this.entities = this.event.getEntityClasses(EntityRegistrationEvent.Type.ENTITY);
/*     */     }
/* 115 */     return this.entities;
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\EntityRegistrationFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */