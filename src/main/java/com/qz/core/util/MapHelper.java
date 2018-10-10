package com.qz.core.util;

import java.util.HashMap;
import java.util.Map;

import com.qz.core.common.NumberUtil;
import com.qz.core.exception.MyException;

/**
 * @author chance
 * @date 2017年11月29日下午2:43:58
 */
public class MapHelper {

	private Map<String, Object> map = new HashMap<String, Object>();
	
	private Map<String, ?> resultMap = null;

	public static Map<String, Object> createEmptyMap() {
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> createMap(String key, Object value) {
		Map<String, Object> map = MapHelper.createEmptyMap();
		map.put(key, value);
		return map;
	}

	private static MapHelper create() {
		return new MapHelper();
	}

	private Map<String, Object> getMap() {
		return map;
	}

	public static MapHelper addFirst(String key, Object value) {
		return MapHelper.create().add(key, value);
	}

	public MapHelper add(String key, Object value) {
		if (null == key || "".equals(key)) {
			throw new MyException("key map is null or empty");
		}
		this.map.put(key, value);
		return this;
	}

	public Map<String, Object> addLast(String key, Object value) {
		return this.add(key, value).getMap();
	}
	
	private MapHelper setResultMap(Map<String, ?> map){
		this.resultMap = map;
		return this;
	}
	
	public static MapHelper setMap(Map<String, ?> map){
		return MapHelper.create().setResultMap(map);
	}
	
	public int getInt(String key){
		Object o = this.resultMap.get(key);
		
		if(null == o){
			return 0;
		}
		
		if(o.getClass().equals(Integer.class)){
			return (Integer) o;
		}else{
			return NumberUtil.parseInt(o.toString());
		}
	}
	
	public double getDouble(String key){
		Object o = this.resultMap.get(key);
		
		if(null == o){
			return 0;
		}
		
		if(o.getClass().equals(Double.class)){
			return (Double) o;
		}else{
			return NumberUtil.parseDouble(o.toString());
		}
	}
	
	public String getString(String key){
		Object o = this.resultMap.get(key);
		return null == o ? null : o.toString();
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", null);
		map.put("d", 2.2);
		
		MapHelper mapHelper = MapHelper.setMap(map);
		
//		System.out.println(mapHelper.getInt("a"));
//		System.out.println(mapHelper.getInt("b"));
//		System.out.println(mapHelper.getString("c"));
//		System.out.println(mapHelper.getDouble("a"));
		System.out.println(mapHelper.getDouble("d"));
		System.out.println((int)mapHelper.getDouble("c"));
	}
	
}
