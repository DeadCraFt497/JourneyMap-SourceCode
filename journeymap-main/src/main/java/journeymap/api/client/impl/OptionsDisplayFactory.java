/*     */ package journeymap.api.client.impl;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import journeymap.api.services.EventBus;
/*     */ import journeymap.api.v2.client.event.RegistryEvent;
/*     */ import journeymap.api.v2.client.option.BooleanOption;
/*     */ import journeymap.api.v2.client.option.Config;
/*     */ import journeymap.api.v2.client.option.CustomIntegerOption;
/*     */ import journeymap.api.v2.client.option.CustomTextOption;
/*     */ import journeymap.api.v2.client.option.EnumOption;
/*     */ import journeymap.api.v2.client.option.FloatOption;
/*     */ import journeymap.api.v2.client.option.IntegerOption;
/*     */ import journeymap.api.v2.client.option.Option;
/*     */ import journeymap.api.v2.client.option.OptionCategory;
/*     */ import journeymap.api.v2.client.option.OptionsRegistry;
/*     */ import journeymap.api.v2.common.event.impl.JourneyMapEvent;
/*     */ import journeymap.client.properties.AddonProperties;
/*     */ import journeymap.client.properties.ClientCategory;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.properties.PropertiesBase;
/*     */ import journeymap.common.properties.catagory.Category;
/*     */ import journeymap.common.properties.config.BooleanField;
/*     */ import journeymap.common.properties.config.ConfigField;
/*     */ import journeymap.common.properties.config.CustomField;
/*     */ import journeymap.common.properties.config.EnumField;
/*     */ import journeymap.common.properties.config.FloatField;
/*     */ import journeymap.common.properties.config.IntegerField;
/*     */ 
/*     */ public class OptionsDisplayFactory
/*     */ {
/*  37 */   public static final Map<String, AddonProperties> PROPERTIES_REGISTRY = new HashMap<>();
/*  38 */   public static final Map<String, Map<String, ConfigField<?>>> MOD_FIELD_REGISTRY = new HashMap<>();
/*     */   
/*  40 */   private final List<AddonProperties> addonPropertiesList = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public OptionsDisplayFactory() {
/*  44 */     EventBus.post((JourneyMapEvent)new RegistryEvent.InfoSlotRegistryEvent(new InfoSlotFactory()));
/*  45 */     EventBus.post((JourneyMapEvent)new RegistryEvent.OptionsRegistryEvent());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(String modId, AddonProperties prop) {
/*  50 */     PROPERTIES_REGISTRY.put(modId, prop);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(String modId, Map<String, ConfigField<?>> fieldMap) {
/*  55 */     MOD_FIELD_REGISTRY.put(modId, fieldMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, ConfigField<?>> getAllFields() {
/*  60 */     return (Map<String, ConfigField<?>>)MOD_FIELD_REGISTRY.values().stream().flatMap(map -> map.entrySet().stream())
/*  61 */       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionsDisplayFactory buildAddonProperties() {
/*  67 */     OptionsRegistry.OPTION_REGISTRY.forEach((modId, map) -> {
/*     */           AddonProperties prop = new AddonProperties();
/*     */           
/*     */           Map<String, ConfigField<?>> fieldMap = new HashMap<>();
/*     */           
/*     */           map.forEach(());
/*     */           
/*     */           prop.setName(modId);
/*     */           
/*     */           prop.setFieldMap(fieldMap);
/*     */           
/*     */           this.addonPropertiesList.add(prop);
/*     */           
/*     */           register(modId, fieldMap);
/*     */           register(modId, prop);
/*     */         });
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public OptionsDisplayFactory load() {
/*  88 */     this.addonPropertiesList.forEach(PropertiesBase::load);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/*  94 */     this.addonPropertiesList.forEach(PropertiesBase::save);
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigField createField(Option option) {
/*     */     try {
/*     */       FloatField floatField;
/* 101 */       ConfigField configField = null;
/* 102 */       int sortOrder = option.getSortOrder();
/* 103 */       Category category = getCategory(option);
/* 104 */       if (option instanceof BooleanOption) { BooleanOption booleanOption = (BooleanOption)option;
/*     */         
/* 106 */         BooleanField booleanField = new BooleanField(category, option.getLabel(), ((Boolean)booleanOption.getDefaultValue()).booleanValue(), booleanOption.isMaster().booleanValue(), sortOrder);
/*     */          }
/*     */       
/* 109 */       else if (option instanceof EnumOption)
/*     */       
/* 111 */       { EnumOption enumOption = (EnumOption)option;
/* 112 */         EnumField enumField = new EnumField(category, option.getLabel(), (Enum)enumOption.getDefaultValue(), sortOrder); }
/*     */       
/* 114 */       else if (option instanceof CustomTextOption)
/*     */       
/* 116 */       { CustomTextOption customOption = (CustomTextOption)option;
/* 117 */         CustomField customField = new CustomField(category, option.getLabel(), (String)customOption.getDefaultValue(), sortOrder); }
/*     */       
/* 119 */       else if (option instanceof CustomIntegerOption)
/*     */       
/* 121 */       { CustomIntegerOption customOption = (CustomIntegerOption)option;
/* 122 */         CustomField customField = new CustomField(category, option.getLabel(), customOption.getMinValue(), customOption.getMaxValue(), (Integer)customOption.getDefaultValue(), Integer.valueOf(sortOrder), customOption.getAllowNeg()); }
/*     */       
/* 124 */       else if (option instanceof IntegerOption)
/*     */       
/* 126 */       { IntegerOption integerOption = (IntegerOption)option;
/* 127 */         IntegerField integerField = new IntegerField(category, option.getLabel(), integerOption.getMinValue(), integerOption.getMaxValue(), ((Integer)integerOption.getDefaultValue()).intValue(), sortOrder); }
/*     */       
/* 129 */       else if (option instanceof FloatOption)
/*     */       
/* 131 */       { FloatOption floatOption = (FloatOption)option;
/* 132 */         floatField = new FloatField(category, option.getLabel(), floatOption.getMinValue(), floatOption.getMaxValue(), ((Float)floatOption.getDefaultValue()).floatValue(), floatOption.getIncrementValue(), floatOption.getPrecision(), sortOrder); }
/*     */       
/*     */       else
/*     */       
/* 136 */       { throw new UnsupportedOperationException("Type not supported for " + option.getClass().getSimpleName()); }
/*     */ 
/*     */ 
/*     */       
/* 140 */       Method method = Option.class.getDeclaredMethod("setConfig", new Class[] { Config.class });
/* 141 */       method.setAccessible(true);
/* 142 */       method.invoke(option, new Object[] { floatField });
/* 143 */       return (ConfigField)floatField;
/*     */     }
/* 145 */     catch (IllegalAccessException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
/*     */       
/* 147 */       Journeymap.getLogger().error("Unable to get ConfigField from option:{}", option.getClass().getName(), e);
/*     */       
/* 149 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Category getCategory(Option option) {
/* 154 */     OptionCategory optionCategory = option.getCategory();
/* 155 */     if ("Hidden".equalsIgnoreCase(optionCategory.getLabel()))
/*     */     {
/* 157 */       return Category.Hidden;
/*     */     }
/*     */     
/* 160 */     return ClientCategory.create(optionCategory.getModId(), optionCategory.getLabel(), optionCategory.getToolTip());
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\client\impl\OptionsDisplayFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */