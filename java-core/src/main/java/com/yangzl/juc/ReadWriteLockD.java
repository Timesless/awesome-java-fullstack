package com.yangzl.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @author yangzl
 * @date 2020/1/5 00:06
 * @desc .. Interface ReadWriteLock
 *
 * 	ReentrantReadWriteLock 高16位表示读锁，低16位表示写锁
 *
 */
public class ReadWriteLockD {

	private final Map<String, Object> map = new HashMap<>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	Lock writeLock = this.lock.writeLock();
	Lock readLock = this.lock.readLock();

	private void put(Object val) {
		writeLock.lock();
		try {
			System.out.println("写数据...");
			map.put(Thread.currentThread().getName(), val);
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("写数据完成...");
		} finally {
			writeLock.unlock();
		}
	}
	
	private Object get() {
		Object rs = null;
		readLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "读数据...");
			rs = map.get(Thread.currentThread().getName());
			System.out.println(Thread.currentThread().getName() + "读完成");
		} finally {
			readLock.unlock();
		}
		return rs;
	}

	public static void main(String[] args) {
		ReadWriteLockD d = new ReadWriteLockD();
		IntStream.rangeClosed(1, 4).forEach(
				x -> {
					new Thread(() -> d.put(x)).start();
					new Thread(() -> d.get()).start();
					new Thread(() -> d.get()).start();
					new Thread(() -> d.get()).start();
				}
		);
	}
}
