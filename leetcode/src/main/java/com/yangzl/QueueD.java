package com.yangzl;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author yangzl
 * @date 2020/2/13 11:11
 *
 * 队列相关问题
 */
public class QueueD {

	/**
	 * @date 2020/2/12
	 *  347.前k个高频元素（优先队列，标准库中为最小堆实现）
	 */
	public static List<Integer> topKFrequent(int[] nums, int k) {
		Map<Integer, Integer> map = new TreeMap<>();
		for (int tmp : nums) {
			if (map.containsKey(tmp))
				// map.merge(tmp, 1, Integer::sum)
				map.put(tmp, map.get(tmp) + 1);
			else
				map.put(tmp, 1);
		}
		// 匿名内部类或局部类可以捕获final，effctively变量，所以直接使用map
		PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(map::get));
		// 存入map，只存了k，但却是按v排序的
		map.keySet().forEach(key -> queue.offer(key));
		int remain = queue.size() - k;
		for (int i = 0; i < remain; i++)
			queue.poll();
		return queue.stream().sorted().collect(Collectors.toList());
	}
	@Test
	public void test1() {
		int[] nums = { 1 };
		System.out.println(topKFrequent(nums, 1));
	}

	
}
