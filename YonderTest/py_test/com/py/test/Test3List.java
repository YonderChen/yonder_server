package com.py.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 基于数组的定长循环顺序列表
 * 保留按id升序的最大的指定个节点，可以动态添加、移除节点。
 * 
 * @author "yonder"
 *
 * @param <T>
 */
public class Test3List<T> implements Iterable<T> {
	
	private int curIndex = -1;
	
	private T[] nodes;
	
	private int count = 0;

	private int minIndex = -1;
	
	private Comparator<T> comparator;
	
	private int modCount = 0;
	
	/**
	 * 创建节点列表
	 * @param maxSize 节点个数上限
	 */
	@SuppressWarnings("unchecked")
	public Test3List(int maxSize, Comparator<T> comparator) {
		this.nodes = (T[]) new Object[maxSize];
		this.comparator = comparator;
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
		System.out.println("minIndex:" + minIndex  + " _ curIndex:" + curIndex + " _ count:" + count);
	}
	/**
	 * 打印当前所有节点
	 */
	public void printNodeList() {
		StringBuilder sb = new StringBuilder();
		forEachNodeList(count, node ->{
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
        modCount++;
		if (count == 0) {
			curIndex = 0;
			minIndex = 0;
			nodes[curIndex] = node;
			count++;
		} else {
			T minNode = nodes[minIndex];
			int minCpVal = comparator.compare(node, minNode);
			if (minCpVal == 0) {
				nodes[minIndex] = node;
				return;
			}
			T maxNode = nodes[curIndex];
			int maxCpVal = comparator.compare(node, maxNode);
			if (maxCpVal == 0) {
				nodes[curIndex] = node;
				return;
			}
			if (maxCpVal > 0) {//往后添加节点
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
			} else if (minCpVal < 0) {//往前添加节点，如果已经满员了就无法添加
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
				int hitNodeIndex = findIndex(nodes, node, false);
				if (hitNodeIndex < 0) {//找不到位置
					return;
				}
				if (comparator.compare(nodes[hitNodeIndex], node) == 0) {//id匹配直接覆盖
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
	public T get(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("max:" + (count - 1));
		}
		int refIndex = getDescRefIndex(index);
		return nodes[refIndex];
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
	public void removeNode(T node) {
		int index = findIndex(nodes, node, true);
		if (index >= 0) {
			removeNodeByIndex(index);
		}
	}

	/**
	 * 
	 * @param nodes
	 * @param id
	 * @param needHit 是否需要命中, false如果没有命中返回小于指定id并且最接近的位置索引
	 * @return
	 */
	private int findIndex(T[] nodes, T targetNode, boolean needHit) {
//		System.out.println("id:" + id);
//		System.out.println("nodes:" + GsonTools.toJsonString(nodes));
		int index = findIndex(nodes, 0, count - 1, targetNode, needHit);
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
	
	private int findIndex(T[] nodes, int beginIndex, int endIndex, T targetNode, boolean needHit) {
//		System.out.println("beginIndex:" + beginIndex);
//		System.out.println("endIndex:" + endIndex);
		int refBeginIndex = getRefIndex(beginIndex);
		int refEndIndex = getRefIndex(endIndex);
//		System.out.println("refBeginIndex:" + refBeginIndex);
//		System.out.println("refEndIndex:" + refEndIndex);
		int beginCpVal = comparator.compare(nodes[refBeginIndex], targetNode);
		int endCpVal = comparator.compare(nodes[refEndIndex], targetNode);
		if (beginCpVal == 0) {
			return beginIndex;
		}
		if (endCpVal == 0) {
			return endIndex;
		}
		if (beginCpVal > 0) {
			return -1;
		}
		if (endCpVal < 0) {
			if (needHit) {
				return -1;
			} else {
				return endIndex;
			}
		}
		if (beginIndex == endIndex - 1) {
			if (needHit) {
				return -1;
			} else {
				return beginIndex;
			}
		} else {
			int midIndex = (beginIndex + endIndex) / 2;
			//数字比较可以用下面方式计算二分法区间进行优化
//			int midIndex = beginIndex + (int)(((float)(id - nodes[refBeginIndex].getId())/(nodes[refEndIndex].getId()-nodes[refBeginIndex].getId()))*(endIndex -beginIndex));
			if (midIndex == beginIndex) {
				midIndex= beginIndex + 1;
			}
			if (midIndex == endIndex) {
				midIndex = endIndex - 1;
			}
			int refMidIndex = getRefIndex(midIndex);
			int midCpVal = comparator.compare(nodes[refMidIndex], targetNode);
			if (midCpVal == 0) {
				return midIndex;
			} else if (midCpVal > 0) {
				return findIndex(nodes, beginIndex, midIndex, targetNode, needHit);
			} else {
				return findIndex(nodes, midIndex, endIndex, targetNode, needHit);
			}
		}
	}
	
	private void removeNodeByIndex(int remIndex) {
		if (remIndex >= 0 && remIndex < nodes.length) {
	        modCount++;
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
			consumer.accept(nodes[i]);
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
			consumer.accept(nodes[i]);
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
	 * @param preNode
	 * @param inclusive
	 * @param size
	 * @param consumer
	 */
	public void forEachNodeList(T preNode, boolean inclusive, int size, Consumer<T> consumer) {
		int index = findIndex(nodes, preNode, false);
		if (index < 0) {
			return;
		}
		if (!inclusive) {//不包含preNode
			if (comparator.compare(nodes[index], preNode) == 0) {//存在preNode
				index--;
				if (index < 0) {
					index = nodes.length - 1;
				}
				if (nodes[index] == null) {//上一个不存在
					return;
				}
			}
		}
		loadNodeListByTargetIndex(index, size, consumer);
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
        modCount++;
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
        int expectedModCount = modCount;

        public boolean hasNext() {
        	if (isEnd) {
				return false;
			}
        	if (count == 0 || cursor < 0 || cursor > nodes.length) {
				return false;
			}
			return nodes[cursor] != null;
        }

		public T next() {
            checkForComodification();
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
            return nodes[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
            	removeNodeByIndex(lastRet);
//                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            while (hasNext() && modCount == expectedModCount) {
            	T node = next();
				consumer.accept(node);
			}
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
	
}
