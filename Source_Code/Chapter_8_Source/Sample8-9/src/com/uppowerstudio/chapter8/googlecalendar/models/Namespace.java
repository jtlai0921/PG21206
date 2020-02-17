package com.uppowerstudio.chapter8.googlecalendar.models;

import com.google.api.client.xml.XmlNamespaceDictionary;

import java.util.Map;

/**
 * Atom XML命名空間Java數據模型類
 * @author UPPower Studio
 *
 */
public class Namespace {

	public static final XmlNamespaceDictionary DICTIONARY = new XmlNamespaceDictionary();
	static {
		Map<String, String> map = DICTIONARY.namespaceAliasToUriMap;
		map.put("", "http://www.w3.org/2005/Atom");
		map.put("atom", "http://www.w3.org/2005/Atom");
		map.put("batch", "http://schemas.google.com/gdata/batch");
		map.put("gAcl", "http://schemas.google.com/acl/2007");
		map.put("gCal", "http://schemas.google.com/gCal/2005");
		map.put("gd", "http://schemas.google.com/g/2005");
		map.put("georss", "http://www.georss.org/georss");
		map.put("gml", "http://www.opengis.net/gml");
		map.put("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
		map.put("xml", "http://www.w3.org/XML/1998/namespace");
	}
}
