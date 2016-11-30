package com.baidu.yun.core.utility;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baidu.yun.core.annotation.JSonPath;
import com.baidu.yun.push.model.DeviceStatUnit;
import com.baidu.yun.push.model.KeyValueForAck;
import com.baidu.yun.push.model.KeyValueForDevice;
import com.baidu.yun.push.model.KeyValueForMsg;
import com.baidu.yun.push.model.KeyValueForTopic;
import com.baidu.yun.push.model.MsgStatUnit;
import com.baidu.yun.push.model.TopicStatUnit;

/**
 *  
 *
 */

public class MapObjectUtility {
	
	public static void convertMap2Object(Object obj, Map map) {
		
	}
	
	public static void convertMap2ObjectWithJson(Object obj, Map map) {
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for ( Field field : fields ) {
			try {
				field.setAccessible(true);
				if ( field.isAnnotationPresent(JSonPath.class) ) {
					Object fobj = field.get(obj);
					JSonPath annotation = field.getAnnotation(JSonPath.class);
					
					Class<?> zlass = field.getType();
					Object jsonObj = queryJsonPathInMap(map, annotation.path());
					if ( jsonObj == null ) {
						continue;
					}
					
					if ( zlass.equals(Long.class) || "long".equalsIgnoreCase(zlass.getName()) ) {
						if (zlass.equals(Long.class)) {
							if ( jsonObj instanceof Long ) {
								field.set(obj, (Long)jsonObj);
							} else if ( jsonObj instanceof Integer ) {
								field.set(obj, ((Integer)jsonObj).intValue());
							} else if ( jsonObj instanceof String ) {
								field.set(obj, Long.parseLong((String)jsonObj));
							}
						}
						if ("long".equalsIgnoreCase(zlass.getName())) {
							if ( jsonObj instanceof Long ) {
								field.setLong(obj, ((Long)jsonObj).longValue());
							} else if ( jsonObj instanceof Integer ) {
								field.setLong(obj, ((Integer)jsonObj).intValue());
							} else if ( jsonObj instanceof String ) {
								field.setLong(obj, Long.parseLong((String)jsonObj));
							}
						}
					} else if ( zlass.equals(Integer.class) || "int".equalsIgnoreCase(zlass.getName()) ) {
						if (zlass.equals(Integer.class)) {
							if ( jsonObj instanceof Long ) {
								field.set(obj, ((Long)jsonObj).intValue());
							} else if ( jsonObj instanceof Integer ) {
								field.set(obj, ((Integer)jsonObj));
							} else if ( jsonObj instanceof String ) {
								field.set(obj, Integer.parseInt((String)jsonObj));
							}
						}
						if ("int".equalsIgnoreCase(zlass.getName())) {
							if ( jsonObj instanceof Long ) {
								field.setInt(obj, ((Long)jsonObj).intValue());
							} else if ( jsonObj instanceof Integer ) {
								field.setInt(obj, ((Integer)jsonObj).intValue());
							} else if ( jsonObj instanceof String ) {
								field.setInt(obj, Integer.parseInt((String)jsonObj));
							}
						}
					} else if ( zlass.equals(String.class) ) {
						String os = jsonObj.toString().replaceAll("\\\\\"", "\"");
						field.set(obj, os);
					} else if ( zlass.equals(java.util.List.class) && jsonObj instanceof java.util.List ) {
						@SuppressWarnings("rawtypes")
						List list = (List) field.get(obj);
						Type type = field.getGenericType();
						if ( type instanceof ParameterizedType ) {
							ParameterizedType ptype = (ParameterizedType)type;
							Type[] types = ptype.getActualTypeArguments(); 
							if ( types.length == 1 ) {
								if ( types[0].equals(Integer.class) ) {
									Class elass = queryListGenericClass(jsonObj);
									if ( elass.equals(Integer.class) || elass.equals(Long.class) ) {
										list.addAll((Collection) jsonObj);
									} else if ( elass.equals(String.class) ) {
										List<String> lobj = (List<String>)jsonObj;
										for ( String o : lobj ) {
											list.add(Integer.parseInt(o));
										}
									}
								} else if ( types[0].equals(Long.class) ) {
									Class elass = queryListGenericClass(jsonObj);
									if ( elass.equals(Integer.class) || elass.equals(Long.class) ) {
										list.addAll((Collection) jsonObj);
									} else if (elass.equals(String.class) ) {
										List<String> lobj = (List<String>)jsonObj;
										for ( String o : lobj ) {
											list.add(Long.parseLong(o));
										}
									}
								} else if ( types[0].equals(String.class) ) {
									Class elass = queryListGenericClass(jsonObj);
									if ( elass.equals(String.class) ) {
//										list.addAll((Collection)(jsonObj));
										
										List<String> sobjs = (List<String>)jsonObj;
										for ( String o : sobjs ) {
											o = o.replaceAll("\\\\\"", "\"");
											list.add(o);
										}
									}
								} else {
									if ( isGenericClassMap(jsonObj) ) {
										for ( Object o : (List)jsonObj ) {
											Map m = (Map)o;
											Object targetOject = ((Class)types[0]).newInstance();
											MapObjectUtility.convertMap2ObjectWithJson(targetOject, m);
											list.add(targetOject);
										}
									}
								} 
							}
						}
					} else {
						Map m = (Map)jsonObj;
						Object object = field.get(obj);
						if (zlass.equals(java.util.List.class) && jsonObj instanceof java.util.Map) {
						    List targetObject = (java.util.List)object;
							Type type = field.getGenericType();
							if ( type instanceof ParameterizedType ) {
								ParameterizedType ptype = (ParameterizedType)type;
								Type[] types = ptype.getActualTypeArguments();
								if (types.length == 1) {
									Object objInList = ((Class)types[0]).newInstance();
									if (objInList instanceof KeyValueForMsg) {
										for (Object key : m.keySet()) {
											KeyValueForMsg keyValue = new KeyValueForMsg();
											MsgStatUnit value = new MsgStatUnit();
											String keyName = key.toString();
											Object valueObj = m.get(keyName);
											
											if (valueObj instanceof java.util.Map) {
												MapObjectUtility.convertMap2ObjectWithJson(value, (Map)valueObj);
											}
											keyValue.setKey(keyName);
											keyValue.setValue(value);
											targetObject.add(keyValue);
										}
									}else if (objInList instanceof KeyValueForTopic) {
										for (Object key : m.keySet()) {
											KeyValueForTopic keyValue = new KeyValueForTopic();
											TopicStatUnit value = new TopicStatUnit();
											String keyName = key.toString();
											Object valueObj = m.get(keyName);
											
											if (valueObj instanceof java.util.Map) {
												MapObjectUtility.convertMap2ObjectWithJson(value, (Map)valueObj);
											}
											keyValue.setKey(keyName);
											keyValue.setValue(value);
											targetObject.add(keyValue);
										}
									}else if (objInList instanceof KeyValueForDevice) {
										for (Object key : m.keySet()) {
											KeyValueForDevice keyValue = new KeyValueForDevice();
											DeviceStatUnit value = new DeviceStatUnit();
											String keyName = key.toString();
											Object valueObj = m.get(keyName);
											
											if (valueObj instanceof java.util.Map) {
												MapObjectUtility.convertMap2ObjectWithJson(value, (Map)valueObj);
											}
											keyValue.setKey(keyName);
											keyValue.setValue(value);
											targetObject.add(keyValue);
										}
									}else if (objInList instanceof KeyValueForAck) {
										for (Object key : m.keySet()) {
											KeyValueForAck keyValue = new KeyValueForAck();
											String keyName = key.toString();
											System.out.println("key:" + keyName);
											Object valueObj = m.get(keyName);
											System.out.println("value:" + Integer.parseInt(valueObj.toString()));
											int value = Integer.parseInt(valueObj.toString());
											keyValue.setKey(keyName);
											keyValue.setValue(value);
											targetObject.add(keyValue);
										}
									}
								}
							}
							field.set(obj, targetObject);
						} else {
							MapObjectUtility.convertMap2ObjectWithJson(object, m);
							field.set(obj, object);
						}
					} 
					
				} 
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	private static Object queryJsonPathInMap(Map map, String path){

		Object obj = map;
		String[] segs = path.split("\\\\");
		
		for (int i = 0; i < segs.length; i++) {
			if (obj == null)
				return null;
			if (segs[i].length() > 0) {
				if (obj instanceof Map) {
					Map curMap = (Map) obj;
					obj = curMap.get(segs[i]);
				} else if (obj instanceof List) {
					List curList = (List) obj;
					obj = null;
					for (Object o : curList) {
						if (o instanceof Map) {
							if (((Map) o).containsKey(segs[i])) {
								obj = o;
								break; 
							}
						}
					}
				} else {
					return null;
				}
			}
		}
		return obj;
	}
	
	private static Class queryListGenericClass(Object obj) {
		if ( obj instanceof java.util.List ) {
			List lobj = (List)obj;
			if ( lobj.size() > 0 ) {
				return lobj.get(0).getClass();
			}
		}
		return null;
	}
	
	
	private static boolean isGenericClassMap(Object obj) {
		if ( obj instanceof java.util.List ) {
			List lobj = (List)obj;
			if ( lobj.size() > 0 ) {
				return lobj.get(0) instanceof java.util.Map;
			}
		}
		return false;
	}
	
}
