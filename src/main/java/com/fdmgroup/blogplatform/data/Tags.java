package com.fdmgroup.blogplatform.data;

import java.util.Arrays;
import java.util.List;

public class Tags {
	public static List<String> getTags(){
		return Arrays.asList(BlogTags.class.getEnumConstants())
				.stream().map(tag -> tag.toString())
				.toList();
		
	}
}
