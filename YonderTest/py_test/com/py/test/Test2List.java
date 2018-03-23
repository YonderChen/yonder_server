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
public class Test2List<T extends Node> implements Iterable<T> {
	
	private int maxSize = 500;
	
	private TreeSet<Node> nodeSet = new TreeSet<Node>(new Comparator<Node>() {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.getId() - o2.getId();
		}
	});
	
	/**
	 * 创建节点列表
	 * @param maxSize 节点个数上限
	 */
	public Test2List(int maxSize) {
		this.maxSize = maxSize;
	}
	/**
	 * 打印当前所有节点
	 */
	public void printNodeList() {
		System.out.println(nodeSet.descendingSet());
	}
	/**
	 * 添加节点
	 * @param node
	 */
	public void addNode(T node) {
		nodeSet.add(node);
		if (nodeSet.size() > maxSize) {
			nodeSet.pollFirst();
		}
	}
	/**
	 * 获取指定位置节点
	 * @param index
	 * @return
	 */
	public T get(int index) {
		if (index < 0 || index >= nodeSet.size()) {
			throw new IndexOutOfBoundsException("max:" + (nodeSet.size() - 1));
		}
		Iterator<Node> it = nodeSet.descendingIterator();
		int i = 0;
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			T node = (T) it.next();
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
		if (index < 0 || index >= nodeSet.size()) {
			throw new IndexOutOfBoundsException("max:" + (nodeSet.size() - 1));
		}
		Iterator<Node> it = nodeSet.descendingIterator();
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
	public void remove(T node) {
		nodeSet.remove(node);
	}
	/**
	 * 移除指定id节点
	 * @param id
	 */
	public void removeNode(int id) {
		Node tempNode = new Node(id);
		nodeSet.remove(tempNode);
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
		Iterator<Node> it = nodeSet.descendingIterator();
		int count = 0;
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			T node = (T) it.next();
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
	 * @param preId
	 * @param size
	 * @param consumer
	 */
	public void forEachNodeList(int preId, int size, Consumer<T> consumer) {
		if (size <= 0) {
			return;
		}
		Node tempNode = new Node(preId);
		Iterator<Node> it = nodeSet.headSet(tempNode, true).descendingIterator();
		int count = 0;
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			T node = (T) it.next();
			consumer.accept(node);
			count++;
			if (count >= size) {
				break;
			}
		}
	}
	/**
	 * 加载小于指定id的指定个节点
	 * @param preId
	 * @param size
	 * @return
	 */
	public List<T> loadNodeList(int preId, int size) {
		List<T> list = new ArrayList<T>();
		forEachNodeList(preId, size, c -> list.add(c));
		return list;
	}
	/**
	 * 当前节点个数
	 * @return
	 */
	public int getCount() {
		return nodeSet.size();
	}
	/**
	 * 节点个数上限
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return (Iterator<T>) nodeSet.descendingIterator();
	}
	
}
