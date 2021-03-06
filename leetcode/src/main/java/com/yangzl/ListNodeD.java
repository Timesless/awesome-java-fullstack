package com.yangzl;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yangzl
 * @date 2020/4/12 01:07
 */
public class ListNodeD {

	private static class ListNode {
		int val;
		ListNode next;

		public ListNode(int val) {
			this.val = val;
		}

		@Override
		public String toString() {
			StringBuilder bd = new StringBuilder(20);
			ListNode p = this;
			while (p.next != null) {
				bd.append(p.val).append(" -> ");
				p = p.next;
			}
			bd.append(p.val);
			return bd.toString();
		}

		/**
		 * 2020/11/29 头插
		 *
		 * @param val 新增节点值
		 * @return ListNode
		 */
		public ListNode addHead(int val) {
			ListNode dummy = new ListNode(-1);
			dummy.next = this;
			// dummy new this
			ListNode newNode = new ListNode(val);
			newNode.next = dummy.next;
			dummy.next = newNode;
			return newNode;
		}

		/**
		 * 2020/11/29 尾插
		 *
		 * @param val 新增节点值
		 * @return ListNode
		 */
		public ListNode addTail(int val) {
			// this next new
			ListNode p = this;
			while (p.next != null) {
				p = p.next;
			}
			p.next = new ListNode(val);
			return this;
		}
	}


	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode rs = reverseList(head.next);
		head.next.next = rs;
		head.next = null;
		return rs;
	}

	@Test
	public void testReverseList() {
		ListNode ft = new ListNode(1);
		ListNode sd = new ListNode(2);
		ListNode td = new ListNode(3);
		ListNode fh = new ListNode(4);
		ft.next = sd;
		sd.next = td;
		td.next = fh;
		System.out.println(reverseList(ft));
	}

	public List<String> stringMatching(String[] words) {
		int ln = words.length;
		Set<String> rs = new HashSet<>();
		if (ln == 0 || ln == 1) return new ArrayList<>();
		for (int i = 0; i < ln; ++i)
			for (int j = i + 1; j < ln; ++j) {
				if (words[i].length() > words[j].length()) {
					if (words[i].contains(words[j]))
						rs.add(words[j]);
				} else {
					if (words[j].contains(words[i]))
						rs.add(words[i]);
				}
			}

		return new ArrayList<>(rs);
	}

	@Test
	public void testStringMatching() {
		String[] strs = {"leetcoder", "leetcode", "od", "od"};
		System.out.println(stringMatching(strs));
	}

	public String entityParser(String text) {
		int ln = text.length();
		if (ln < 4) return text;
		Map<String, String> map = new HashMap<>(8);
		map.put("&quot;", "\"");
		map.put("&apos;", "'");
		map.put("&amp;", "&");
		map.put("&gt;", ">");
		map.put("&lt;", "<");
		map.put("&frasl;", "/");
		for (Map.Entry<String, String> node : map.entrySet()) {
			String k = node.getKey();
			if (text.indexOf(k) >= 0)
				text = text.replace(k, node.getValue());
		}
		return text;
	}

	@Test
	public void testEntityParse() {
		System.out.println(entityParser("and I quote: &quot;...&quot;"));
	}

	public int[] processQueries(int[] queries, int m) {
		if (m == 1) return queries;
		List<Integer> list = IntStream.rangeClosed(1, m)
				.boxed().collect(Collectors.toCollection(LinkedList::new));
		int[] rs = new int[queries.length];
		for (int i = 0; i < queries.length; ++i) {
			rs[i] = list.indexOf(queries[i]);
			list.remove((Object) queries[i]);
			list.add(0, queries[i]);
		}
		return rs;
	}

	@Test
	public void testQuires() {
		int[] quires = {4, 1, 2, 2};
		System.out.println(Arrays.toString(processQueries(quires, 4)));
	}

	String[] dp;

	public int kthGrammar(int N, int K) {
		if (N == 1) return 0;
		dp = new String[N + 1];
		String str = getKthStr(N);
		return str.charAt(K - 1) - 48;
	}

	private String getKthStr(int N) {
		dp[1] = "0";
		dp[2] = "01";
		dp[3] = "0110";
		for (int i = 4; i <= N; ++i) {
			StringBuilder bd = new StringBuilder();
			for (char c : dp[i - 1].toCharArray()) {
				if (c == '0')
					bd.append("01");
				else if (c == '1')
					bd.append("10");
			}
			dp[i] = bd.toString();
		}
		return dp[N];
	}

	@Test
	public void testKthGrammer() {
		System.out.println(kthGrammar(7, 6));
		System.out.println(Arrays.toString(dp));

	}

	// ================================================================
	// 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
	// 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
	// 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
	// 输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
	// 输出：7 -> 8 -> 0 -> 7
	// ================================================================

	public ListNode addTwoNumbers(ListNode list1, ListNode list2) {
		ListNode pl = reverseList2(list1), pr = reverseList2(list2);
		ListNode dummyHead = new ListNode(-1), cur = dummyHead;
		int carry = 0;
		ListNode l1 = pl, l2 = pr;
		while (l1 != null || l2 != null) {
			int v1 = l1 == null ? 0 : l1.val, v2 = l2 == null ? 0 : l2.val, tmp = v1 + v2 + carry;
			/*
			 * TODO
			 * 这里请自行深入理解一下，不然每次都是凑，那不能在ide运行的时候呢。我真是个sb
			 */
			cur.next = new ListNode(tmp % 10);
			cur = cur.next;
			carry = tmp / 10;
			l1 = l1 == null ? l1 : l1.next;
			l2 = l2 == null ? l2 : l2.next;
		}
		if (carry != 0) cur.next = new ListNode(carry);
		return reverseList2(dummyHead.next);
	}

	private ListNode reverseList2(ListNode head) {
		if (head == null || head.next == null) return head;

		ListNode rs = reverseList2(head.next);
		head.next.next = head;
		head.next = null;
		return rs;
	}

	@Test
	public void testAddTwoNumbers() {
		ListNode l1 = new ListNode(7);
		ListNode l12 = new ListNode(2);
		ListNode l13 = new ListNode(4);
		ListNode l14 = new ListNode(3);
		l1.next = l12;
		l12.next = l13;
		l13.next = l14;
		ListNode l2 = new ListNode(5);
		ListNode l21 = new ListNode(6);
		ListNode l22 = new ListNode(4);
		l2.next = l21;
		l21.next = l22;
		System.out.println(addTwoNumbers(l1, l2));
	}

	@Test
	public void testAddTwoNumbers2() {
		ListNode l1 = new ListNode(9);
		ListNode l12 = new ListNode(9);
		l1.next = l12;
		ListNode l2 = new ListNode(1);
		System.out.println(addTwoNumbers(l1, l2));
	}

	// 合并k个有序链表
	// TODO
	public ListNode mergeKLists(ListNode[] lists) {

		int ln = lists.length;
		ListNode dummyHead = new ListNode(-1), p = dummyHead;
		for (int i = 0; i < ln; ++i) {
			ListNode cur = lists[i], next;
			if (cur != null) {
			}
		}

		return dummyHead.next;
	}


	/**
	 * 2020/11/29 将链表1的 a,b 之间的元素替换为 list2. list1 = 1 -> 2 -> 3 -> 4 list2 = 11 -> 12
	 * 							a = 1, b = 2 => rs: 1 -> 11 -> 12 -> 4
	 * 
	 * @param list1 链表1
	 * @param  a start
	 * @param  b end
	 * @param  list2 链表2
	 * @return ListNode
	 */
	public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {

		ListNode dummy = new ListNode(-1);
		dummy.next = list1;
		int gap = b - a + 1;
		ListNode fast = dummy, slow = dummy;
		for (int i = 0; i < gap; ++i) {
			fast = fast.next;
		}
		// 当slow走到a指向的节点时，fast走到b指向的节点
		for (int i = 0; i < a; ++i) {
			slow = slow.next;
			fast = fast.next;
		}
		
		slow.next = list2;
		while (list2.next != null) {
			list2 = list2.next;
		}
		list2.next = fast.next;
		
		return dummy.next;
	}
	@Test
	public void testMergeInBetween() {
		ListNode l = new ListNode(1).addHead(2).addHead(3).addHead(4);
		ListNode t = new ListNode(11).addTail(12).addTail(13).addTail(14);
		System.out.println(mergeInBetween(l, 1, 2, t));
	}

}
