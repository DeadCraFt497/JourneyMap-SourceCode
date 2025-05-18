/*    */ package journeymap.api.plugins;
/*    */ 
/*    */ import journeymap.api.v2.client.IClientAPI;
/*    */ import journeymap.api.v2.client.IClientPlugin;
/*    */ import journeymap.api.v2.client.option.BooleanOption;
/*    */ import journeymap.api.v2.client.option.OptionCategory;
/*    */ import journeymap.client.feature.Feature;
/*    */ import journeymap.client.feature.FeatureManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PokemonOptionsPlugin
/*    */   implements IClientPlugin
/*    */ {
/*    */   private final OptionCategory category;
/*    */   private final BooleanOption showPokemon;
/*    */   static PokemonOptionsPlugin instance;
/*    */   
/*    */   public PokemonOptionsPlugin(String modId) {
/* 20 */     instance = this;
/* 21 */     this.category = new OptionCategory(modId, "jm.mod.addon.pokemon.options.category", "jm.mod.addon.pokemon.options.category.tooltip");
/* 22 */     this.showPokemon = new BooleanOption(this.category, "show_pokemon", "jm.mod.addon.pokemon.options.show_pokemon", Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasPokemonMod() {
/* 27 */     return (instance != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PokemonOptionsPlugin getInstance() {
/* 32 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize(IClientAPI iClientAPI) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean showPokemon() {
/* 43 */     return (((Boolean)this.showPokemon.get()).booleanValue() && FeatureManager.getInstance().isAllowed(Feature.RadarAnimals));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getModId() {
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\api\plugins\PokemonOptionsPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */