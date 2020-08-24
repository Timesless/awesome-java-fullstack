package com.yangzl.thread.printinorder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yangzl
 * @Date: 2020/1/18 10:43
 * @Desc: .. CountDownLatch 所有人离开了再锁门
 * 			CyclicBarrier 七颗龙珠召唤神龙
 */
public class PrintInorderCdl {

	private CountDownLatch latch1 = new CountDownLatch(1);
	private CountDownLatch latch2 = new CountDownLatch(2);
	
	public void first(Runnable printFirst) {
		printFirst.run();
		latch1.countDown();
		latch2.countDown();
	}

	public void second(Runnable printSecond) throws InterruptedException {
		latch1.await();
		printSecond.run();
		latch2.countDown();
	}

	public void third(Runnable printThird) throws InterruptedException {
		latch2.await();
		printThird.run();
	}

	public static void main(String[] args) {
		PrintInorderCdl cdl = new PrintInorderCdl();
		new Thread(() -> {
			try {
				cdl.third(() -> System.out.println("three"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
		new Thread(() -> {
			try {
				cdl.second(() -> System.out.println("two"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> cdl.first(() -> System.out.println("one"))).start();
	}
}