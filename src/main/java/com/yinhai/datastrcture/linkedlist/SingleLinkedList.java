package com.yinhai.datastrcture.linkedlist;

/**
 * @Author: yangzl
 * @Date: 2019/10/22 19:53
 * @Desc:
 * 	带头节点的单链表
 * 	由于head节点始终标识单链表开始，所以head节点不变，需定义临时节点来遍历链表
 *      size可以不用定义，有2种实现方式，一种通过size获取到元素总数，一种每次都遍历整个链表取值， 下列方法使用2种混合实现。
 **/
public class SingleLinkedList {

	// 数据有效个数
	private int size;
	// 头节点不存储数据
	private Node head;
	public SingleLinkedList() { this.head = new Node(-1); }
	
	// 添加到尾节点
	public void add(int n) {
		Node tmp = head;
		while (tmp.next != null) {
			tmp = tmp.next;
		}
		tmp.next = new Node(n);
		size++;
	}
	// 索引位置添加
	public void add(int index, int n) {
		rangeCheck(index);
		// 可以取第一个元素，或者哑元节点
		Node tmp = head;
		// 找到要添加的位置。即index前一个
		for(int x = 0; x < index; ++x) {
			tmp = tmp.next;
		}
		Node node = new Node(n);
		node.next = tmp.next;
		tmp.next = node;
		size++;
	}
	
	// 从尾节点删除
	public void remove() { this.remove(size - 1); }
	/*
	 * 注：这里未实现通过元素删除，因为这里数据类型和index类型都是int
	 */
	// 索引删除
	public int remove(int index) {
		rangeCheck(index);
		Node tmp = head;
		// tmp = 要删除元素的前一个元素
		for (int x = 0; x < index; ++x)
			tmp = tmp.next;
		Node remove = tmp.next;
		int rs = remove.val;
		// 如果要删除元素的后一个为null，那么当前元素是最后一个。置为null即可
		if(null == remove.next)
			tmp.next = null;
		if(null != remove.next)
			tmp.next = remove.next;
		size--;
		return rs;
	}
	
	/*
	 * 获取单链表倒数第k个元素
	 * 双指针法
	 * 第一个指针向后移动k步时，第二个指针开始向后移动，当第一个指针到达尾节点，第二个指针刚好指向k last
	 */
	public int getKlastVal(int k) {
		rangeCheck(k);
		Node first = head.next;
		Node second = head.next;
		while (null != first) {
			if(k <= 0) {
				second = second.next;
			}
			k--;
			first = first.next;
		}
		return second.val;
	}
	
	/*
	 * 反转当前链表
	 * head -> 1 -> 2 -> 3
	 * reverseHead采用头插 -> 3 -> 2 -> 1
	 * 最后将head.next = reverseHead.next | 返回 reverseHead.next 错误，只会返回最后一个元素，即1;
	 * 像单链表的操作一般不返回值，返回值只会有当前一个node会被toString()
	 */
	public void reverseList() {
		if(size < 1)
			return;
		// 倒序节点，遍历原链表采用头插链接在reverseHead后
		Node reverseHead = new Node(-1);
		Node cur = head.next;
		Node next = null;
		while (null != cur) {
			next = cur.next;	//保存下一个引用
			// 头插
			cur.next = reverseHead.next;
			reverseHead.next = cur;
			cur = next;
		}
		/*Node cur = head.next;
		Node node;
		while (null != cur) {
			node = new Node(cur.val);
			node.next = reverseHead.next;
			reverseHead.next = node;
			cur = cur.next;
		}*/
		head.next = reverseHead.next;
	}
	
	/**
	 * 边界校验，确定index在有效范围
	 **/
	private void rangeCheck(int index) {
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException("索引不在有效范围内");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		if(head.next == null) {
			sb.append("]");
			return sb.toString();
		}
		// 这里tmp指向第一个元素比较好，可以巧妙打印单链表，如果指向头节点，那么while种是判断当前节点是否null，不好处理 -> 符号
		Node tmp = head.next;
		while (tmp.next != null) {
			sb.append(tmp.val).append(" -> ");
			tmp = tmp.next;
		}
		sb.append(tmp.val);
		return sb.append("]").toString();
	}


	// main
	public static void main(String[] args) {
		SingleLinkedList list = new SingleLinkedList();
		list.add(1);
		
		// 测试删除
		list.remove();
		System.out.println(list);
	}
	
	

}