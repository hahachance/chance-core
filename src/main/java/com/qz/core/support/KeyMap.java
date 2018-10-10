package com.qz.core.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author chance
 * @date 2017年11月12日 下午5:14:23
 */
public class KeyMap {

	private Map<String, Object> selectKey;
	
	public KeyMap() {
		this.selectKey = createMap();
	}
	
	public KeyMap(String keyName, Object value) {
		this.selectKey = createMap();
		this.put(keyName, value);
	}
	
	public static KeyMap create() {
		return new KeyMap();
	}
	
	public KeyMap put(String keyName, Object value) {
		this.selectKey.put(keyName, value);
		return this;
	}

	public Map<String, Object> getSelectKey() {
		return selectKey;
	}

	public KeyMap setSelectKey(Map<String, Object> selectKey) {
		this.selectKey = selectKey;
		return this;
	}
	
	private Map<String, Object> createMap(){
		return new HashMap<String, Object>();
	}

}
