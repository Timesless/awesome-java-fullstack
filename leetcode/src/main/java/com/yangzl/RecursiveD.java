package com.yangzl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzl
 * @date 2020/4/12 00:23
 *   递归
 */
public class RecursiveD {

	// =======================================================================
	// 生成杨辉三角
	// =======================================================================

	List<List<Integer>> rs;
	int len;
	public List<List<Integer>> generate(int numRows) {
		rs = new ArrayList<>(numRows);
		len = numRows;
		recursiveGenerate(1);
		return rs;
	}
	private void recursiveGenerate(int idx) {
		if (idx > len) return;
		List<Integer> list = new ArrayList<>(idx);
		for(int i = 0; i < idx; ++i) {
			if (i > 0 && i < idx - 1) {
				List<Integer> last = rs.get(idx - 2);
				list.add(last.get(i - 1) + last.get(i));
			} else
				list.add(1);
		}
		rs.add(list);
		recursiveGenerate(idx + 1);
	}
	@Test
	public void testGenerate() { System.out.println(generate(5)); }
	
	// =======================================================================
	//  获取杨辉三角第 rowIndex 行
	// =======================================================================

	public List<Integer> getRow(int rowIndex) {
		if (rowIndex == 0) return new ArrayList<Integer>(){{add(1);}};
		if (rowIndex == 1) return new ArrayList<Integer>(){{add(1);add(1);}};

		List<Integer> list = new ArrayList<>(rowIndex + 1);
		list.add(0, 1);
		for (int i = 1; i < rowIndex; ++i) {
			List<Integer> last = getRow(rowIndex - 1);
			list.add(i, last.get(i-1) + last.get(i));
		}
		list.add(rowIndex, 1);
		return list;
	}
	@Test
	public void testGetRow() { System.out.println(getRow(4)); }

}
