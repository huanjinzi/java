# DataOutputStream
A data output stream lets java application write primitive Java data type to an output stream in a portable way.
An application can use a data input stream read it back.

1.can write primitive Java data type to output stream,`int`,`boolean`,`char`,`long`,`float`,`double`,etc.
2.`>>>` unsigned right move,will move the most high bit to right.
3.`>>` is not move the most high bit.

Algorithm
```java

// write boolean and read it back.
public final void writeBoolean(boolean v) throws IOException {
        out.write(v ? 1 : 0);
        incCount(1);
}

public final boolean readBoolean() throws IOException {
        int ch = in.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
}

// write int and read it back
public final void writeInt(int v) throws IOException {
        out.write((v >>> 24) & 0xFF);
        out.write((v >>> 16) & 0xFF);
        out.write((v >>>  8) & 0xFF);
        out.write((v >>>  0) & 0xFF);
        incCount(4);
}

public final int readInt() throws IOException {
        int ch1 = in.read();
        int ch2 = in.read();
        int ch3 = in.read();
        int ch4 = in.read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
}

```