package com.rssfeed.helper;

import java.util.Comparator;

import org.json.JSONObject;

public class ReverseOrder implements Comparator<RssFeedStructure>{

public int compare(RssFeedStructure o1, RssFeedStructure o2) {
return (o2.getTitle()).compareTo(o1.getTitle());
}
}