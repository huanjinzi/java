# ByteArrayOutputStream
This class implements an output stream in which the data is written into a byte array. The buffer automatically grows as data is written to it. The data can be retrieved using toByteArray() and toString().

Closing a ByteArrayOutputStream has no effect. The methods in this class can be called after the stream has been closed without generating an IOException.

1. output data stores in a byte array.
2. the byte array is automatically grows.
3. byte data can be retrieved using toByteArray() and toString().
4. close() has no effect.after call close() method,you can still call read() and write(),and don't generate IOException.

Specifications

1. ByteArrayOutputStream's internal has a `byte[] buf` to store bytes written to it.
2. use `old_capacity << 2` to grow.
3. call `Array.copy()` return a copy of `buf`
4. it's nature a byte array,thereby read() and write() is not blocking the thread.

Algorithm
```java

/**
 * The maximum size of array to allocate.
 * Some VMs reserve some header words in an array.
 * Attempts to allocate larger arrays may result in
 * OutOfMemoryError: Requested array size exceeds VM limit
 */
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

private void grow(int minCapacity) {
        // overflow-conscious code
        // 1. size * 2
        int oldCapacity = buf.length;
        int newCapacity = oldCapacity << 1;

        // 2. not enough
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        // 3. boder condition
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);

        buf = Arrays.copyOf(buf, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```
