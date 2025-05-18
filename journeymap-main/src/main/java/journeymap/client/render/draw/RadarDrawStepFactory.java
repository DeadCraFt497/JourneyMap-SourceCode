/*     */ package journeymap.client.render.draw;
/*     */ 
/*     */ import java.util.List;
/*     */ import journeymap.api.plugins.PokemonOptionsPlugin;
/*     */ import journeymap.client.model.entity.EntityDTO;
/*     */ import journeymap.client.properties.InGameMapProperties;
/*     */ import journeymap.client.render.map.Renderer;
/*     */ import journeymap.common.util.ReflectionHelper;
/*     */ import net.minecraft.class_1297;
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
/*     */ public class RadarDrawStepFactory
/*     */ {
/*     */   public List<DrawStep> prepareSteps(List<EntityDTO> entityDTOs, Renderer renderer, InGameMapProperties mapProperties) {
/*     */     // Byte code:
/*     */     //   0: aload_3
/*     */     //   1: getfield showAnimals : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   4: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   7: invokevirtual booleanValue : ()Z
/*     */     //   10: istore #4
/*     */     //   12: aload_3
/*     */     //   13: getfield showAmbientCreatures : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   16: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   19: invokevirtual booleanValue : ()Z
/*     */     //   22: istore #5
/*     */     //   24: aload_3
/*     */     //   25: getfield showPets : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   28: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   31: invokevirtual booleanValue : ()Z
/*     */     //   34: istore #6
/*     */     //   36: aload_3
/*     */     //   37: getfield showVillagers : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   40: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   43: invokevirtual booleanValue : ()Z
/*     */     //   46: istore #7
/*     */     //   48: aload_3
/*     */     //   49: getfield mobDisplay : Ljourneymap/common/properties/config/EnumField;
/*     */     //   52: invokevirtual get : ()Ljava/lang/Enum;
/*     */     //   55: checkcast journeymap/client/ui/minimap/EntityDisplay
/*     */     //   58: astore #8
/*     */     //   60: aload_3
/*     */     //   61: getfield playerDisplay : Ljourneymap/common/properties/config/EnumField;
/*     */     //   64: invokevirtual get : ()Ljava/lang/Enum;
/*     */     //   67: checkcast journeymap/client/ui/minimap/EntityDisplay
/*     */     //   70: astore #9
/*     */     //   72: aload_3
/*     */     //   73: getfield showMobHeading : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   76: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   79: invokevirtual booleanValue : ()Z
/*     */     //   82: istore #10
/*     */     //   84: aload_3
/*     */     //   85: getfield showPlayerHeading : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   88: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   91: invokevirtual booleanValue : ()Z
/*     */     //   94: istore #11
/*     */     //   96: aload_3
/*     */     //   97: getfield showTeamNames : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   100: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   103: invokevirtual booleanValue : ()Z
/*     */     //   106: istore #12
/*     */     //   108: aload_3
/*     */     //   109: getfield showPlayerNames : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   112: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   115: invokevirtual booleanValue : ()Z
/*     */     //   118: istore #13
/*     */     //   120: aload_3
/*     */     //   121: getfield mobDisplayScale : Ljourneymap/common/properties/config/FloatField;
/*     */     //   124: invokevirtual get : ()Ljava/lang/Float;
/*     */     //   127: invokevirtual floatValue : ()F
/*     */     //   130: fstore #14
/*     */     //   132: aload_3
/*     */     //   133: getfield playerDisplayScale : Ljourneymap/common/properties/config/FloatField;
/*     */     //   136: invokevirtual get : ()Ljava/lang/Float;
/*     */     //   139: invokevirtual floatValue : ()F
/*     */     //   142: fstore #15
/*     */     //   144: new java/util/ArrayList
/*     */     //   147: dup
/*     */     //   148: invokespecial <init> : ()V
/*     */     //   151: astore #16
/*     */     //   153: aload_1
/*     */     //   154: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   159: astore #17
/*     */     //   161: aload #17
/*     */     //   163: invokeinterface hasNext : ()Z
/*     */     //   168: ifeq -> 704
/*     */     //   171: aload #17
/*     */     //   173: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   178: checkcast journeymap/client/model/entity/EntityDTO
/*     */     //   181: astore #18
/*     */     //   183: aconst_null
/*     */     //   184: astore #19
/*     */     //   186: aconst_null
/*     */     //   187: astore #20
/*     */     //   189: aconst_null
/*     */     //   190: astore #21
/*     */     //   192: aload #18
/*     */     //   194: getfield entityRef : Ljava/lang/ref/WeakReference;
/*     */     //   197: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   200: checkcast net/minecraft/class_1297
/*     */     //   203: astore #24
/*     */     //   205: aload #24
/*     */     //   207: instanceof net/minecraft/class_1657
/*     */     //   210: istore #22
/*     */     //   212: aload #24
/*     */     //   214: ifnonnull -> 220
/*     */     //   217: goto -> 161
/*     */     //   220: aload_2
/*     */     //   221: aload #18
/*     */     //   223: getfield posX : D
/*     */     //   226: aload #18
/*     */     //   228: getfield posZ : D
/*     */     //   231: invokeinterface getPixel : (DD)Ljava/awt/geom/Point2D$Double;
/*     */     //   236: ifnonnull -> 307
/*     */     //   239: iload #22
/*     */     //   241: ifeq -> 304
/*     */     //   244: aload_2
/*     */     //   245: invokeinterface getContext : ()Ljourneymap/api/v2/client/display/Context$UI;
/*     */     //   250: getstatic journeymap/api/v2/client/display/Context$UI.Minimap : Ljourneymap/api/v2/client/display/Context$UI;
/*     */     //   253: if_acmpne -> 274
/*     */     //   256: invokestatic getInstance : ()Ljourneymap/client/JourneymapClient;
/*     */     //   259: invokevirtual getActiveMiniMapProperties : ()Ljourneymap/client/properties/MiniMapProperties;
/*     */     //   262: getfield showOffScreenPlayers : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   265: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   268: invokevirtual booleanValue : ()Z
/*     */     //   271: ifne -> 307
/*     */     //   274: aload_2
/*     */     //   275: invokeinterface getContext : ()Ljourneymap/api/v2/client/display/Context$UI;
/*     */     //   280: getstatic journeymap/api/v2/client/display/Context$UI.Fullscreen : Ljourneymap/api/v2/client/display/Context$UI;
/*     */     //   283: if_acmpne -> 304
/*     */     //   286: invokestatic getInstance : ()Ljourneymap/client/JourneymapClient;
/*     */     //   289: invokevirtual getFullMapProperties : ()Ljourneymap/client/properties/FullMapProperties;
/*     */     //   292: getfield showOffScreenPlayers : Ljourneymap/common/properties/config/BooleanField;
/*     */     //   295: invokevirtual get : ()Ljava/lang/Boolean;
/*     */     //   298: invokevirtual booleanValue : ()Z
/*     */     //   301: ifne -> 307
/*     */     //   304: goto -> 161
/*     */     //   307: aload #18
/*     */     //   309: getfield owner : Ljava/lang/String;
/*     */     //   312: ifnull -> 326
/*     */     //   315: aload #18
/*     */     //   317: getfield owner : Ljava/lang/String;
/*     */     //   320: invokestatic isNullOrEmpty : (Ljava/lang/String;)Z
/*     */     //   323: ifeq -> 349
/*     */     //   326: aload #24
/*     */     //   328: instanceof net/minecraft/class_1496
/*     */     //   331: ifeq -> 353
/*     */     //   334: aload #24
/*     */     //   336: checkcast net/minecraft/class_1496
/*     */     //   339: astore #25
/*     */     //   341: aload #25
/*     */     //   343: invokevirtual method_6727 : ()Z
/*     */     //   346: ifeq -> 353
/*     */     //   349: iconst_1
/*     */     //   350: goto -> 354
/*     */     //   353: iconst_0
/*     */     //   354: istore #23
/*     */     //   356: iload #6
/*     */     //   358: ifne -> 369
/*     */     //   361: iload #23
/*     */     //   363: ifeq -> 369
/*     */     //   366: goto -> 161
/*     */     //   369: iload #5
/*     */     //   371: ifne -> 400
/*     */     //   374: aload #18
/*     */     //   376: getfield ambientCreature : Z
/*     */     //   379: ifeq -> 400
/*     */     //   382: iload #22
/*     */     //   384: ifne -> 400
/*     */     //   387: iload #23
/*     */     //   389: ifeq -> 397
/*     */     //   392: iload #6
/*     */     //   394: ifne -> 400
/*     */     //   397: goto -> 161
/*     */     //   400: aload_0
/*     */     //   401: aload #24
/*     */     //   403: invokevirtual isPokemon : (Lnet/minecraft/class_1297;)Z
/*     */     //   406: istore #25
/*     */     //   408: iload #4
/*     */     //   410: ifne -> 444
/*     */     //   413: iload #25
/*     */     //   415: ifne -> 444
/*     */     //   418: aload #18
/*     */     //   420: getfield passiveAnimal : Z
/*     */     //   423: ifeq -> 444
/*     */     //   426: iload #22
/*     */     //   428: ifne -> 444
/*     */     //   431: iload #23
/*     */     //   433: ifeq -> 441
/*     */     //   436: iload #6
/*     */     //   438: ifne -> 461
/*     */     //   441: goto -> 161
/*     */     //   444: iload #25
/*     */     //   446: ifeq -> 461
/*     */     //   449: invokestatic getInstance : ()Ljourneymap/api/plugins/PokemonOptionsPlugin;
/*     */     //   452: invokevirtual showPokemon : ()Z
/*     */     //   455: ifne -> 461
/*     */     //   458: goto -> 161
/*     */     //   461: iload #7
/*     */     //   463: ifne -> 485
/*     */     //   466: aload #18
/*     */     //   468: getfield profession : Ljava/lang/String;
/*     */     //   471: ifnonnull -> 482
/*     */     //   474: aload #18
/*     */     //   476: getfield npc : Z
/*     */     //   479: ifeq -> 485
/*     */     //   482: goto -> 161
/*     */     //   485: getstatic journeymap/client/data/DataCache.INSTANCE : Ljourneymap/client/data/DataCache;
/*     */     //   488: aload #24
/*     */     //   490: invokevirtual getDrawEntityStep : (Lnet/minecraft/class_1297;)Ljourneymap/client/render/draw/DrawEntityStep;
/*     */     //   493: astore #26
/*     */     //   495: iload #22
/*     */     //   497: ifeq -> 590
/*     */     //   500: aload #9
/*     */     //   502: iload #11
/*     */     //   504: invokestatic getLocatorTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Z)Lnet/minecraft/class_1043;
/*     */     //   507: astore #20
/*     */     //   509: aload #9
/*     */     //   511: iload #11
/*     */     //   513: invokestatic getLocatorBGTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Z)Lnet/minecraft/class_1043;
/*     */     //   516: astore #21
/*     */     //   518: aload #24
/*     */     //   520: checkcast net/minecraft/class_1657
/*     */     //   523: astore #27
/*     */     //   525: aload #9
/*     */     //   527: aload #27
/*     */     //   529: invokevirtual method_7334 : ()Lcom/mojang/authlib/GameProfile;
/*     */     //   532: invokestatic getEntityTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/class_1043;
/*     */     //   535: astore #19
/*     */     //   537: aload #9
/*     */     //   539: invokevirtual isOutlined : ()Z
/*     */     //   542: istore #28
/*     */     //   544: aload #26
/*     */     //   546: aload #9
/*     */     //   548: aload #20
/*     */     //   550: aload #21
/*     */     //   552: aload #19
/*     */     //   554: aload #18
/*     */     //   556: getfield color : I
/*     */     //   559: aload #18
/*     */     //   561: getfield labelColor : I
/*     */     //   564: iload #11
/*     */     //   566: iload #12
/*     */     //   568: iload #13
/*     */     //   570: iload #28
/*     */     //   572: fload #15
/*     */     //   574: invokevirtual update : (Ljourneymap/client/ui/minimap/EntityDisplay;Lnet/minecraft/class_1043;Lnet/minecraft/class_1043;Lnet/minecraft/class_1043;IIZZZZF)V
/*     */     //   577: aload #16
/*     */     //   579: aload #26
/*     */     //   581: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   586: pop
/*     */     //   587: goto -> 678
/*     */     //   590: aload #8
/*     */     //   592: astore #27
/*     */     //   594: aload #27
/*     */     //   596: aload #18
/*     */     //   598: invokestatic hasEntityIcon : (Ljourneymap/client/ui/minimap/EntityDisplay;Ljourneymap/client/model/entity/EntityDTO;)Z
/*     */     //   601: ifne -> 611
/*     */     //   604: aload #8
/*     */     //   606: invokevirtual getDot : ()Ljourneymap/client/ui/minimap/EntityDisplay;
/*     */     //   609: astore #27
/*     */     //   611: aload #27
/*     */     //   613: iload #10
/*     */     //   615: invokestatic getLocatorTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Z)Lnet/minecraft/class_1043;
/*     */     //   618: astore #20
/*     */     //   620: aload #27
/*     */     //   622: iload #10
/*     */     //   624: invokestatic getLocatorBGTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Z)Lnet/minecraft/class_1043;
/*     */     //   627: astore #21
/*     */     //   629: aload #27
/*     */     //   631: aload #18
/*     */     //   633: invokestatic getEntityTexture : (Ljourneymap/client/ui/minimap/EntityDisplay;Ljourneymap/client/model/entity/EntityDTO;)Lnet/minecraft/class_1043;
/*     */     //   636: astore #19
/*     */     //   638: aload #26
/*     */     //   640: aload #27
/*     */     //   642: aload #20
/*     */     //   644: aload #21
/*     */     //   646: aload #19
/*     */     //   648: aload #18
/*     */     //   650: getfield color : I
/*     */     //   653: aload #18
/*     */     //   655: getfield labelColor : I
/*     */     //   658: iload #10
/*     */     //   660: iconst_0
/*     */     //   661: iconst_0
/*     */     //   662: iconst_0
/*     */     //   663: fload #14
/*     */     //   665: invokevirtual update : (Ljourneymap/client/ui/minimap/EntityDisplay;Lnet/minecraft/class_1043;Lnet/minecraft/class_1043;Lnet/minecraft/class_1043;IIZZZZF)V
/*     */     //   668: aload #16
/*     */     //   670: aload #26
/*     */     //   672: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   677: pop
/*     */     //   678: goto -> 701
/*     */     //   681: astore #19
/*     */     //   683: invokestatic getLogger : ()Lorg/apache/logging/log4j/Logger;
/*     */     //   686: aload #19
/*     */     //   688: invokestatic toString : (Ljava/lang/Throwable;)Ljava/lang/String;
/*     */     //   691: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   696: invokeinterface error : (Ljava/lang/String;)V
/*     */     //   701: goto -> 161
/*     */     //   704: goto -> 727
/*     */     //   707: astore #17
/*     */     //   709: invokestatic getLogger : ()Lorg/apache/logging/log4j/Logger;
/*     */     //   712: aload #17
/*     */     //   714: invokestatic toString : (Ljava/lang/Throwable;)Ljava/lang/String;
/*     */     //   717: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   722: invokeinterface error : (Ljava/lang/String;)V
/*     */     //   727: aload #16
/*     */     //   729: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #38	-> 0
/*     */     //   #39	-> 12
/*     */     //   #40	-> 24
/*     */     //   #41	-> 36
/*     */     //   #42	-> 48
/*     */     //   #43	-> 60
/*     */     //   #44	-> 72
/*     */     //   #45	-> 84
/*     */     //   #46	-> 96
/*     */     //   #47	-> 108
/*     */     //   #48	-> 120
/*     */     //   #49	-> 132
/*     */     //   #50	-> 144
/*     */     //   #54	-> 153
/*     */     //   #58	-> 183
/*     */     //   #59	-> 186
/*     */     //   #60	-> 189
/*     */     //   #63	-> 192
/*     */     //   #64	-> 205
/*     */     //   #65	-> 212
/*     */     //   #67	-> 217
/*     */     //   #70	-> 220
/*     */     //   #72	-> 245
/*     */     //   #73	-> 275
/*     */     //   #75	-> 304
/*     */     //   #78	-> 307
/*     */     //   #79	-> 356
/*     */     //   #81	-> 366
/*     */     //   #84	-> 369
/*     */     //   #86	-> 387
/*     */     //   #88	-> 397
/*     */     //   #91	-> 400
/*     */     //   #92	-> 408
/*     */     //   #94	-> 431
/*     */     //   #96	-> 441
/*     */     //   #99	-> 444
/*     */     //   #101	-> 458
/*     */     //   #105	-> 461
/*     */     //   #107	-> 482
/*     */     //   #111	-> 485
/*     */     //   #113	-> 495
/*     */     //   #115	-> 500
/*     */     //   #116	-> 509
/*     */     //   #118	-> 518
/*     */     //   #119	-> 525
/*     */     //   #120	-> 537
/*     */     //   #121	-> 544
/*     */     //   #122	-> 577
/*     */     //   #123	-> 587
/*     */     //   #126	-> 590
/*     */     //   #127	-> 594
/*     */     //   #129	-> 604
/*     */     //   #132	-> 611
/*     */     //   #133	-> 620
/*     */     //   #134	-> 629
/*     */     //   #136	-> 638
/*     */     //   #137	-> 668
/*     */     //   #144	-> 678
/*     */     //   #141	-> 681
/*     */     //   #143	-> 683
/*     */     //   #145	-> 701
/*     */     //   #150	-> 704
/*     */     //   #147	-> 707
/*     */     //   #149	-> 709
/*     */     //   #152	-> 727
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   341	8	25	horse	Lnet/minecraft/class_1496;
/*     */     //   525	62	27	entity	Lnet/minecraft/class_1657;
/*     */     //   544	43	28	outlined	Z
/*     */     //   594	84	27	actualDisplay	Ljourneymap/client/ui/minimap/EntityDisplay;
/*     */     //   186	492	19	entityIcon	Lnet/minecraft/class_1043;
/*     */     //   189	489	20	locatorImg	Lnet/minecraft/class_1043;
/*     */     //   192	486	21	locatorBGImg	Lnet/minecraft/class_1043;
/*     */     //   212	466	22	isPlayer	Z
/*     */     //   356	322	23	isPet	Z
/*     */     //   205	473	24	entityLiving	Lnet/minecraft/class_1297;
/*     */     //   408	270	25	isPokemon	Z
/*     */     //   495	183	26	drawStep	Ljourneymap/client/render/draw/DrawEntityStep;
/*     */     //   683	18	19	e	Ljava/lang/Exception;
/*     */     //   183	518	18	dto	Ljourneymap/client/model/entity/EntityDTO;
/*     */     //   709	18	17	t	Ljava/lang/Throwable;
/*     */     //   0	730	0	this	Ljourneymap/client/render/draw/RadarDrawStepFactory;
/*     */     //   0	730	1	entityDTOs	Ljava/util/List;
/*     */     //   0	730	2	renderer	Ljourneymap/client/render/map/Renderer;
/*     */     //   0	730	3	mapProperties	Ljourneymap/client/properties/InGameMapProperties;
/*     */     //   12	718	4	showAnimals	Z
/*     */     //   24	706	5	showAmbient	Z
/*     */     //   36	694	6	showPets	Z
/*     */     //   48	682	7	showVillagers	Z
/*     */     //   60	670	8	mobDisplay	Ljourneymap/client/ui/minimap/EntityDisplay;
/*     */     //   72	658	9	playerDisplay	Ljourneymap/client/ui/minimap/EntityDisplay;
/*     */     //   84	646	10	showMobHeading	Z
/*     */     //   96	634	11	showPlayerHeading	Z
/*     */     //   108	622	12	showTeamNames	Z
/*     */     //   120	610	13	showPlayerNames	Z
/*     */     //   132	598	14	mobDisplayDrawScale	F
/*     */     //   144	586	15	playerDisplayDrawScale	F
/*     */     //   153	577	16	drawStepList	Ljava/util/List;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	730	1	entityDTOs	Ljava/util/List<Ljourneymap/client/model/entity/EntityDTO;>;
/*     */     //   153	577	16	drawStepList	Ljava/util/List<Ljourneymap/client/render/draw/DrawStep;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   153	704	707	java/lang/Throwable
/*     */     //   183	217	681	java/lang/Exception
/*     */     //   220	304	681	java/lang/Exception
/*     */     //   307	366	681	java/lang/Exception
/*     */     //   369	397	681	java/lang/Exception
/*     */     //   400	441	681	java/lang/Exception
/*     */     //   444	458	681	java/lang/Exception
/*     */     //   461	482	681	java/lang/Exception
/*     */     //   485	678	681	java/lang/Exception
/*     */   }
/*     */   
/*     */   private boolean isPokemon(class_1297 entity) {
/* 157 */     return (PokemonOptionsPlugin.hasPokemonMod() && 
/* 158 */       ReflectionHelper.isInstanceOf(entity, new String[] { "com.cobblemon.mod.common.entity.pokemon.PokemonEntity", "com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon" }));
/*     */   }
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\render\draw\RadarDrawStepFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */