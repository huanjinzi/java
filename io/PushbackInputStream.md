# PushbackInputStream
it is convenient for a fragment code to read an indefinite number of data bytes that delimited by a particular byte value;
after reading the terminating byte,the code fragment can 'unread' it, so that the next read operation on the input stream will
 re-read the byte that was pushed back.

 For example, bytes representing the characters constituting an identifier might be terminated by a byte representing an operator charactor;
 a method who job is to read just an identifier can read until it sees the operator and then push the operator back to be re-read.

Specification

 1. decoding ASCII and BIG5 mix encoding bytes.
 2. scan "for(int i=0;i<10;i++)" from left to right, when got 'for', the parser has to see next byte,it's '(',so parser make sure identifier is 'for', and put '(' back for continue parse.

Algorithm

```java
// put the byte in the buffer.
public void unread(int b) throws IOException {
        ensureOpen();
        if (pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        buf[--pos] = (byte)b;
}
// every time read,find in the buffer first.
public int read() throws IOException {
        ensureOpen();
        if (pos < buf.length) {
            return buf[pos++] & 0xff;
        }
        return super.read();
}
```