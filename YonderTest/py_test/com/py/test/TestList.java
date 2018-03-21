package com.py.test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class TestList<T extends PObj> implements Iterable<Node<T>> {
	
	private int curIndex = -1;
	
	private int curNodeId = 0;

	private Node<?>[] nodes;
	
	private int count = 0;
	
	private int minId = 0;
	
	private int maxId = 0;
	
//	private static Random random = new Random();
	
	private List<Node<T>> ListObject = new ArrayList<Node<T>>(20);
	
	public TestList(int maxSize) {
		nodes = new Node[maxSize];
	}
	
	public void addNode(T obj) {
		curIndex++;
		if (curIndex >= nodes.length) {
			curIndex = 0;
		}
		curNodeId++;
		maxId = curNodeId;
		if (minId == 0) {
			minId = curNodeId;
		}
//		nodeId += random.nextInt(10) + 1;
		Node<T> node = new Node<T>();
		node.setId(curNodeId);
		node.setObj(obj);
		if (nodes[curIndex] == null) {
			count++;
		} else {
			Node<?> MinIdNode = nodes[(curIndex + 1) % nodes.length];
			if (MinIdNode == null) {
				minId = maxId;
			} else {
				minId = nodes[(curIndex + 1) % nodes.length].getId();
			}
		}
		nodes[curIndex] = node;
	}
	
	@SuppressWarnings("unchecked")
	public Node<T> get(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("max:" + (count - 1));
		}
		int refIndex = getDescRefIndex(index);
		return (Node<T>)nodes[getDescRefIndex(refIndex)];
	}
	
	public void remove(int index) {
		if (index < 0 || index >= count) {
			throw new IndexOutOfBoundsException("max:" + (count - 1));
		}
		int refIndex = getDescRefIndex(index);
		removeNodeByIndex(refIndex);
	}
	
	public void remove(Node<T> node) {
		removeNode(node.getId());
	}

	/**
	 * 
	 * @param nodes
	 * @param id
	 * @param needHit 是否需要命中, false如果没有命中返回小于指定id并且最接近的位置索引
	 * @return
	 */
	private int findIndex(Node<?>[] nodes, int id, boolean needHit) {
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
		return count - 1 - (index + curIndex + 1 + nodes.length - count) % nodes.length;
	}
	
	private int findIndex(Node<?>[] nodes, int beginIndex, int endIndex, int id, boolean needHit) {
//		System.out.println("beginIndex:" + beginIndex);
//		System.out.println("endIndex:" + endIndex);
		int refBeginIndex = getRefIndex(beginIndex);
		int refEndIndex = getRefIndex(endIndex);
//		System.out.println("refBeginIndex:" + refBeginIndex);
//		System.out.println("refEndIndex:" + refEndIndex);
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
				curIndex = (curIndex + nodes.length - 1) % nodes.length;
				count--;
				if (nodes[curIndex] != null) {
					maxId = nodes[curIndex].getId();
				}
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
				curIndex = (curIndex + nodes.length - 1) % nodes.length;
				count--;
				if (nodes[(remIndex + nodes.length - 1) % nodes.length] == null) {
					if (nodes[remIndex] != null) {
						minId = nodes[remIndex].getId();
					}
				}
			}
		}
	}

	public void removeNode(int id) {
		int index = findIndex(nodes, id, true);
		if (index >= 0) {
			removeNodeByIndex(index);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void forEachNodeListByTargetIndex(int targetIndex, int size, Consumer<? super Node<T>> consumer) {
		int count = 0;
		for (int i = targetIndex; i >= 0; i--) {
			if (count >= size) {
				return;
			}
			Node<?> node = nodes[i];
			if (node == null) {
				return;
			}
			count++;
			consumer.accept((Node<T>)node);
			if (i == ((curIndex + 1) % nodes.length)) {
				return;
			}
		}
		for (int i = nodes.length - 1; i > targetIndex; i--) {
			if (count >= size) {
				return;
			}
			Node<?> node = nodes[i];
			if (node == null) {
				return;
			}
			count++;
			consumer.accept((Node<T>)node);
			if (i == ((curIndex + 1) % nodes.length)) {
				return;
			}
		}
	}
	
	public List<Node<T>> loadNodeList(int size) {
		return loadNodeListByTargetIndex(curIndex, size);
	}
	
	public List<Node<T>> loadNodeList(int preId, int size) {
		int index = findIndex(nodes, preId, false);
		if (index < 0) {
			return new ArrayList<Node<T>>();
		}
		return loadNodeListByTargetIndex(index, size);
	}
	
	private List<Node<T>> loadNodeListByTargetIndex(int targetIndex, int size) {
		ListObject.clear();
		forEachNodeListByTargetIndex(targetIndex, size, (c) -> {
			ListObject.add(c);
		});
		return ListObject;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getMaxSize() {
		return nodes.length;
	}

	@Override
	public Iterator<Node<T>> iterator() {
		return new Itr();
	}


    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<Node<T>> {
        int cursor = curIndex;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        boolean isEnd = false;

        public boolean hasNext() {
        	if (isEnd) {
				return false;
			}
        	Node<?> node = nodes[cursor];
			return node != null;
        }

        @SuppressWarnings("unchecked")
		public Node<T> next() {
            int i = cursor;
            if (i < 0 || i >= nodes.length || !hasNext())
                throw new NoSuchElementException();
            cursor = (i - 1) % nodes.length;
            if (cursor < 0) {
				cursor = nodes.length - 1;
			}
            if (nodes[lastRet = cursor] == null || cursor == curIndex) {
				isEnd = true;
			}
            return (Node<T>)nodes[lastRet = i];
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
        public void forEachRemaining(Consumer<? super Node<T>> consumer) {
            Objects.requireNonNull(consumer);
            while (hasNext()) {
            	Node<T> node = next();
				consumer.accept(node);
			}
        }
    }
	
    public int getMaxId() {
    	return maxId;
    }
    
    public int getMinId() {
    	return minId;
    }
	
}
