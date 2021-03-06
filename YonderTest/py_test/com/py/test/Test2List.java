package com.py.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * 基于TreeSet的定长循环顺序列表
 * 保留按id升序的最大的指定个节点，可以动态添加、移除节点。
 * 
 * @author "yonder"
 *
 * @param <T>
 */
public class Test2List<T> implements Iterable<T> {
	
	private int maxSize;
	
	private TreeSet<T> nodes;
	
	/**
	 * 创建节点列表
	 * @param maxSize 节点个数上限
	 */
	public Test2List(int maxSize, Comparator<T> comparator) {
		this.maxSize = maxSize;
		nodes = new TreeSet<T>(comparator);
	}
	/**
	 * 打印当前内部数组情况
	 */
	public void printNodeArray() {
		StringBuilder sb = new StringBuilder();
		for (T node : nodes) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(node);
		}
		System.out.println(sb.toString());
//		System.out.println("count:" + nodes.size());
	}
	/**
	 * 打印当前所有节点
	 */
	public void printNodeList() {
		StringBuilder sb = new StringBuilder();
		forEachNodeList(nodes.size(), node ->{
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(node);
		});
		System.out.println(sb.toString());
	}
	/**
	 * 添加节点
	 * @param node
	 */
	public void addNode(T node) {
		nodes.add(node);
		if (nodes.size() > maxSize) {
			nodes.pollFirst();
		}
	}
	/**
	 * 获取指定位置节点
	 * @param index
	 * @return
	 */
	public T get(int index) {
		if (index < 0 || index >= nodes.size()) {
			throw new IndexOutOfBoundsException("max:" + (nodes.size() - 1));
		}
		Iterator<T> it = nodes.descendingIterator();
		int i = 0;
		while (it.hasNext()) {
			T node = it.next();
			if (i == index) {
				return node;
			}
			i++;
		}
		return null;
	}
	/**
	 * 移除指定位置节点
	 * @param index
	 */
	public void remove(int index) {
		if (index < 0 || index >= nodes.size()) {
			throw new IndexOutOfBoundsException("max:" + (nodes.size() - 1));
		}
		Iterator<T> it = nodes.descendingIterator();
		int i = 0;
		while (it.hasNext()) {
			it.next();
			if (i == index) {
				it.remove();
				break;
			}
			i++;
		}
	}
	/**
	 * 移除节点
	 * @param node
	 */
	public void removeNode(T node) {
		nodes.remove(node);
	}
	/**
	 * 循环遍历指定个节点
	 * @param size
	 * @param consumer
	 */
	public void forEachNodeList(int size, Consumer<T> consumer) {
		if (size <= 0) {
			return;
		}
		Iterator<T> it = nodes.descendingIterator();
		int count = 0;
		while (it.hasNext()) {
			T node = it.next();
			consumer.accept(node);
			count++;
			if (count >= size) {
				break;
			}
		}
	}
	/**
	 * 加载指定个节点
	 * @param size
	 * @return
	 */
	public List<T> loadNodeList(int size) {
		List<T> list = new ArrayList<T>();
		forEachNodeList(size, c -> list.add(c));
		return list;
	}
	/**
	 * 遍历小于目标id的指定个节点
	 * @param preNode
	 * @param inclusive
	 * @param size
	 * @param consumer
	 */
	public void forEachNodeList(T preNode, boolean inclusive, int size, Consumer<T> consumer) {
		if (size <= 0) {
			return;
		}
		Iterator<T> it = nodes.headSet(preNode, inclusive).descendingIterator();
		int count = 0;
		while (it.hasNext()) {
			T node = it.next();
			consumer.accept(node);
			count++;
			if (count >= size) {
				break;
			}
		}
	}
	/**
	 * 加载小于指定id的指定个节点
	 * @param preNode
	 * @param inclusive
	 * @param size
	 * @return
	 */
	public List<T> loadNodeList(T preNode, boolean inclusive, int size) {
		List<T> list = new ArrayList<T>();
		forEachNodeList(preNode, inclusive, size, c -> list.add(c));
		return list;
	}
	/**
	 * 当前节点个数
	 * @return
	 */
	public int getCount() {
		return nodes.size();
	}
	/**
	 * 节点个数上限
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}
	
	/**
	 * 清空列表
	 */
	public void clear() {
		nodes.clear();
	}

	@Override
	public Iterator<T> iterator() {
		return nodes.descendingIterator();
	}
	
}
