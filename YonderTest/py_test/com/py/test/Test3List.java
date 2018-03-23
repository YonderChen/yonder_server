package com.py.test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

import com.py.tools.GsonTools;

/**
 * 基于数组的定长循环顺序列表
 * 保留按id升序的最大的指定个节点，可以动态添加、移除节点。
 * 
 * @author "yonder"
 *
 * @param <T>
 */
public class Test3List<T extends Node> implements Iterable<T> {
	
	private int curIndex = -1;
	
	private Node[] nodes;
	
	private int count = 0;

	private int minIndex = -1;

	/**
	 * 创建节点列表
	 * @param maxSize 节点个数上限
	 */
	public Test3List(int maxSize) {
		nodes = new Node[maxSize];
	}
	/**
	 * 打印当前内部数组情况
	 */
	public void printNodeArray() {
		System.out.println(GsonTools.toJsonString(nodes));
		System.out.println("minIndex:" + minIndex  + " _ curIndex:" + curIndex + " _ count:" + count);
	}
	/**
	 * 打印当前所有节点
	 */
	public void printNodeList() {
		System.out.println(loadNodeList(count));
	}
	/**
	 * 添加节点
	 * @param node
	 */
	public void addNode(T node) {
		if (count == 0) {
			curIndex = 0;
			minIndex = 0;
			nodes[curIndex] = node;
			count++;
		} else {
			Node minNode = nodes[minIndex];
			if (node.getId() == minNode.getId()) {
				nodes[minIndex] = node;
				return;
			}
			Node maxNode = nodes[curIndex];
			if (node.getId() == maxNode.getId()) {
				nodes[curIndex] = node;
				return;
			}
			if (node.getId() > maxNode.getId()) {//往后添加节点
				curIndex++;
				if (curIndex >= nodes.length) {
					curIndex = 0;
				}
				if (nodes[curIndex] == null) {
					count++;
				} else {
					minIndex++;
					if (minIndex >= nodes.length) {
						minIndex = 0;
					}
				}
				nodes[curIndex] = node;
			} else if (node.getId() < minNode.getId()) {//往前添加节点，如果已经满员了就无法添加
				int minPreIndex = minIndex - 1;
				if (minPreIndex < 0) {
					minPreIndex = nodes.length - 1;
				}
				if (nodes[minPreIndex] != null) {//已经满了
					return;
				}
				nodes[minPreIndex] = node;
				minIndex = minPreIndex;
				count++;
			} else {//在中间插入节点
				int hitNodeIndex = findIndex(nodes, node.getId(), false);
				if (hitNodeIndex < 0) {//找不到位置
					return;
				}
				if (nodes[hitNodeIndex].getId() == node.getId()) {//id匹配直接覆盖
					nodes[hitNodeIndex] = node;
				} else {
					int minPreIndex = minIndex - 1;
					if (minPreIndex < 0) {
						minPreIndex = nodes.length - 1;
					}
					if (nodes[minPreIndex] == null) {
						nodes[minPreIndex] = nodes[minIndex];
						minIndex = minPreIndex;
						count++;
					}
					if (hitNodeIndex >= minIndex) {
						for (int i = minIndex; i < hitNodeIndex; i++) {
							nodes[i] = nodes[i + 1];
						}
					} else {
						for (int i = minIndex; i < nodes.length - 1; i++) {
							nodes[i] = nodes[i + 1];
						}
						nodes[nodes.length - 1] = nodes[0];
						for (int i = 0; i < hitNodeIndex; i++) {
							nodes[i] = nodes[i + 1];
						}
					}
					nodes[hitNodeIndex] = node;
				}
			}
		}
	}
	/**
	 * 获取指定位置节点
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("max:" + (count - 1));
		}
		int refIndex = getDescRefIndex(index);
		return (T) nodes[refIndex];
	}
	/**
	 * 移除指定位置节点
	 * @param index
	 */
	public void remove(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("max:" + (count - 1));
		}
		int refIndex = getDescRefIndex(index);
		removeNodeByIndex(refIndex);
	}
	/**
	 * 移除节点
	 * @param node
	 */
	public void remove(T node) {
		removeNode(node.getId());
	}

	/**
	 * 
	 * @param nodes
	 * @param id
	 * @param needHit 是否需要命中, false如果没有命中返回小于指定id并且最接近的位置索引
	 * @return
	 */
	private int findIndex(Node[] nodes, int id, boolean needHit) {
//		System.out.println("id:" + id);
//		System.out.println("nodes:" + GsonTools.toJsonString(nodes));
		int index = findIndex(nodes, 0, count - 1, id, needHit);
//		System.out.println("find index:" + index);
//		System.out.println("curIndex:" + curIndex);
		if (index < 0) {
			return -1;
		}
		return getRefIndex(index);
	}
	
	private int getRefIndex(int index) {
		return (index + curIndex + 1 + nodes.length - count) % nodes.length;
	}
	
	private int getDescRefIndex(int index) {
		return (curIndex - index  + nodes.length) % nodes.length;
	}
	
	private int findIndex(Node[] nodes, int beginIndex, int endIndex, int id, boolean needHit) {
//		System.out.println("beginIndex:" + beginIndex);
//		System.out.println("endIndex:" + endIndex);
		int refBeginIndex = getRefIndex(beginIndex);
		int refEndIndex = getRefIndex(endIndex);
//		System.out.println("refBeginIndex:" + refBeginIndex);
//		System.out.println("refEndIndex:" + refEndIndex);
		if (nodes[refBeginIndex].getId() == id) {
			return refBeginIndex;
		}
		if (nodes[refEndIndex].getId() == id) {
			return refEndIndex;
		}
		if (nodes[refBeginIndex].getId() > id) {
			return -1;
		}
		if (nodes[refEndIndex].getId() < id) {
			if (needHit) {
				return -1;
			} else {
				return endIndex;
			}
		}
		if (beginIndex == endIndex - 1) {
			if (nodes[refBeginIndex].getId() == id) {
				return beginIndex;
			} else if (nodes[refEndIndex].getId() == id) {
				return endIndex;
			} else {
				if (needHit) {
					return -1;
				} else {
					return beginIndex;
				}
			}
		} else {
//			int midIndex = (beginIndex + endIndex) / 2;
			int midIndex = beginIndex + (int)(((float)(id - nodes[refBeginIndex].getId())/(nodes[refEndIndex].getId()-nodes[refBeginIndex].getId()))*(endIndex -beginIndex));
			if (midIndex == beginIndex) {
				midIndex= beginIndex + 1;
			}
			if (midIndex == endIndex) {
				midIndex = endIndex - 1;
			}
			int refMidIndex = getRefIndex(midIndex);
			int midId = nodes[refMidIndex].getId();
			if (midId == id) {
				return midIndex;
			} else if (midId > id) {
				return findIndex(nodes, beginIndex, midIndex, id, needHit);
			} else {
				return findIndex(nodes, midIndex, endIndex, id, needHit);
			}
		}
	}
	
	private void removeNodeByIndex(int remIndex) {
		if (remIndex >= 0 && remIndex < nodes.length) {
			if (remIndex == curIndex) {
				nodes[remIndex] = null;
				curIndex--;
				if (curIndex < 0) {
					curIndex = nodes.length - 1;
				}
				count--;
			} else {
				if (remIndex < curIndex) {
					for (int i = remIndex; i < curIndex; i++) {
						nodes[i] = nodes[i + 1];
					}
					nodes[curIndex] = null;
				} else {
					for (int i = remIndex; i < nodes.length - 1; i++) {
						nodes[i] = nodes[i + 1];
					}
					nodes[nodes.length - 1] = nodes[0];
					for (int i = 0; i < curIndex; i++) {
						nodes[i] = nodes[i + 1];
					}
					nodes[curIndex] = null;
				}
				curIndex--;
				if (curIndex < 0) {
					curIndex = nodes.length - 1;
				}
				count--;
			}
			if (count <= 0) {
				curIndex = -1;
				minIndex = -1;
				count = 0;
			}
		}
	}
	/**
	 * 移除指定id节点
	 * @param id
	 */
	public void removeNode(int id) {
		int index = findIndex(nodes, id, true);
		if (index >= 0) {
			removeNodeByIndex(index);
		}
	}
	
	private void forEachNodeListByTargetIndex(int targetIndex, int size, Consumer<T> consumer) {
		if (size <= 0) {
			return;
		}
		int count = 0;
		for (int i = targetIndex; i >= 0; i--) {
			if (count >= size) {
				return;
			}
			if (nodes[i] == null) {
				return;
			}
			count++;
			@SuppressWarnings("unchecked")
			T node = (T) nodes[i];
			consumer.accept(node);
			if (i == minIndex) {
				return;
			}
		}
		for (int i = nodes.length - 1; i > targetIndex; i--) {
			if (count >= size) {
				return;
			}
			if (nodes[i] == null) {
				return;
			}
			count++;
			@SuppressWarnings("unchecked")
			T node = (T) nodes[i];
			consumer.accept(node);
			if (i == minIndex) {
				return;
			}
		}
	}
	/**
	 * 循环遍历指定个节点
	 * @param size
	 * @param consumer
	 */
	public void forEachNodeList(int size, Consumer<T> consumer) {
		loadNodeListByTargetIndex(curIndex, size, consumer);
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
		int index = findIndex(nodes, preId, false);
		if (index < 0) {
			return;
		}
		loadNodeListByTargetIndex(index, size, consumer);
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
	
	private void loadNodeListByTargetIndex(int targetIndex, int size, Consumer<T> consumer) {
		forEachNodeListByTargetIndex(targetIndex, size, consumer);
	}
	/**
	 * 当前节点个数
	 * @return
	 */
	public int getCount() {
		return count;
	}
	/**
	 * 节点个数上限
	 * @return
	 */
	public int getMaxSize() {
		return nodes.length;
	}
	
	/**
	 * 清空列表
	 */
	public void clear() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = null;
		}
		curIndex = -1;
		minIndex = -1;
		count = 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new Itr();
	}


    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<T> {
        int cursor = curIndex;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        boolean isEnd = false;

        public boolean hasNext() {
        	if (isEnd) {
				return false;
			}
        	if (count == 0 || cursor < 0 || cursor > nodes.length) {
				return false;
			}
			return nodes[cursor] != null;
        }

		@SuppressWarnings("unchecked")
		public T next() {
            int i = cursor;
            if (i < 0 || i >= nodes.length || !hasNext())
                throw new NoSuchElementException();
            cursor--;
            if (cursor < 0) {
				cursor = nodes.length - 1;
			}
            if (nodes[lastRet = cursor] == null || cursor == curIndex) {
				isEnd = true;
			}
            return (T) nodes[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
            	removeNodeByIndex(lastRet);
//                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            while (hasNext()) {
            	T node = next();
				consumer.accept(node);
			}
        }
    }
	
}