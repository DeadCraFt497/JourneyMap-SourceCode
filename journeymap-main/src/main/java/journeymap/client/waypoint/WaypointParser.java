/*     */ package journeymap.client.waypoint;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import journeymap.api.common.waypoint.WaypointFactoryImpl;
/*     */ import journeymap.api.v2.common.waypoint.Waypoint;
/*     */ import journeymap.client.Constants;
/*     */ import journeymap.common.Journeymap;
/*     */ import journeymap.common.helper.DimensionHelper;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_2588;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
/*     */ import net.minecraft.class_7225;
/*     */ import net.minecraft.class_7417;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointParser
/*     */ {
/*  36 */   public static String[] QUOTES = new String[] { "'", "\"" };
/*  37 */   public static Pattern PATTERN = Pattern.compile("(\\w+\\s*:\\s*-?[\\w\\d\\s'\"]+,\\s*)+(\\w+\\s*:\\s*-?[\\w\\d\\s'\"]+)", 2);
/*  38 */   private static Pattern TEXT_BETWEEN_QUOTES = Pattern.compile("\".*?\"|'.*?'|`.*`");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getWaypointStrings(String line) {
/*  48 */     List<String> list = null;
/*  49 */     String[] candidates = StringUtils.substringsBetween(line, "[", "]");
/*  50 */     if (candidates != null)
/*     */     {
/*  52 */       for (String candidate : candidates) {
/*     */         
/*  54 */         if (PATTERN.matcher(candidate).find())
/*     */         {
/*  56 */           if (parse(candidate) != null) {
/*     */             
/*  58 */             if (list == null)
/*     */             {
/*  60 */               list = new ArrayList<>(1);
/*     */             }
/*  62 */             list.add("[" + candidate + "]");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  68 */     if (list == null && line.startsWith("xaero-waypoint:")) {
/*     */       
/*  70 */       list = new ArrayList<>(1);
/*  71 */       list.add(line);
/*     */     } 
/*     */     
/*  74 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static List<Waypoint> getWaypoints(String line) {
/*  85 */     List<Waypoint> list = null;
/*  86 */     String[] candidates = StringUtils.substringsBetween(line, "[", "]");
/*  87 */     if (candidates != null)
/*     */     {
/*  89 */       for (String candidate : candidates) {
/*     */         
/*  91 */         if (PATTERN.matcher(candidate).find()) {
/*     */           
/*  93 */           Waypoint waypoint = parse(candidate);
/*  94 */           if (waypoint != null) {
/*     */             
/*  96 */             if (list == null)
/*     */             {
/*  98 */               list = new ArrayList<>(1);
/*     */             }
/* 100 */             list.add(waypoint);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 106 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Waypoint parse(String original) {
/* 117 */     ParsedWaypoint wp = ParsedWaypoint.parse(original);
/* 118 */     String name = wp.name;
/* 119 */     String dim = wp.dim;
/*     */     
/* 121 */     if (wp.x != null && wp.z != null) {
/*     */       
/* 123 */       if (name == null)
/*     */       {
/* 125 */         name = String.format("%s,%s", new Object[] { wp.x, wp.z });
/*     */       }
/* 127 */       if (dim == null)
/*     */       {
/*     */         
/* 130 */         if (class_310.method_1551() == null) {
/*     */           
/* 132 */           dim = "minecraft:overworld";
/*     */         }
/*     */         else {
/*     */           
/* 136 */           dim = DimensionHelper.getDimKeyName((class_310.method_1551()).field_1687.method_27983());
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 141 */       return WaypointFactoryImpl.createWaypoint("journeymap", new class_2338(wp.x.intValue(), wp.y, wp.z.intValue()), name, dim, wp.showDeviation);
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_2561 parseChatForWaypoints(class_2561 component) {
/* 149 */     List<String> matches = getWaypointStrings(component.getString());
/* 150 */     class_2561 response = null;
/* 151 */     if (matches != null) {
/*     */       
/* 153 */       boolean changed = false;
/* 154 */       class_7417 class_7417 = component.method_10851(); if (class_7417 instanceof class_2588) { class_2588 contents = (class_2588)class_7417;
/*     */         
/* 156 */         Object[] formatArgs = contents.method_11023();
/* 157 */         for (int i = 0; i < formatArgs.length; i++) {
/*     */           
/* 159 */           if (matches.isEmpty()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 164 */           if (formatArgs[i] instanceof class_2561) {
/*     */             
/* 166 */             class_2561 arg = (class_2561)formatArgs[i];
/* 167 */             class_2561 result = addWaypointMarkup(arg.getString(), matches);
/* 168 */             if (result != null)
/*     */             {
/* 170 */               formatArgs[i] = result;
/* 171 */               changed = true;
/*     */             }
/*     */           
/* 174 */           } else if (formatArgs[i] instanceof String) {
/*     */             
/* 176 */             String arg = (String)formatArgs[i];
/* 177 */             class_2561 result = addWaypointMarkup(arg, matches);
/* 178 */             if (result != null) {
/*     */               
/* 180 */               formatArgs[i] = result;
/* 181 */               changed = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 186 */         if (changed)
/*     */         {
/* 188 */           class_5250 class_5250 = class_2561.method_43469(contents.method_11022(), formatArgs);
/*     */         } }
/*     */       
/* 191 */       else if (component instanceof class_5250) { class_5250 mutableComponent = (class_5250)component;
/*     */         
/* 193 */         for (class_2561 content : mutableComponent.method_10855()) {
/*     */           
/* 195 */           class_2561 result = addWaypointMarkup(content.getString(), matches);
/* 196 */           if (result != null) {
/*     */             
/* 198 */             response = result;
/* 199 */             changed = true;
/*     */           } 
/*     */         } 
/*     */         
/* 203 */         if (response != null)
/*     */         {
/* 205 */           changed = true;
/*     */         } }
/*     */       
/*     */       else
/*     */       
/* 210 */       { Journeymap.getLogger().warn("No implementation for handling waypoints in ITextComponent " + String.valueOf(component.getClass())); }
/*     */ 
/*     */       
/* 213 */       if (!changed)
/*     */       {
/* 215 */         Journeymap.getLogger().warn(String.format("Matched waypoint in chat but failed to update message for %s : %s\n%s", new Object[] { component.getClass(), component.getString(), class_2561.class_2562.method_10867(component, (class_7225.class_7874)(class_310.method_1551()).field_1687.method_30349()) }));
/*     */       }
/* 217 */       return response;
/*     */     } 
/* 219 */     return component;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_2561 addWaypointMarkup(String text, List<String> matches) {
/* 224 */     List<class_2561> newParts = new ArrayList<>();
/*     */     
/* 226 */     int index = 0;
/*     */     
/* 228 */     boolean matched = false;
/* 229 */     Iterator<String> iterator = matches.iterator();
/* 230 */     while (iterator.hasNext()) {
/*     */       
/* 232 */       String match = iterator.next();
/* 233 */       if (text.contains(match)) {
/*     */         
/* 235 */         int start = text.indexOf(match);
/* 236 */         if (start > index)
/*     */         {
/* 238 */           newParts.add(Constants.getStringTextComponent(text.substring(index, start)));
/*     */         }
/*     */         
/* 241 */         matched = true;
/*     */         
/* 243 */         String name = (ParsedWaypoint.parse(match)).name;
/* 244 */         if (name == null)
/*     */         {
/* 246 */           name = match;
/*     */         }
/* 248 */         class_5250 clickable = Constants.getStringTextComponent(Constants.getString("jm.waypoint.chat_share") + " §b§n" + Constants.getString("jm.waypoint.chat_share") + "§r");
/* 249 */         clickable.method_27694(style -> style.method_10958((class_2558)new class_2558.class_10609("/jm wpedit " + match)));
/*     */         
/* 251 */         class_5250 hover = class_2561.method_43471("jm.common.share.chat.journeymap");
/* 252 */         hover.method_27694(style -> style.method_27703(class_5251.method_27718(class_124.field_1054)));
/*     */         
/* 254 */         class_5250 hover2 = class_2561.method_43471("jm.common.share.chat.description");
/* 255 */         hover2.method_27694(style -> style.method_27703(class_5251.method_27718(class_124.field_1075)));
/* 256 */         hover.method_10852((class_2561)hover2);
/*     */         
/* 258 */         clickable.method_27694(style -> style.method_10949((class_2568)new class_2568.class_10613((class_2561)hover)));
/*     */         
/* 260 */         newParts.add(clickable);
/*     */         
/* 262 */         index = start + match.length();
/*     */         
/* 264 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     if (!matched)
/*     */     {
/* 270 */       return null;
/*     */     }
/* 272 */     if (index < text.length() - 1)
/*     */     {
/* 274 */       newParts.add(Constants.getStringTextComponent(text.substring(index, text.length())));
/*     */     }
/*     */     
/* 277 */     if (!newParts.isEmpty()) {
/*     */       
/* 279 */       class_5250 replacement = Constants.getStringTextComponent("");
/* 280 */       for (class_2561 sib : newParts)
/*     */       {
/* 282 */         replacement.method_10852(sib);
/*     */       }
/* 284 */       return (class_2561)replacement;
/*     */     } 
/*     */     
/* 287 */     return null;
/*     */   }
/*     */   public static final class ParsedWaypoint extends Record { private final String name; private final String dim; private final Integer x; private final int y; private final Integer z; private final boolean showDeviation;
/*     */     
/* 291 */     public ParsedWaypoint(String name, String dim, Integer x, int y, Integer z, boolean showDeviation) { this.name = name; this.dim = dim; this.x = x; this.y = y; this.z = z; this.showDeviation = showDeviation; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #291	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 291 */       //   0	7	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #291	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #291	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Ljourneymap/client/waypoint/WaypointParser$ParsedWaypoint;
/* 291 */       //   0	8	1	o	Ljava/lang/Object; } public String dim() { return this.dim; } public Integer x() { return this.x; } public int y() { return this.y; } public Integer z() { return this.z; } public boolean showDeviation() { return this.showDeviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static String xaerosDimParser(String dim) {
/* 305 */       dim = dim.replace("Internal-", "").replace("-waypoints", "").replace("dim%", "").replace("$", ":");
/* 306 */       if ("overworld".equals(dim) || "the-nether".equals(dim) || "the-end".equals(dim))
/*     */       {
/* 308 */         dim = "minecraft:" + dim;
/*     */       }
/*     */       
/* 311 */       if ("External".equals(dim))
/*     */       {
/* 313 */         dim = "minecraft:overworld";
/*     */       }
/* 315 */       dim = dim.replace("-", "_");
/*     */       
/* 317 */       return dim;
/*     */     }
/*     */ 
/*     */     
/*     */     public static ParsedWaypoint parse(String string) {
/* 322 */       String name = null;
/* 323 */       String raw = string.replaceAll("[\\[\\]]", "");
/* 324 */       if (string.startsWith("xaero-waypoint:")) {
/*     */         
/* 326 */         String[] split = string.split(":");
/* 327 */         String dimName = xaerosDimParser(split[9]);
/* 328 */         name = split[1].replace("gui.", "");
/* 329 */         int i = Integer.parseInt(split[3]);
/* 330 */         int j = Integer.parseInt(split[4].replace("~", "63"));
/* 331 */         int k = Integer.parseInt(split[5]);
/*     */         
/* 333 */         return new ParsedWaypoint(name, dimName, Integer.valueOf(i), j, Integer.valueOf(k), false);
/*     */       } 
/*     */       
/* 336 */       String[] nameMatch = StringUtils.substringsBetween(string, "\"", "\"");
/* 337 */       if (nameMatch != null && nameMatch.length > 0)
/*     */       {
/* 339 */         name = nameMatch[0];
/*     */       }
/*     */       
/* 342 */       Integer x = null;
/* 343 */       int y = 63;
/* 344 */       Integer z = null;
/* 345 */       String dim = "minecraft:overworld";
/*     */       
/* 347 */       boolean showDeviation = false;
/* 348 */       for (String part : raw.split(",")) {
/*     */         
/* 350 */         if (part.contains(":")) {
/*     */           
/* 352 */           String[] prop = part.split(":");
/* 353 */           if (prop.length == 2 || (prop.length == 3 && part.contains("dim:"))) {
/*     */             
/* 355 */             String key = prop[0].trim().toLowerCase();
/* 356 */             String val = prop[1].trim();
/*     */             
/*     */             try {
/* 359 */               if ("x".equals(key)) {
/*     */                 
/* 361 */                 x = Integer.valueOf(Integer.parseInt(val));
/*     */               }
/* 363 */               else if ("y".equals(key)) {
/*     */                 
/* 365 */                 y = Integer.parseInt(val);
/*     */               }
/* 367 */               else if ("z".equals(key)) {
/*     */                 
/* 369 */                 z = Integer.valueOf(Integer.parseInt(val));
/*     */               }
/* 371 */               else if ("dim".equals(key)) {
/*     */                 
/* 373 */                 dim = val + ":" + val;
/*     */               }
/* 375 */               else if ("name".equals(key)) {
/*     */                 
/* 377 */                 if (name == null)
/*     */                 {
/* 379 */                   name = val.replace("\"", "");
/*     */                 }
/*     */               } 
/* 382 */               if ("showDeviation".equals(key))
/*     */               {
/* 384 */                 showDeviation = Boolean.parseBoolean(val);
/*     */               }
/*     */             }
/* 387 */             catch (Exception e) {
/*     */               
/* 389 */               Journeymap.getLogger().warn("Bad format in waypoint text part: " + part + ": " + String.valueOf(e));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 394 */       if (name == null)
/*     */       {
/* 396 */         name = "" + x + "," + x;
/*     */       }
/* 398 */       return new ParsedWaypoint(name, dim, x, y, z, showDeviation);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\zmm11\Downloads\jarfile\hack\journeymap-fabric-1.21.5-6.0.0-beta.46.jar!\journeymap\client\waypoint\WaypointParser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */