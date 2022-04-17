package io.github.jihch.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

@Component
public class QuestionRandom {

	
	public List<Entry<String, Integer>> random(List<Entry<String, Integer>> src, int n) {
		
		List<Entry<String, Integer>> ret = new ArrayList<>(src);
		
		Random r = new Random();
		
		int size = ret.size();
		
		//随机若干
		int loop = size - n;//要n个，就是随机删除 -n 个
		
		for (int i = 0; i < loop; i++) {
			
			size = ret.size();
			
			//生成下标 0到tempSize
			int x = r.nextInt(size);
			
			ret.remove(x);
			
		}
		
		//打乱顺序
		Collections.shuffle(ret);
		
		return ret;
	}
	
}
