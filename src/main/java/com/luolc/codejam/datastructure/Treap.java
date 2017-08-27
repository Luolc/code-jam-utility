package com.luolc.codejam.datastructure;

import java.util.*;

/**
 * @author LuoLiangchen
 * @since 2017/7/22
 */
public class Treap<T> {
  private Random random = new Random();

  private Node<T> root;

  private Comparator<? super T> comparator;

  public Treap() {
  }

  public Treap(Comparator<? super T> comparator) {
    this.comparator = comparator;
  }

  public void insert(T item) {
    root = insert(root, item);
  }

  private Node<T> insert(Node<T> node, T item) {
    if (node == null) {
      node = new Node<>(item, random.nextInt());
    } else {
      if (compare(item, node.value) < 0) {
        node.left = insert(node.left, item);
        node.maintain();
        if (node.left.r > node.r) {
          node = rightRotate(node);
        }
      } else {
        node.right = insert(node.right, item);
        node.maintain();
        if (node.right.r > node.r) {
          node = leftRotate(node);
        }
      }
    }
    return node;
  }

  public void remove(T item) {
    root = remove(root, item);
  }

  private Node<T> remove(Node<T> node, T item) {
    final int cmp = compare(item, node.value);
    if (cmp == 0) {
      if (node.left == null) {
        node = node.right;
      } else if (node.right == null) {
        node = node.left;
      } else {
        if (node.left.r > node.right.r) {
          node = rightRotate(node);
          node.right = remove(node.right, item);
        } else {
          node = leftRotate(node);
          node.left = remove(node.left, item);
        }
      }
    } else if (cmp < 0) {
      node.left = remove(node.left, item);
    } else {
      node.right = remove(node.right, item);
    }
    node.maintain();
    return node;
  }

  public boolean contains(T item) {
    return contains(root, item);
  }

  private boolean contains(Node<T> node, T item) {
    while (node != null) {
      final int cmp = compare(item, node.value);
      if (cmp == 0) {
        return true;
      } else if (cmp < 0) {
        node = node.left;
      } else {
        node = node.right;
      }
    }
    return false;
  }

  public T kSmallest(int k) {
    return kSmallest(root, k);
  }

  private T kSmallest(Node<T> node, int k) {
    final int leftSize = node.left == null ? 0 : node.left.size;
    if (k == leftSize + 1) {
      return node.value;
    } else if (k <= leftSize) {
      return kSmallest(node.left, k);
    } else {
      return kSmallest(node.right, k - leftSize - 1);
    }
  }

  public Collection<T> kSmallestValues(int k) {
    return kSmallestValues(root, k);
  }

  private Collection<T> kSmallestValues(Node<T> node, int k) {
    if (node == null) {
      return Collections.emptyList();
    }
    final int leftSize = node.left == null ? 0 : node.left.size;
    if (k == leftSize + 1) {
      return values(node.left);
    } else if (k <= leftSize) {
      return kSmallestValues(node.left, k);
    } else {
      final Collection<T> values = new ArrayList<>(node.size);
      values.addAll(values(node.left));
      values.add(node.value);
      values.addAll(kSmallestValues(node.right, k - leftSize - 1));
      return values;
    }
  }

  /**
   * Gets the count of items which are less than the given item
   *
   * @param item the given item
   * @return the count
   */
  public int rank(T item) {
    return rank(root, item);
  }

  private int rank(Node<T> node, T item) {
    if (node == null) return 0;
    final int leftSize = node.left == null ? 0 : node.left.size;
    final int cmp = compare(item, node.value);
    if (cmp <= 0) {
      return rank(node.left, item);
    } else {
      return leftSize + 1 + rank(node.right, item);
    }
  }

  /**
   * Gets the count of items which are less than or equal to the given item
   *
   * @param item the given item
   * @return the count
   */
  public int rankInclusive(T item) {
    return rankInclusive(root, item);
  }

  private int rankInclusive(Node<T> node, T item) {
    if (node == null) return 0;
    final int leftSize = node.left == null ? 0 : node.left.size;
    final int cmp = compare(item, node.value);
    if (cmp < 0) {
      return rankInclusive(node.left, item);
    } else {
      return leftSize + 1 + rankInclusive(node.right, item);
    }
  }

  public Collection<T> values() {
    return values(root);
  }

  private Collection<T> values(Node<T> node) {
    if (node == null) {
      return Collections.emptyList();
    } else {
      final Collection<T> values = new ArrayList<>(node.size);
      values.addAll(values(node.left));
      values.add(node.value);
      values.addAll(values(node.right));
      return values;
    }
  }

  @SuppressWarnings("unchecked")
  private int compare(T o1, T o2) {
    if (comparator == null) {
      return ((Comparable<? super T>) o1).compareTo(o2);
    } else {
      return comparator.compare(o1, o2);
    }
  }

  private Node<T> leftRotate(Node<T> node) {
    final Node<T> result = node.right;
    node.right = result.left;
    result.left = node;
    node.maintain();
    result.maintain();
    return result;
  }

  private Node<T> rightRotate(Node<T> node) {
    final Node<T> result = node.left;
    node.left = result.right;
    result.right = node;
    node.maintain();
    result.maintain();
    return result;
  }

  public Iterator<T> iterator() {
    final TreapIterator<T> itr = new TreapIterator<>();
    itr.size = root == null ? 0 : root.size;
    itr.comparator = comparator;
    itr.index = 0;
    if (root != null) {
      Node<T> current = root;
      while (current.left != null) {
        itr.parents.push(current);
        current = current.left;
      }
      itr.self = current;
    }
    itr.parents.push(itr.self);
    itr.self = new Node<>();
    return itr;
  }

  private static class TreapIterator<T> implements Iterator<T> {
    private Stack<Node<T>> parents = new Stack<>();

    private Node<T> self;

    private int index;

    private int size;

    private Comparator<? super T> comparator;

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    public T next() {
      if (self.right != null) {
        parents.push(self);
        Node<T> current = self.right;
        while (current.left != null) {
          parents.push(current);
          current = current.left;
        }
        self = current;
      } else if (self.value == null) {
        self = parents.pop();
      } else {
        final T currentValue = self.value;
        while (compare(currentValue, parents.peek().value) > 0) {
          self = parents.pop();
        }
      }
      ++index;
      return self.value;
    }

    @SuppressWarnings("unchecked")
    private int compare(T o1, T o2) {
      if (comparator == null) {
        return ((Comparable<? super T>) o1).compareTo(o2);
      } else {
        return comparator.compare(o1, o2);
      }
    }
  }

  private static class Node<T> {
    private Node<T> left;

    private Node<T> right;

    private int r;

    private T value;

    private int size;

    private Node() {
    }

    private Node(T value, int r) {
      this.value = value;
      this.r = r;
      size = 1;
    }

    private void maintain() {
      size = 1;
      if (left != null) {
        size += left.size;
      }
      if (right != null) {
        size += right.size;
      }
    }
  }
}
